package com.openclassrooms.safetynet.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class PersonByFirestationDTO {

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private Integer age;
	private Integer numberChild;
	private Integer numberAdult;

	/**
	 * Empty class constructor
	 */
	public PersonByFirestationDTO() {}

}
