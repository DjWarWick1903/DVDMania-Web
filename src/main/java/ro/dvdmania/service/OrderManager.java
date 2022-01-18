package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import ro.dvdmania.entities.Album;
import ro.dvdmania.entities.Client;
import ro.dvdmania.entities.Employee;
import ro.dvdmania.entities.Game;
import ro.dvdmania.entities.Movie;
import ro.dvdmania.entities.Order;
import ro.dvdmania.entities.Stock;
import ro.dvdmania.entities.Store;

public class OrderManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static OrderManager instance = null;

	public static OrderManager getInstance() {
		if (instance == null) {
			instance = new OrderManager();
		}
		return instance;
	}

	public int checkAvailability(final Stock stock, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int results = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT COUNT(id_prod) FROM imprumuturi WHERE id_prod=? AND data_ret=NULL";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, stock.getIdProduct());
			result = statement.executeQuery();

			while (result.next()) {
				results = result.getInt(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return results;
	}

	public int checkInOrder(final Stock stock, final Client client) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			String sql = "UPDATE imprumuturi SET data_ret=SYSDATE() WHERE id_prod=? AND id_cl=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, stock.getIdProduct());
			statement.setInt(2, client.getId());
			rowsUpdated = statement.executeUpdate();
			statement.close();

			if (rowsUpdated > 0) {
				sql = "SELECT DATEDIFF(data_ret, data_imp) FROM imprumuturi WHERE id_prod=? AND id_cl=?";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, stock.getIdProduct());
				statement.setInt(2, client.getId());
				result = statement.executeQuery();

				while (result.next()) {
					rowsUpdated = result.getInt(1);
				}
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return rowsUpdated;
	}

	public LocalDate checkOutMovieOrder(final Movie movie, final Client client, final Employee employee) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		LocalDate date = null;

		final StockManager stockMan = StockManager.getInstance();
		final Store store = employee.getStore();
		final Stock stock = stockMan.getMovieStock(movie, store);

		try {
			connection = connMan.openConnection();
			String sql = "INSERT INTO imprumuturi(id_prod, id_cl, id_angaj, id_mag, data_imp, pret) VALUES(?,?,?,?, SYSDATE(),?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, stock.getIdProduct());
			statement.setInt(2, client.getId());
			statement.setInt(3, employee.getId());
			statement.setInt(4, employee.getStore().getId());
			statement.setInt(5, stock.getPrice());
			statement.executeUpdate();
			statement.close();

			sql = "SELECT DATE_ADD(sysdate(), INTERVAL 7 day)";
			statement = connection.prepareStatement(sql);
			result = statement.executeQuery();

			while (result.next()) {
				date = result.getDate(1).toLocalDate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return date;
	}

	public LocalDate checkOutGameOrder(final Game game, final Client client, final Employee employee) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		LocalDate date = null;

		final StockManager stockMan = StockManager.getInstance();
		final Store store = employee.getStore();
		final Stock stock = stockMan.getGameStock(game, store);

		try {
			connection = connMan.openConnection();
			String sql = "INSERT INTO imprumuturi(id_prod, id_cl, id_angaj, id_mag, data_imp, pret) VALUES(?,?,?,?, SYSDATE(),?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, stock.getIdProduct());
			statement.setInt(2, client.getId());
			statement.setInt(3, employee.getId());
			statement.setInt(4, employee.getStore().getId());
			statement.setInt(5, stock.getPrice());
			statement.executeUpdate();
			statement.close();
			
			sql = "SELECT DATE_ADD(sysdate(), INTERVAL 7 day)";
			statement = connection.prepareStatement(sql);
			result = statement.executeQuery();

			while (result.next()) {
				date = result.getDate(1).toLocalDate();
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return date;
	}

	public LocalDate checkOutAlbumOrder(final Album album, final Client client, final Employee employee) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		LocalDate date = null;

		final StockManager stockMan = StockManager.getInstance();
		final Store store = employee.getStore();
		final Stock stock = stockMan.getAlbumStock(album, store);

		try {
			connection = connMan.openConnection();
			String sql = "INSERT INTO imprumuturi(id_prod, id_cl, id_angaj, id_mag, data_imp, pret) VALUES(?,?,?,?, SYSDATE(),?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, stock.getIdProduct());
			statement.setInt(2, client.getId());
			statement.setInt(3, employee.getId());
			statement.setInt(4, employee.getStore().getId());
			statement.setInt(5, stock.getPrice());
			statement.executeUpdate();
			statement.close();

			sql = "SELECT DATE_ADD(sysdate(), INTERVAL 7 day)";
			statement = connection.prepareStatement(sql);
			result = statement.executeQuery();

			while (result.next()) {
				date = result.getDate(1).toLocalDate();
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return date;
	}

	public ArrayList<Order> getStoreActiveOrders(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Order> orderList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT i.id_prod, i.id_cl, i.id_angaj, i.data_imp, i.pret FROM imprumuturi i JOIN magazin m USING(id_mag) "
					+ "WHERE i.data_ret IS NULL AND m.id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			final StockManager stockMan = StockManager.getInstance();
			final ClientManager clientMan = ClientManager.getInstance();
			final EmployeeManager empMan = EmployeeManager.getInstance();

			while (result.next()) {
				final int idStock = result.getInt("id_prod");
				final int idClient = result.getInt("id_cl");
				final int idEmp = result.getInt("id_angaj");
				final LocalDate date = result.getDate("data_imp").toLocalDate();
				final int price = result.getInt("pret");

				final Stock stock = stockMan.getStockById(idStock);
				final Client client = clientMan.getClientById(idClient);
				final Employee emp = empMan.getEmployeeById(idEmp);

				final Order order = new Order(stock, client, emp, store, date, null, price);
				orderList.add(order);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return orderList;
	}

	public ArrayList<Order> getAllStoreOrders(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Order> orderList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, id_cl, id_angaj, data_imp, data_ret, pret FROM imprumuturi "
					+ "WHERE id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			final StockManager stockMan = StockManager.getInstance();
			final ClientManager clientMan = ClientManager.getInstance();
			final EmployeeManager empMan = EmployeeManager.getInstance();

			while (result.next()) {
				final int idStock = result.getInt("id_prod");
				final int idClient = result.getInt("id_cl");
				final int idEmp = result.getInt("id_angaj");
				final LocalDate date = result.getDate("data_imp").toLocalDate();
				final LocalDate retDate = (result.getDate("data_ret") == null) ? null
						: result.getDate("data_ret").toLocalDate();
				final int price = result.getInt("pret");

				final Stock stock = stockMan.getStockById(idStock);
				final Client client = clientMan.getClientById(idClient);
				final Employee emp = empMan.getEmployeeById(idEmp);

				final Order order = new Order(stock, client, emp, store, date, retDate, price);
				orderList.add(order);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return orderList;
	}

	public ArrayList<Order> getAllClientOrders(final Client client) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Order> orderList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_mag, id_prod, id_angaj, data_imp, data_ret, pret FROM imprumuturi "
					+ "WHERE id_cl=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, client.getId());
			result = statement.executeQuery();

			final StockManager stockMan = StockManager.getInstance();
			final StoreManager storeMan = StoreManager.getInstance();
			final EmployeeManager empMan = EmployeeManager.getInstance();

			while (result.next()) {
				final int idStock = result.getInt("id_prod");
				final int idStore = result.getInt("id_mag");
				final int idEmp = result.getInt("id_angaj");
				final LocalDate date = result.getDate("data_imp").toLocalDate();
				final LocalDate retDate = (result.getDate("data_ret") == null) ? null
						: result.getDate("data_ret").toLocalDate();
				final int price = result.getInt("pret");

				final Stock stock = stockMan.getStockById(idStock);
				final Store store = storeMan.getStoreById(idStore);
				final Employee emp = empMan.getEmployeeById(idEmp);

				final Order order = new Order(stock, client, emp, store, date, retDate, price);
				orderList.add(order);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return orderList;
	}

	public int getActiveOrders(final int idStock, final int idStore) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int orders = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT COUNT(*) FROM imprumuturi " + "WHERE data_ret IS NULL AND id_prod=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idStock);
			statement.setInt(2, idStore);
			result = statement.executeQuery();

			while (result.next()) {
				orders = result.getInt(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return orders;
	}

	public String[] orderToRow(final Order order) {
		final String[] row = new String[5];
		row[0] = order.getClient().getNume();
		row[1] = order.getClient().getPrenume();
		row[2] = order.getClient().getCnp();
		row[3] = order.getStock().getIdProduct() + "";
		row[4] = order.getBorrowDate().toString();

		return row;
	}

	public ArrayList<Order> getOrdersByDates(final LocalDate date1, final LocalDate date2, final Store oras) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Order> orderList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, id_cl, id_angaj, id_mag, data_imp, data_ret, pret FROM dvdmania.imprumuturi ";

			if (date1 != null && date2 != null) {
				if (oras == null) {
					sql += "WHERE data_imp > ? AND data_imp < ?";
					statement = connection.prepareStatement(sql);
					statement.setObject(1, date1);
					statement.setObject(2, date2);
					result = statement.executeQuery();
				} else {
					sql += "WHERE data_imp > ? AND data_imp < ? AND id_mag = ?";
					statement = connection.prepareStatement(sql);
					statement.setObject(1, date1);
					statement.setObject(2, date2);
					statement.setInt(3, oras.getId());
					result = statement.executeQuery();
				}
			} else if (date1 == null && date2 == null) {
				if (oras != null) {
					sql += "WHERE id_mag = ?";
					statement = connection.prepareStatement(sql);
					statement.setInt(1, oras.getId());
					result = statement.executeQuery();
				} else {
					statement = connection.prepareStatement(sql);
					result = statement.executeQuery();
				}
			} else if (date1 == null) {
				if (oras == null) {
					sql += "WHERE data_imp > ?";
					statement = connection.prepareStatement(sql);
					statement.setObject(1, date1);
					result = statement.executeQuery();
				} else {
					sql += "WHERE data_imp > ? AND id_mag = ?";
					statement = connection.prepareStatement(sql);
					statement.setObject(1, date1);
					statement.setInt(2, oras.getId());
					result = statement.executeQuery();
				}
			} else if (date2 == null) {
				if (oras == null) {
					sql += "WHERE data_imp < ?";
					statement = connection.prepareStatement(sql);
					statement.setObject(1, date1);
					result = statement.executeQuery();
				} else {
					sql += "WHERE data_imp < ? AND id_mag = ?";
					statement = connection.prepareStatement(sql);
					statement.setObject(1, date1);
					statement.setInt(2, oras.getId());
					result = statement.executeQuery();
				}
			}

			final StockManager stockMan = StockManager.getInstance();
			final ClientManager clientMan = ClientManager.getInstance();
			final EmployeeManager empMan = EmployeeManager.getInstance();

			while (result.next()) {
				final int idStock = result.getInt("id_prod");
				final int idClient = result.getInt("id_cl");
				final int idEmp = result.getInt("id_angaj");
				final LocalDate date = result.getDate("data_imp").toLocalDate();
				final LocalDate retDate = (result.getDate("data_ret") == null) ? null
						: result.getDate("data_ret").toLocalDate();
				final int price = result.getInt("pret");

				final Store store;

				if (oras == null) {
					final int storeId = result.getInt("id_mag");
					store = StoreManager.getInstance().getStoreById(storeId);
				} else {
					store = oras;
				}

				final Stock stock = stockMan.getStockById(idStock);
				final Client client = clientMan.getClientById(idClient);
				final Employee emp = empMan.getEmployeeById(idEmp);

				final Order order = new Order(stock, client, emp, store, date, retDate, price);
				orderList.add(order);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return orderList;
	}

	public HashMap<String, Integer> getOrderTimeline() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		final HashMap<String, Integer> orderTimeline = new HashMap<String, Integer>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT YEAR(data_imp) AS An, COUNT(id_prod) AS Produse FROM imprumuturi GROUP BY YEAR(data_imp)";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				final String year = resultSet.getString("An");
				final int produse = resultSet.getInt("Produse");

				orderTimeline.put(year, produse);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, resultSet);
		}

		return orderTimeline;
	}

	public HashMap<String, Integer> getProductOrderTimeline() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		final HashMap<String, Integer> orderTimeline = new HashMap<String, Integer>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT 'Filme' as Categorie, COUNT(id_film) as Produse FROM produse JOIN imprumuturi USING(id_prod) UNION "
					+ "SELECT 'Jocuri' as Categorie, COUNT(id_joc) as Produse FROM produse JOIN imprumuturi USING(id_prod) UNION "
					+ "SELECT 'Albume' as Categorie, COUNT(id_album) as Produse FROM produse JOIN imprumuturi USING(id_prod)";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				final String cat = resultSet.getString("Categorie");
				final int produse = resultSet.getInt("Produse");

				orderTimeline.put(cat, produse);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, resultSet);
		}

		return orderTimeline;
	}
}
