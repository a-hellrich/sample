package de.derandy.restTest.model;

import java.util.HashMap;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

@Component
public class MyColor {

	private final String BLAU = "blau";
	private final String GRÜN = "grün";
	private final String VIOLETT = "violett";
	private final String ROT = "rot";
	private final String GELB = "gelb";
	private final String TÜRKIES = "türkies";
	private final String WEISS = "weiß";
	
	private  String[] colors;
	
	private HashMap<String, String> colorMap = new HashMap<String, String>();
	
	
	
	
	public MyColor() {
		
		colorMap.put("blau","1");
		colorMap.put("grün","2");
		colorMap.put("violett","3");
		colorMap.put("rot","4");
		colorMap.put("gelb", "5");
		colorMap.put("türkies","6");
		colorMap.put("weiß", "7");
		
		colors = new String[7];
		colors[0] = BLAU;
		colors[1] = GRÜN;
		colors[2] = VIOLETT;
		colors[3] = ROT;
		colors[4] = GELB;
		colors[5] = TÜRKIES;
		colors[6] = WEISS;
		
	}
	
	
	public String[] getColors() {
		
		return colors;
	}
	
	public String getColorVal(String colorKey) {
		return colorMap.get(colorKey);
	}
}
