package com.openclassrooms.safetynet.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fire {

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private Integer age;
	private Integer firestation;
	private List<String> medications;
	private List<String> allergies;

	public Fire() {
	}
}
