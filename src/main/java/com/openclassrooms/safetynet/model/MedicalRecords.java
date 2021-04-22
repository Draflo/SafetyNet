package com.openclassrooms.safetynet.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "medicalrecords")
public class MedicalRecords {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstname;
	private String lastname;
	private Integer birthdate;
	
	private List<String> medications;
	private List<String> allergies;
	
	public MedicalRecords () {}

}
