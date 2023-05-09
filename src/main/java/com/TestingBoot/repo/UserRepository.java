package com.TestingBoot.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TestingBoot.entity.CustomUser;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Integer> {
	
	Optional<CustomUser> findByUserName(String userName);

}
