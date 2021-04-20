package com.openclassrooms.safetynet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Firestation;

@Repository
public interface FirestationRepository extends CrudRepository<Firestation, Long> {

}
