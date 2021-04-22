package com.openclassrooms.safetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.MedicalRecords;
import com.openclassrooms.safetynet.service.MedicalRecordService;

@RestController
public class MedicalRecordsController {
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	public MedicalRecordsController(MedicalRecordService medicalRecordService) {
		this.medicalRecordService = medicalRecordService;
	}
	
	@GetMapping
	public Iterable<MedicalRecords> list() {
		return medicalRecordService.list();
	}

}
