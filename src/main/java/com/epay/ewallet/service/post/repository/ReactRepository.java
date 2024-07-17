package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.React;

@Repository
public interface ReactRepository extends MongoRepository<React, String> {

//	@Query("{ 'referenceId' : ?0 }")
//	List<React> findReactsById(String id);

	@Query("{'referenceId' : :#{#referenceId}, 'status' : :#{#status}}")
	List<React> findReactsById(@Param("referenceId") String referenceId, @Param("status") String status);

	@Query("{'referenceId' : :#{#referenceId}, 'status' : :#{#status}, 'userId' : :#{#userId}}")
	List<React> findReactPostByUserId(@Param("referenceId") String referenceId, @Param("status") String status,
			@Param("userId") String userId);

}