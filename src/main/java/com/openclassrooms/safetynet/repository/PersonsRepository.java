package com.openclassrooms.safetynet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Persons;

@Repository
public interface PersonsRepository extends CrudRepository<Persons, Long>{

}
