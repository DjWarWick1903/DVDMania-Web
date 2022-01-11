package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import ro.dvdmania.entities.Employee;
import ro.dvdmania.entities.Store;

public class StoreManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static StoreManager instance = null;

	public static StoreManager getInstance() {
		if (instance == null) {
			instance = new StoreManager();
		}
		return instance;
	}

	public Store getStoreById(final int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Store store = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT adresa, oras, tel FROM magazin WHERE id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();

			while (result.next()) {
				final String adress = result.getString(1);
				final String city = result.getString(2);
				final String tel = result.getString(3);

				store = new Store(id, adress, city, tel);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return store;
	}

	public Store getStoreByCity(final String city) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Store store = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_mag, adresa, tel FROM dvdmania.magazin WHERE oras=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, city);
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt(1);
				final String adress = result.getString(2);
				final String tel = result.getString(3);

				store = new Store(id, adress, city, tel);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return store;
	}

	public int createStore(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		int newKey = 0;
		
		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO dvdmania.magazin (adresa, oras, tel) VALUES (?, ?, ?)";
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, store.getAdresa());
			statement.setString(2, store.getOras());
			statement.setString(3, store.getTelefon());

			final int rowsInserted = statement.executeUpdate();
			if (rowsInserted != 0) {
				final ResultSet keySet = statement.getGeneratedKeys();
				if (keySet.next()) {
					newKey = keySet.getInt(1);
					store.setId(newKey);
				}
				keySet.close();
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return newKey;
	}

	public int updateStore(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.magazin SET adresa=?, oras=?, tel=? WHERE id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, store.getAdresa());
			statement.setString(2, store.getOras());
			statement.setString(3, store.getTelefon());
			statement.setInt(4, store.getId());

			rowsUpdated = statement.executeUpdate();

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsUpdated;
	}

	public ArrayList<Store> getStores() {
		final ArrayList<Store> stores = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_mag, adresa, oras, tel FROM dvdmania.magazin";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				final int idMag = result.getInt("id_mag");
				final String adresa = result.getString("adresa");
				final String oras = result.getString("oras");
				final String telefon = result.getString("tel");

				final Store store = new Store(idMag, adresa, oras, telefon);
				stores.add(store);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stores;
	}

	public LinkedList<String> getStoreCities() {
		final LinkedList<String> cities = new LinkedList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT oras FROM dvdmania.magazin";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				final String city = result.getString("oras");
				cities.add(city);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return cities;
	}

	public Store getStoreByEmployee(final Employee employee) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Store store = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT m.id_mag, m.adresa, m.oras, m.tel FROM magazin m JOIN angajati a USING(id_mag) WHERE a.id_angaj=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, employee.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt(1);
				final String adress = result.getString(2);
				final String city = result.getString(3);
				final String tel = result.getString(4);

				store = new Store(id, adress, city, tel);
				employee.setIdMag(id);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return store;
	}

	public String[] storeToRow(final Store store) {
		final String[] row = new String[4];
		row[0] = store.getId() + "";
		row[1] = store.getAdresa();
		row[2] = store.getOras();
		row[3] = store.getTelefon();

		return row;
	}
}
