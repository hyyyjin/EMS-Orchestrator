package com.client.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.client.domain.DataPointDomain;
import com.client.structure.EmaTriple;

@Component
public class DRAlgorithm {
	@Autowired
	MessageService msgService;
	@Autowired
	RepositoryService repoService;
	
	private final double capacity = 400 * 5;	// base capacity of each set
	private final double mag = 200;		// magnitude of sing wave
	private final double rate = 0.8;	// warning flag
	private int ang = 0;	// angle for sine wave
	
	@Scheduled(fixedRate = 3000, initialDelay = 3000)
	@Async("backgroundTaskExecutor")
	public void demandResponse() {
		// capacity changes every three seconds (sine wave)
		double current = capacity + mag*Math.sin(Math.toRadians(ang*18));
		ang = (ang+1)%20;
		
		// EMA comparison set
		// triple is (srcEMA, power, price) structure
		List<EmaTriple> defaultSet = repoService.findDefaultSet();	// none: 	EMA06 ~ EMA10
		List<EmaTriple> greedySet = repoService.findGreedySet();	// greedy: 	EMA11 ~ EMA15
		List<EmaTriple> knapsackSet = repoService.findKnapsackSet();// knapsack:EMA16 ~ EMA20
		
		// sum of each set
		double defaultSum=0, greedySum=0, knapsackSum=0;
		for (EmaTriple e: defaultSet) {
			defaultSum += e.power;
		}
		for (EmaTriple e: greedySet) {
			greedySum += e.power;
		}
		for (EmaTriple e: knapsackSet) {
			knapsackSum += e.power;
		}
		
		// save current power and sum
		// this DB table (data_point) is different with report table.  
		List<DataPointDomain> dataPoints = new ArrayList<>();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		dataPoints.add(new DataPointDomain(time, current, defaultSum, false, false));
		dataPoints.add(new DataPointDomain(time, current, greedySum, true, false));
		dataPoints.add(new DataPointDomain(time, current, knapsackSum, false, true));
		repoService.saveDataPoints(dataPoints);
		
		// if percentage is more than 80%, execute algorithm
		if (greedySum / current > rate)
			greedy(greedySet, current, greedySum - current * rate);
		if (knapsackSum / current > rate)
			greedy(knapsackSet, current, knapsackSum - current * rate);
		
	}

	@Async("backgroundTaskExecutor")
	public void greedy(List<EmaTriple> list, double cur, double over) {

		// filtering
		//sort by power
		list.sort((e1, e2) -> {
			return Double.compare(e1.power, e2.power);
		});
		// remove EMA whose power is less than over/n (mean value of over)
		while (list.get(0).power < over / list.size())
			list.remove(0);
		
		// sort by (unit)price
		list.sort((e1, e2) -> {
			return Double.compare(e1.price, e2.price);
		});
		
		// greedy
		// select cheaper EMA until saved power is less than over
		double saved = 0;
		List<EmaTriple> selected = new ArrayList<>(list.size());
		for(EmaTriple e : list) {
			if (saved < over) {
				// select EMA
				double n = e.power * 0.2;
				e.power -= n;
				saved += n;
				selected.add(e);	// remember selected EMA
			}
			else{
				//nothing
				break;
			}
		}
		for (EmaTriple e: selected) {
			msgService.distributeEvent(e.ema, new Timestamp(System.currentTimeMillis()).toString(), e.power);
		}
	}

	@Async("backgroundTaskExecutor")
	public void knapsack(List<EmaTriple> list, double cur, double over) {

		// filtering
		//sort by power
		list.sort((e1, e2) -> {
			return Double.compare(e1.power, e2.power);
		});
		// remove EMA whose power is less than over/n
		while (list.get(0).power < over / list.size())
			list.remove(0);
		
		// cannot apply knapsack
		// if minimum power is more than over, 
		if (over < list.get(0).power * 0.2) {
			EmaTriple e = list.get(0);
			msgService.distributeEvent(e.ema, new Timestamp(System.currentTimeMillis()).toString(), e.power*0.8);
			return ;
		}
		
		// array and matrix for knapsack
		// result of knapsack is equal or less than W because W is upper bound.
		// therefore unlike greedy, we cannot decrease more than over. 
		// so I set W as (over x 1.5)
		int W = (int) Math.ceil(over*1.5);	// weight of bag
		int[] v = new int[list.size()+1];	// array of value
		int[] wt = new int[list.size()+1];	// array of weight
		int[][] m = new int[list.size()+1][W+1];	// DP matrix
		// init
		for (int i=0; i<list.size(); i++) {
			EmaTriple e = list.get(i);
			double n = e.power * 0.2;
			v[i+1] = (int) (e.price * n); // total price (power * unit price)
			wt[i+1] = (int) Math.ceil(n);
		}
		// knapsack
		// current situation: cur - over = 80%
		// Price(cur) is fixed, if maximize Price(over), can minimize Price(80%)
		// to maximize Price(over), use dynamic programming
		for (int i=1; i<=list.size(); i++) {	/* i: item index */
			for (int j=1; j<=W; j++) {	/* j: bag weight */
				// i-th item weight is more than bag weight, cannot put item
				// copy previous optimized data
				if (wt[i] > j)
					m[i][j] = m[i-1][j];
				// can put item into bag, compare two cases and get max one.
				// 1. previous data without i-th item
				// 2. previous data + i-th item
				else
					m[i][j] = Math.max(m[i-1][j], m[i-1][j-wt[i]] + v[i]);
			}
		}
		// backtracking
		List<EmaTriple> selected = new ArrayList<>(list.size());
		// start from last cell m[n][W]
		int res = m[list.size()][W];
		for (int line=W, i=list.size(); i>0 && line>0; i--) {
			// if value came from above cell, i-th item hasn't selected
			if (res == m[i-1][line]) {
				// nothing
				continue;
			}
			else {
				selected.add(list.get(i-1));
				// move to next line
				res -= v[i-1];
				line -= wt[i-1];
			}
		}
		for (EmaTriple e: selected) {
			msgService.distributeEvent(e.ema, new Timestamp(System.currentTimeMillis()).toString(), e.power*0.8);
		}
	}
	
}
