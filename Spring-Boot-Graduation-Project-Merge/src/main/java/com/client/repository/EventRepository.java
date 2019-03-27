package com.client.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.client.domain.EventDomain;
import com.client.structure.EmaTriple;

public interface EventRepository extends CrudRepository<EventDomain, Long> {

	public Optional<EventDomain> findFirstBySpecificDestEMAOrderByIdDesc(String specificDestEMA);
	
	@Query(value="select e.specific_destema as ema, es.price from event e left outer join event_signals es "
			+ "on e.id = es.event_id where e.id in (select max(id) from event group by specific_destema)",
			nativeQuery = true)
	public Iterable<EmaTriple> findAllEmaAndPrice();
	
	@Query(value="select * from event where id in (select max(id) from event group by specific_destema)",
			nativeQuery = true)
	public Iterable<EventDomain> findAllLastRecords();
}
