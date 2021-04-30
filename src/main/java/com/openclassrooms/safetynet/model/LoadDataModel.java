package com.openclassrooms.safetynet.model;

import java.util.List;

import lombok.Data;

@Data
public class LoadDataModel {
	
	private List<Firestation> firestations;
	private List<Person> persons;
	private List<MedicalRecord> medicalrecords;
	
	public LoadDataModel() {}
}
