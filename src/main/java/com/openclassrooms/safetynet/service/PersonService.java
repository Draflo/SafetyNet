package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FirestationRepository;
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

	public Iterable<Person> list() {
		return personRepository.findAll();
	}

	public void deletePersons(final Long id) {
		personRepository.deleteById(id);
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
