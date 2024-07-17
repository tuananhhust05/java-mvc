package com.epay.ewallet.service.post.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

	@Query("{ 'id' : ?0 }")
	Category findCategoryById(String id);

}