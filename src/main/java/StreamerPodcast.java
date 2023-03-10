import java.util.ArrayList;

public class StreamerPodcast extends Streamer{

    private static ArrayList<StreamerPodcast> streamersPodcast = new ArrayList<StreamerPodcast>();
    public StreamerPodcast(int id, String nume) {
        super(id, nume);
        streamersPodcast.add(this);
    }

    public static ArrayList<StreamerPodcast> getStreamersPodcast() {
        return streamersPodcast;
    }
}
