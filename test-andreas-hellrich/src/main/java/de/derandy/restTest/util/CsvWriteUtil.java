package de.derandy.restTest.util;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.derandy.restTest.model.MyColor;

@Component
public class CsvWriteUtil {


	
	/***
	 * Schreibt die Person in die Output Datei
	 * @param csv_source
	 * @param name
	 * @param lastname
	 * @param zipcode
	 * @param city
	 * @param color
	 * @throws IOException
	 */
	public void write(String csv_source,String name,String lastname,String zipcode,String city,String color) throws IOException {
		
		
		FileWriter pw = new FileWriter(csv_source, true);
        
        
       
			
            pw.append(lastname);
            pw.append(", ");
            pw.append(name);
            pw.append(", ");
            pw.append(zipcode);
            pw.append(", ");
            pw.append(city);
            pw.append(", ");
            
            
        
            
            switch(color){
            	
            case "blau":
            	pw.append("1");
            	break;
            	
            case "grün":
            	pw.append("2");
            	break;
            	
            case "violett":
            	pw.append("3");
            	break;
            
            case "rot":
            	pw.append("4");
            	break;
            	
            case "gelb":
            	pw.append("5");
            	break;
            	
            case "türkies":
            	pw.append("6");
            	break;
            	
            case "weiß":
            	pw.append("7");
            	break;
            	
            }
            
            pw.append("\n");
            pw.flush();
            pw.close();
    
            System.out.println("Appended CSV!");
		
	}
}
