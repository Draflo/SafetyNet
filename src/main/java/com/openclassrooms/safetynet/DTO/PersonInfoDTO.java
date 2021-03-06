package com.openclassrooms.safetynet.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonInfoDTO {

	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private Integer age;
	private List<String> medications;
	private List<String> allergies;

	/**
	 * Empty class constructor
	 */
	public PersonInfoDTO() {
	}
}
