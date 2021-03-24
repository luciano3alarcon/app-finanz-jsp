package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPhone {

	public boolean isPhoneValid(String rufnummer) {
	
		boolean isValid = true;
		if(rufnummer.matches("^(?=(?:[8-9]){1})(?=[0-9]{8}).*")){
			if(rufnummer.length() > 5 || !rufnummer.isEmpty()) {
				isValid = true; 
			}
		}
		return isValid; 
	}
}
