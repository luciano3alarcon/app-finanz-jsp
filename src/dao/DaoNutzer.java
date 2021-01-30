package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanFinanzJsp;
import connection.SingleConnection;
import validator.IsPasswordValid;

public class DaoNutzer {

	private static Connection connection;
	BeanFinanzJsp nutzer = new BeanFinanzJsp();

	public DaoNutzer() {
		connection = SingleConnection.getConnection();
	}

	public void nutzerSpeichernDB(BeanFinanzJsp nutzer) { // Das Objekt Bean hat Login und Passwort

		try {
			String sql = "INSERT INTO finappuser(login, passwort, name, rufnummer, email) VALUES (?, ?, ?, ?, ?);";

			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, nutzer.getLogin());
			insert.setString(2, nutzer.getPassword());
			insert.setString(3, nutzer.getName());
			insert.setString(4, nutzer.getRufnummer());
			insert.setString(5, nutzer.getEmail());
			insert.execute();

			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println("Fehler Class DaoNutzer");
				e1.printStackTrace();
			}
		}
	}

	public List<BeanFinanzJsp> aufgelisteteNutzer() throws Exception {

		List<BeanFinanzJsp> liste = new ArrayList<BeanFinanzJsp>();
		String sqlQuery = "SELECT * FROM finappuser;";

		PreparedStatement queryListe = this.connection.prepareStatement(sqlQuery);
		ResultSet resultSet = queryListe.executeQuery();

		while (resultSet.next()) {

			BeanFinanzJsp beanFinanzNutzer = new BeanFinanzJsp();// Obj mit User und PW
			beanFinanzNutzer.setLogin(resultSet.getString("login"));
			beanFinanzNutzer.setPassword(resultSet.getString("passwort"));
			beanFinanzNutzer.setId(resultSet.getLong("id"));
			beanFinanzNutzer.setName(resultSet.getString("name"));
			beanFinanzNutzer.setRufnummer(resultSet.getString("rufnummer"));
			beanFinanzNutzer.setEmail(resultSet.getString("email"));
			liste.add(beanFinanzNutzer);
		}

		return liste;
	}

	public void loeschen(String id) throws Exception {

		try {

			if (IsAdmin(id)) {
				String sql = "DELETE FROM finappuser WHERE id = '" + id + "';";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.execute();

				connection.commit();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public boolean validateLogin(String login) throws Exception {

		String sql = "SELECT COUNT (1) as anzahl from finappuser WHERE login= '" + login + "';";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			return result.getInt("anzahl") <= 0; /* Hier wird ein True erwartet. */
		}

		return false;
	}

	public boolean validateLoginUpdate(String login, String id) throws Exception {

		String sql = "SELECT COUNT (1) as anzahl from finappuser WHERE login= '" + login + "' AND id <> " + id + " ;";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			return result.getInt("anzahl") <= 0; /* Hier wird ein True erwartet. */
		}

		return false;
	}

	public BeanFinanzJsp consulta(String id) throws Exception {

		String sql = "SELECT * FROM finappuser WHERE id = '" + id + "';";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		if (result.next()) {

			BeanFinanzJsp beanObj = new BeanFinanzJsp();
			beanObj.setLogin(result.getString("login"));
			beanObj.setPassword(result.getString("passwort"));
			beanObj.setId(result.getLong("id"));
			beanObj.setName(result.getString("name"));
			beanObj.setRufnummer(result.getString("rufnummer"));
			beanObj.setEmail(result.getString("email"));

			return beanObj;
		}
		return null;
	}

	public void update(BeanFinanzJsp nutzer) {

		String abfrage = "UPDATE finappuser SET login=?, passwort=?, id = ?, name=?, rufnummer= ?, email= ? WHERE  id= "
				+ nutzer.getId() + " ; ";
		try {
			PreparedStatement statement = connection.prepareStatement(abfrage);
			statement.setString(1, nutzer.getLogin());
			statement.setString(2, nutzer.getPassword());
			statement.setLong(3, nutzer.getId());
			statement.setString(4, nutzer.getName());
			statement.setString(5, nutzer.getRufnummer());
			statement.setString(6, nutzer.getEmail());
			statement.executeUpdate();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public boolean IsAdmin(String id) throws Exception { /* Darf nur bei Editieren benutzt werden*/

		/* Query in der DB mit Login und Id, zu überprüfen, ob die ID ein ADmin ist. */

		String sql = "SELECT COUNT (1) as anzahlAdmin from finappuser WHERE id = '" + id + "' AND login= 'admin';";
		boolean isAdmin = false;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			if (result.getInt("anzahlAdmin") <= 0) {
				isAdmin = true;
			} else {
				isAdmin = false;
			}
		}
		return isAdmin;
	}

}