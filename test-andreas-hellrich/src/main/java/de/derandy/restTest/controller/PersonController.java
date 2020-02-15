package de.derandy.restTest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.derandy.restTest.model.Person;
import de.derandy.restTest.service.PersonService;
import de.derandy.restTest.util.CsvWriteUtil;

@RestController
@EnableAutoConfiguration
public class PersonController {

	@Autowired
	PersonService personService;
	
	@Autowired
	CsvWriteUtil csvWriteUtil;

	/***
	 * Gibt alle Personen zurück
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/persons")
	public ArrayList<Person> persons() {

		return personService.findAll();
	}

	/***
	 * Gibt die Person mit der angegebenen ID zurück
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/persons/{id}")
	public Person personId(@PathVariable String id) {

		return personService.findById(Long.valueOf(id));
	}

	/***
	 * Gibt die Personen mit der angebenen Farbe zurück
	 * 
	 * @param color
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/persons/color/{color}")
	public ArrayList<Person> personColor(@PathVariable String color) {

		return personService.findByColor(Long.valueOf(color));

	}

	/***
	 * Dient dazu, Personen in die CSV zu schreiben
	 * 
	 * @param payload
	 * @throws Exception
	 */
	@RequestMapping(value = "/persons", method = RequestMethod.POST)
	public void process(@RequestBody Person payload) throws Exception {

		
		
		String name = payload.getName();
		String lastname = payload.getLastname();
		String zipcode = payload.getZipcode();
		String city = payload.getCity();
		String color = payload.getColor();
		
		System.out.println("received: "+name+" "+lastname+" "+zipcode+" "+city+" "+color);
		
		String csv_source = "\\sample-output.csv";
		
		csvWriteUtil.write(csv_source,name,lastname,zipcode,city,color);

	}
}
