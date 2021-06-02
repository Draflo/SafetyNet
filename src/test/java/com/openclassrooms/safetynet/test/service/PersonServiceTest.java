package com.openclassrooms.safetynet.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	
	private PersonService personService;

	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private FirestationService firestationService;
	
	@Mock
	private MedicalRecordService medicalRecordService;
	
	public static List<Person> personList = new ArrayList<>();
	
	static { 
		Person person = new Person();
		person.setId(1);
		person.setFirstName("John");
		Person person2 = new Person();
		person2.setId(2);
		person2.setFirstName("Jacob");
		personList.add(person);
		personList.add(person2);
	}
	
	@Test
	public void GetAll() {
		when(personRepository.findAll()).thenReturn(personList);
		
		Iterable<Person> foundList = personService.findAll();
		
		assertThat(foundList.toString().contains("John"));
	}
	
	// verifier que repository a bien été appelé
	
	// Vérifier que la personne qui a été appelé est bien la bonne
	
	// Vérifier l'erreur NoSuchElementException
}
