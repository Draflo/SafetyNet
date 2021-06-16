package com.openclassrooms.safetynet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.PersonInfoDTO;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.util.AgeCalculator;

@RestController
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
	public PersonInfoDTO getPersonInfoDTO(@RequestParam String firstName, String lastName) throws NoSuchElementException {
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

	
	@GetMapping("/childAlert")
	public Iterable<PersonInfoDTO> getChildAlert(@RequestParam String address) throws NoSuchElementException {
		Iterable<Person> persons = personService.findByAddress(address);
		List<PersonInfoDTO> childAlert = new ArrayList<>();
		for (Person person : persons) {
			PersonInfoDTO child = new PersonInfoDTO();
			MedicalRecord medicalrecord = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(),
					person.getLastName());
			if (ageCalculator.calculateAge(medicalrecord.getBirthdate()) < 18) {
				child.setFirstName(person.getFirstName());
				child.setLastName(person.getLastName());
				child.setAge(ageCalculator.calculateAge(medicalrecord.getBirthdate()));
				childAlert.add(child);
			}
		}
		if (childAlert.isEmpty()) {
			return childAlert;
			
		} else {

			for (Person person : persons) {
				PersonInfoDTO adult = new PersonInfoDTO();
				MedicalRecord medicalrecord = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(),
						person.getLastName());
				if (ageCalculator.calculateAge(medicalrecord.getBirthdate()) >= 18) {
					adult.setFirstName(person.getFirstName());
					adult.setLastName(person.getLastName());
					childAlert.add(adult);
				}
			}
			return childAlert;
		}

	}
}
