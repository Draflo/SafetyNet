package com.openclassrooms.safetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Persons;
import com.openclassrooms.safetynet.service.PersonService;

@RestController
public class PersonsController {
	@Autowired
	private PersonService personService;

	public PersonsController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping
	public Iterable<Persons> list() {
		return personService.list();
	}

}
