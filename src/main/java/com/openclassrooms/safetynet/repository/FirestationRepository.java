package com.openclassrooms.safetynet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Firestation;

@Repository
public interface FirestationRepository extends CrudRepository<Firestation, Long> {

	@Modifying
	@Query("delete from Firestation f where f.address = :address")
	void delete(String address);
	
	Optional<Firestation> findByAddress(String address);
	
	@Query(value = "select f.station from Firestation f where f.address = :address")
	Iterable<Integer> findStationByAddress(String address);
	
	@Query("select f.address from Firestation f where f.station = :station")
    Iterable<String> findAddressesByStation(int station);
}
