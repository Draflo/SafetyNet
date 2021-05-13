package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public Optional<Person> getPersons(final Long id) {
		return personRepository.findById(id);
	}
	
	public Person findByFirstNameAndLastName(String firstName, String lastName) {
		Optional<Person> person = personRepository.findByFirstNameAndLastName(firstName, lastName);
		
		return person.orElseThrow(()->new NoSuchElementException("Person doesn't exist"));
	}
	
	public Iterable<Person> getMailFromCity(String city) {
		Optional<Iterable<Person>> emails = personRepository.findMailFromCity(city);
		
		return emails.orElseThrow(()->new NoSuchElementException("No mail found for this city"));
	}

	public Iterable<Person> list() {
		return personRepository.findAll();
	}

	@Transactional
	public void deletePerson(final String lastName, final String firstName) {
		personRepository.delete(lastName, firstName);
	}

	public Iterable<Person> save(List<Person> persons) {
		return personRepository.saveAll(persons);
	}

	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public Person saveUpdated(Person person) {
		return personRepository.save(person);
	}
}
