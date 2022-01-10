package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ro.dvdmania.entities.Game;
import ro.dvdmania.entities.Store;

public class GameManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static GameManager instance = null;

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	public ArrayList<Game> getAllGames() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<Game> gameList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_joc, titlu, an, platforma, developer, publisher, gen, audienta FROM jocuri";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				final int id = result.getInt("id_joc");
				final String title = result.getString("titlu");
				final String platform = result.getString("platforma");
				final String developer = result.getString("developer");
				final String publisher = result.getString("publisher");
				final String genre = result.getString("gen");
				final int audience = result.getInt("audienta");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

				final Game game = new Game(id, title, year, platform, developer, publisher, genre, audience);
				gameList.add(game);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return gameList;
	}

	public ArrayList<Game> getGamesByStore(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Game> gameList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT j.id_joc, j.titlu, j.an, j.platforma, j.developer, j.publisher, j.gen, j.audienta FROM jocuri j "
					+ "JOIN produse p ON p.id_joc=j.id_joc JOIN magazin m ON m.id_mag=p.id_mag WHERE m.id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt("id_joc");
				final String title = result.getString("titlu");
				final String platform = result.getString("platforma");
				final String developer = result.getString("developer");
				final String publisher = result.getString("publisher");
				final String genre = result.getString("gen");
				final int audience = result.getInt("audienta");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

				final Game game = new Game(id, title, year, platform, developer, publisher, genre, audience);
				gameList.add(game);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return gameList;
	}

	public Game getGameByTitle(final String title) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Game game = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_joc, an, platforma, developer, publisher, gen, audienta FROM dvdmania.jocuri WHERE titlu=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, title);
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt("id_joc");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);
				final String platform = result.getString("platforma");
				final String developer = result.getString("developer");
				final String publisher = result.getString("publisher");
				final String genre = result.getString("gen");
				final int audience = result.getInt("audienta");

				game = new Game(id, title, year, platform, developer, publisher, genre, audience);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return game;
	}

	public Game getGameById(final int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Game game = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT titlu, an, platforma, developer, publisher, gen, audienta FROM jocuri WHERE id_joc=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();

			while (result.next()) {
				final String title = result.getString("titlu");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);
				final String platform = result.getString("platforma");
				final String developer = result.getString("developer");
				final String publisher = result.getString("publisher");
				final String genre = result.getString("gen");
				final int audience = result.getInt("audienta");

				game = new Game(id, title, year, platform, developer, publisher, genre, audience);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return game;
	}

	public int createGame(final Game game) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO jocuri (titlu, platforma, developer, publisher, gen, an, audienta) VALUES (?, ?, ?, ?, ?, YEAR(?), ?)";
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, game.getTitle());
			statement.setString(2, game.getPlatform());
			statement.setString(3, game.getDeveloper());
			statement.setString(4, game.getPublisher());
			statement.setString(5, game.getGenre());
			statement.setDate(6, Date.valueOf(game.getYear() + "-01-01"));
			statement.setInt(7, game.getAudience());
			rowsInserted = statement.executeUpdate();

			final ResultSet keySet = statement.getGeneratedKeys();
			while (keySet.next()) {
				final int id = keySet.getInt(1);
				game.setIdGame(id);
			}
			keySet.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsInserted;
	}

	public int updateGame(final Game game) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.jocuri SET titlu=?, platforma=?, developer=?, publisher=?, gen=?, an=YEAR(?), audienta=? WHERE id_joc=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, game.getTitle());
			statement.setString(2, game.getPlatform());
			statement.setString(3, game.getDeveloper());
			statement.setString(4, game.getPublisher());
			statement.setString(5, game.getGenre());
			statement.setDate(6, Date.valueOf(game.getYear() + "-01-01"));
			statement.setInt(7, game.getAudience());
			statement.setInt(8, game.getIdGame());
			rowsUpdated = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsUpdated;
	}

	public int deleteGame(final Game game) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsDeleted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "DELETE FROM dvdmania.jocuri WHERE id_joc=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, game.getIdGame());
			rowsDeleted = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsDeleted;
	}

	public ArrayList<String> getGenres() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<String> genres = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT DISTINCT gen FROM dvdmania.jocuri";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			genres.add("Toate");
			while (result.next()) {
				genres.add(result.getString(1));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return genres;
	}

	public String[] gameToRow(final Game game, final int price) {
		final String[] row = new String[9];
		row[0] = game.getIdGame() + "";
		row[1] = game.getTitle();
		row[2] = game.getPlatform();
		row[3] = game.getDeveloper();
		row[4] = game.getPublisher();
		row[5] = game.getGenre();
		row[6] = game.getYear();
		row[7] = game.getAudience() + "";
		row[8] = price + "";

		return row;
	}
}
