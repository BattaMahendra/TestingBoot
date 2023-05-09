package com.TestingBoot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TestingBoot.entity.AnEntity;
import com.TestingBoot.entity.CustomUser;
import com.TestingBoot.jms.RedisPublisher;
import com.TestingBoot.repo.UserRepository;
import com.TestingBoot.service.AService;

@RestController
@RequestMapping("/app")
@RefreshScope
public class AController {
	@Autowired
	private AService aService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private RedisPublisher redisPublisher; 
	
	@Value("${testing.value}")
	private int myValue;
	
	@Value("${cars}")
	private List<String> listOfCars;
	
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
	
	/*
	 * when ever you want to get config values dynamically while running the application , you need to hit the actuator endpoint
	 * i.e localhost:8080/actuator/refresh in post request , this will update the values dynamically into ur application values.
	 * if you don't do that then u need to restart this client app every time to see dynamically updated config files.
	 * 
	 * suppose you change some values in github config files while this application is running. Then those values are not updated in the 
	 * application dynamically. In order to get values without restarting the server you need to use above mentioned process. And for this 
	 * process to work you need to keep @RefreshScope in the class where the values are being updated.
	 * 
	 * 
	 */
	@GetMapping("/values")
	public List<Object> getvaluesFromExternalProps() {
		System.out.println("enterd into values endpoint");
		List<Object> listOfValues=new ArrayList<>();
		listOfValues.add(Integer.toString(myValue));
		if(listOfCars.isEmpty()) return listOfValues; else listOfValues.addAll(listOfCars);
		return listOfValues;
	}
	
/*
 * in the get mapping we have two end point urls.
 * suppose if user don't pass the userName along with url then the 
 * first end point is taken and if user passes path variable then second end point 
 * with path variable is triggered.
 */
	@GetMapping(value={"/welcome","/welcome/{userName}"})
	public String welcomeMessage(@PathVariable Optional<String> userName) {
	
		/*
		 * used ternary operator to filter out optional userName coming form path variable
		 */
//		CustomUser user = new CustomUser();
//		user.setActive(true);
//		user.setRoles("ROLES_ADMIN");
//		user.setPassword("12345");
//		user.setUserName("admin");
//		userRepository.save(user);
		return userName
				.isPresent() ?
				 ("<h1>Hey "+userName.get()+"<br>welcome to TestingBoot app</h1>")
				:("<h1>Hey Mahendra <br> welcome to TestingBoot app</h1>");
	}

}
