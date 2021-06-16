package com.openclassrooms.safetynet.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;

@WebMvcTest(PersonService.class)
class PersonServiceTest {

	@Autowired
	private PersonService personService;

	@MockBean
	private PersonRepository personRepository;

	@MockBean
	private FirestationService firestationService;

	@MockBean
	private MedicalRecordService medicalRecordService;

	@MockBean
	private ObjectMapper objectMapper;

	public static List<Person> personList = new ArrayList<>();

	static {
		personList.add(
				new Person(1, "John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"));
		personList.add(
				new Person(2, "Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "drk@email.com"));
		personList.add(
				new Person(3, "Tenley", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "tenz@email.com"));
	}

	public static Person personTest = new Person();

	static {
		personTest.setId(1);
		personTest.setFirstName("John");
		personTest.setLastName("Boyd");
		personTest.setAddress("1509 Culver St");
		personTest.setCity("Culver");
		personTest.setZip(97451);
		personTest.setPhone("841-874-6512");
		personTest.setEmail("jaboyd@email.com");
	}

	@Test
	void GetAll() {
		when(personRepository.findAll()).thenReturn(personList);

		Iterable<Person> foundList = personService.findAll();

		verify(personRepository, times(1)).findAll();
		assertThat(foundList.toString().contains("John"));
		assertThat(foundList.toString().contains("Jacob"));
		assertThat(foundList.toString().contains("Tenley"));

	}

	@Test
	void GetAPersonByFirstNameAndLastName() {
		when(personRepository.findByFirstNameAndLastName("John", "Boyd")).thenReturn(Optional.of(personTest));

		Person expected = personService.findByFirstNameAndLastName("John", "Boyd");

		assertThat(expected.getFirstName()).isEqualTo("John");

	}

	@Test
	void TryingToFindAPersonNotRegisteredGetNoSuchElementException() throws NoSuchElementException {
		assertThrows(NoSuchElementException.class, () -> personService.findByFirstNameAndLastName("I don't", "Exist"));
	}
	
	@Test
	void TryingToFindPersonAtNonExistingAddressGetNoSuchElementException() throws NoSuchElementException {
		assertThrows(NoSuchElementException.class, () -> personService.findByAddress("I don't exist"));
	}
	
	@Test
	void TryingToFindAPhoneAtNonExistingStationGetNoSuchElementException() throws NoSuchElementException {
		assertThrows(NoSuchElementException.class, () -> personService.getPhoneByStation(8));
	}
	
	@Test
	void TryingToFindPersonsAtNonExistingStationGetNoSuchElementException() throws NoSuchElementException {
		assertThrows(NoSuchElementException.class, () -> personService.getPersonByStation(8));
	}
	
	@Test
	void TryingToFindMailsAtCityNotCoveredGetNoSuchElementException() throws NoSuchElementException {
		assertThrows(NoSuchElementException.class, () -> personService.getMailFromCity("I don't exist"));
	}

	@Test
	void CreateANewPerson() {
		Person personToCreate = new Person(2, "Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513",
				"drk@email.com");
		when(personRepository.save(personToCreate)).thenReturn(personToCreate);

		Person personCreated = personService.savePerson(personToCreate);

		assertThat(personCreated.getFirstName()).isEqualTo("Jacob");
		assertThat(personCreated.getLastName()).isEqualTo("Boyd");

	}

	@Test
	void UpdateAPerson() {
		Person personToUpdate = personTest;
		personToUpdate.setPhone("0505050505");
		personToUpdate.setEmail("updated.mail@mail.com");

		when(personRepository.save(personToUpdate)).thenReturn(personToUpdate);
		Person updatedPerson = personService.saveUpdated(personToUpdate);

		assertThat(updatedPerson.getFirstName()).isEqualTo(personToUpdate.getFirstName());
		assertThat(updatedPerson.getEmail()).isEqualTo(personToUpdate.getEmail());
		assertThat(updatedPerson.getPhone()).isEqualTo(personToUpdate.getPhone());
	}

	@Test
	void DeleteAPerson() throws NoSuchElementException {

		when(personRepository.findByFirstNameAndLastName("Boyd", "John")).thenReturn(Optional.of(personTest));
		personService.deletePerson("John", "Boyd");
		verify(personRepository, times(1)).delete("John", "Boyd");
	}

	@Test
	void FindPersonByAddress() {
		when(personRepository.findPersonByAddress("1509 Culver St")).thenReturn(Optional.of(personList));
		Iterable<Person> expectedPersons = personService.findByAddress("1509 Culver St");
		assertEquals(expectedPersons, personList);
	}

	@Test
	void FindPersonByStation() {
		when(personRepository.findPersonByStation(3)).thenReturn(Optional.of(personList));
		Iterable<Person> expectedPersons = personService.getPersonByStation(3);
		assertEquals(expectedPersons, personList);
	}

	@Test
	void FindPhoneByStation() {
		when(personRepository.findPhoneByStation(3)).thenReturn(Optional.of(personList));
		Iterable<Person> expectedPersons = personService.getPhoneByStation(3);
		assertEquals(expectedPersons, personList);
	}

	@Test
	void FindMailByCity() {
		when(personRepository.findMailFromCity("Culver")).thenReturn(Optional.of(personList));
		Iterable<Person> expectedPersons = personService.getMailFromCity("Culver");
		assertEquals(expectedPersons, personList);
	}

}
