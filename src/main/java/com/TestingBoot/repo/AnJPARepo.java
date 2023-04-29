package com.TestingBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TestingBoot.entity.AnEntity;
@Repository
public interface AnJPARepo extends JpaRepository<AnEntity, Integer>{

}
