package com.openclassrooms.safetynet.model;

import lombok.Data;

@Data
public class MedicalRecords {
	
	private Integer id;
	
	private String firstname;
	
	private String lastname;
	
	private Integer birthdate;
	
	private String medications;
	
	private String allergies;

}
