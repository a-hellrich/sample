package de.derandy.restTest.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import de.derandy.restTest.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>  {

	// Filtert die Personen mit der angebenen Farbe
	ArrayList<Person> findByColor(String color);
	
	// Findet alle Personen
	ArrayList<Person> findAll();
	
	// Filtert die Person mit der angebenen ID
	Optional<Person> findById (Long id);
	
}
