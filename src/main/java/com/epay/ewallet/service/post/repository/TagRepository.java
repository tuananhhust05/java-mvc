package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Tag;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

	@Query("{'postId' : :#{#postId}, 'status' : :#{#status}}")
	List<Tag> findTagsByPostId(@Param("postId") String postId, @Param("status") String status);

}