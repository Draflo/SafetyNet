package com.openclassrooms.safetynet.service;

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
	
	public Iterable<Firestation> getFirestation() { 
		return firestationRepository.findAll();
	}
	
	public void deleteFirestation(final Long id) {
		firestationRepository.deleteById(id);
	}
	
	public Firestation saveFirestation(Firestation firestation) {
		Firestation savedFirestation = firestationRepository.save(firestation);
		return savedFirestation;
	}

}
