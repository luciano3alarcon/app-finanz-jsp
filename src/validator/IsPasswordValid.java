package validator;

public class IsPasswordValid {

	/*
	 * Die Klasse dient für die Validierung des Passwots Das Passwort muss >8 und
	 * <15 Zeichen und 1 grossen Buchstab und ein Ziffer
	 */

	public static final int PASSWORD_LENGTH = 8;

	public boolean isValidPassword(String passwort) {

		if (passwort.length() < PASSWORD_LENGTH)
			return false;

		int charCount = 0;
		int numCount = 0;

		for (int i = 0; i < passwort.length(); i++) {
			char ch = passwort.charAt(i);

			if (is_Numeric(ch))
				numCount++;
			else if (is_Letter(ch))
				charCount++;
			else
				return false;
		}

		return (charCount >= 2 && numCount >= 2);
	}

	public static boolean is_Letter(char ch) {

		char chCharacter = Character.toUpperCase(ch);
		return (chCharacter >= 'A' && chCharacter <= 'Z');

	}

	public static boolean is_Numeric(char ch) {

		return (ch >= '0' && ch <= '9');
	}

}
