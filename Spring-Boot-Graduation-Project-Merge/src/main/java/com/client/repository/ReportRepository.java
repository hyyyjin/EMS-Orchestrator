package com.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.client.domain.ReportDomain;

public interface ReportRepository extends CrudRepository<ReportDomain, Long> {

	public Optional<ReportDomain> findFirstBySrcEMAOrderByIdDesc(String srcEMA);
	
	@Query(value="select * from Report r where r.report_name <> 'METADATA_TELEMETRY_USAGE' "
			+ "and r.srcema = :srcEMA and r.created_date_time > :time limit :n", nativeQuery = true)
	public Iterable<ReportDomain> findBySrcEMALimitN(@Param("srcEMA")String srcEMA, 
			@Param("time")String time, @Param("n")int n);
	
	public List<ReportDomain> findFirst20BySrcEMAOrderByIdDesc(String srcEMA);
	
	@Query(value="select * from report where id in (select max(id) from report "
			+ "where report_name <> 'METADATA_TELEMETRY_USAGE' group by srcema)", nativeQuery = true)
	public Iterable<ReportDomain> findAllLastRecords();
}
