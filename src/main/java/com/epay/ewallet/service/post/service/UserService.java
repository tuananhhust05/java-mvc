package com.epay.ewallet.service.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epay.ewallet.service.post.mapperDataOne.IUser;
import com.epay.ewallet.service.post.model.User;

@Service
public class UserService {

	@Autowired
	private IUser userDao;

	public User getUser(String phone) {
		return userDao.getUserByPhone(phone);
	}
}
