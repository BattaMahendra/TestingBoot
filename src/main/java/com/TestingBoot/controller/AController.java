package com.TestingBoot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TestingBoot.entity.AnEntity;
import com.TestingBoot.jms.RedisPublisher;
import com.TestingBoot.service.AService;

@RestController
@RequestMapping("/app")
public class AController {
	@Autowired
	private AService aService;
	
	@Autowired
	private RedisPublisher redisPublisher; 
	
	@GetMapping("/g")
	public List<AnEntity> getAllValues(){
		return aService.getAllValues();
	}
	
	@GetMapping("/g/{id}")
//	@Cacheable(value = "employees",key = "#id")
	public AnEntity getById(@PathVariable int id){
		return aService.getById(id);
	}
	
	@PutMapping("/u")
	public AnEntity updateEntity(@RequestBody AnEntity a) {
		return aService.updateEntity(a);
	}
	
	@DeleteMapping("/d/{id}")
	public boolean deleteAnEntity(@PathVariable int id) {
		return aService.deleteEntity(id);
	}
	
	@PostMapping("/p")
	public AnEntity addAValue(@RequestBody AnEntity a) {
		return aService.addAVAlue(a);
	}
	
	@PostMapping("/publish")
	public AnEntity publishToRedis(@RequestBody AnEntity a) {
		return redisPublisher.publishMessage(a);
	}

}
