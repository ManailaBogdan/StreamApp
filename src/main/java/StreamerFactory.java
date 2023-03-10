public class StreamerFactory {

    public static StreamerFactory instance = null;

    private StreamerFactory(){
    }

    public static StreamerFactory getInstance(){
        if(instance == null){
            instance = new StreamerFactory();
        }
        return instance;
    }

    public Streamer getStreamer(StreamerType type, int id, String nume){
        switch(type){
            case MUZICA:
                return new StreamerMuzica(id, nume);
            case PODCAST:
                return new StreamerPodcast(id, nume);
            case AUDIOBOOK:
                return new StreamerAudiobook(id, nume);
            default:
                return null;
        }
    }
}
