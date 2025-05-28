package music;

import database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public record Song(String artist, String title, String length) {
    public static class Persistence{
        public static Optional<Song> read(int index){
            Optional<Song> optionalSong = Optional.empty();
            try {
                String sql = "SELECT artist, title, length FROM song WHERE id = ?";
                PreparedStatement statement = DatabaseConnection.getConnection("songs").prepareStatement(sql);
                statement.setInt(1, index);
                statement.execute();

                ResultSet result = statement.getResultSet();
                if(!result.next()) throw new IndexOutOfBoundsException("no song with this index");

                optionalSong = Optional.of(new Song(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3)
                ));


            }catch (SQLException e){
                throw new RuntimeException(e);
            }

            return optionalSong;
        }
    }
}
