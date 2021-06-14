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

import com.openclassrooms.safetynet.controller.FireController;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.util.AgeCalculator;

@WebMvcTest(controllers = FireController.class)
public class FireControllerTest {
	
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
	
	@Test
	public void testFire() throws Exception {
		mockMvc.perform(get("/fire?address=951 LoneTree Rd")).andExpect(status().isOk());
	}
// Un autre test pour rentrer dans la boucle for
	
	@Test
	public void testFlood() throws Exception {
		mockMvc.perform(get("/flood?station=3")).andExpect(status().isOk());
	}
	
	@Test
	public void GetAllPersonsByStationAdress() throws Exception {
		when(personService.findByAddress("1509 CUlver St")).thenReturn(personList);
		mockMvc.perform(get("/fire?address=1509 Culver St")).andExpect(status().isOk())
		.andExpect(content().string("John"))
		.andExpect(content().string("Jacob"))
		.andExpect(content().string("Tenley"));
		
	}
	
	@Test
	public void GetAllPersonsByStationNumber() {
		
		
	}
}
