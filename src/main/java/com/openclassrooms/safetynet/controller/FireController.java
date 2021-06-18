package com.openclassrooms.safetynet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.FireDTO;
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
	
	@ExceptionHandler(Exception.class)
	public void handleExeption() {}
	
	private static final Logger logger = LogManager.getLogger(FireController.class);

	public FireController(PersonService personService, MedicalRecordService medicalRecordService,
			FirestationService firestationService) {
		this.personService = personService;
		this.medicalRecordService = medicalRecordService;
		this.firestationService = firestationService;
	}

	@GetMapping("/fire")
	public Iterable<FireDTO> getFire(@RequestParam String address) throws NoSuchElementException {
		logger.info("FireController (GET) : Getting all persons covered by a firestation at this address" + address);
		Firestation firestation = firestationService.findByAddress(address);
		Iterable<Person> persons = personService.findByAddress(address);
		List<FireDTO> fire = new ArrayList<>();
		for (Person person : persons) {
			FireDTO fireperson = new FireDTO();
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
		logger.info("List of persons covered by a firestation at this address : {}", fire);
		return fire;
	}
	
	@GetMapping("/flood")
	public Iterable<FireDTO> getFlood(@RequestParam Integer station) throws NoSuchElementException {
		logger.info("FireController (GET) : Getting all persons covered by firestation number" + station);
		Iterable<String> address = firestationService.findByStation(station);
		List<FireDTO> flood = new ArrayList<>();
		for (String string : address) {
		Iterable<Person> persons = personService.findByAddress(string);
		for (Person person : persons) {
			FireDTO floodperson = new FireDTO();
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
		logger.info("List of all persons covered by firestation number : {}", flood);
		return flood;
	}

}