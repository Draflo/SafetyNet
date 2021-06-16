package com.openclassrooms.safetynet;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.LoadDataModel;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;

@SpringBootApplication
public class SafetyNetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}

	private static final Logger logger = LogManager.getLogger();
	
	@Bean
	CommandLineRunner runner(FirestationService firestationService, PersonService personService, MedicalRecordService
			medicalRecordService) {
		return args -> {
			var mapper = new ObjectMapper();
			TypeReference<LoadDataModel> typeReference = new TypeReference<LoadDataModel>() {};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
			try {
				LoadDataModel loadDataModel = mapper.readValue(inputStream,typeReference);
				personService.save(loadDataModel.getPersons());
				firestationService.save(loadDataModel.getFirestations());
				medicalRecordService.save(loadDataModel.getMedicalrecords());
				logger.info("Users Saved !");
			} catch (IOException e) {
				logger.error("Unable to save users: " + e.getMessage());
			}
		};
	}

}
