package ro.dvdmania.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectionManager {

	private static ConnectionManager instance = null;
	private static final String CONNECTION = "jdbc:mysql://localhost:3306/dvdmania";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "robertmaster1";

	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	public Connection openConnection() throws SQLException {
		Connection myConn = null;
		try {
			
			/*
			 * final String[] array = readFromFile();
			 * 
			 * if (array[0] != null && array[1] != null && array[2] != null) { myConn =
			 * DriverManager.getConnection(array[0], array[1], array[2]); }
			 */
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return myConn;
	}

	public void closeConnection(final Connection connection, final Statement statement, final ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch(final SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection(final Connection connection, final Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch(final SQLException e) {
			e.printStackTrace();
		}
	}

	private static String[] readFromFile() {
		final String[] array = new String[3];
		final Path path = FileSystems.getDefault().getPath("connection.dat");

		try (final DataInputStream locFile = new DataInputStream(new BufferedInputStream(new FileInputStream(path.toAbsolutePath().toString())))) {
			array[0] = locFile.readUTF();
			array[1] = locFile.readUTF();
			array[2] = locFile.readUTF();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return array;
	}

	public static void writeToFile(final String url, final String user, final String password) {
		final ArrayList<String> list = new ArrayList<String>();

		try (final DataOutputStream locFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("connection.dat")))) {
			locFile.writeUTF(url);
			locFile.writeUTF(user);
			locFile.writeUTF(password);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
