package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import ro.dvdmania.entities.Account;
import ro.dvdmania.entities.Client;

public class ClientManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static ClientManager instance = null;

	public static ClientManager getInstance() {
		if (instance == null) {
			instance = new ClientManager();
		}
		return instance;
	}

	public Client getClientById(final int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Client client = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT nume, pren, adresa, oras, datan, cnp, tel, email, loialitate FROM clienti WHERE id_cl=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();

			while (result.next()) {
				final String nume = result.getString("nume");
				final String prenume = result.getString("pren");
				final String adress = result.getString("adresa");
				final String oras = result.getString("oras");
				final LocalDate date = result.getDate("datan").toLocalDate();
				final String cnp = result.getString("cnp");
				final String tel = result.getString("tel");
				final String email = result.getString("email");
				final int loyal = result.getInt("loialitate");

				client = new Client(id, nume, prenume, adress, oras, date, cnp, tel, email, loyal);

				final AccountManager accMan = AccountManager.getInstance();
				final Account account = accMan.getClientAccount(client);
				client.setAccount(account);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return client;
	}

	public ArrayList<Client> getAllClients() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<Client> clientList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_cl, nume, pren, adresa, oras, datan, cnp, tel, email, loialitate FROM clienti";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				final int id = result.getInt("id_cl");
				final String nume = result.getString("nume");
				final String prenume = result.getString("pren");
				final String adress = result.getString("adresa");
				final String oras = result.getString("oras");
				final LocalDate date = result.getDate("datan").toLocalDate();
				final String cnp = result.getString("cnp");
				final String tel = result.getString("tel");
				final String email = result.getString("email");
				final int loyal = result.getInt("loialitate");

				final Client client = new Client(id, nume, prenume, adress, oras, date, cnp, tel, email, loyal);
				final AccountManager accMan = AccountManager.getInstance();
				final Account account = accMan.getClientAccount(client);
				client.setAccount(account);
				clientList.add(client);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return clientList;
	}

	public int createClient(final Client client) {
		Connection connection = null;
		PreparedStatement statement = null;
		int newKey = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO dvdmania.clienti " + "(nume, pren, adresa, oras, datan, cnp, tel, email) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, client.getNume());
			statement.setString(2, client.getPrenume());
			statement.setString(3, client.getAdresa());
			statement.setString(4, client.getOras());
			statement.setObject(5, client.getDatan());
			statement.setString(6, client.getCnp());
			statement.setString(7, client.getTel());
			statement.setString(8, client.getEmail());

			final int rowsInserted = statement.executeUpdate();
			if (rowsInserted != 0) {
				final ResultSet keySet = statement.getGeneratedKeys();
				if (keySet.next()) {
					newKey = keySet.getInt(1);
					client.setId(newKey);
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

	public int updateClient(final Client client) {
		int rowsUpdated = 0;
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.clienti SET nume=?, pren=?, adresa=?, oras=?, datan=?, cnp=?, tel=?, email=?, loialitate=? WHERE id_cl=?";

			statement = connection.prepareStatement(sql);
			statement.setString(1, client.getNume());
			statement.setString(2, client.getPrenume());
			statement.setString(3, client.getAdresa());
			statement.setString(4, client.getOras());
			statement.setObject(5, client.getDatan());
			statement.setString(6, client.getCnp());
			statement.setString(7, client.getTel());
			statement.setString(8, client.getEmail());
			statement.setInt(9, client.getLoialitate());
			statement.setInt(10, client.getId());
			rowsUpdated = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsUpdated;
	}

	public int rewardClient(final Client client) {
		int rowsUpdated = 0;
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.clienti SET loialitate=loialitate+1 WHERE id_cl=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, client.getId());
			rowsUpdated = statement.executeUpdate();

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsUpdated;
	}

	public int punishClient(final Client client) {
		int rowsUpdated = 0;
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.clienti SET loialitate=loialitate-1 WHERE id_cl=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, client.getId());
			rowsUpdated = statement.executeUpdate();

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsUpdated;
	}

	public String[] clientToRow(final Client client) {
		final String[] row = new String[12];
		row[0] = client.getId() + "";
		row[1] = client.getNume();
		row[2] = client.getPrenume();
		row[3] = client.getAdresa();
		row[4] = client.getOras();
		row[5] = client.getDatan().toString();
		row[6] = client.getCnp();
		row[7] = client.getTel();
		row[8] = client.getEmail();
		row[9] = client.getLoialitate() + "";
		row[10] = client.getAccount().getUsername();
		row[11] = client.getAccount().getPassword();

		return row;
	}
}
