package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Media;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {

	@Query("{ 'objectId' : ?0 }")
	List<Media> findMediaListPostByPostId(String postId);

	@Query("{'referenceId' : :#{#referenceId}, 'status' : :#{#status}, 'type' : :#{#type}}")
	List<Media> findMediaListById(@Param("referenceId") String referenceId, @Param("status") String status,
			@Param("type") String type);
}