package com.client.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.client.domain.DataPointDomain;

public interface DataPointRepository extends CrudRepository<DataPointDomain, Long> {

	@Query(value="select * from data_point d where d.greedy = :greedy and d.knapsack = :knapsack" +
				" and d.time > :time limit :n", nativeQuery = true)
	Iterable<DataPointDomain> findLimitN(@Param("greedy")boolean greedy, @Param("knapsack")boolean knapsack,
			@Param("time")Timestamp time, @Param("n")int n);
}
