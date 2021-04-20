package com.openclassrooms.safetynet.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.service.FirestationService;

@RestController
public class FirestationController {

	@Autowired
	private FirestationService firestationService;

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
	 * Read - Get all firestations
	 * 
	 * @return - An Iterable object of Firestation full filled
	 */
	@GetMapping("/firestations")
	public Iterable<Firestation> getFirestation() {
		return firestationService.getFirestation();
	}

	/**
	 * Update - Update an existing firestation
	 * 
	 * @param id       - The id of the firestation to update
	 * @param employee - The firestation object updated
	 * @return
	 */
	@PutMapping("/firestation/{id}")
	public Firestation updateFirestation(@PathVariable("id") final Long id, @RequestBody Firestation firestation) {
		Optional<Firestation> f = firestationService.getFirestation(id);
		if (f.isPresent()) {
			Firestation currentFirestation = f.get();

			String adress = firestation.getAdress();
			if (adress != null) {
				currentFirestation.setAdress(adress);
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

	/**
	 * Delete - Delete an employee
	 * 
	 * @param id - The id of the employee to delete
	 */
	@DeleteMapping("/firestation/{id}")
	public void deleteFirestation(@PathVariable("id") final Long id) {
		firestationService.deleteFirestation(id);
	}

}