package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import ro.dvdmania.entities.Account;
import ro.dvdmania.entities.Employee;
import ro.dvdmania.entities.Store;

public class EmployeeManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static EmployeeManager instance = null;

	public static EmployeeManager getInstance() {
		if (instance == null) {
			instance = new EmployeeManager();
		}
		return instance;
	}

	public int createEmployee(final Employee employee) {
		Connection connection = null;
		PreparedStatement statement = null;
		int newKey = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO angajati(nume, pren, adresa, oras, "
					+ "datan, cnp, tel, email, functie, salariu, activ, id_mag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, employee.getNume());
			statement.setString(2, employee.getPrenume());
			statement.setString(3, employee.getAdresa());
			statement.setString(4, employee.getOras());
			statement.setObject(5, employee.getDatan());
			statement.setString(6, employee.getCnp());
			statement.setString(7, employee.getTel());
			statement.setString(8, employee.getEmail());
			statement.setString(9, employee.getFunctie());
			statement.setInt(10, employee.getSalariu());
			statement.setString(11, "Activ");
			statement.setInt(12, employee.getStore().getId());

			final int rowsInserted = statement.executeUpdate();
			if (rowsInserted != 0) {
				final ResultSet keySet = statement.getGeneratedKeys();
				if (keySet.next()) {
					newKey = keySet.getInt(1);
					employee.setId(newKey);
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

	public int updateEmployee(final Employee employee) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.angajati SET nume=?, pren=?, adresa=?, oras=?, "
					+ "datan=?, cnp=?, tel=?, email=?, functie=?, salariu=?, activ=?, id_mag=? WHERE id_angaj=?";

			statement = connection.prepareStatement(sql);
			statement.setString(1, employee.getNume());
			statement.setString(2, employee.getPrenume());
			statement.setString(3, employee.getAdresa());
			statement.setString(4, employee.getOras());
			statement.setObject(5, employee.getDatan());
			statement.setString(6, employee.getCnp());
			statement.setString(7, employee.getTel());
			statement.setString(8, employee.getEmail());
			statement.setString(9, employee.getFunctie());
			statement.setInt(10, employee.getSalariu());
			if (employee.isActiv()) {
				statement.setString(11, "Activ");
			} else {
				statement.setString(11, "Inactiv");
			}
			statement.setInt(12, employee.getStore().getId());
			statement.setInt(13, employee.getId());

			rowsInserted = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsInserted;
	}

	public Employee getEmployeeById(final int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Employee emp = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT a.nume, a.pren, a.adresa, a.oras, a.datan, "
					+ "a.cnp, a.tel, a.email, a.functie, a.salariu, m.id_mag FROM angajati a "
					+ "JOIN magazin m ON m.id_mag=a.id_mag WHERE a.activ='Activ' AND a.id_angaj=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();

			while (result.next()) {
				final String nume = result.getString("nume");
				final String prenume = result.getString("pren");
				final String adresa = result.getString("adresa");
				final String oras = result.getString("oras");
				final LocalDate datan = result.getDate("datan").toLocalDate();
				final String cnp = result.getString("cnp");
				final String telefon = result.getString("tel");
				final String email = result.getString("email");
				final String functie = result.getString("functie");
				final int salariu = result.getInt("salariu");
				final int idMag = result.getInt("id_mag");

				final StoreManager storeMan = StoreManager.getInstance();
				emp = new Employee(id, nume, prenume, adresa, oras, datan, cnp, telefon, email, functie, salariu, true);
				final Store store = storeMan.getStoreById(idMag);
				emp.setStore(store);
				final AccountManager accMan = AccountManager.getInstance();
				final Account account = accMan.getEmployeeAccount(emp);
				emp.setAccount(account);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return emp;
	}

	public ArrayList<Employee> getEmployees() {
		final ArrayList<Employee> employees = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT a.id_angaj, a.nume, a.pren, a.adresa, a.oras, a.datan, "
					+ "a.cnp, a.tel, a.email, a.functie, a.salariu, m.id_mag FROM dvdmania.angajati a, "
					+ "dvdmania.magazin m WHERE m.id_mag=a.id_mag AND a.activ='Activ'";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				final int idEmp = result.getInt("id_angaj");
				final String nume = result.getString("nume");
				final String prenume = result.getString("pren");
				final String adresa = result.getString("adresa");
				final String oras = result.getString("oras");
				final LocalDate datan = result.getDate("datan").toLocalDate();
				final String cnp = result.getString("cnp");
				final String telefon = result.getString("tel");
				final String email = result.getString("email");
				final String functie = result.getString("functie");
				final int salariu = result.getInt("salariu");
				final int idMag = result.getInt("id_mag");
				
				final StoreManager storeMan = StoreManager.getInstance();
				final Employee employee = new Employee(idEmp, nume, prenume, adresa, oras, datan, cnp, telefon, email, functie, salariu, true);
				employee.setStore(storeMan.getStoreById(idMag));

				final AccountManager accMan = AccountManager.getInstance();
				final Account account = accMan.getEmployeeAccount(employee);
				employee.setAccount(account);

				employees.add(employee);
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return employees;
	}

	public String getEmployeePost(final String cnp) {
		String post = new String();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT functie FROM dvdmania.angajati WHERE cnp=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, cnp);
			result = statement.executeQuery();

			while (result.next()) {
				post = result.getString(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return post;
	}

	public String getEmployeeEmail(final String cnp) {
		String email = new String();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT email FROM dvdmania.angajati WHERE cnp=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, cnp);
			result = statement.executeQuery();

			while (result.next()) {
				email = result.getString(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return email;
	}
}
