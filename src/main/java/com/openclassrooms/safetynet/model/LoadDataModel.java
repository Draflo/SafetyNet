package com.openclassrooms.safetynet.model;

import java.util.List;

import lombok.Data;

@Data
public class LoadDataModel {
	
	private List<Firestation> firestations;
	private List<Persons> persons;
	private List<MedicalRecords> medicalrecords;
	
	public LoadDataModel() {}
}
