package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import ro.dvdmania.entities.Album;
import ro.dvdmania.entities.Game;
import ro.dvdmania.entities.Movie;
import ro.dvdmania.entities.Stock;
import ro.dvdmania.entities.Store;

public class StockManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static StockManager instance = null;

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
				final int idStock = result.getInt("id_prod");
				final int idMovie = result.getInt("id_film");
				final int idGame = result.getInt("id_joc");
				final int idAlbum = result.getInt("id_album");
				final int idStore = result.getInt("id_mag");
				final int quantity = result.getInt("cant");
				final int price = result.getInt("pret");

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

				final StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				stockList.add(stock);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stockList;
	}

	public ArrayList<Stock> getAllStock(final Store store) {
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
				final int idStock = result.getInt("id_prod");
				final int idMovie = result.getInt("id_film");
				final int idGame = result.getInt("id_joc");
				final int idAlbum = result.getInt("id_album");
				final int quantity = result.getInt("cant");
				final int price = result.getInt("pret");

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
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stockList;
	}

	public Stock getStockById(final int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Stock stock = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_film, id_joc, id_album, id_mag, cant, pret FROM produse " + "WHERE id_prod=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();

			while (result.next()) {
				final int idMovie = result.getInt(1);
				final int idGame = result.getInt(2);
				final int idAlbum = result.getInt(3);
				final int idStore = result.getInt(4);
				final int quantity = result.getInt(5);
				final int price = result.getInt(6);

				stock = new Stock();
				stock.setIdProduct(id);
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

				final StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
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
				final int idStock = result.getInt("id_prod");
				final int idMovie = result.getInt("id_film");
				final int idStore = result.getInt("id_mag");
				final int quantity = result.getInt("cant");
				final int price = result.getInt("pret");

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
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		final Iterator<Stock> iter = stockList.iterator();
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
		final ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, id_album, id_mag, cant, pret FROM produse WHERE id_album IS NOT NULL";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				final int idStock = result.getInt("id_prod");
				final int idAlbum = result.getInt("id_album");
				final int idStore = result.getInt("id_mag");
				final int quantity = result.getInt("cant");
				final int price = result.getInt("pret");

				final Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				final Album album = new Album();
				album.setIdAlbum(idAlbum);
				final Store store = new Store();
				store.setId(idStore);
				stock.setAlbum(album);
				stock.setStore(store);

				stockList.add(stock);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		final Iterator<Stock> iter = stockList.iterator();
		while (iter.hasNext()) {
			final Stock stock = (Stock) iter.next();

			final AlbumManager albumMan = AlbumManager.getInstance();
			stock.setAlbum(albumMan.getAlbumById(stock.getAlbum().getIdAlbum()));

			final StoreManager storeMan = StoreManager.getInstance();
			stock.setStore(storeMan.getStoreById(stock.getStore().getId()));

			final SongManager songMan = SongManager.getInstance();
			final Album album = stock.getAlbum();
			album.setSongs(songMan.getSongs(album.getIdAlbum()));
		}

		return stockList;
	}

	public ArrayList<Stock> getAllGameStock() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, id_joc, id_mag, cant, pret FROM produse WHERE id_joc IS NOT NULL";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				final int idStock = result.getInt("id_prod");
				final int idGame = result.getInt("id_joc");
				final int idStore = result.getInt("id_mag");
				final int quantity = result.getInt("cant");
				final int price = result.getInt("pret");

				final Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				final Game game = new Game();
				game.setIdGame(idGame);
				stock.setGame(game);
				final Store store = new Store();
				store.setId(idStore);
				stock.setStore(store);

				final GameManager gameMan = GameManager.getInstance();
				stock.setGame(gameMan.getGameById(idGame));
				final StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				stockList.add(stock);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stockList;
	}

	public ArrayList<Stock> getAllMovieStock(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, id_film, id_mag, cant, pret FROM produse WHERE id_mag=? AND id_film IS NOT NULL";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int idStock = result.getInt("id_prod");
				final int idMovie = result.getInt("id_film");
				final int idStore = result.getInt("id_mag");
				final int quantity = result.getInt("cant");
				final int price = result.getInt("pret");

				final Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				final MovieManager movieMan = MovieManager.getInstance();
				stock.setMovie(movieMan.getMovieById(idMovie));
				final StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				stockList.add(stock);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stockList;
	}

	public ArrayList<Stock> getAllAlbumStock(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, id_album, id_mag, cant, pret FROM produse WHERE id_mag=? AND id_album IS NOT NULL";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int idStock = result.getInt("id_prod");
				final int idAlbum = result.getInt("id_album");
				final int idStore = result.getInt("id_mag");
				final int quantity = result.getInt("cant");
				final int price = result.getInt("pret");

				final Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				final AlbumManager albumMan = AlbumManager.getInstance();
				stock.setAlbum(albumMan.getAlbumById(idAlbum));
				final StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				final SongManager songMan = SongManager.getInstance();
				final Album album = stock.getAlbum();
				album.setSongs(songMan.getSongs(album.getIdAlbum()));

				stockList.add(stock);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stockList;
	}

	public ArrayList<Stock> getAllGameStock(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Stock> stockList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, id_joc, id_mag, cant, pret FROM produse WHERE id_mag=? AND id_joc IS NOT NULL";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int idStock = result.getInt("id_prod");
				final int idGame = result.getInt("id_joc");
				final int idStore = result.getInt("id_mag");
				final int quantity = result.getInt("cant");
				final int price = result.getInt("pret");

				final Stock stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				final GameManager gameMan = GameManager.getInstance();
				stock.setGame(gameMan.getGameById(idGame));
				final StoreManager storeMan = StoreManager.getInstance();
				stock.setStore(storeMan.getStoreById(idStore));

				stockList.add(stock);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stockList;
	}

	public Stock getMovieStock(final Movie movie, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Stock stock = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, cant, pret FROM produse " + "WHERE id_film=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int idStock = result.getInt(1);
				final int quantity = result.getInt(2);
				final int price = result.getInt(3);

				stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				stock.setMovie(movie);
				stock.setStore(store);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}

	public Stock getGameStock(final Game game, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Stock stock = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, cant, pret FROM produse " + "WHERE id_joc=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int idStock = result.getInt(1);
				final int quantity = result.getInt(2);
				final int price = result.getInt(3);

				stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				stock.setGame(game);
				stock.setStore(store);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}

	public Stock getAlbumStock(final Album album, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Stock stock = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, cant, pret FROM produse " + "WHERE id_album=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int idStock = result.getInt(1);
				final int quantity = result.getInt(2);
				final int price = result.getInt(3);

				stock = new Stock();
				stock.setIdProduct(idStock);
				stock.setQuantity(quantity);
				stock.setPrice(price);
				stock.setAlbum(album);
				stock.setStore(store);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}

	public int insertMovieStock(final Movie movie, final Store store, final int quantity, final int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO dvdmania.produse (id_film, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			statement.setInt(2, store.getId());
			statement.setInt(3, quantity);
			statement.setInt(4, price);

			rowsInserted = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsInserted;
	}

	public int insertGameStock(final Game game, final Store store, final int quantity, final int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO dvdmania.produse (id_joc, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			statement.setInt(2, store.getId());
			statement.setInt(3, quantity);
			statement.setInt(4, price);

			rowsInserted = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsInserted;
	}

	public int insertAlbumStock(final Album album, final Store store, final int quantity, final int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO dvdmania.produse (id_album, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			statement.setInt(2, store.getId());
			statement.setInt(3, quantity);
			statement.setInt(4, price);

			rowsInserted = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsInserted;
	}

	public int updateMovieStock(final Movie movie, final Store store, final int quantity, final int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_film=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, quantity);
			statement.setInt(2, price);
			statement.setInt(3, movie.getIdMovie());
			statement.setInt(4, store.getId());

			rowsUpdated = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsUpdated;
	}

	public int updateGameStock(final Game game, final Store store, final int quantity, final int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_joc=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, quantity);
			statement.setInt(2, price);
			statement.setInt(3, game.getIdGame());
			statement.setInt(4, store.getId());

			rowsUpdated = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsUpdated;
	}

	public int updateAlbumStock(final Album album, final Store store, final int quantity, final int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_album=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, quantity);
			statement.setInt(2, price);
			statement.setInt(3, album.getIdAlbum());
			statement.setInt(4, store.getId());

			rowsUpdated = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsUpdated;
	}

	public int deleteMovieStock(final Movie movie, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsDeleted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "DELETE FROM dvdmania.produse WHERE id_film=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			statement.setInt(2, store.getId());

			rowsDeleted = statement.executeUpdate();

			if (checkMovieStock(movie) == 0) {
				final MovieManager movieMan = MovieManager.getInstance();
				movieMan.deleteMovie(movie);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsDeleted;
	}

	public int deleteGameStock(final Game game, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsDeleted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "DELETE FROM dvdmania.produse WHERE id_joc=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			statement.setInt(2, store.getId());

			rowsDeleted = statement.executeUpdate();

			if (checkGameStock(game) == 0) {
				final GameManager gameMan = GameManager.getInstance();
				gameMan.deleteGame(game);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsDeleted;
	}

	public int deleteAlbumStock(final Album album, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsDeleted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "DELETE FROM dvdmania.produse WHERE id_album=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			statement.setInt(2, store.getId());

			rowsDeleted = statement.executeUpdate();

			if (checkAlbumStock(album) == 0) {
				final AlbumManager albumMan = AlbumManager.getInstance();
				albumMan.deleteAlbum(album);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsDeleted;
	}

	public int checkMovieStock(final Movie movie) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT COUNT(*) FROM produse WHERE id_film=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			result = statement.executeQuery();

			if (result.next()) {
				stock = result.getInt(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}

	public int checkMovieStock(final Movie movie, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = -1;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, cant FROM produse WHERE id_film=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int idProd = result.getInt(1);
				final int quantity = result.getInt(2);

				if (idProd == 0) {
					break;
				}

				final OrderManager orderMan = OrderManager.getInstance();
				final int activeOrders = orderMan.getActiveOrders(idProd, store.getId());

				stock = quantity - activeOrders;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}
	
	public int checkMovieTotalStock(final Movie movie, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = -1;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT cant FROM produse WHERE id_film=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				stock = result.getInt("cant");
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}

	public int checkGameStock(final Game game) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_joc=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			result = statement.executeQuery();

			if (result.next()) {
				stock = result.getInt(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}

	public int checkGameStock(final Game game, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, cant FROM produse WHERE id_joc=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int idProd = result.getInt(1);
				final int quantity = result.getInt(2);

				if (idProd == 0) {
					break;
				}

				final OrderManager orderMan = OrderManager.getInstance();
				final int activeOrders = orderMan.getActiveOrders(idProd, store.getId());

				stock = quantity - activeOrders;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}
	
	public int checkGameTotalStock(final Game game, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = -1;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT cant FROM produse WHERE id_joc=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				stock = result.getInt("cant");
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}

	public int checkAlbumStock(final Album album) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_album=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			result = statement.executeQuery();

			if (result.next()) {
				stock = result.getInt(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}

	public int checkAlbumStock(final Album album, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_prod, cant FROM produse WHERE id_album=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int idProd = result.getInt(1);
				final int quantity = result.getInt(2);

				if (idProd == 0) {
					break;
				}

				final OrderManager orderMan = OrderManager.getInstance();
				final int activeOrders = orderMan.getActiveOrders(idProd, store.getId());

				stock = quantity - activeOrders;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}
	
	public int checkAlbumTotalStock(final Album album, final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int stock = -1;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT cant FROM produse WHERE id_album=? AND id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			statement.setInt(2, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				stock = result.getInt("cant");
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return stock;
	}
}
