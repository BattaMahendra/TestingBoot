package com.TestingBoot.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@javax.persistence.Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnEntity implements Serializable{
	
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String name ;
	

}
