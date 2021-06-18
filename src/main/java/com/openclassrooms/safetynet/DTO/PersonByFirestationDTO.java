package com.openclassrooms.safetynet.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
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
