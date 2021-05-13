package com.openclassrooms.safetynet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{
	
	@Modifying
	@Query("delete from Person p where p.lastName=?1 and p.firstName=?2")
	void delete(String lastName, String firstName);
	
	Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
	
	@Query(value = "select p.email from Person p where p.city =:city")
	Optional<Iterable<Person>> findMailFromCity(String city);
}
