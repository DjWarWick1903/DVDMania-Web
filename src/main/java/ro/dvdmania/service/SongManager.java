package ro.dvdmania.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ro.dvdmania.entities.Album;
import ro.dvdmania.entities.Song;

public class SongManager {

	private final ConnectionManager connMan = ConnectionManager.getInstance();
	private static SongManager instance = null;

	public static SongManager getInstance() {
		if (instance == null) {
			instance = new SongManager();
		}
		return instance;
	}

	public ArrayList<Song> getSongs(final int idAlbum) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final ArrayList<Song> songs = new ArrayList<>();

		try {
			connection = connMan.openConnection();
			final String sql = "SELECT id_muzica, nume, durata FROM dvdmania.muzica WHERE id_album=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idAlbum);
			result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt(1);
				final String name = result.getString(2);
				final int duration = result.getInt(3);
				final Song song = new Song(id, name, duration);
				songs.add(song);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement, result);
		}

		return songs;
	}

	public int CreateSong(final Album album, final Song song) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsInserted = 0;

		try {
			connection = connMan.openConnection();
			final String sql = "INSERT INTO dvdmania.muzica (nume, durata, id_album) VALUES (?, ?, ?)";
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, song.getNume());
			statement.setInt(2, song.getDuration());
			statement.setInt(3, album.getIdAlbum());
			rowsInserted = statement.executeUpdate();

			if (rowsInserted != 0) {
				final ResultSet keys = statement.getGeneratedKeys();
				if (keys.next()) {
					song.setIdSong(keys.getInt(1));
				}
				keys.close();
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			connMan.closeConnection(connection, statement);
		}

		return rowsInserted;
	}

	public String[] songToRow(final Song song) {
		final String[] row = new String[3];
		row[0] = song.getIdSong() + "";
		row[1] = song.getNume();
		row[2] = song.getDuration() + "";

		return row;
	}
}
