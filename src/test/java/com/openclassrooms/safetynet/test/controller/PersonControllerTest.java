package com.openclassrooms.safetynet.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.controller.PersonsController;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;

@WebMvcTest(controllers = PersonsController.class)
class PersonControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PersonService personService;
	
	@MockBean
	private FirestationService firestationService;
	
	@MockBean
	private MedicalRecordService medicalRecordService;
	
	@Test
	void testGetAllPersons() throws Exception {
		mockMvc.perform(get("/persons")).andExpect(status().isOk());
	}
	
	@Test
	void testPostAPerson() throws Exception {
		Person person = new Person();
		person.setLastName("TestName");
		mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(person))).andExpect(status().isCreated());
	}
	 
	@Test
	void testPutAPerson() throws Exception {
		Person person = new Person(1, "John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
		when(personService.getPersons(1L)).thenReturn(Optional.of(person));
		mockMvc.perform(put("/person?firstName=John&lastName=Boyd").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(person))).andExpect(status().isOk());
	}
	
	@Test
	void testGetAPersonByID() throws Exception {
		Person person = new Person(1, "John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
		when(personService.getPersons(1L)).thenReturn(Optional.of(person));
		mockMvc.perform(get("/person/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(person))).andExpect(status().isOk());
	}
	
	@Test
	void testDeleteAPerson() throws Exception {
		Person person = new Person();
		person.setFirstName("WillBe");
		person.setLastName("Deleted");
		mockMvc.perform(delete("/person?firstName=WillBe&lastName=Deleted").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(person))).andExpect(status().isOk());
	}
	
	@Test
	void testGetAllMailAddressesByCity() throws Exception {
		mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk());
	}
	
	@Test
	void testGetAllPhoneNumberByStation() throws Exception {
		mockMvc.perform(get("/phoneAlert?firestation=1")).andExpect(status().isOk());
	}

}
