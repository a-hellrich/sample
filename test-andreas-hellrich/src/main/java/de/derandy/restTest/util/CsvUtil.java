package de.derandy.restTest.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import de.derandy.restTest.model.Person;
import de.derandy.restTest.repository.PersonRepository;
import de.derandy.restTest.service.PersonService;

@Component
@EnableAutoConfiguration
public class CsvUtil {

	private int targetsize = 4;
	
	private String[] colors = {"blau", "grün","violett","rot","gelb","türkis","weiß"};
	
	@Autowired	
	PersonService personService;
	
	/***
	 * Dient dazu, die CSV Datei einzulesen
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */	
	public ArrayList<ArrayList<String>> readCsv(String filename)  {
		
		ArrayList<ArrayList<String>> records = new ArrayList<ArrayList<String>>();
		try (CSVReader csvReader = new CSVReader(new FileReader(filename));) {
			
			    String[] values = null;
			    while ((values = csvReader.readNext()) != null) {
			        records.add(new ArrayList<>( Arrays.asList(values)));
			    }
			    
			    records = fixCsvLineBreak(records);
				createPersonen(records);
		}
		
		catch(Exception ex) {
			System.out.println("Fehler beim Lesen der CSV Datei: "+ex.getMessage());
		}
		
		
		
		return records;
	}
	
	/***
	 * Dient dazu, zwei Zeilen zu vereinigen, wenn in einem Eintrag versehentlich Enter gedrückt wurde
	 * @param oldRecords
	 * @return
	 */
	public  ArrayList<ArrayList<String>> fixCsvLineBreak(ArrayList<ArrayList<String>> oldRecords){
		
		ArrayList<ArrayList<String>> newRecords = (ArrayList<ArrayList<String>>) oldRecords;
		
		for(int i = 0; i< newRecords.size(); i++) {
			
			ArrayList<String> record = (ArrayList<String>) newRecords.get(i);
			
			if(i < newRecords.size()-1) {
				ArrayList<String> nextRecord = (ArrayList<String>)newRecords.get(i+1);
				
				int size = record.size();
				
				if(size <targetsize) {
					for(int j = 0 ; j < nextRecord.size(); j++) {
						record.add(nextRecord.get(j));
					}
				
				
					newRecords.remove(nextRecord);
					
				}
			
				
				
				
			}
			
			record = cleanRecord(record);
			record = splitAddressAndRemoveWhitespace(record);
			
		}
		
		return newRecords;
	}
	
	/***
	 * Dient dazu, leerer Strings aus dem Record zu werfen
	 * @param record
	 * @return
	 */
	public  ArrayList<String> cleanRecord(ArrayList<String> record){
		
		ArrayList<String> newRecord = record;
		
		newRecord.removeAll(Collections.singleton(null));
		newRecord.removeAll(Collections.singleton(""));
		newRecord.removeAll(Collections.singleton(" "));
		
		return newRecord;
	}
	
	/***
	 * Dient dazu, Postleitzahl und Ort zu trennen und unnötige Leerzeichen zu löschen
	 * @param record
	 * @return
	 */
	public  ArrayList<String> splitAddressAndRemoveWhitespace(ArrayList<String> record){
		
		ArrayList<String> newRecord = record;
		
		for(int i = 0; i<record.size(); i++) {
			
			String toCheck = record.get(i);
			if (Character.isWhitespace(toCheck.charAt(0))){
				
				toCheck = toCheck.substring(1);
				record.set(i,toCheck);
			}
			
		}
		
		String plzOrt = record.get(2);
		
		int stop = plzOrt.length();
		
		String plz  = plzOrt.substring(0,5);
		
		String ort = plzOrt.substring(6,stop);
		
		record.set(2, plz);
		
		record.add(3,ort);
		
		if(record.get(4).length() > 1) {
			record.set(4, String.valueOf(record.get(4).charAt(0))); 
		}
		return newRecord;
		
	}
	
	/***
	 * Erzeugt die Personen
	 * @param records
	 */
	public  void createPersonen(ArrayList<ArrayList<String>> records) {
		
		
		
		for(int i = 0; i<records.size();i++) {
			
			ArrayList<String> thisRecord = records.get(i);
			
			int thisId = i+1;
	
			
			Integer thisColorPosition = Integer.parseInt(thisRecord.get(4)) -1;
			String thisColor = colors[thisColorPosition];
			
			de.derandy.restTest.model.Person newPerson = new de.derandy.restTest.model.Person( thisRecord.get(1), thisRecord.get(0), thisRecord.get(2),thisRecord.get(3), thisColor );
			
			personService.save(newPerson);
		}
		
	}
	
}
