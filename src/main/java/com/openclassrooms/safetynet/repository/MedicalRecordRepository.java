package com.openclassrooms.safetynet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

	@Modifying
	@Query("delete from MedicalRecord mr where mr.lastName=?1 and mr.firstName=?2")
	void delete(String lastName, String firstName);

	Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);
	
	@Query(value = "select p from Person p where p.address = :address")
	Optional<Iterable<MedicalRecord>> findPersonByAddress(String address);
}
