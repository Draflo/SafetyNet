package com.openclassrooms.safetynet.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.safetynet.controller.FireController;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;

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
	
	@Test
	private void testFire() throws Exception {
		mockMvc.perform(get("/fire?address=951 LoneTree Rd")).andExpect(status().isOk());
	}

}
