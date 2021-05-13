package com.openclassrooms.safetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.safetynet.DTO.PersonInfoDTO;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.util.AgeCalculator;

public class PersonInfoController {
	@Autowired
	private PersonService personService;
	@Autowired
	private MedicalRecordService medicalRecordService;
	@Autowired
	private AgeCalculator ageCalculator;

	public PersonInfoController(PersonService personService, MedicalRecordService medicalRecordService) {
		this.personService = personService;
		this.medicalRecordService = medicalRecordService;
	}
	
	@GetMapping("/personInfo")
	public PersonInfoDTO getPersonInfoDTO(@RequestParam String firstName, String lastName) throws Exception {
		Person person = personService.findByFirstNameAndLastName(firstName, lastName);
		MedicalRecord medicalrecord = medicalRecordService.findByFirstNameAndLastName(firstName, lastName);
		PersonInfoDTO personInfoDTO = new PersonInfoDTO();
		personInfoDTO.setFirstName(person.getFirstName());
		personInfoDTO.setLastName(person.getLastName());
		personInfoDTO.setAddress(person.getAddress());
		personInfoDTO.setEmail(person.getEmail());
		personInfoDTO.setAge(ageCalculator.calculateAge(medicalrecord.getBirthdate()));
		personInfoDTO.setMedications(medicalrecord.getMedications());
		personInfoDTO.setAllergies(medicalrecord.getAllergies());
		return personInfoDTO;
	}

}
