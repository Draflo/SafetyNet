package com.openclassrooms.safetynet.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.openclassrooms.safetynet.model.converter.ListStringConverter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "medicalrecords")
public class MedicalRecords {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "first_name")
	private String firstname;
	
	@Column(name = "last_name")
	private String lastname;
	
	@Column(name = "birthdate")
	private Integer birthdate;
	
	@Column(name = "medications")
	@Convert(converter = ListStringConverter.class)
	private List<String> medications;
	
	@Column(name = "allergies")
	@Convert(converter = ListStringConverter.class)
	private List<String> allergies;
	

	public MedicalRecords() {
	}

}
