package com.client.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.client.domain.DataPointDomain;
import com.client.domain.EmaDomain;
import com.client.domain.EventDomain;
import com.client.domain.LatLongDomain;
import com.client.domain.ProfileDomain;
import com.client.domain.ReportDomain;
import com.client.repository.DataPointRepository;
import com.client.repository.EmaRepository;
import com.client.repository.EventRepository;
import com.client.repository.LatLongRepository;
import com.client.repository.ProfileRepository;
import com.client.repository.ReportDescriptionRepository;
import com.client.repository.ReportRepository;
import com.client.structure.EmaTriple;

/*
 * implementation class of RepositoryService interface
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {
	@Autowired
	EmaRepository emaRepo;
	@Autowired
	ReportRepository reportRepo;
	@Autowired
	ReportDescriptionRepository descRepo;
	@Autowired
	EventRepository eventRepo;
	@Autowired
	ProfileRepository profileRepo;
	@Autowired
	LatLongRepository latLongRepo;
	@Autowired
	DataPointRepository dataRepo;

	@Override
	public void saveEma(EmaDomain ema) {
		// TODO Auto-generated method stub
		emaRepo.save(ema);
	}

	@Override
	public void saveProfile(ProfileDomain profile) {
		// TODO Auto-generated method stub
		profileRepo.save(profile);
	}
	
	@Override
	public void saveProfile(List<ProfileDomain> profiles) {
		// TODO Auto-generated method stub
		profileRepo.saveAll(profiles);
	}

	@Override
	public EmaDomain findEma(String srcEMA) {
		// TODO Auto-generated method stub
		Optional<EmaDomain> opt = emaRepo.findFirstBySrcEMAOrderByIdDesc(srcEMA);
		// return deep copy
		return opt.isPresent()? new EmaDomain(opt.get()): null;
	}

	@Override
	public List<ProfileDomain> findProfile(String srcEMA) {
		// TODO Auto-generated method stub
		Iterable<ProfileDomain> it = profileRepo.findAllBySrcEMA(srcEMA);
		if (!it.iterator().hasNext())
			return null;
		
		List<ProfileDomain> list = new ArrayList<>();
		for (ProfileDomain p: it) {
			list.add(p);
		}
		return list;
	}

	@Override
	public List<String> findEmaList() {
		// TODO Auto-generated method stub
		return emaRepo.findSrcema();
	}

	@Override
	public void saveEvent(List<EventDomain> events) {
		// TODO Auto-generated method stub
		eventRepo.saveAll(events);
	}

	@Override
	public void saveReport(List<ReportDomain> reports) {
		// TODO Auto-generated method stub
		reportRepo.saveAll(reports);
	}

	@Override
	public ReportDomain findReport(String srcEMA) {
		// TODO Auto-generated method stub
		Optional<ReportDomain> opt = reportRepo.findFirstBySrcEMAOrderByIdDesc(srcEMA);
		return opt.isPresent()? opt.get(): null;
	}

	@Override
	public EventDomain findEvent(String specificDestEMA) {
		// TODO Auto-generated method stub
		Optional<EventDomain> opt = eventRepo.findFirstBySpecificDestEMAOrderByIdDesc(specificDestEMA);
		return opt.isPresent()? opt.get(): null;
	}

	@Override
	public List<ReportDomain> findReportsLimitN(String srcEMA, int n, long millis) {
		// TODO Auto-generated method stub
		// if millis == 0, just find last 20 entities in DB
		if (millis == 0) {
			List<ReportDomain> list = reportRepo.findFirst20BySrcEMAOrderByIdDesc(srcEMA);
			list.sort((r1, r2) -> {
				return r1.getCreatedDateTime().compareTo(r2.getCreatedDateTime());
			});
			return list;
		}
		
		List<ReportDomain> list = new ArrayList<>(n);
		Timestamp time = new Timestamp(millis);
		// select where time > :time limit :n
		Iterable<ReportDomain> it = reportRepo.findBySrcEMALimitN(srcEMA, time.toString(), n);
		if (!it.iterator().hasNext())
			return null;
		for (ReportDomain r: it) {
			list.add(r);
		}
		return list;
	}

	@Override
	public void saveLatLong(LatLongDomain latLong) {
		// TODO Auto-generated method stub
		latLongRepo.save(latLong);
	}

	@Override
	public boolean hasLocation(String srcEMA) {
		// TODO Auto-generated method stub
		Optional<Long> id = latLongRepo.findIdBySrcEMA(srcEMA);
		return id.isPresent()? true: false;
	}

	@Override
	public List<LatLongDomain> findAllLocation() {
		// TODO Auto-generated method stub
		List<LatLongDomain> list = new ArrayList<>();
		Iterable<LatLongDomain> it = latLongRepo.findAll();
		for (LatLongDomain latLong: it) {
			list.add(latLong);
		}
		return list;
	}

	@Override
	public List<EmaTriple> findDefaultSet() {
		List<EmaTriple> list = new ArrayList<>();
		// default set(without algorithm) is from CLIENT_EMA06 to CLIENT_EMA10
		for (int i=6; i<=10; i++) {
			String srcEMA = String.format("CLIENT_EMA%02d", i);
			Optional<Double> opt1 = descRepo.findPowerByRID(srcEMA);
			Optional<EventDomain> opt2 = eventRepo.findFirstBySpecificDestEMAOrderByIdDesc(srcEMA);
			if (opt1.isPresent() && opt2.isPresent()) {
				double power = opt1.get();
				double price = opt2.get().getEventSignals().get(0).getPrice();
				list.add(new EmaTriple(srcEMA, power, price));
			}
		}
		
		return list;
	}
	
	@Override
	public List<EmaTriple> findGreedySet() {
		List<EmaTriple> list = new ArrayList<>();
		// from CLIENT_EMA11 to CLIENT_EMA15
		for (int i=11; i<=15; i++) {
			String srcEMA = String.format("CLIENT_EMA%02d", i);
			Optional<Double> opt1 = descRepo.findPowerByRID(srcEMA);
			Optional<EventDomain> opt2 = eventRepo.findFirstBySpecificDestEMAOrderByIdDesc(srcEMA);
			if (opt1.isPresent() && opt2.isPresent()) {
				double power = opt1.get();
				double price = opt2.get().getEventSignals().get(0).getPrice();
				list.add(new EmaTriple(srcEMA, power, price));
			}
		}
		
		return list;
	}
	
	@Override
	public List<EmaTriple> findKnapsackSet() {
		List<EmaTriple> list = new ArrayList<>();
		// from CLIENT_EMA16 to CLIENT_EMA20
		for (int i=16; i<=20; i++) {
			String srcEMA = String.format("CLIENT_EMA%02d", i);
			Optional<Double> opt1 = descRepo.findPowerByRID(srcEMA);
			Optional<EventDomain> opt2 = eventRepo.findFirstBySpecificDestEMAOrderByIdDesc(srcEMA);
			if (opt1.isPresent() && opt2.isPresent()) {
				double power = opt1.get();
				double price = opt2.get().getEventSignals().get(0).getPrice();
				list.add(new EmaTriple(srcEMA, power, price));
			}
		}
		
		return list;
	}

	@Override
	public void saveDataPoints(List<DataPointDomain> dataPoints) {
		// TODO Auto-generated method stub
		dataRepo.saveAll(dataPoints);
	}
	
	@Override
	public List<DataPointDomain> findDataPoints(int type, int n, long millis) {
		Iterable<DataPointDomain> it = null;
		Timestamp time = new Timestamp(millis);
		switch(type) {
		case 0:	// without algorithm(greedy = false, knapsack = false)
			it = dataRepo.findLimitN(false, false, time, n);
			break;
		case 1:	// greedy algorithm(greedy = true, knapsack = false)
			it = dataRepo.findLimitN(true, false, time, n);
			break;
		case 2:	// knapsack algorithm(greedy = false, knapsack = true)
			it = dataRepo.findLimitN(false, true, time, n);
			break;
		}
		if (!it.iterator().hasNext())
			return null;
		List<DataPointDomain> list = new ArrayList<>(n);
		for (DataPointDomain d: it) {
			list.add(d);
		}
		return list;
	}
}
