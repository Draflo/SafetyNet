package com.openclassrooms.safetynet.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;

@RestController
public class MedicalRecordsController {
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@ExceptionHandler(Exception.class)
	public void handleExeption() {}
	
	private static final Logger logger = LogManager.getLogger(MedicalRecordsController.class);
	
	public MedicalRecordsController(MedicalRecordService medicalRecordService) {
		this.medicalRecordService = medicalRecordService;
	}
	
	/**
	 * Read - Get all medicalRecords
	 * 
	 * @return - An Iterable object of MedicalRecord full filled
	 */
	@GetMapping("/medicalRecords")
	public Iterable<MedicalRecord> list() {
		logger.info("MedicalRecordController (GET) Getting all medicalrecords");
		Iterable<MedicalRecord> list = medicalRecordService.list();
		logger.info("Medical Records List : {}", list);
		return list;

	}

	/**
	 * Read - Get one medicalRecord
	 * 
	 * @param id The id of the medicalRecord
	 * @return An medicalRecord object full filled
	 */
	@GetMapping("/medicalRecord/{id}")
	public MedicalRecord getMedicalRecord(@PathVariable("id") final Long id) {
		logger.info("MedicalRecordController (GET) Getting the medicalrecord with ID number" + id);
		Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecords(id);
		logger.info("Medical Record : {}", medicalRecord);
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
	public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		MedicalRecord saveMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
		logger.info("MedicalRecordController (POST) Medicalrecord added to Database: {}", saveMedicalRecord);
		return ResponseEntity.created(null).body(saveMedicalRecord);
	}

	/**
	 * Delete - Delete an employee
	 * 
	 * @param id - The id of the employee to delete
	 */
	@DeleteMapping("/medicalRecord")
	public void deleteMedicalRecord(@RequestParam("lastName") String lastName, @RequestParam("firstName") String firstName) {
		medicalRecordService.deleteMedicalRecord(lastName, firstName);
		logger.info("MedicalRecordController (DEL) Medicalrecord deleted from Database");
	}

	/**
	 * Update - Update an existing medicalRecord
	 * 
	 * @param id       - The id of the medicalRecord to update
	 * @param employee - The medicalRecord object updated
	 * @return
	 */
	@PutMapping("/medicalRecord")
	public MedicalRecord updateMedicalRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestBody MedicalRecord medicalRecord) {
		MedicalRecord mr = medicalRecordService.findByFirstNameAndLastName(firstName, lastName);
			MedicalRecord currentMedicalRecord = mr;

			List<String> medications = medicalRecord.getMedications();
			if (medications != null) {
				currentMedicalRecord.setMedications(medications);
			}
			List<String> allergies = medicalRecord.getAllergies();
			if (allergies != null) {
				currentMedicalRecord.setAllergies(allergies);
			}

			medicalRecordService.saveMedicalRecord(currentMedicalRecord);
			logger.info("MedicalRecordController (PUT) Medicalrecord updated in Database: {}", currentMedicalRecord);
			return currentMedicalRecord;

	}
}