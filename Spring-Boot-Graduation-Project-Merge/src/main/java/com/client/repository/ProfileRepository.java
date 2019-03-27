package com.client.repository;

import org.springframework.data.repository.CrudRepository;

import com.client.domain.ProfileDomain;

public interface ProfileRepository extends CrudRepository<ProfileDomain, Long> {

	public Iterable<ProfileDomain> findAllBySrcEMA(String srcEMA);

}
