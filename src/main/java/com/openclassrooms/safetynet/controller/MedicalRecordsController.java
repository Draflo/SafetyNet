package com.openclassrooms.safetynet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;

@RestController
public class MedicalRecordsController {
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	public MedicalRecordsController(MedicalRecordService medicalRecordService) {
		this.medicalRecordService = medicalRecordService;
	}
	
	/**
	 * Read - Get all medicalRecords
	 * 
	 * @return - An Iterable object of MedicalRecord full filled
	 */
	@GetMapping("/medicalrecords")
	public Iterable<MedicalRecord> list() {
		return medicalRecordService.list();

	}

	/**
	 * Read - Get one medicalRecord
	 * 
	 * @param id The id of the medicalRecord
	 * @return An medicalRecord object full filled
	 */
	@GetMapping("/medicalrecord/{id}")
	public MedicalRecord getMedicalRecord(@PathVariable("id") final Long id) {
		Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecords(id);
		if (medicalRecord.isPresent()) {
			return medicalRecord.get();
		} else {
			return null;
		}
	}

	/**
	 * Create - Add a new medicalRecord
	 * 
	 * @param MedicalRecord An object medicalRecord
	 * @return The medicalRecord object saved
	 */
	@PostMapping("/medicalRecord")
	public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		return medicalRecordService.saveMedicalRecord(medicalRecord);
	}

	/**
	 * Delete - Delete an employee
	 * 
	 * @param id - The id of the employee to delete
	 */
	@DeleteMapping("/medicalRecord/{id}")
	public void deleteMedicalRecord(@PathVariable("id") final Long id) {
		medicalRecordService.deleteMedicalRecords(id);
	}

	/**
	 * Update - Update an existing medicalRecord
	 * 
	 * @param id       - The id of the medicalRecord to update
	 * @param employee - The medicalRecord object updated
	 * @return
	 */
	@PutMapping("/medicalRecord/{id}")
	public MedicalRecord updateMedicalRecord(@PathVariable("id") final Long id, @RequestBody MedicalRecord medicalRecord) {
		Optional<MedicalRecord> mr = medicalRecordService.getMedicalRecords(id);
		if (mr.isPresent()) {
			MedicalRecord currentMedicalRecord = mr.get();

			List<String> medications = medicalRecord.getMedications();
			if (medications != null) {
				currentMedicalRecord.setMedications(medications);
			}
			List<String> allergies = medicalRecord.getAllergies();
			if (allergies != null) {
				currentMedicalRecord.setAllergies(allergies);
			}

			medicalRecordService.saveMedicalRecord(currentMedicalRecord);
			return currentMedicalRecord;

		} else {
			return null;
		}
	}
}