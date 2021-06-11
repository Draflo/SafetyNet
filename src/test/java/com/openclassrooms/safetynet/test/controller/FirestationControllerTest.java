package com.openclassrooms.safetynet.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.DTO.PersonByFirestation;
import com.openclassrooms.safetynet.controller.FirestationController;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.util.AgeCalculator;

@WebMvcTest(controllers = FirestationController.class)
public class FirestationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FirestationService firestationService;
	
	@MockBean
	private PersonService personService;
	
	@MockBean
	private MedicalRecordService medicalRecordService;
	
	@MockBean
	private AgeCalculator ageCalculator;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	public void testGetAllFirestations() throws Exception {
		mockMvc.perform(get("/firestations")).andExpect(status().isOk());
	}
	
	@Test
	public void testPostAFirestation() throws Exception {
		Firestation firestation = new Firestation();
		firestation.setAddress("TestAddress");
		mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(firestation))).andExpect(status().isCreated());
	}
	 
	@Test
	public void testPutAFirestation() throws Exception {
		Firestation firestation = new Firestation();
		firestation.setAddress("TestAddress");
		mockMvc.perform(put("/firestation/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(firestation))).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteAFirestation() throws Exception {
		Firestation firestation = new Firestation();
		firestation.setAddress("WillBeDeleted");
		mockMvc.perform(delete("/firestation?address=WillBeDeleted").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(firestation))).andExpect(status().isOk());
	}
	
	@Test
	public void testGetAllPersonsByStation() throws Exception {
		PersonByFirestation personByFirestation = new PersonByFirestation();
		personByFirestation.setNumberAdult(5);
		personByFirestation.setNumberChild(1);
		mockMvc.perform(get("/firestation?station=1")).andExpect(status().isOk());
		
	}
}
