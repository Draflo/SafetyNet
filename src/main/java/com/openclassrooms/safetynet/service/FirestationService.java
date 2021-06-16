package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.repository.FirestationRepository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class FirestationService {

	@Autowired
	private FirestationRepository firestationRepository;

	public Optional<Firestation> getFirestation(final Long id) {
		return firestationRepository.findById(id);
	}

	public Iterable<Firestation> getAll() {
		return firestationRepository.findAll();
	}
	
	public Firestation findByAddress(String address) {
		Optional<Firestation> firestation = firestationRepository.findByAddress(address);
		
		return firestation.orElseThrow(()->new NoSuchElementException("No one at this address"));
	}
	
	public Iterable<String> findByStation(int station) {
        return firestationRepository.findAddressesByStation(station);
    }

	@Transactional
	public void deleteFirestation(final String address) {
		firestationRepository.delete(address);
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
