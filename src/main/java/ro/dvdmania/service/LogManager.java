package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ro.dvdmania.entities.Employee;

public class LogManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static LogManager instance = null;

	public static LogManager getInstance() {
		if (instance == null) {
			instance = new LogManager();
		}
		return instance;
	}

	public int insertLog(final Employee employee, final String action) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO log (id_angajat, action, data) VALUES (?, ?, SYSDATE())";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, employee.getIdEmp());
			statement.setString(2, action);

			rowsInserted = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsInserted;
	}
}
