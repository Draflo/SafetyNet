package com.openclassrooms.safetynet.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.service.FirestationService;

@RestController
public class FirestationController {

	@Autowired
	private FirestationService firestationService;

	public FirestationController(FirestationService firestationService) {
		this.firestationService = firestationService;
	}

	/**
	 * Read - Get all firestations
	 * 
	 * @return - An Iterable object of Firestation full filled
	 */
	@GetMapping("/firestations")
	public Iterable<Firestation> list() {
		return firestationService.list();

	}

	/**
	 * Read - Get one firestation
	 * 
	 * @param id The id of the firestation
	 * @return An firestation object full filled
	 */
	@GetMapping("/firestation/{id}")
	public Firestation getFirestation(@PathVariable("id") final Long id) {
		Optional<Firestation> firestation = firestationService.getFirestation(id);
		if (firestation.isPresent()) {
			return firestation.get();
		} else {
			return null;
		}
	}

	/**
	 * Create - Add a new firestation
	 * 
	 * @param Firestation An object firestation
	 * @return The firestation object saved
	 */
	@PostMapping("/firestation")
	public Firestation createFirestation(@RequestBody Firestation firestation) {
		return firestationService.saveFirestation(firestation);
	}

	/**
	 * Delete - Delete a firestation
	 * 
	 * @param id - The id of the firestation to delete
	 */
	@DeleteMapping("/firestation")
	public void deleteFirestation(@RequestParam("address") String address) {
		firestationService.deleteFirestation(address);
	}

	/**
	 * Update - Update an existing firestation
	 * 
	 * @param id       - The id of the firestation to update
	 * @param firestation - The firestation object updated
	 * @return
	 */
	@PutMapping("/firestation/{id}")
	public Firestation updateFirestation(@PathVariable("id") final Long id, @RequestBody Firestation firestation) {
		Optional<Firestation> f = firestationService.getFirestation(id);
		if (f.isPresent()) {
			Firestation currentFirestation = f.get();

			String address = firestation.getAddress();
			if (address != null) {
				currentFirestation.setAddress(address);
			}
			Integer station = firestation.getStation();
			if (station != null) {
				currentFirestation.setStation(station);
			}

			firestationService.saveFirestation(currentFirestation);
			return currentFirestation;

		} else {
			return null;
		}
	}
}