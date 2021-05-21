package com.openclassrooms.safetynet.DTO;

import lombok.Data;

@Data
public class PersonByFirestation {

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private Integer age;
	private Integer numberChild;
	private Integer numberAdult;

	
	public PersonByFirestation() {}

}
