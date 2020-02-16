package de.derandy.restTest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import de.derandy.restTest.model.Person;
import de.derandy.restTest.repository.PersonRepository;
import de.derandy.restTest.util.CsvReadUtil;

@Service
@EnableAutoConfiguration
public class PersonService {

	@Autowired
	PersonRepository personRepository;
	
	@Autowired 
	CsvReadUtil csvUtil;
	
	@Autowired
	Environment env;
	
	private String csv_source = ".\\sample-input.csv";
	
	/***
	 * Gibt alle Personen zurück
	 * @return
	 */
	public ArrayList<Person> findAll() {
		
		return personRepository.findAll();
	}
	
	/***
	 * Filtert die Personen mit der angegebenen Farbe
	 * @param color
	 * @return
	 */
	public ArrayList<Person> findByColor(Long color){
		String[] colors = {"blau", "grün","violett","rot","gelb","türkis","weiß"};
		Integer colorposition = Long.valueOf(color -1).intValue();
		String colorstring = colors[colorposition];
		return personRepository.findByColor(colorstring);
	}
	
	/***
	 * Filtert die Person mit der angegebenen ID 
	 * @param id
	 * @return
	 */
	public Person findById(Long id) {
		
		
		if(personRepository.findById(id).isPresent()) {
			return personRepository.findById(id).get();
		}
		
		else {
			System.out.println("PERSON NICHT GEFUNDEN!");
			return null;
		}
	}
	
	/***
	 * Speichert alle Personen
	 * @param persons
	 * @return
	 */
	public List<Person> saveAll(List<Person> persons){
		
		return  personRepository.saveAll(persons);
	}
	
	/***
	 * Speichert die Person
	 * @param person
	 * @return
	 */
	public Person save(Person person) {
		return personRepository.save(person);
	}
	
	/***
	 * Erzeugt die Datei aus der CSV, wenn das Profil csv verwendet wird
	 */
	@PostConstruct	
	private void constructFromCSV() {
		
		if (Arrays.asList(env.getActiveProfiles()).contains("csv"))
			csvUtil.readCsv(csv_source);
	}
	
}
