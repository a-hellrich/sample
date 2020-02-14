package de.derandy.restTest.controller;



import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import de.derandy.restTest.model.Person;
import de.derandy.restTest.repository.PersonRepository;
import de.derandy.restTest.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PersonControllerTest {

	
	
	@Autowired
    private MockMvc mvc;
	
	@LocalServerPort
	int randomServerPort;
	 
	@MockBean
	PersonService personService;
		
	

	
	
	@Test
	void testPersons() throws URISyntaxException {

		Person peter = new Person("Peter", "Petersen", "11111", "Petershausen", "blau");
		Person kati  = new Person("Kati", "Katulki", "20922", "Sonstwo", "blau");
		Person klaus = new Person("Klaus", "Klauser", "11112", "Klausen","grün");
		Person wambo = new Person("Wambo", "Gürtel", "33333", "Am Ende", "grün");
		Person donald = new Person("Donald", "Duck", "33322", "Entenhausen", "rot");
		ArrayList<Person> personen = new ArrayList<Person>();
		peter.setId(1L);
		personen.add(peter);
		kati.setId(2L);
		personen.add(kati);
		klaus.setId(3L);
		personen.add(klaus);
		wambo.setId(4L);
		personen.add(wambo);
		donald.setId(5L);
		personen.add(donald);
		
		Mockito.when(personService.findAll()).thenReturn( personen);
	
		RestTemplate restTemplate = new RestTemplate();
	     
	    final String baseUrl = "http://localhost:" + randomServerPort + "/persons";
	    URI uri = new URI(baseUrl);
	 
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	     System.out.println(result);
	    
	    Assert.assertEquals(200, result.getStatusCodeValue());
	    Assert.assertTrue(result.getBody().contains("Peter"));
	    Assert.assertTrue(result.getBody().contains("Kati"));
	    Assert.assertTrue(result.getBody().contains("Klaus"));
	    Assert.assertTrue(result.getBody().contains("Wambo"));
	    Assert.assertTrue(result.getBody().contains("Donald"));
		
		
	}

	@Test
	void testPersonId() throws URISyntaxException {
	
		Person peter = new Person("Peter", "Petersen", "11111", "Petershausen", "blau");
		Person kati  = new Person("Kati", "Katulki", "20922", "Sonstwo", "blau");
		Person klaus = new Person("Klaus", "Klauser", "11112", "Klausen","grün");
		Person wambo = new Person("Wambo", "Gürtel", "33333", "Am Ende", "grün");
		Person donald = new Person("Donald", "Duck", "33322", "Entenhausen", "rot");
		ArrayList<Person> personen = new ArrayList<Person>();
		peter.setId(1L);
		personen.add(peter);
		kati.setId(2L);
		personen.add(kati);
		klaus.setId(3L);
		personen.add(klaus);
		wambo.setId(4L);
		personen.add(wambo);
		donald.setId(5L);
		personen.add(donald);
		
		Person personToFind = personen.stream()                        
                .filter(x -> Long.valueOf("1").equals(x.getId()))       
                .findAny()                                      
                .orElse(null);    
		
		Mockito.when(personService.findById(1L)).thenReturn(personToFind);
	
		RestTemplate restTemplate = new RestTemplate();
	     
	    final String baseUrl = "http://localhost:" + randomServerPort + "/persons/1";
	    URI uri = new URI(baseUrl);
	 
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	     System.out.println(result);
	    
	    Assert.assertEquals(200, result.getStatusCodeValue());
	    Assert.assertEquals(true,result.getBody().contains("Peter"));
	    Assert.assertFalse(result.getBody().contains("Kati"));
	    Assert.assertFalse(result.getBody().contains("Klaus"));
	    Assert.assertFalse(result.getBody().contains("Wambo"));
	    Assert.assertFalse(result.getBody().contains("Donald"));
	}

	@Test
	void testPersonColor() throws URISyntaxException {
		
		Person peter = new Person("Peter", "Petersen", "11111", "Petershausen", "blau");
		Person kati  = new Person("Kati", "Katulki", "20922", "Sonstwo", "blau");
		Person klaus = new Person("Klaus", "Klauser", "11112", "Klausen","grün");
		Person wambo = new Person("Wambo", "Gürtel", "33333", "Am Ende", "grün");
		Person donald = new Person("Donald", "Duck", "33322", "Entenhausen", "rot");
		ArrayList<Person> personen = new ArrayList<Person>();
		peter.setId(1L);
		personen.add(peter);
		kati.setId(2L);
		personen.add(kati);
		klaus.setId(3L);
		personen.add(klaus);
		wambo.setId(4L);
		personen.add(wambo);
		donald.setId(5L);
		personen.add(donald);
		
		ArrayList<Person> filteredPersonen =  (ArrayList<Person>) personen.stream()                    
                .filter(x -> "blau".equals(x.getColor())).collect(Collectors.toList()); 
                                                
                
		
		Mockito.when(personService.findByColor(1L)).thenReturn(filteredPersonen);
	
		RestTemplate restTemplate = new RestTemplate();
	     
	    final String baseUrl = "http://localhost:" + randomServerPort + "/persons/color/1";
	    URI uri = new URI(baseUrl);
	 
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	     System.out.println(result);
	    
	    Assert.assertEquals(200, result.getStatusCodeValue());
	    Assert.assertTrue(result.getBody().contains("Peter"));
	    Assert.assertTrue(result.getBody().contains("Kati"));
	    Assert.assertFalse(result.getBody().contains("Klaus"));
	    Assert.assertFalse(result.getBody().contains("Wambo"));
	    Assert.assertFalse(result.getBody().contains("Donald"));
	}

}
