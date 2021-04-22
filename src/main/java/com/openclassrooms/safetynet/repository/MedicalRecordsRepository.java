package com.openclassrooms.safetynet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.MedicalRecords;

@Repository
public interface MedicalRecordsRepository extends CrudRepository<MedicalRecords, Long> {

}
