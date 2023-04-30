package com.TestingBoot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.TestingBoot.entity.AnEntity;
import com.TestingBoot.repo.AnJPARepo;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;



@Service
@Slf4j
public class AService {
	
	@Autowired
	private AnJPARepo anJPARepo;
	
	/*
	 * we generally uses parameter as key value , but when we don't have a parameter in a
	 * method then we can give root.methodName which sets as method name as key.
	 * 
	 * or u can use @Cacheable(value="someName")
	 */
	@Cacheable(value="someName" ,  key = "#root.methodName")
	public List<AnEntity> getAllValues(){
		return anJPARepo.findAll();
	}
	
	public AnEntity addAVAlue(AnEntity a) {
		return anJPARepo.save(a);
	
	}
	
	@Cacheable(value = "employees",key = "#id")
	public AnEntity getById(int id) {
		log.info("we are entering into service with id {}" , id);
		System.out.println("we are entering service");
		return anJPARepo.findById(id).get();
	}
	
	@CachePut(value = "employees")
	public AnEntity updateEntity(AnEntity anEntity) {
		if(getById(anEntity.getId())!=null) {
			return anJPARepo.save(anEntity);
		}
		return null;
	}
	@CacheEvict(value = "employees", allEntries = true)
	public boolean deleteEntity(int id) {
		if(getById(id)!=null) {
			anJPARepo.deleteById(id);
			return true;
		}
		return false;
	}

}
