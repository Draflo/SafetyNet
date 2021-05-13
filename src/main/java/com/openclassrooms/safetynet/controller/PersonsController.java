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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;

@RestController
public class PersonsController {
	@Autowired
	private PersonService personService;

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
		return personService.list();

	}
	
	@GetMapping("/communityEmail")
	public Iterable<Person> getAllMailFromCity(@RequestParam String city) {
		return personService.getMailFromCity(city);
	}

	/**
	 * Read - Get one person
	 * 
	 * @param id The id of the person
	 * @return An person object full filled
	 */
	@GetMapping("/person/{id}")
	public Person getPerson(@PathVariable("id") final Long id) {
		Optional<Person> person = personService.getPersons(id);
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
	public Person createPerson(@RequestBody Person person) {
		return personService.savePerson(person);
	}

	/**
	 * Delete - Delete a person
	 * 
	 * @param id - The id of the person to delete
	 */
	@DeleteMapping("/person")
	public void deletePerson(@RequestParam("lastName") String lastName, @RequestParam("firstName") String firstName) {
		personService.deletePerson(lastName, firstName);
	}

	/**
	 * Update - Update an existing person
	 * 
	 * @param id     - The id of the person to update
	 * @param person - The person object updated
	 * @return
	 */
	@PutMapping("/person/{id}")
	public Person updatePerson(@PathVariable("id") final Long id, @RequestBody Person person) {
		Optional<Person> f = personService.getPersons(id);
		if (f.isPresent()) {
			Person currentPerson = f.get();

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
			return currentPerson;

		} else {
			return null;
		}
	}

}