package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ro.dvdmania.entities.Movie;
import ro.dvdmania.entities.Store;

public class MovieManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static MovieManager instance = null;

	public static MovieManager getInstance() {
		if (instance == null) {
			instance = new MovieManager();
		}
		return instance;
	}

	public ArrayList<Movie> getAllMovies() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<Movie> movieList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_film, titlu, actor_pr, director, durata, gen, an, audienta FROM filme";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				final int id = result.getInt("id_film");
				final String title = result.getString("titlu");
				final String mainActor = result.getString("actor_pr");
				final String director = result.getString("director");
				final int duration = result.getInt("durata");
				final String genre = result.getString("gen");
				final int audience = result.getInt("audienta");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

				final Movie movie = new Movie(id, title, mainActor, director, duration, genre, year, audience);
				movieList.add(movie);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return movieList;
	}

	public ArrayList<Movie> getMovieByStore(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Movie> movieList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT f.id_film, f.titlu, f.actor_pr, f.director, f.durata, f.gen, f.an, f.audienta FROM filme f "
					+ "JOIN produse p ON p.id_film=f.id_film JOIN magazin m ON m.id_mag=p.id_mag WHERE m.id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt("id_film");
				final String title = result.getString("titlu");
				final String mainActor = result.getString("actor_pr");
				final String director = result.getString("director");
				final int duration = result.getInt("durata");
				final String genre = result.getString("gen");
				final int audience = result.getInt("audienta");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

				final Movie movie = new Movie(id, title, mainActor, director, duration, genre, year, audience);
				movieList.add(movie);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return movieList;
	}

	public Movie getMovieByTitle(final String title) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Movie movie = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_film, actor_pr, director, durata, gen, an, audienta FROM dvdmania.filme WHERE titlu=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, title);
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt("id_film");
				final String mainActor = result.getString("actor_pr");
				final String director = result.getString("director");
				final int duration = result.getInt("durata");
				final String genre = result.getString("gen");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);
				final int audience = result.getInt("audienta");

				movie = new Movie(id, title, mainActor, director, duration, genre, year, audience);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return movie;
	}

	public Movie getMovieById(final int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Movie movie = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT titlu, actor_pr, director, durata, gen, an, audienta FROM filme WHERE id_film=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();

			while (result.next()) {
				final String title = result.getString("titlu");
				final String mainActor = result.getString("actor_pr");
				final String director = result.getString("director");
				final int duration = result.getInt("durata");
				final String genre = result.getString("gen");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);
				final int audience = result.getInt("audienta");

				movie = new Movie(id, title, mainActor, director, duration, genre, year, audience);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return movie;
	}

	public int createMovie(final Movie movie) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO dvdmania.filme (titlu, actor_pr, director, durata, gen, an, audienta) VALUES (?, ?, ?, ?, ?, YEAR(?), ?)";
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, movie.getTitle());
			statement.setString(2, movie.getMainActor());
			statement.setString(3, movie.getDirector());
			statement.setInt(4, movie.getDuration());
			statement.setString(5, movie.getGenre());
			statement.setDate(6, Date.valueOf(movie.getYear() + "-01-01"));
			statement.setInt(7, movie.getAudience());

			rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				final ResultSet keySet = statement.getGeneratedKeys();
				if (keySet.next()) {
					movie.setIdMovie(keySet.getInt(1));
				}
				keySet.close();
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsInserted;
	}

	public int updateMovie(final Movie movie) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE filme SET titlu=?, actor_pr=?, director=?, durata=?, gen=?, an=YEAR(?), audienta=? WHERE id_film=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, movie.getTitle());
			statement.setString(2, movie.getMainActor());
			statement.setString(3, movie.getDirector());
			statement.setInt(4, movie.getDuration());
			statement.setString(5, movie.getGenre());
			statement.setDate(6, Date.valueOf(movie.getYear() + "-01-01"));
			statement.setInt(7, movie.getAudience());
			statement.setInt(8, movie.getIdMovie());
			rowsInserted = statement.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsInserted;
	}

	public int deleteMovie(final Movie movie) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsDeleted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "DELETE FROM dvdmania.filme WHERE id_film=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, movie.getIdMovie());
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
			final String sql = "SELECT DISTINCT gen FROM dvdmania.filme";
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

	public String[] movieToRow(final Movie movie, final int price) {
		final String[] row = new String[9];
		row[0] = movie.getIdMovie() + "";
		row[1] = movie.getTitle();
		row[2] = movie.getMainActor();
		row[3] = movie.getDirector();
		row[4] = movie.getDuration() + "";
		row[5] = movie.getGenre();
		row[6] = movie.getYear();
		row[7] = movie.getAudience() + "";
		row[8] = price + "";

		return row;
	}
}
