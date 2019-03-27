package com.client.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.client.domain.ReportDescriptionDomain;

public interface ReportDescriptionRepository extends CrudRepository<ReportDescriptionDomain, Long> {

	@Query(value="select power from report_description where rid = :rID order by id desc limit 1", nativeQuery=true)
	public Optional<Double> findPowerByRID(@Param("rID")String rID); 
	
	@Query(value="SELECT * FROM report_description where id in "
			+ "(select max(id) from report_description where device_type = 'EMA' group by rid)",
			nativeQuery = true)
	public Iterable<ReportDescriptionDomain> findAllLastRecords();
}
