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
import com.openclassrooms.safetynet.controller.MedicalRecordsController;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;

@WebMvcTest(controllers = MedicalRecordsController.class)
public class MedicalRecordsControllerTest {
	
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
	public void testGetAllMedicalRecords() throws Exception {
		mockMvc.perform(get("/medicalRecords")).andExpect(status().isOk());
	}
	
	@Test
	public void testPostAMedicalRecord() throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName("TestName");
		mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(medicalRecord))).andExpect(status().isCreated());
	}
	@Test
	public void testPutAMedicalRecord() throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName("TestName");
		mockMvc.perform(put("/medicalRecord/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(medicalRecord))).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteAMedicalRecord() throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName("Deleted");
		medicalRecord.setFirstName("WillBe");
		mockMvc.perform(delete("/medicalRecord?firstName=WillBe&lastName=Deleted").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(medicalRecord))).andExpect(status().isOk());
	}

}
