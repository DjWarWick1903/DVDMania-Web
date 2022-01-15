package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import ro.dvdmania.entities.Account;
import ro.dvdmania.entities.Client;
import ro.dvdmania.entities.Employee;

public class AccountManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static AccountManager instance = null;

	public static AccountManager getInstance() {
		if (instance == null) {
			instance = new AccountManager();
		}
		return instance;
	}

	public Account getAccount(final String username, final String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Account account = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_cont, data_creat, id_cl, id_angaj FROM dvdmania.conturi WHERE util=? AND parola=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt(1);
				final LocalDate date = result.getDate(2).toLocalDate();
				final int idClient = result.getInt(3);
				final int idEmployee = result.getInt(4);

				if (idClient != 0) {
					account = new Account(id, username, password, date, 1, idClient);
				} else if (idEmployee != 0) {
					account = new Account(id, username, password, date, 2, idEmployee);
				}
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return account;
	}

	public Account getClientAccount(final Client client) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Account account = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_cont, util, parola, data_creat FROM conturi WHERE id_cl=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, client.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt(1);
				final String username = result.getString(2);
				final String password = result.getString(3);
				final LocalDate date = result.getDate(4).toLocalDate();

				account = new Account(id, username, password, date, 1, client.getId());
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return account;
	}

	public boolean updateClientAccount(final Account account) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isUpdated = false;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE conturi SET util=?, parola=? WHERE id_cl=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, account.getUsername());
			statement.setString(2, account.getPassword());
			statement.setInt(3, account.getIdUtil());
			final int rowsUpdated = statement.executeUpdate();

			if (rowsUpdated > 0) {
				isUpdated = true;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return isUpdated;
	}

	public boolean updateEmployeeAccount(final Account account) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isUpdated = false;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE conturi SET util=?, parola=? WHERE id_angaj=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, account.getUsername());
			statement.setString(2, account.getPassword());
			statement.setInt(3, account.getIdUtil());
			final int rowsUpdated = statement.executeUpdate();

			if (rowsUpdated > 0) {
				isUpdated = true;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return isUpdated;
	}

	public Account getEmployeeAccount(final Employee employee) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Account account = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_cont, util, parola, data_creat FROM conturi WHERE id_angaj=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, employee.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt(1);
				final String username = result.getString(2);
				final String password = result.getString(3);
				final LocalDate date = result.getDate(4).toLocalDate();

				account = new Account(id, username, password, date, 1, employee.getId());
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return account;
	}

	public int createClientAccount(final Account account) {
		Connection connection = null;
		PreparedStatement statement = null;
		int newKey = 0;

		final boolean exists = checkAccountExists(account.getUsername(), account.getPassword());

		if (!exists) {
			try {
				connection = connMan.openConnection();
				final String sql = "INSERT INTO dvdmania.conturi (util, parola, data_creat, id_cl) VALUES(?,?,SYSDATE(),?)";
				statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, account.getUsername());
				statement.setString(2, account.getPassword());
				statement.setInt(3, account.getIdUtil());

				final int rowsInserted = statement.executeUpdate();
				if (rowsInserted != 0) {
					final ResultSet keySet = statement.getGeneratedKeys();
					if (keySet.next()) {
						newKey = keySet.getInt(1);
						account.setIdAcc(newKey);
					}
					keySet.close();
				}
			} catch (final SQLException e) {
				e.printStackTrace();
			} finally {
				connMan.closeConnection(connection, statement);
			}
		}

		return newKey;
	}

	public int createEmployeeAccount(final Account account) {
		Connection connection = null;
		PreparedStatement statement = null;
		int newKey = 0;

		final boolean exists = checkAccountExists(account.getUsername(), account.getPassword());

		if (!exists) {
			try {
				connection = connMan.openConnection();
				final String sql = "INSERT INTO dvdmania.conturi (util, parola, data_creat, id_angaj) VALUES(?,?,SYSDATE(),?)";
				statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, account.getUsername());
				statement.setString(2, account.getPassword());
				statement.setInt(3, account.getIdUtil());

				final int rowsInserted = statement.executeUpdate();
				if (rowsInserted != 0) {
					final ResultSet keySet = statement.getGeneratedKeys();
					if (keySet.next()) {
						newKey = keySet.getInt(1);
						account.setIdAcc(newKey);
					}
					keySet.close();
				}
			} catch (final SQLException e) {
				e.printStackTrace();
			} finally {
				connMan.closeConnection(connection, statement);
			}
		}

		return newKey;
	}

	public boolean checkAccountExists(final String username, final String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		boolean exists = false;

		try {
			connection = connMan.openConnection();
			if (connection != null) {
				final String sql = "SELECT util, parola FROM conturi WHERE util = ? AND parola = ?";
				statement = connection.prepareStatement(sql);
				statement.setString(1, username);
				statement.setString(2, password);
				result = statement.executeQuery();

				while (result.next()) {
					exists = true;
				}
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return exists;
	}

	/**
	 * Checks what kind of privileges the given account has.
	 * 0 - guest
	 * 1 - client
	 * 2 - employee
	 * 3 - admin
	 * @param account
	 * @return int
	 */
	public int checkAccountPrivilege(final Account account) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int priv = 1;

		try {
			connection = connMan.openConnection();
			if (account.getPriv() == 2) {
				priv = 2;
				final String sql = "SELECT functie FROM angajati WHERE id_angaj=?";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, account.getIdUtil());
				result = statement.executeQuery();

				while (result.next()) {
					final String post = result.getString(1);
					if (post.equals("Director") || post.equals("Manager")) {
						priv = 3;
						account.setPriv(3);
					}
				}
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return priv;
	}
}
