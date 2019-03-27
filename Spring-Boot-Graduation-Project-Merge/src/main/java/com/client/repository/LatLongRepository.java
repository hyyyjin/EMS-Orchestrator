package com.client.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.client.domain.LatLongDomain;

public interface LatLongRepository extends CrudRepository<LatLongDomain, Long> {

	public Optional<Long> findIdBySrcEMA(String srcEMA);
	
	public Optional<LatLongDomain> findBySrcEMA(String srcEMA);
}
