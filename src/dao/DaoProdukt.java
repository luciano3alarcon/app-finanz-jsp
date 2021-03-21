package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanProdukt;
import connection.SingleConnection;

public class DaoProdukt {

	private static Connection connection;
	BeanProdukt produkt = new BeanProdukt();

	public DaoProdukt() {
		connection = SingleConnection.getConnection();
	}

	public void produtktSpeichern(BeanProdukt produkt) {

		try {

			String sql = "INSERT INTO finappprodukt(beschreibung, anzahl, preis) VALUES (?, ?, ?);";
			
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, produkt.getBeschreibung());
			insert.setLong(2, produkt.getAnzahl());
			insert.setDouble(3, produkt.getPreis());
			insert.execute();

			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<BeanProdukt> aufgelisteteProdukte() throws Exception {

		List<BeanProdukt> produktListe = new ArrayList<BeanProdukt>();
		String sqlListe = "SELECT * FROM finappprodukt;";

		PreparedStatement queryListe = this.connection.prepareStatement(sqlListe);
		ResultSet result = queryListe.executeQuery();

		while (result.next()) {

			BeanProdukt beanProdukt = new BeanProdukt();
			beanProdukt.setBeschreibung(result.getString("beschreibung"));
			beanProdukt.setAnzahl(result.getLong("anzahl"));
			beanProdukt.setId(result.getLong("id"));
			beanProdukt.setPreis(result.getDouble("preis"));
			produktListe.add(beanProdukt);
		}
		
		return produktListe; 
	}

	public void loeschen(String id) throws Exception {

		try {
			String sql = "DELETE FROM finappprodukt WHERE id = '" + id + "'";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// abfragen = consulta
	public BeanProdukt abfragen(String id) throws Exception {

		String sqlAbfrage = "SELECT * FROM finappprodukt WHERE id = " + id + ";";

		PreparedStatement statement = connection.prepareStatement(sqlAbfrage);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			BeanProdukt beanObj = new BeanProdukt();
			beanObj.setBeschreibung(result.getString("beschreibung"));
			beanObj.setAnzahl(result.getLong("anzahl"));
			beanObj.setId(result.getLong("id"));
			beanObj.setPreis(result.getDouble("preis"));

			return beanObj;
		}

		return null;
	}

	public void update(BeanProdukt produkt) {

		String abfrage = "UPDATE finappprodukt SET beschreibung=?, anzahl=?, id=?, preis=? WHERE id= " + produkt.getId() + ";";

		try {
			PreparedStatement statement = connection.prepareStatement(abfrage);
			statement.setString(1, produkt.getBeschreibung());
			statement.setLong(2, produkt.getAnzahl());
			statement.setLong(3, produkt.getId()) ;
			statement.setDouble(4, produkt.getPreis());
			statement.executeUpdate();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
