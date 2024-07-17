package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.UserGroup;

@Repository
public interface UserGroupRepository extends MongoRepository<UserGroup, String> {

	// @Query("{ 'userId' : ?0 }")
	// List<UserGroup> findRoleByUserId(@Param("userId") String userId);

	@Query("{'userId' : :#{#userId}}, 'groupId' : :#{#groupId}}")
	UserGroup findRoleByUserId(@Param("userId") String userId, @Param("groupId") String groupId);

	@Query("{ 'userId' : ?0 }")
	List<UserGroup> findRoleById(@Param("userId") String userId);

}