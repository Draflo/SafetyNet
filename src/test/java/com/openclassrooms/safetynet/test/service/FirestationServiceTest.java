package com.openclassrooms.safetynet.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;

@WebMvcTest(FirestationService.class)
public class FirestationServiceTest {
	
	@Autowired
	private FirestationService firestationService;
	
	@MockBean
	private FirestationRepository firestationRepository;
	
	@MockBean
	private PersonService personService;
	
	@MockBean
	private MedicalRecordService medicalRecordService;
	
	public static List<Firestation> firestationList = new ArrayList<>();
	
	static {
		firestationList.add(new Firestation(1L, "1509 Culver St", 3));
		firestationList.add(new Firestation(2L, "29 15th St",2 ));
	}
	
	public static Firestation firestationTest = new Firestation(1L, "1509 Culver St", 3);
	
	@Test
	public void GetAll() {
		when(firestationRepository.findAll()).thenReturn(firestationList);
		
		Iterable<Firestation> foundlist = firestationService.getAll();
		
		verify(firestationRepository, times(1)).findAll();
		assertThat(foundlist.toString().contains("1509 Culver St"));
		assertThat(foundlist.toString().contains("29 15th St"));
	}
	
	@Test
	public void CreateANewFirestation() {
		Firestation stationToCreate = new Firestation(1L, "1509 Culver St", 3);
		when(firestationRepository.save(stationToCreate)).thenReturn(stationToCreate);
		
		Firestation stationCreated = firestationService.saveFirestation(stationToCreate);
		
		assertThat(stationCreated.getAddress()).isEqualTo("1509 Culver St");
	}
	
	@Test
	public void UpdateAFirestation() {
		Firestation stationToUpdate = firestationTest;
		stationToUpdate.setAddress("Address Test");
		stationToUpdate.setStation(5);
		
		when(firestationRepository.save(stationToUpdate)).thenReturn(stationToUpdate);
		Firestation updatedStation = firestationService.saveUpdated(stationToUpdate);
		
		assertThat(updatedStation.getAddress()).isEqualTo(stationToUpdate.getAddress());
		assertThat(updatedStation.getStation()).isEqualTo(stationToUpdate.getStation());
		
	}
	
//	@Test
//	public void DeleteAFirestation() throws NoSuchElementException {
//		when(firestationRepository.findByAddress("1509 Culver St")).thenReturn(Optional.of(firestationTest));
//		firestationService.deleteFirestation("1509 Culver St");
//		assertThrows(NoSuchElementException.class, () -> firestationService.findByAddress("1509 Culver St"));
//	}
	
	@Test
	public void FindByAddress() {
		when(firestationRepository.findByAddress("1509 Culver St")).thenReturn(Optional.of(firestationTest));
		Firestation expectedFirestation = firestationService.findByAddress("1509 Culver St");
		assertEquals(expectedFirestation, firestationTest);
	}
	
	@Test
	public void FindAddressesByStation() {
		List<String> addresses = new ArrayList<>();
		addresses.add("TestAddress");
		when(firestationRepository.findAddressesByStation(3)).thenReturn(addresses);
		List<String> addressesExpected = (List<String>) firestationService.findByStation(3);
		assertEquals(addresses, addressesExpected);
		
		
	}
}
