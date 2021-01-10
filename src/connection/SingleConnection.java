package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Diese Class ist für Verbindung mit der DB verantwortlich
 * @author: Luciano 
 */

public class SingleConnection {

	private static String dataBank = "jdbc:postgresql://localhost:5432/pinanzapp?autoReconnect=true";
	private static String username = "postgres";
	private static String passwort = "admin";
	private static Connection connection = null;

	static {
		verbinden();
	}

	public SingleConnection() {
		verbinden();
	}

	private static void verbinden() {

		try { // Zuerst macht man die DB Verbindung. Wenn die Connection = null ist, dann muss
				// man verbinden
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(dataBank, username, passwort);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Ein Fehler ist bei der DB-Verbindung aufgetretten!");
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}

}
