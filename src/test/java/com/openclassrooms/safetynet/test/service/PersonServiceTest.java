package com.openclassrooms.safetynet.test.service;

import org.mockito.Mock;

import com.openclassrooms.safetynet.repository.PersonRepository;

public class PersonServiceTest {

	@Mock
	PersonRepository personRepository;
	// verifier que repository a bien été appelé
	
	// Vérifier que la personne qui a été appelé est bien la bonne
	
	// Vérifier l'erreur NoSuchElementException
}
