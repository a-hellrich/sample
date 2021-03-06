package de.derandy.restTest.controller;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import de.derandy.restTest.model.MyColor;
import de.derandy.restTest.model.Person;
import de.derandy.restTest.repository.PersonRepository;
import de.derandy.restTest.service.PersonService;
import de.derandy.restTest.util.Flags;

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

	@Autowired
	MyColor color;
	
	@Autowired
	Flags flags;

	Person peter = new Person(1L, "Peter", "Petersen", "11111", "Petershausen", "blau");
	Person kati = new Person(2L, "Kati", "Katulki", "20922", "Sonstwo", "blau");
	Person klaus = new Person(3L, "Klaus", "Klauser", "11112", "Klausen", "grün");
	Person wambo = new Person(4L, "Wambo", "Gürtel", "33333", "Am Ende", "violett");
	Person donald = new Person(5L, "Donald", "Duck", "33322", "Entenhausen", "rot");
	ArrayList<Person> personen = new ArrayList<Person>(Arrays.asList(peter, kati, klaus, wambo, donald));

	
	/***
	 * Testet den Zugriff auf alle Personen
	 * @throws URISyntaxException
	 */
	@Test
	void testPersons() throws URISyntaxException {

		Mockito.when(personService.findAll()).thenReturn(personen);

		ResponseEntity<String> result = getResult("/persons");

		Assert.assertTrue(200 == result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains(peter.getLastname()));
		Assert.assertTrue(result.getBody().contains(kati.getLastname()));
		Assert.assertTrue(result.getBody().contains(klaus.getLastname()));
		Assert.assertTrue(result.getBody().contains(wambo.getLastname()));
		Assert.assertTrue(result.getBody().contains(donald.getLastname()));

	}

	/***
	 * Testet den Zugriff auf die Person mit der ID
	 * @throws URISyntaxException
	 */
	@Test
	void testPersonId() throws URISyntaxException {

		Mockito.when(personService.findById(1L)).thenReturn(personen.get(0));

		ResponseEntity<String> result = getResult("/persons/1");

		Assert.assertTrue(200 == result.getStatusCodeValue());
		Assert.assertEquals(true, result.getBody().contains(peter.getLastname()));
		Assert.assertFalse(result.getBody().contains(kati.getLastname()));
		Assert.assertFalse(result.getBody().contains(klaus.getLastname()));
		Assert.assertFalse(result.getBody().contains(wambo.getLastname()));
		Assert.assertFalse(result.getBody().contains(donald.getLastname()));
	}

	/***
	 * Testet den Zugriff auf alle Personen mit der Farbe
	 * @throws URISyntaxException
	 */
	@Test
	void testPersonColor() throws URISyntaxException {

		

		ArrayList<Person> filteredPersonen = (ArrayList<Person>) personen.stream()
				.filter(x -> "blau".equals(x.getColor())).collect(Collectors.toList());

		Mockito.when(personService.findByColor(1L)).thenReturn(filteredPersonen);

		ResponseEntity<String> result = getResult("/persons/color/1");

		Assert.assertTrue(200  == result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains(peter.getLastname()));
		Assert.assertTrue(result.getBody().contains(kati.getLastname()));
		Assert.assertFalse(result.getBody().contains(klaus.getLastname()));
		Assert.assertFalse(result.getBody().contains(wambo.getLastname()));
		Assert.assertFalse(result.getBody().contains(donald.getLastname()));
		
		
	}

	/***
	 * Testet den Post
	 * @throws Exception
	 */
	@Test
	void testPost() throws Exception{
		
		flags.setReal(false);
		
		 mvc.perform(MockMvcRequestBuilders.post("/persons")
	                .content(asJsonString(peter))
	                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
		 
		 flags.setReal(true);
	}
	
	/***
	 * Gibt die Response Entity zurück
	 * @param ending
	 * @return
	 * @throws URISyntaxException
	 */
	ResponseEntity<String> getResult(String ending) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + ending;
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		return result;
	}

	/***
	 * Wandelt ein Objkekt in eine JSON String um
	 * @param obj
	 * @return
	 */
	 public static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	

}
