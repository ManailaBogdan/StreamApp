import java.util.ArrayList;

public class StreamerAudiobook extends Streamer {

    private static ArrayList<StreamerAudiobook> streamersAudiobook = new ArrayList<StreamerAudiobook>();
    public StreamerAudiobook(int id, String nume) {
        super(id, nume);
        streamersAudiobook.add(this);
    }

    public static ArrayList<StreamerAudiobook> getStreamersAudiobook() {
        return streamersAudiobook;
    }
}



