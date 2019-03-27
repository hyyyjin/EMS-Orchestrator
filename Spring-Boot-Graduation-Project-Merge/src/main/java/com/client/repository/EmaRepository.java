package com.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.client.domain.EmaDomain;

public interface EmaRepository extends CrudRepository<EmaDomain, Long> {

	public Optional<EmaDomain> findFirstBySrcEMAOrderByIdDesc(String srcEMA);
		
	@Query("select srcEMA from Ema group by srcEMA")
	public List<String> findSrcema();
}
