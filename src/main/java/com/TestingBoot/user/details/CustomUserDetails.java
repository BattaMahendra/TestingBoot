package com.TestingBoot.user.details;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.TestingBoot.entity.CustomUser;
/*
 * in this class we map the values that we got from UserDetailsService and 
 * send back an instance of UserDetails
 */
public class CustomUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	
	
	public CustomUserDetails(CustomUser customUser) {
		this.userName=customUser.getUserName();
		this.password=customUser.getPassword();
		this.active=customUser.isActive();
		/*
		 * we have roles(as a string) in DB separated by commas
		 * we will split them into individual strings so that we can stream them to process
		 * Then also they are only strings not GrantedAuthority objects
		 * So we need to convert them to that object.
		 * we have a simple constructor new SimpleGrantedAuthority("ROLE_USER") which takes a string and convert that 
		 * role into one of GrantedAuthrity object and collects them in list
		 * 
		 */
		this.authorities=Arrays.stream(customUser.getRoles().split(","))
				/*
				 * here we are using constructor of simple granted authority as method referencing
				 * this constructor takes string as argument
				 * new SimpleGrantedAuthority("ROLE_USER")
				 */
									.map(SimpleGrantedAuthority::new)
									.collect(Collectors.toList());
									
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		/*
		 * the below commented code refers to hard coding the granted authority
		 */
//		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		
		return authorities;
		
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

}
