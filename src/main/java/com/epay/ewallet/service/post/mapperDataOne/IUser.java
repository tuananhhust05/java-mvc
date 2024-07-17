package com.epay.ewallet.service.post.mapperDataOne;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.epay.ewallet.service.post.model.User;

@Mapper
public interface IUser {
	Map loadUserByPhone(String phone);

	User getUserByPhone(String phone);

	User getUserById(String userId);

	List<User> getUserByName(String name, int des, int src);

	User getUserByUserId(long id);
	
	List<User> getListUserById(Map<String, Object> map);
}
