package com.openclassrooms.safetynet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;

@Repository
public interface FirestationRepository extends CrudRepository<Firestation, Long> {

	@Modifying
	@Query("delete from Firestation f where f.address=?1")
	void delete(String address);
	
	Optional<Firestation> findByAddress(String address);
}
