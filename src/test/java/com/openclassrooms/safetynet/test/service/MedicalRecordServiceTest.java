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

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonService;

@WebMvcTest(MedicalRecordService.class)
public class MedicalRecordServiceTest {

	@Autowired
	private MedicalRecordService medicalRecordService;

	@MockBean
	MedicalRecordRepository medicalRecordRepository;

	@MockBean
	private FirestationService firestationService;

	@MockBean
	private PersonService personService;

	public static List<MedicalRecord> mrList = new ArrayList<>();

	static {

		mrList.add(new MedicalRecord(1L, "John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"),
				List.of("nillacilan")));
		mrList.add(new MedicalRecord(2L, "Jacob", "Boyd", "03/06/1989",
				List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), null));
		mrList.add(new MedicalRecord(3L, "Tenley", "Boyd", "03/06/1989", null, List.of("peanut")));
	}

	public static MedicalRecord mrTest = new MedicalRecord();

	static {
		mrTest.setId(1L);
		mrTest.setFirstName("John");
		mrTest.setLastName("Boyd");
		mrTest.setBirthdate("03/06/1984");
		mrTest.setMedications(List.of("medication: 20mg", "medication2 : 5mg"));
		mrTest.setAllergies(List.of("allergies"));

	}

	@Test
	public void GetAll() {
		when(medicalRecordRepository.findAll()).thenReturn(mrList);

		Iterable<MedicalRecord> foundList = medicalRecordService.list();

		verify(medicalRecordRepository, times(1)).findAll();
		assertThat(foundList.toString().contains("John"));
		assertThat(foundList.toString().contains("Jacob"));
		assertThat(foundList.toString().contains("Tenley"));
	}

	@Test
	public void GetAMedicalRecordByFirstNameAndLastName() {
		when(medicalRecordRepository.findByFirstNameAndLastName("John", "Boyd")).thenReturn(Optional.of(mrTest));

		MedicalRecord expectedMedicalRecord = medicalRecordService.findByFirstNameAndLastName("John", "Boyd");

		assertThat(expectedMedicalRecord.getFirstName()).isEqualTo("John");
	}

	@Test
	public void TryingToFindAMedicalRecordNotRegisteredGetNoSuchElementException() throws NoSuchElementException {
		assertThrows(NoSuchElementException.class,
				() -> medicalRecordService.findByFirstNameAndLastName("I don't", "Exist"));
	}

	@Test
	public void CreateANewMedicalRecord() {
		MedicalRecord medicalRecordToCreate = new MedicalRecord();
		medicalRecordToCreate.setFirstName("John");
		medicalRecordToCreate.setLastName("Boyd");
		medicalRecordToCreate.setBirthdate("03/06/1984");

		when(medicalRecordRepository.save(medicalRecordToCreate)).thenReturn(medicalRecordToCreate);

		MedicalRecord medicalRecordCreated = medicalRecordService.saveMedicalRecord(medicalRecordToCreate);

		assertThat(medicalRecordCreated.getFirstName()).isEqualTo("John");
		assertThat(medicalRecordCreated.getLastName()).isEqualTo("Boyd");

	}

	@Test
	public void UpdateAMedicalRecord() {
		MedicalRecord medicalRecordToUpdate = mrTest;
		medicalRecordToUpdate.setBirthdate("01/01/1980");

		when(medicalRecordRepository.save(medicalRecordToUpdate)).thenReturn(medicalRecordToUpdate);
		MedicalRecord updatedMedicalRecord = medicalRecordService.saveUpdated(medicalRecordToUpdate);

		assertThat(updatedMedicalRecord.getFirstName()).isEqualTo(medicalRecordToUpdate.getFirstName());
		assertThat(updatedMedicalRecord.getBirthdate()).isEqualTo(medicalRecordToUpdate.getBirthdate());
	}

	@Test
	public void DeleteAMedicalRecord() throws NoSuchElementException {
		when(medicalRecordRepository.findByFirstNameAndLastName("John", "Boyd")).thenReturn(Optional.of(mrTest));
		medicalRecordService.deleteMedicalRecord("John", "Boyd");
		verify(medicalRecordRepository, times(1)).delete("John", "Boyd");
	}

	@Test
	public void FindByAddress() {
		when(medicalRecordRepository.findPersonByAddress("TestAddress")).thenReturn(Optional.of(mrList));
		Iterable<MedicalRecord> expectedMedicalRecords = medicalRecordService.findByAddress("TestAddress");
		assertEquals(expectedMedicalRecords, mrList);
	}
}
