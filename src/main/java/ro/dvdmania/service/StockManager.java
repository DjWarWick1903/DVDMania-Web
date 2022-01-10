package ro.dvdmania.service;

import dvdmania.products.*;
import dvdmania.tools.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class StockManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static StockManager instance = null;

	private StockManager() {
	}

	public static StockManager getInstance() {
		if (instance == null) {
			instance = new StockManager();
		}

		return instance;
	}

	public ArrayList<Stock> getAllStock() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, id_film, id_joc, id_album, id_mag, cant, pret FROM produse";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				int idStock = result.getInt("id_prod");
				int idMovie = result.getInt("id_film");
				int idGame = result.getInt("id_joc");
				int idAlbum = result.getInt("id_album");
				int idStore = result.getInt("id_mag");
				int quantity = result.getInt("cant");
				int price = result.getInt("pret");

				final Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);

				if (idMovie != 0) {
					MovieManager movieMan = MovieManager.getInstance();
					stock.setMovie(movieMan.getMovieById(idMovie));
				} else if (idGame != 0) {
					GameManager gameMan = GameManager.getInstance();
					stock.setGame(gameMan.getGameById(idGame));
				} else if (idAlbum != 0) {
					AlbumManager albumMan = AlbumManager.getInstance();
					stock.setAlbum(albumMan.getAlbumById(idAlbum));
				}

				final StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stockList;
	}

	public ArrayList<Stock> getAllStock(Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, id_film, id_joc, id_album, cant, pret FROM produse WHERE id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idStock = result.getInt("id_prod");
				int idMovie = result.getInt("id_film");
				int idGame = result.getInt("id_joc");
				int idAlbum = result.getInt("id_album");
				int quantity = result.getInt("cant");
				int price = result.getInt("pret");

				final Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);

				if (idMovie != 0) {
					final MovieManager movieMan = MovieManager.getInstance();
					stock.setMovie(movieMan.getMovieById(idMovie));
				} else if (idGame != 0) {
					final GameManager gameMan = GameManager.getInstance();
					stock.setGame(gameMan.getGameById(idGame));
				} else if (idAlbum != 0) {
					final AlbumManager albumMan = AlbumManager.getInstance();
					stock.setAlbum(albumMan.getAlbumById(idAlbum));
				}

				stock.setStore(store);

				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stockList;
	}

	public Stock getStockById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Stock stock = null;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_film, id_joc, id_album, id_mag, cant, pret FROM produse " + "WHERE id_prod=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();

			while (result.next()) {
				int idMovie = result.getInt(1);
				int idGame = result.getInt(2);
				int idAlbum = result.getInt(3);
				int idStore = result.getInt(4);
				int quantity = result.getInt(5);
				int price = result.getInt(6);

				stock = new Stock();
				stock.setIdProduct(id);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				if (idMovie != 0) {
					MovieManager movieMan = MovieManager.getInstance();
					stock.setMovie(movieMan.getMovieById(idMovie));
				} else if (idGame != 0) {
					GameManager gameMan = GameManager.getInstance();
					stock.setGame(gameMan.getGameById(idGame));
				} else if (idAlbum != 0) {
					AlbumManager albumMan = AlbumManager.getInstance();
					stock.setAlbum(albumMan.getAlbumById(idAlbum));
				}

				StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}

	public ArrayList<Stock> getAllMovieStock() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, id_film, id_mag, cant, pret FROM produse WHERE id_film IS NOT NULL";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				int idStock = result.getInt("id_prod");
				int idMovie = result.getInt("id_film");
				int idStore = result.getInt("id_mag");
				int quantity = result.getInt("cant");
				int price = result.getInt("pret");

				final Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				final Movie movie = new Movie();
				movie.setIdMovie(idMovie);
				stock.setMovie(movie);
				final Store store = new Store();
				store.setId(idStore);
				stock.setStore(store);

				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Iterator iter = stockList.iterator();
		while (iter.hasNext()) {
			final Stock stock = (Stock) iter.next();
			final MovieManager movieMan = MovieManager.getInstance();
			stock.setMovie(movieMan.getMovieById(stock.getMovie().getIdMovie()));
			final StoreManager storeMan = StoreManager.getInstance();
			stock.setStore(storeMan.getStoreById(stock.getStore().getId()));
		}

		return stockList;
	}

	public ArrayList<Stock> getAllAlbumStock() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, id_album, id_mag, cant, pret FROM produse WHERE id_album IS NOT NULL";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				int idStock = result.getInt("id_prod");
				int idAlbum = result.getInt("id_album");
				int idStore = result.getInt("id_mag");
				int quantity = result.getInt("cant");
				int price = result.getInt("pret");

				Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				Album album = new Album();
				album.setIdAlbum(idAlbum);
				Store store = new Store();
				store.setId(idStore);
				stock.setAlbum(album);
				stock.setStore(store);

				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Iterator iter = stockList.iterator();
		while (iter.hasNext()) {
			Stock stock = (Stock) iter.next();

			AlbumManager albumMan = AlbumManager.getInstance();
			stock.setAlbum(albumMan.getAlbumById(stock.getAlbum().getIdAlbum()));

			StoreManager storeMan = StoreManager.getInstance();
			stock.setStore(storeMan.getStoreById(stock.getStore().getId()));

			SongManager songMan = SongManager.getInstance();
			Album album = stock.getAlbum();
			album.setSongs(songMan.getSongs(album.getIdAlbum()));
		}

		return stockList;
	}

	public ArrayList<Stock> getAllGameStock() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, id_joc, id_mag, cant, pret FROM produse WHERE id_joc IS NOT NULL";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				int idStock = result.getInt("id_prod");
				int idGame = result.getInt("id_joc");
				int idStore = result.getInt("id_mag");
				int quantity = result.getInt("cant");
				int price = result.getInt("pret");

				Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				Game game = new Game();
				game.setIdGame(idGame);
				stock.setGame(game);
				Store store = new Store();
				store.setId(idStore);
				stock.setStore(store);

				GameManager gameMan = GameManager.getInstance();
				stock.setGame(gameMan.getGameById(idGame));
				StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stockList;
	}

	public ArrayList<Stock> getAllMovieStock(Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, id_film, id_mag, cant, pret FROM produse WHERE id_mag=? AND id_film IS NOT NULL";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idStock = result.getInt("id_prod");
				int idMovie = result.getInt("id_film");
				int idStore = result.getInt("id_mag");
				int quantity = result.getInt("cant");
				int price = result.getInt("pret");

				Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				MovieManager movieMan = MovieManager.getInstance();
				stock.setMovie(movieMan.getMovieById(idMovie));
				StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stockList;
	}

	public ArrayList<Stock> getAllAlbumStock(Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, id_album, id_mag, cant, pret FROM produse WHERE id_mag=? AND id_album IS NOT NULL";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idStock = result.getInt("id_prod");
				int idAlbum = result.getInt("id_album");
				int idStore = result.getInt("id_mag");
				int quantity = result.getInt("cant");
				int price = result.getInt("pret");

				Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				AlbumManager albumMan = AlbumManager.getInstance();
				stock.setAlbum(albumMan.getAlbumById(idAlbum));
				StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				SongManager songMan = SongManager.getInstance();
				Album album = stock.getAlbum();
				album.setSongs(songMan.getSongs(album.getIdAlbum()));

				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stockList;
	}

	public ArrayList<Stock> getAllGameStock(Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, id_joc, id_mag, cant, pret FROM produse WHERE id_mag=? AND id_joc IS NOT NULL";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idStock = result.getInt("id_prod");
				int idGame = result.getInt("id_joc");
				int idStore = result.getInt("id_mag");
				int quantity = result.getInt("cant");
				int price = result.getInt("pret");

				Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				GameManager gameMan = GameManager.getInstance();
				stock.setGame(gameMan.getGameById(idGame));
				StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stockList;
	}

	public Stock getMovieStock(Movie movie, Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Stock stock = null;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, cant, pret FROM produse " + "WHERE id_film=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idStock = result.getInt(1);
				int quantity = result.getInt(2);
				int price = result.getInt(3);

				stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				stock.setMovie(movie);
				stock.setStore(store);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}

	public Stock getGameStock(Game game, Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Stock stock = null;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, cant, pret FROM produse " + "WHERE id_joc=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idStock = result.getInt(1);
				int quantity = result.getInt(2);
				int price = result.getInt(3);

				stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				stock.setGame(game);
				stock.setStore(store);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}

	public Stock getAlbumStock(Album album, Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Stock stock = null;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, cant, pret FROM produse " + "WHERE id_album=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idStock = result.getInt(1);
				int quantity = result.getInt(2);
				int price = result.getInt(3);

				stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				stock.setAlbum(album);
				stock.setStore(store);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}

	public int insertMovieStock(Movie movie, Store store, int quantity, int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			String sql = "INSERT INTO dvdmania.produse (id_film, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			statement.setInt(2, store.getId());
			statement.setInt(3, quantity);
			statement.setInt(4, price);

			rowsInserted = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowsInserted;
	}

	public int insertGameStock(Game game, Store store, int quantity, int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			String sql = "INSERT INTO dvdmania.produse (id_joc, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			statement.setInt(2, store.getId());
			statement.setInt(3, quantity);
			statement.setInt(4, price);

			rowsInserted = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowsInserted;
	}

	public int insertAlbumStock(Album album, Store store, int quantity, int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			String sql = "INSERT INTO dvdmania.produse (id_album, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			statement.setInt(2, store.getId());
			statement.setInt(3, quantity);
			statement.setInt(4, price);

			rowsInserted = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowsInserted;
	}

	public int updateMovieStock(Movie movie, Store store, int quantity, int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			String sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_film=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, quantity);
			statement.setInt(2, price);
			statement.setInt(3, movie.getIdMovie());
			statement.setInt(4, store.getId());

			rowsUpdated = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowsUpdated;
	}

	public int updateGameStock(Game game, Store store, int quantity, int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			String sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_joc=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, quantity);
			statement.setInt(2, price);
			statement.setInt(3, game.getIdGame());
			statement.setInt(4, store.getId());

			rowsUpdated = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowsUpdated;
	}

	public int updateAlbumStock(Album album, Store store, int quantity, int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			String sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_album=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, quantity);
			statement.setInt(2, price);
			statement.setInt(3, album.getIdAlbum());
			statement.setInt(4, store.getId());

			rowsUpdated = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowsUpdated;
	}

	public int deleteMovieStock(Movie movie, Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsDeleted = 0;

		try {
			connection = connMan.openConnection();
			String sql = "DELETE FROM dvdmania.produse WHERE id_film=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			statement.setInt(2, store.getId());

			rowsDeleted = statement.executeUpdate();

			if (checkMovieStock(movie) == 0) {
				MovieManager movieMan = MovieManager.getInstance();
				movieMan.deleteMovie(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowsDeleted;
	}

	public int deleteGameStock(Game game, Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsDeleted = 0;

		try {
			connection = connMan.openConnection();
			String sql = "DELETE FROM dvdmania.produse WHERE id_joc=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			statement.setInt(2, store.getId());

			rowsDeleted = statement.executeUpdate();

			if (checkGameStock(game) == 0) {
				GameManager gameMan = GameManager.getInstance();
				gameMan.deleteGame(game);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowsDeleted;
	}

	public int deleteAlbumStock(Album album, Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsDeleted = 0;

		try {
			connection = connMan.openConnection();
			String sql = "DELETE FROM dvdmania.produse WHERE id_album=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			statement.setInt(2, store.getId());

			rowsDeleted = statement.executeUpdate();

			if (checkAlbumStock(album) == 0) {
				AlbumManager albumMan = AlbumManager.getInstance();
				albumMan.deleteAlbum(album);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowsDeleted;
	}

	public int checkMovieStock(Movie movie) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT COUNT(*) FROM produse WHERE id_film=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			result = statement.executeQuery();

			if (result.next()) {
				stock = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}

	public int checkMovieStock(Movie movie, Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = -1;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, cant FROM produse WHERE id_film=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idProd = result.getInt(1);
				int quantity = result.getInt(2);

				if (idProd == 0) {
					break;
				}

				OrderManager orderMan = OrderManager.getInstance();
				int activeOrders = orderMan.getActiveOrders(idProd, store.getId());

				stock = quantity - activeOrders;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}

	public int checkGameStock(Game game) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_joc=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			result = statement.executeQuery();

			if (result.next()) {
				stock = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}

	public int checkGameStock(Game game, Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, cant FROM produse WHERE id_joc=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idProd = result.getInt(1);
				int quantity = result.getInt(2);

				if (idProd == 0) {
					break;
				}

				OrderManager orderMan = OrderManager.getInstance();
				int activeOrders = orderMan.getActiveOrders(idProd, store.getId());

				stock = quantity - activeOrders;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}

	public int checkAlbumStock(Album album) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_album=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			result = statement.executeQuery();

			if (result.next()) {
				stock = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}

	public int checkAlbumStock(Album album, Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			String sql = "SELECT id_prod, cant FROM produse WHERE id_album=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				int idProd = result.getInt(1);
				int quantity = result.getInt(2);

				if (idProd == 0) {
					break;
				}

				OrderManager orderMan = OrderManager.getInstance();
				int activeOrders = orderMan.getActiveOrders(idProd, store.getId());

				stock = quantity - activeOrders;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connMan.closeConnection(connection, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stock;
	}
}
