package com.TestingBoot;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeAll;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.TestingBoot.entity.AnEntity;
import com.TestingBoot.repo.AnJPARepo;
import com.TestingBoot.service.AService;

@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {
	
	@InjectMocks
	AService aservice;
	
	@MockBean
	 AnJPARepo anJPARepo;
	
	private  List<AnEntity> list=new ArrayList<>();
	
	@BeforeAll
	public void setUp() {
		
		AnEntity obj1=new AnEntity(1, "Ramesh");
		AnEntity obj2=new AnEntity(1, "Suresh");
		AnEntity obj3=new AnEntity(1, "Naresh");
		AnEntity obj4=new AnEntity(1, "Sarvesh");
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		list.add(obj4);
			
	}
	
	@org.junit.jupiter.api.Test
	public void getAllTest() {
		when(anJPARepo.findAll()).thenReturn(list);
		aservice.getAllValues();
		
	}

}
