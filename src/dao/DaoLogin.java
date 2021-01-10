package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class DaoLogin {

	private static Connection connection;

	public DaoLogin() {
		connection = SingleConnection.getConnection();
	}

	public boolean passwortValidierung(String user, String passwort) throws Exception{
	
		String sql = "SELECT * FROM finappuser WHERE login = '" + user + "' and passwort = '" + passwort + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			
			return true; // richtig passwort und user
		}else {
			return false; //falsch user und/oder passwort 
		}
	}

}
