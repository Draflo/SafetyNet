package com.openclassrooms.safetynet.controller;

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

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;

@RestController
public class PersonsController {
	@Autowired
	private PersonService personService;
	
	@ExceptionHandler(Exception.class)
	public void handleExeption() {}
	
	private static final Logger logger = LogManager.getLogger(PersonsController.class);

	public PersonsController(PersonService personService) {
		this.personService = personService;
	}

	/**
	 * Read - Get all persons
	 * 
	 * @return - An Iterable object of Person full filled
	 */
	@GetMapping("/persons")
	public Iterable<Person> list() {
		logger.info("PersonsController (GET) Getting all persons");
		Iterable<Person> findAll = personService.findAll();
		logger.info("Persons List : {}", findAll);
		return findAll;

	}
	
	@GetMapping("/communityEmail")
	public Iterable<Person> getAllMailFromCity(@RequestParam String city) {
		logger.info("PersonsController (GET) Getting all emails from city: " + city);
		Iterable<Person> mailFromCity = personService.getMailFromCity(city);
		logger.info("Emails List : {}", mailFromCity);
		return mailFromCity;
	}
	
	@GetMapping("/phoneAlert")
	public Iterable<Person> getPhoneNumberFromStation(@RequestParam(value = "firestation") Integer station) {
		logger.info("PersonsController (GET) Getting all phone numbers from firestation number: " + station);
		Iterable<Person> phoneByStation = personService.getPhoneByStation(station);
		logger.info("Phones List : {}", phoneByStation);
		return phoneByStation;
	}

	/**
	 * Read - Get one person
	 * 
	 * @param id The id of the person
	 * @return A person object full filled
	 */
	@GetMapping("/person/{id}")
	public Person getPerson(@PathVariable("id") final Long id) {
		logger.info("PersonsController (GET) Getting the person with ID number" + id);
		Optional<Person> person = personService.getPersons(id);
		logger.info("Person : {}", person);
		if (person.isPresent()) {
			return person.get();
		} else {
			return null;
		}
	}

	/**
	 * Create - Add a new person
	 * 
	 * @param Person An object person
	 * @return The person object saved
	 */
	@PostMapping("/person")
	public ResponseEntity<Person> createPerson(@RequestBody Person person) {
		Person savePerson = personService.savePerson(person);
		logger.info("PersonsController (POST) Person added to Database : {}", savePerson);
		return ResponseEntity.created(null).body(savePerson);
	}

	/**
	 * Delete - Delete a person
	 * 
	 * @param id - The id of the person to delete
	 */
	@DeleteMapping("/person")
	public void deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		personService.deletePerson(firstName, lastName);
		logger.info("PersonsController (DEL) Person deleted from Database");
	}

	/**
	 * Update - Update an existing person
	 * 
	 * @param id     - The id of the person to update
	 * @param person - The person object updated
	 * @return
	 */
	@PutMapping("/person")
	public Person updatePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestBody Person person) {
		Person f = personService.findByFirstNameAndLastName(firstName, lastName);
			Person currentPerson = f;

			String address = person.getAddress();
			if (address != null) {
				currentPerson.setAddress(address);
			}
			String city = person.getCity();
			if (city != null) {
				currentPerson.setCity(city);
			}
			Integer zip = person.getZip();
			if (zip != null) {
				currentPerson.setZip(zip);
			}
			String phone = person.getPhone();
			if (phone != null) {
				currentPerson.setPhone(phone);
			}
			String email = person.getEmail();
			if (email != null) {
				currentPerson.setEmail(email);
			}

			personService.savePerson(currentPerson);
			logger.info("PersonsController (PUT) Person updated in Database" + currentPerson.toString());
			return currentPerson;

	}

}