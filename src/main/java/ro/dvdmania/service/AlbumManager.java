package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ro.dvdmania.entities.Album;
import ro.dvdmania.entities.Store;

public class AlbumManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static AlbumManager instance = null;

	public static AlbumManager getInstance() {
		if (instance == null) {
			instance = new AlbumManager();
		}
		return instance;
	}

	public ArrayList<Album> getAllAlbums() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<Album> albumList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_album, trupa, titlu, nr_mel, casa_disc, gen, an FROM albume";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				final int id = result.getInt("id_album");
				final String artist = result.getString("trupa");
				final String title = result.getString("titlu");
				final int nrSongs = result.getInt("nr_mel");
				final String publisher = result.getString("casa_disc");
				final String genre = result.getString("gen");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

				final Album album = new Album(id, artist, title, nrSongs, genre, publisher, year);
				albumList.add(album);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return albumList;
	}

	public ArrayList<Album> getAlbumsByStore(final Store store) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Album> albumList = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT a.id_album, a.trupa, a.titlu, a.nr_mel, a.casa_disc, a.gen, a.an FROM albume a "
					+ "JOIN produse p ON a.id_album=p.id_album JOIN magazin m ON m.id_mag=p.id_mag WHERE m.id_mag=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, store.getId());
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt("id_album");
				final String artist = result.getString("trupa");
				final String title = result.getString("titlu");
				final int nrSongs = result.getInt("nr_mel");
				final String publisher = result.getString("casa_disc");
				final String genre = result.getString("gen");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

				final Album album = new Album(id, artist, title, nrSongs, genre, publisher, year);
				albumList.add(album);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return albumList;
	}

	public Album getAlbumByTitle(final String title) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Album album = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_album, trupa, nr_mel, casa_disc, gen, an FROM dvdmania.jocuri WHERE titlu=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, title);
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt("id_album");
				final String artist = result.getString("trupa");
				final int nrSongs = result.getInt("nr_mel");
				final String publisher = result.getString("casa_disc");
				final String genre = result.getString("gen");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

				album = new Album(id, artist, title, nrSongs, genre, publisher, year);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return album;
	}

	public Album getAlbumById(final int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Album album = null;

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT titlu, trupa, nr_mel, casa_disc, gen, an FROM albume WHERE id_album=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();

			while (result.next()) {
				final String title = result.getString("titlu");
				final String artist = result.getString("trupa");
				final int nrSongs = result.getInt("nr_mel");
				final String publisher = result.getString("casa_disc");
				final String genre = result.getString("gen");
				final String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

				album = new Album(id, artist, title, nrSongs, genre, publisher, year);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return album;
	}

	public int createAlbum(final Album album) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO albume (titlu, trupa, nr_mel , casa_disc, gen, an) VALUES (?, ?, ?, ?, ?, YEAR(?))";
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, album.getTitle());
			statement.setString(2, album.getArtist());
			statement.setInt(3, album.getNrMel());
			statement.setString(4, album.getProducer());
			statement.setString(5, album.getGenre());
			statement.setDate(6, Date.valueOf(album.getYear() + "-01-01"));

			rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				final ResultSet keySet = statement.getGeneratedKeys();
				if (keySet.next()) {
					album.setIdAlbum(keySet.getInt(1));
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

	public int updateAlbum(final Album album) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsUpdated = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "UPDATE dvdmania.albume SET titlu=?, trupa=?, nr_mel=?, casa_disc=?, gen=?, an=YEAR(?) WHERE id_album=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, album.getTitle());
			statement.setString(2, album.getArtist());
			statement.setInt(3, album.getNrMel());
			statement.setString(4, album.getProducer());
			statement.setString(5, album.getGenre());
			statement.setDate(6, Date.valueOf(album.getYear() + "-01-01"));
			statement.setInt(7, album.getIdAlbum());
			rowsUpdated = statement.executeUpdate();

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsUpdated;
	}

	public int deleteAlbum(final Album album) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsDeleted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "DELETE FROM dvdmania.albume WHERE id_album=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, album.getIdAlbum());
			rowsDeleted = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsDeleted;
	}

	public ArrayList<String> getAlbumNames() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<String> albumNames = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT titlu FROM dvdmania.albume";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				albumNames.add(result.getString(1));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return albumNames;
	}

	public ArrayList<String> getGenres() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		final ArrayList<String> genres = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT DISTINCT gen FROM dvdmania.albume";
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

	public String[] albumToRow(final Album album, final int price) {
		final String[] row = new String[8];
		row[0] = album.getIdAlbum() + "";
		row[1] = album.getArtist();
		row[2] = album.getTitle();
		row[3] = album.getProducer();
		row[4] = album.getNrMel() + "";
		row[5] = album.getGenre();
		row[6] = album.getYear();
		row[7] = price + "";

		return row;
	}
}
