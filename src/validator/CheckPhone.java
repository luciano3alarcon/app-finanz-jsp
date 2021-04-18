package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPhone {

	public boolean isPhoneValid(String rufnummer) {
	
		boolean isValid = false;
		if (!rufnummer.isEmpty() && rufnummer.length() > 5) {
			//if(rufnummer.matches("^(?=(?:[8-9]){1})(?=[0-9]{8}).*")) {
				isValid = true; 
			}
		return isValid; 
	}
}
