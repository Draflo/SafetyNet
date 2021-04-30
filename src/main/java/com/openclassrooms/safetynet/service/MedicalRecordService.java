package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;

import lombok.Data;

@Data
@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public Optional<MedicalRecord> getMedicalRecords(final Long id) {
		return medicalRecordRepository.findById(id);
	}

	public Iterable<MedicalRecord> list() {
		return medicalRecordRepository.findAll();
	}

	public void deleteMedicalRecords(final Long id) {
		medicalRecordRepository.deleteById(id);
	}

	public Iterable<MedicalRecord> save(List<MedicalRecord> medicalRecords) {
		return medicalRecordRepository.saveAll(medicalRecords);
	}

	public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord saveUpdated(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}
}
