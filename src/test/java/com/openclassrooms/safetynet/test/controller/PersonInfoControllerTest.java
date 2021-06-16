package com.openclassrooms.safetynet.test.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.safetynet.DTO.PersonInfoDTO;
import com.openclassrooms.safetynet.controller.PersonInfoController;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.util.AgeCalculator;

@WebMvcTest(controllers = PersonInfoController.class)
class PersonInfoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonService personService;

	@MockBean
	private FirestationService firestationService;

	@MockBean
	private MedicalRecordService medicalRecordService;

	@MockBean
	private AgeCalculator ageCalculator;
	
	public static List<Person> personList = new ArrayList<>();

	static {
		personList.add(
				new Person(1, "John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"));
		personList.add(
				new Person(2, "Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "drk@email.com"));
		personList.add(
				new Person(3, "Tenley", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "tenz@email.com"));
	}
	
	public static MedicalRecord mrJohn = new MedicalRecord(1L, "John", "Boyd", "03/06/1984",
			List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"));
	public static MedicalRecord mrJacob = new MedicalRecord(2L, "Jacob", "Boyd", "03/06/1989",
			List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), null);
	public static MedicalRecord mrTenley = new MedicalRecord(3L, "Tenley", "Boyd", "03/06/1989", null,
			List.of("peanut"));

	@Test
	void testGetPersonInfo() throws Exception {
		PersonInfoDTO personInfoDTO = new PersonInfoDTO();
		Person person = new Person();
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("Test");
		medicalRecord.setLastName("Name");
		person.setFirstName("Test");
		person.setLastName("Name");
		personInfoDTO.setFirstName(person.getFirstName());
		personInfoDTO.setLastName(person.getLastName());
		personInfoDTO.setAddress("TestAddress");
		personInfoDTO.setAge(8);
		medicalRecord.setMedications(null);
		medicalRecord.setAllergies(null);
		when(personService.findByFirstNameAndLastName("Test", "Name")).thenReturn(person);
		when(medicalRecordService.findByFirstNameAndLastName("Test", "Name")).thenReturn(medicalRecord);
		mockMvc.perform(get("/personInfo?firstName=Test&lastName=Name")).andExpect(status().isOk());

	}

	@Test
	void testChildAlert() throws Exception {
		when(personService.findByAddress("1509 Culver St")).thenReturn(personList);
		when(medicalRecordService.findByFirstNameAndLastName("John", "Boyd")).thenReturn(mrJohn);
		when(medicalRecordService.findByFirstNameAndLastName("Jacob", "Boyd")).thenReturn(mrJacob);
		when(medicalRecordService.findByFirstNameAndLastName("Tenley", "Boyd")).thenReturn(mrTenley);
		mockMvc.perform(get("/childAlert?address=1509 Culver St")).andExpect(status().isOk())
		.andExpect(content().string(containsString("John")))
		.andExpect(content().string(containsString("Jacob")))
		.andExpect(content().string(containsString("Tenley")));;
	}
}
