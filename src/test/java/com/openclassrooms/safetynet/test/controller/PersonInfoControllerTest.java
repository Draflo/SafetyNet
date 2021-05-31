package com.openclassrooms.safetynet.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class PersonInfoControllerTest {

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

	@Test
	private void testGetPersonInfo() throws Exception {
		PersonInfoDTO personInfoDTO = new PersonInfoDTO();
		Person person = new Person();
		MedicalRecord medicalRecord = new MedicalRecord();
		person.setFirstName("Test");
		person.setLastName("Name");
		medicalRecord.setBirthdate("03/03/1980");
		personInfoDTO.setFirstName(person.getFirstName());
		personInfoDTO.setLastName(person.getLastName());
		personInfoDTO.setAge(ageCalculator.calculateAge(medicalRecord.getBirthdate()));
		mockMvc.perform(get("/personInfo?firstName=Test&lastName=Name")).andExpect(status().isOk());
	}

	@Test
	private void testChildAlert() throws Exception {
		PersonInfoDTO personInfoDTO = new PersonInfoDTO();
		personInfoDTO.setAddress("TestAddress");
		personInfoDTO.setAge(8);
		mockMvc.perform(get("/childAlert?address=TestAddress")).andExpect(status().isOk());
	}
}
