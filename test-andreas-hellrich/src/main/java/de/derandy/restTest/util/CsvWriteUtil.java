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
	
	@Autowired 
	MyColor myColor;
	
	public void write(String csv_source,String name,String lastname,String zipcode,String city,String color) throws IOException {
		
		
		
        
        
			String newcolor = myColor.getColorVal(color);
			FileWriter pw = new FileWriter(csv_source, true);
			
            pw.append(lastname+", "+name+", "+zipcode+" "+city+", "+newcolor+"\n");
          
            pw.flush();
            pw.close();
    
            System.out.println("Appended CSV!");
		
	}
}
