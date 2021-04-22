package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Persons;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import com.openclassrooms.safetynet.repository.PersonsRepository;

import lombok.Data;

@Data
@Service
public class PersonService {

	@Autowired
	private PersonsRepository personsRepository;

	public Optional<Persons> getPersons(final Long id) {
		return personsRepository.findById(id);
	}

	public Iterable<Persons> list() {
		return personsRepository.findAll();
	}

	public void deletePersons(final Long id) {
		personsRepository.deleteById(id);
	}

	public Iterable<Persons> save(List<Persons> persons) {
		return personsRepository.saveAll(persons);
	}

}
