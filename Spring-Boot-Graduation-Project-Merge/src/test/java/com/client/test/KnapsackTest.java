package com.client.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.client.structure.EmaTriple;

public class KnapsackTest {
	
	public class Item implements Comparable<Item> {
		public double power;
		public double threshold;
		public int price;
		public boolean join;
		public boolean accepted;
		
		public Item() {
			this.accepted = true;
			this.join = true;
		}
		
		public Item(double threshold, int price) {
			this();
			this.threshold = threshold;
			this.price = price;
		}

		public Item(double power, double threshold, int price, boolean join, boolean accepted) {
			this.power = power;
			this.threshold = threshold;
			this.price = price;
			this.join = join;
			this.accepted = accepted;
		}

		@Override
		public int compareTo(Item o) {
			// TODO Auto-generated method stub
			return Double.compare(threshold, o.threshold);
		}
		
		public int compareToPrice(Item o) {
			// TODO Auto-generated method stub
			return Double.compare(this.price, o.price);
		}
	
	}
	
	public int size = 10;
	public double max = 500.0;
	public double total = 0;
	public double price = 0;
	public double clock = 0;

	private final double capacity = 400*3 + 450;
	private final double mag = 250; 
	private final double rate = 0.8;
	private int ang = 0;
	private boolean pass = false;
	
//	@Test
	public void demandResponse() {
		List<EmaTriple> list = new ArrayList<>();
		Random rand = new Random();
		//init
		for (int i=1; i<=3; i++) {
			String ema = String.format("CLIENT_EMA%02d", i);
			double power = 400;
			double price = (double) (rand.nextInt(6)+1) * 1000;
			System.out.format("%s %.2f %.2f\n", ema, power, price);
			list.add(new EmaTriple(ema, power, price));
		}
		//update
		try {
			while (true) {
				double current = capacity - mag*Math.sin(Math.toRadians(ang*18));
				ang = (ang+1)%20;
				
				double sum = 0, cost = 0;
				for (EmaTriple e: list) {
					sum += e.power;
					cost += e.power * e.price;
				}
				double per = sum / current;
				System.out.format("capacity: %.2f, sum: %.2f, percentage: %.2f%%, cost: %d\n", 
						current, sum, per * 100, (int) cost);
				
				if (pass) {
					pass = !pass;
					continue;
				}
				if (per > rate) {
					minKnapsack(list, current*rate);
				}
				Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("thread error");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("matrix error");
			e.printStackTrace();
		}
	}
	
	@Test
	public void geekMain() {
		int cost[] = {20, 10, 4, 50, 100}, W = 5; 
		List<EmaTriple> list=  new ArrayList<>();
		for (int i=1; i<=5; i++) {
			String ema = String.format("CLIENT_EMA%02d", i);
			double power = i;
			double price = cost[i-1];
			System.out.format("%s %.2f %.2f\n", ema, power, price);
			list.add(new EmaTriple(ema, power, price));
		}
	
		minKnapsack(list, W); 
	}
	
	public void minKnapsack(List<EmaTriple> list, double bound) {
		int n = list.size(), W = (int) Math.ceil(bound);
		int[] val = new int[n+1];
		int[] wt = new int[n+1];
		
		int min_cost[][] = new int[n+1][W+1]; 
		
		for (int i=0; i<n; i++) {
			EmaTriple e = list.get(i);
			val[i+1] = (int) e.price;
			wt[i+1] = (int) Math.ceil(e.power * 0.8);
		}
	       
//		// fill 0th row with infinity 
//		for (int i = 0; i <= W; i++) 
//			min_cost[0][i] = Integer.MAX_VALUE; 
//
//		// fill 0'th column with 0 
//		for (int i = 1; i <= n; i++) 
//			min_cost[i][0] = 0; 
       
        // now check for each weight one by one and 
        // fill the matrix according to the condition 
        for (int i = 1; i <= n; i++) 
        { 
            for (int j = 1; j <= W; j++) 
            { 
                // wt[i-1]>j means capacity of bag is 
                // less then weight of item 
                if (wt[i] > j) 
                    min_cost[i][j] = min_cost[i-1][j]; 
       
                // here we check we get minimum cost  
                // either by including it or excluding it 
                else
                    min_cost[i][j] = Math.max(min_cost[i-1][j], min_cost[i-1][j-wt[i]] + val[i]); 
            } 
        }
        for (int i=0; i<=n; i++) {
        	for (int j=0; j<=W; j++) {
        		System.out.print(min_cost[i][j] + " ");
        	}
        	System.out.println();
        }
       
        // exactly weight W can not be made by  
        // given weights 
        System.out.println((min_cost[n][W] == Integer.MAX_VALUE)? -1: min_cost[n][W]); 
		
	}
	
	public void print() {
		System.out.format("%.2f%% \n", 80.45324);
	}
	
	public void init(List<Item> list) {
		Random rand = new Random();
		
		for(int i=1; i<=size; i++) {
			Item item = new Item(40, rand.nextInt(50)*10 + 10);
			list.add(item);
		}
	}
	
	public void result(List<Item> list) {
		price = total = 0;
		for(Item i : list) {
			System.out.format("threshold: %.2f, price: %d, join: %s\n", i.threshold, i.price, i.join);
			total += i.threshold;
			price += i.price * i.threshold;
		}
		System.out.format("power: %.2f, max: %.2f, percentage: %.2f, price: %.2f\n\n", total, max, (total/max)*100, price);
	}
	
	public void update(List<Item> list) {
		Random rand = new Random();
		total = 0;
		max = 500 + 20*Math.sin(clock);
		clock += Math.toRadians(10);
		
		for (Item item: list) {
			item.join = rand.nextBoolean();
			item.threshold += rand.nextDouble();
			if (item.threshold < 0) item.threshold = 0;
			total += item.threshold;
		}
	}
	
	public void main() {
		test1();
//		test2();
	}
	
	public void test1() {
		List<Item> itemList = new ArrayList<>();
		init(itemList);
		
		try {
			while(true) {
				update(itemList);
				System.out.println("after update");
				result(itemList);
				double old = price;
				if (total > max * rate) {
					List<Item> emaList = new ArrayList<>();
					// step1 all EMAs
					emaList.addAll(itemList);
					
					// sort by threshold asc
					emaList.sort((i1, i2) -> {
						return i1.compareTo(i2);
					});
					// filtering (can reduce or not)
					while (emaList.get(0).threshold < (total - max * 0.75) / emaList.size())
						emaList.remove(0);
					// step2 divide excess by n
					for(Item i : emaList) {
						i.threshold -= (total - max * 0.75) / emaList.size();
					}
					
					double limit = 0;
					// sort by price asc
					emaList.sort((i1, i2) -> {
						return i1.compareToPrice(i2);
					});
					// step3 greedy (1-1)
					for(Item i : emaList) {
						if (i.threshold + limit < (max * 0.75)) {
							i.accepted = true;
							limit += i.threshold;
						}
						else {
							i.accepted = false;
						}
					}
					total = limit;
				}
				System.out.println("after DR");
				result(itemList);
				double newP = price;
				System.out.format("saved price: %.2f\n", old - newP);
				System.out.println("=====================================");
				
				Thread.sleep(2000);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void test2() {
		List<Item> itemList = new ArrayList<>();
		init(itemList);
		
		try {
			while(true) {
				update(itemList);
				System.out.println("after update");
				result(itemList);
				double old = price;
				if (total > max * rate) {
					List<Item> emaList = new ArrayList<>();
					double subTotal = 0;
					// step1 Candidates
					for(Item i : itemList) {
						if (i.join) {
							emaList.add(i);
							subTotal += i.threshold;
						}
					}
					
					// step2 percentage
					for(Item i : emaList) {
						i.threshold -= (total - (max * 0.75)) * i.threshold / subTotal;
					}
					
					double limit = 0;
					// sort by price asc
					emaList.sort((i1, i2) -> {
						return i1.compareToPrice(i2);
					});
					// step3 greedy (1-1)
					for(Item i : emaList) {
						if (i.threshold + limit < (max * 0.75)) {
							i.accepted = true;
							limit += i.threshold;
						}
						else {
							i.accepted = false;
						}
					}
				}
				System.out.println("after DR");
				result(itemList);
				double newP = price;
				System.out.format("saved price: %.2f\n", old - newP);
				System.out.println("=====================================");
				
				Thread.sleep(2000);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
//	public void step1_1() {
//		for (EMA e : emaList) {
//			
//		}
//	}
//	
//	public void step1_2() {
//		for (int i = 0; i < emaList.size; i++) {
//			if (e.getJoin) {
//				
//			}
//		}
//	}
//	
//	public void step1_3() {
//		for (int i = 0; i < emaOrderList.size; i++) {
//				
//			
//		}
//	}
//	
//	public void step2_1(double weight) {
//		double threshold = weight / emaList.size;
//	}
//	
//	public void step2_2(double weight) {
//		double threshold = weight * percentage;
//	}
//	
//	public void step3_1() {
//		sort by price desc
//	}
//	
//	public void step3_2() {
//		sort by price asc
//	}
//	
//	public void step4_1() {
//		greedy
//	}
//	
//	public void step4_2() {
//		knapsack
//	}

}
