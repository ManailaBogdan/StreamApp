import java.util.ArrayList;

public class StreamerMuzica extends Streamer{

    private static ArrayList<StreamerMuzica> streamersMuzica = new ArrayList<StreamerMuzica>();
    public StreamerMuzica(int id, String nume) {
        super(id, nume);
        streamersMuzica.add(this);
    }

    public static ArrayList<StreamerMuzica> getStreamersMuzica() {
        return streamersMuzica;
    }
}
