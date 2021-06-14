package com.openclassrooms.safetynet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.Fire;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.util.AgeCalculator;

@RestController
public class FireController {
	@Autowired
	private PersonService personService;
	@Autowired
	private MedicalRecordService medicalRecordService;
	@Autowired
	private FirestationService firestationService;
	@Autowired
	private AgeCalculator ageCalculator;

	public FireController(PersonService personService, MedicalRecordService medicalRecordService,
			FirestationService firestationService) {
		this.personService = personService;
		this.medicalRecordService = medicalRecordService;
		this.firestationService = firestationService;
	}

	@GetMapping("/fire")
	public Iterable<Fire> getFire(@RequestParam String address) throws Exception {
		Firestation firestation = firestationService.findByAddress(address);
		Iterable<Person> persons = personService.findByAddress(address);
		List<Fire> fire = new ArrayList<>();
		for (Person person : persons) {
			Fire fireperson = new Fire();
			fireperson.setFirstName(person.getFirstName());
			fireperson.setLastName(person.getLastName());
			MedicalRecord medicalrecord = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
			fireperson.setPhone(person.getPhone());
			fireperson.setAge(ageCalculator.calculateAge(medicalrecord.getBirthdate()));
			fireperson.setMedications(medicalrecord.getMedications());
			fireperson.setAllergies(medicalrecord.getAllergies());
			fireperson.setFirestation(firestation.getStation());
			fire.add(fireperson);
		}
		return fire;
	}
	
	@GetMapping("/flood")
	public Iterable<Fire> getFlood(@RequestParam Integer station) throws Exception {
		Iterable<String> address = firestationService.findByStation(station);
		List<Fire> flood = new ArrayList<>();
		for (String string : address) {
		Iterable<Person> persons = personService.findByAddress(string);
		for (Person person : persons) {
			Fire floodperson = new Fire();
			floodperson.setFirstName(person.getFirstName());
			floodperson.setLastName(person.getLastName());
			MedicalRecord medicalrecord = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
			floodperson.setPhone(person.getPhone());
			floodperson.setAge(ageCalculator.calculateAge(medicalrecord.getBirthdate()));
			floodperson.setMedications(medicalrecord.getMedications());
			floodperson.setAllergies(medicalrecord.getAllergies());
			flood.add(floodperson);
		}
		}
		return flood;
	}

}