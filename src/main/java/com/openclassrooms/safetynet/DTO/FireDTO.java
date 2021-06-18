package com.openclassrooms.safetynet.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FireDTO {

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private Integer age;
	private Integer firestation;
	private List<String> medications;
	private List<String> allergies;

	/**
	 * Empty class constructor
	 */
	public FireDTO() {
	}
}
