package music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Playlist() {
        super();
    }
    public Song atSecond(int seconds) throws IndexOutOfBoundsException{
        if(seconds < 0){
            throw new IndexOutOfBoundsException("number of seconds can't be lower than zero");
        }
        for(Song song : this){
            seconds -= Integer.parseInt(song.length());
            if(seconds<=0){
                return song;
            }
        }
        throw new IndexOutOfBoundsException("this playlist is shorter");
    }
}
