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
	
		/*
		 * from here we will get the user details by using JPA
		 */
		Optional<CustomUser> user =userRepository.findByUserName(username);
		
//		return new CustomUserDetails(user.get());
		
		/*
		 * or u can use
		 * here we are mapping the user details(Optional) into a UserDetails object
		 * so that AuthenticationProvider class can work with it.
		 */
		return user.map(CustomUserDetails::new).get();
	}

}
