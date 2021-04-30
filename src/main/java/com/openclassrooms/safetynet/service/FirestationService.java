package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.repository.FirestationRepository;

import lombok.Data;

@Data
@Service
public class FirestationService {

	@Autowired
	private FirestationRepository firestationRepository;

	public Optional<Firestation> getFirestation(final Long id) {
		return firestationRepository.findById(id);
	}

	public Iterable<Firestation> list() {
		return firestationRepository.findAll();
	}

	public void deleteFirestation(final Long id) {
		firestationRepository.deleteById(id);
	}

	public Iterable<Firestation> save(List<Firestation> firestations) {
		return firestationRepository.saveAll(firestations);
	}

	public Firestation saveFirestation(Firestation firestation) {
		return firestationRepository.save(firestation);
	}

	public Firestation saveUpdated(Firestation firestation) {
		return firestationRepository.save(firestation);
	}

}
