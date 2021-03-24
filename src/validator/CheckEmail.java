package validator;

public class CheckEmail {

	public  boolean isValidEmailAdresse(String email) {

		String zeichen = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			
		return email.matches(zeichen); //True
	}

}
