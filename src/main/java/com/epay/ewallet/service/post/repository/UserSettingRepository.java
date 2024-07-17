package com.epay.ewallet.service.post.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.UserSetting;

@Repository
public interface UserSettingRepository extends MongoRepository<UserSetting, String> {

	@Query("{ 'userId' : ?0 }")
	UserSetting findSettingByUserId(@Param("userId") String userId);

}