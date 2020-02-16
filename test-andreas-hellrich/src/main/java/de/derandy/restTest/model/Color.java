package de.derandy.restTest.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

@Component
public class Color {

	private final String BLAU = "blau";
	private final String GRÜN = "grün";
	private final String VIOLETT = "violett";
	private final String ROT = "rot";
	private final String GELB = "gelb";
	private final String TÜRKIES = "türkies";
	private final String WEISS = "weiß";
	
	private  String[] colors;
	
	public String[] getColors() {
		
		colors = new String[7];
		colors[0] = BLAU;
		colors[1] = GRÜN;
		colors[2] = VIOLETT;
		colors[3] = ROT;
		colors[4] = GELB;
		colors[5] = TÜRKIES;
		colors[6] = WEISS;
		
		return colors;
	}
}
