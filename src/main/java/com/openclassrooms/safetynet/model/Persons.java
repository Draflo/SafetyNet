package com.openclassrooms.safetynet.model;

import lombok.Data;

@Data
public class Persons {
	
	private Integer id;
	
	private String firstname;
	
	private String lastname;
	
	private String adress;
	
	private String city;
	
	private Integer zip;
	
	private Integer phone;
	
	private String email;
}
