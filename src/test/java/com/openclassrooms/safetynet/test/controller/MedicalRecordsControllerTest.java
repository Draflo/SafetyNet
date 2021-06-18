package com.openclassrooms.safetynet.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.controller.MedicalRecordsController;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;

@WebMvcTest(controllers = MedicalRecordsController.class)
class MedicalRecordsControllerTest {
	
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
	
	public static MedicalRecord mrJohn = new MedicalRecord(1L, "John", "Boyd", "03/06/1984",
			List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"));
	
	@Test
	void testGetAllMedicalRecords() throws Exception {
		mockMvc.perform(get("/medicalRecords")).andExpect(status().isOk());
	}
	
	@Test
	void testPostAMedicalRecord() throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName("TestName");
		mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(medicalRecord))).andExpect(status().isCreated());
	}
	@Test
	void testPutAMedicalRecord() throws Exception {
		when(medicalRecordService.findByFirstNameAndLastName("John", "Boyd")).thenReturn(mrJohn);
		mockMvc.perform(put("/medicalRecord?firstName=John&lastName=Boyd").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(mrJohn))).andExpect(status().isOk());
	}
	
	@Test
	void testGetAMedicalRecordByID() throws Exception {
		mockMvc.perform(get("/medicalRecord/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(mrJohn))).andExpect(status().isOk());
	}
	
	@Test
	void testDeleteAMedicalRecord() throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName("Deleted");
		medicalRecord.setFirstName("WillBe");
		mockMvc.perform(delete("/medicalRecord?firstName=WillBe&lastName=Deleted").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(medicalRecord))).andExpect(status().isOk());
	}

}
