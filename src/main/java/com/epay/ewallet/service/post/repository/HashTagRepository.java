package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.HashTag;

@Repository
public interface HashTagRepository extends MongoRepository<HashTag, String> {

	@Query("{ 'postId' : ?0 }")
	List<HashTag> findHashTagsByPostId(String postId);

}