package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.MedicalRecords;
import com.openclassrooms.safetynet.repository.MedicalRecordsRepository;

import lombok.Data;

@Data
@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordsRepository medicalRecordsRepository;

	public Optional<MedicalRecords> getMedicalRecords(final Long id) {
		return medicalRecordsRepository.findById(id);
	}

	public Iterable<MedicalRecords> list() {
		return medicalRecordsRepository.findAll();
	}

	public void deleteMedicalRecords(final Long id) {
		medicalRecordsRepository.deleteById(id);
	}

	public Iterable<MedicalRecords> save(List<MedicalRecords> medicalRecords) {
		return medicalRecordsRepository.saveAll(medicalRecords);
	}

}
