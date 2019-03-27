package com.client.repository;

import org.springframework.data.repository.CrudRepository;

import com.client.domain.EventSignalsDomain;

public interface EventSignalsRepository extends CrudRepository<EventSignalsDomain, Long> {

}
