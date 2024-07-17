package com.epay.ewallet.service.post.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {

	@Query("{ 'id' : ?0 }")
	Role findRoleNameById(@Param("id") int id);

}