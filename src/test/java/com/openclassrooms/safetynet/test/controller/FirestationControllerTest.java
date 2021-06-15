package com.openclassrooms.safetynet.test.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
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
	
	public static List<Person> personList = new ArrayList<>();

	static {
		personList.add(
				new Person(1, "John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"));
		personList.add(
				new Person(2, "Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "drk@email.com"));
		personList.add(
				new Person(3, "Tenley", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "tenz@email.com"));
	}
	
	public static List<Person> personList2 = new ArrayList<>();

	static {
		personList2.add(
				new Person(1, "Tessa", "Carman", "834 Binoc Ave", "Culver", 97451, "841-874-6512", "tenz@email.com"));
	}

	public static MedicalRecord mrJohn = new MedicalRecord(1L, "John", "Boyd", "03/06/1984",
			List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"));
	public static MedicalRecord mrJacob = new MedicalRecord(2L, "Jacob", "Boyd", "03/06/1989",
			List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), null);
	public static MedicalRecord mrTenley = new MedicalRecord(3L, "Tenley", "Boyd", "03/06/1989", null,
			List.of("peanut"));
	public static MedicalRecord mrTessa = new MedicalRecord(4L, "Tessa", "Carman", "02/18/2012", null,
			null);

	public static List<String> firestationList = new ArrayList<>();

	static {
		firestationList.add("1509 Culver St");
		firestationList.add("834 Binoc Ave");
	}
	
	@Test
	public void testGetAllFirestations() throws Exception {
		mockMvc.perform(get("/firestations")).andExpect(status().isOk());
	}
	
	@Test
	public void testGetAFirestationByID() throws Exception {
		Firestation firestation = new Firestation(1L, "1509 Culver St", 3);
		when(firestationService.getFirestation(1L)).thenReturn(Optional.of(firestation));
		mockMvc.perform(get("/firestation/1")).andExpect(status().isOk());
	}
	
	@Test
	public void testPostAFirestation() throws Exception {
		Firestation firestation = new Firestation();
		firestation.setAddress("TestAddress");
		mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(firestation))).andExpect(status().isCreated());
	}
	 
	@Test
	public void testPutAFirestation() throws Exception {
		Firestation firestation = new Firestation(1L, "1509 Culver St", 3);
		when(firestationService.getFirestation(1L)).thenReturn(Optional.of(firestation));
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
	
	@Test
	public void GetAllPersonsByStationNumber() throws Exception {
		when(personService.findByAddress("1509 Culver St")).thenReturn(personList);
		when(personService.findByAddress("834 Binoc Ave")).thenReturn(personList2);
		when(firestationService.findByStation(3)).thenReturn(firestationList);
		when(medicalRecordService.findByFirstNameAndLastName("John", "Boyd")).thenReturn(mrJohn);
		when(medicalRecordService.findByFirstNameAndLastName("Jacob", "Boyd")).thenReturn(mrJacob);
		when(medicalRecordService.findByFirstNameAndLastName("Tenley", "Boyd")).thenReturn(mrTenley);
		when(medicalRecordService.findByFirstNameAndLastName("Tessa", "Carman")).thenReturn(mrTessa);
		mockMvc.perform(get("/firestation?station=3")).andExpect(status().isOk())
				.andExpect(content().string(containsString("John")))
				.andExpect(content().string(containsString("Jacob")))
				.andExpect(content().string(containsString("Tenley")))
				.andExpect(content().string(containsString("Tessa")));

	}
}
