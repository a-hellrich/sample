package de.derandy.restTest.util;

import org.springframework.stereotype.Component;

@Component
public class Flags {

	// Gibt an, ob ein realer Post vorliegt, bei dem tatsächlich geschrieben werden soll, oder ob die Post funktion getestet wird
	private boolean real = true;

	public boolean isReal() {
		return real;
	}

	public void setReal(boolean real) {
		this.real = real;
	}
	
	
	
}
