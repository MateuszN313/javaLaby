import database.DatabaseConnection;
import music.Playlist;
import music.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest {
    @Test
    void testIsArrayEmpty(){
        Playlist playlist = new Playlist();
        assertEquals(0, playlist.size());
    }
    @Test
    void testPlaylistSize(){
        Playlist playlist = new Playlist();
        playlist.add(new Song("UFO", "Rock Bottom", "389"));
        assertEquals(1, playlist.size());
    }
    @Test
    void testCorrectAdd(){
        Playlist playlist = new Playlist();
        Song song = new Song("UFO", "Rock Bottom", "389");
        playlist.add(song);
        assertEquals(song,playlist.getLast());
    }
    @Test
    void testAtSecond(){
        Playlist playlist = new Playlist();
        Song song = new Song("UFO", "Rock Bottom", "389");
        playlist.add(song);
        assertEquals(song, playlist.atSecond(100));
    }
    @Test
    void testAtSecondThrowsTooLongException(){
        Playlist playlist = new Playlist();
        playlist.add(new Song("UFO", "Rock Bottom", "389"));
        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(500));
        assertEquals("this playlist is shorter", e.getMessage());
    }
    @Test
    void testAtSecondThrowsLessThanZeroException(){
        Playlist playlist = new Playlist();
        playlist.add(new Song("UFO", "Rock Bottom", "389"));
        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-2));
        assertEquals("number of seconds can't be lower than zero", e.getMessage());
    }
    @Test
    void testReadSongsDB(){
        DatabaseConnection.connect("songs.db", "songs");
        Optional<Song> song = Song.Persistence.read(1);
        Optional<Song> expected = Optional.of(new Song("The Beatles", "Hey Jude", "431"));
        assertEquals(expected,song);
        DatabaseConnection.disconnect("songs");
    }
    @Test
    void testWrongIndexSongsDB(){
        DatabaseConnection.connect("songs.db", "songs");
        assertThrows(IndexOutOfBoundsException.class, () -> Song.Persistence.read(-1));
        DatabaseConnection.disconnect("songs");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/songs.csv", numLinesToSkip = 1)
    void testDBWithCSVFile(String id, String artist, String title, String length){
        DatabaseConnection.connect("songs.db", "songs");
        try {
            PreparedStatement statement = DatabaseConnection.getConnection("songs").prepareStatement(
                    "SELECT artist, title, length FROM song WHERE id = ?"
            );
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                assertEquals(artist, rs.getString("artist"));
                assertEquals(title, rs.getString("title"));
                assertEquals(length, rs.getString("length"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        DatabaseConnection.disconnect("songs");
    }
}
