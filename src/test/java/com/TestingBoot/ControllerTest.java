package com.TestingBoot;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.TestingBoot.controller.AController;
import com.TestingBoot.entity.AnEntity;
import com.TestingBoot.service.AService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType;

//@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@WebMvcTest(controllers =AController.class )
//@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {
	@Autowired
//@ExtendWith(SpringExtension.class)
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper =new ObjectMapper();
	private ObjectWriter objectWriter= objectMapper.writer();
	
	@InjectMocks
	private AController aController;
	
	@MockBean
	 AService aService;
	
	private  List<AnEntity> list=new ArrayList<>();
	
	@BeforeAll
	public  void setUp() {
		
		AnEntity obj1=new AnEntity(1, "Ramesh");
		AnEntity obj2=new AnEntity(1, "Suresh");
		AnEntity obj3=new AnEntity(1, "Naresh");
		AnEntity obj4=new AnEntity(1, "Sarvesh");
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		list.add(obj4);
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		when(aService.getAllValues()).thenReturn(list); 
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/app/g")
					.contentType(MediaType.APPLICATION_JSON))		
					.andExpect(status().isOk())
//					.andExpect(MockMvcResultMatchers.jsonPath("$",is(list.)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(list.size())));
		
	}
	
	@Test
	public void createTest()  {
		
		AnEntity obj1=new AnEntity();
		obj1=list.get(0);
		
		
//		when(aService.addAVAlue(any(AnEntity.class))).thenReturn(obj1);
		
		
		try {
			this.mockMvc.perform(post("/app/p")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectWriter.writeValueAsString(obj1)))
//						.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$", isNotNull()))
						.andExpect(status().isCreated());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
