package com.epay.ewallet.service.post.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epay.ewallet.service.post.mapperDataOne.IUser;
import com.epay.ewallet.service.post.model.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private IUser userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = userDao.getUserByPhone(username);
		if (user == null) {
			return null;
//			throw new UsernameNotFoundException("User " + username + " can not be found!");
		}
		String userName = user.getPhoneNumber();
		String pass = user.getPassword();

		return new org.springframework.security.core.userdetails.User(userName, pass, new ArrayList<>());
	}

}
