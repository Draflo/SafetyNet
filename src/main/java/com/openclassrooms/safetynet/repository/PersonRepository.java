package com.openclassrooms.safetynet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{

}
