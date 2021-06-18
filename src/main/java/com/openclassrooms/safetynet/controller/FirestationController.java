package com.openclassrooms.safetynet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

import com.openclassrooms.safetynet.DTO.PersonByFirestationDTO;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.util.AgeCalculator;

@RestController
public class FirestationController {

	@Autowired
	private FirestationService firestationService;
	@Autowired
	private PersonService personService;
	@Autowired
	private MedicalRecordService medicalRecordService;
	@Autowired
	private AgeCalculator ageCalculator;
	
	@ExceptionHandler(Exception.class)
	public void handleExeption() {}
	
	private static final Logger logger = LogManager.getLogger(FirestationController.class);

	public FirestationController(FirestationService firestationService, PersonService personService,
			MedicalRecordService medicalRecordService) {
		this.firestationService = firestationService;
		this.medicalRecordService = medicalRecordService;
		this.personService = personService;
	}

	private Integer child = 0;
	private Integer adult = 0;

	/**
	 * Read - Get all firestations
	 * 
	 * @return - An Iterable object of Firestation full filled
	 */
	@GetMapping("/firestations")
	public Iterable<Firestation> list() {
		logger.info("FirestationController (GET) Getting all firestations");
		Iterable<Firestation> findAll = firestationService.getAll();
		logger.info("Firestations List : {}", findAll);
		return findAll;

	}

	/**
	 * Read - Get one firestation
	 * 
	 * @param id The id of the firestation
	 * @return An firestation object full filled
	 */
	@GetMapping("/firestation/{id}")
	public Firestation getFirestation(@PathVariable("id") final Long id) {
		logger.info("FirestationController (GET) Getting the firestation with ID number" + id);
		Optional<Firestation> firestation = firestationService.getFirestation(id);
		logger.info("Firestation : {}", firestation);
		if (firestation.isPresent()) {
			return firestation.get();
		} else {
			return null;
		}
	}

	/**
	 * Gets all persons covered by station number
	 * 
	 * @param station
	 * @return persons covered by the station number
	 * @throws NoSuchElementException
	 */
	
	@GetMapping("/firestation")
	public Iterable<PersonByFirestationDTO> getPersonByFirestation(@RequestParam Integer station) throws NoSuchElementException {
		logger.info("FirestationController (GET) Getting all persons covered by firestation number " + station);
		Iterable<String> firestation = firestationService.findByStation(station);
		List<PersonByFirestationDTO> personByFirestations = new ArrayList<>();
		for (String string : firestation) {
		Iterable<Person> persons = personService.findByAddress(string);
		for (Person person : persons) {
			PersonByFirestationDTO personByFirestation = new PersonByFirestationDTO();
			personByFirestation.setFirstName(person.getFirstName());
			personByFirestation.setLastName(person.getLastName());
			personByFirestation.setAddress(person.getAddress());
			personByFirestation.setPhone(person.getPhone());
			MedicalRecord medicalrecord = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
			personByFirestation.setAge(ageCalculator.calculateAge((medicalrecord).getBirthdate()));
		if (personByFirestation.getAge() < 18)
			child++;
		else
			adult++;
		personByFirestations.add(personByFirestation);
		}
		}
		logger.info("List of persons covered by this station : {}", personByFirestations);
		PersonByFirestationDTO personByFirestation = new PersonByFirestationDTO();
		personByFirestation.setNumberChild(child);
		personByFirestation.setNumberAdult(adult);
		personByFirestations.add(personByFirestation);
		
		return personByFirestations;
	
		}

	/**
	 * Create - Add a new firestation
	 * 
	 * @param Firestation An object firestation
	 * @return The firestation object saved
	 */
	@PostMapping("/firestation")
	public ResponseEntity<Firestation> createFirestation(@RequestBody Firestation firestation) {
		Firestation saveFirestation = firestationService.saveFirestation(firestation);
		logger.info("FirestationController (POST) Firestation added to Database: {}", saveFirestation);
		return ResponseEntity.created(null).body(saveFirestation);
	}

	/**
	 * Delete - Delete a firestation
	 * 
	 * @param id - The id of the firestation to delete
	 */
	@DeleteMapping("/firestation")
	public void deleteFirestation(@RequestParam("address") String address) {
		firestationService.deleteFirestation(address);
		//logger.info("FirestationController (DEL) Firestation deleted from Database");
	}

	/**
	 * Update - Update an existing firestation
	 * 
	 * @param id          - The id of the firestation to update
	 * @param firestation - The firestation object updated
	 * @return
	 */
	@PutMapping("/firestation")
	public Firestation updateFirestation(@RequestParam("id") final Long id, @RequestBody Firestation firestation) {
		Optional<Firestation> f = firestationService.getFirestation(id);
		if (f.isPresent()) {
			Firestation currentFirestation = f.get();

			String address = firestation.getAddress();
			if (address != null) {
				currentFirestation.setAddress(address);
			}
			Integer station = firestation.getStation();
			if (station != null) {
				currentFirestation.setStation(station);
			}

			firestationService.saveFirestation(currentFirestation);
			logger.info("Firestation updated : {}", currentFirestation);
			return currentFirestation;

		} else {
			return null;
		}
	}
}