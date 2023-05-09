package com.TestingBoot.user.details;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.TestingBoot.entity.CustomUser;
import com.TestingBoot.repo.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<CustomUser> user =userRepository.findByUserName(username);
		
//		return new CustomUserDetails(user.get());
		/*
		 * or u can use
		 */
		return user.map(CustomUserDetails::new).get();
	}

}
