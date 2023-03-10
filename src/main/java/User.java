import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class User {

    private int id;
    private String name;
    private ArrayList<Stream> streams;
    private static ArrayList<User> users = new ArrayList<User>();

    private Stream deletedStream = null;

    public User(int id, String name, ArrayList<Stream> streams) {
        this.id = id;
        this.name = name;
        this.streams = streams;
        users.add(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString (){
        return "Utilizatorul cu id-ul " + id + " si numele " + name + " a vizionat urmatoarele stream-uri: " + streams;
    }

    public static void userFileReader(String path){

        ArrayList<Stream> streams = Stream.getStreams();
        User[] users = new User[100];
        int i = 0;

        try {
            FileReader filereader = new FileReader(path);
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            String[] nextRecord;
            User user = null;
            while ((nextRecord = csvReader.readNext()) != null) {

                int id = Integer.parseInt(nextRecord[0]);
                String name = nextRecord[1];
                String[] streamIds = nextRecord[2].split(" ");
                ArrayList<Stream> streamList = new ArrayList<Stream>();
                for (String streamId : streamIds) {
                    Stream stream = Stream.getStreamById(Integer.parseInt(streamId));
                    stream.addObserver(user);

                    streamList.add(stream);

                }

                user = new User(id, name, streamList);
                for(Stream stream: streamList){
                    stream.getStreamer().addListener(user);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUserById(int id){
        for (User user : users) {
            if(user == null)
                break;
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void listStreams(){
        int i = 0;
        System.out.print("[");
        for (Stream stream : streams) {
            if(i != 0) {
                System.out.print(",");
            }
            System.out.print(stream);
            i++;

        }
        System.out.println("]");
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public void update(Stream stream){
        deletedStream = stream;
        streams.remove(deletedStream);
    }

    public void addStream(Stream stream){
        streams.add(stream);

    }

    public PriorityQueue recommendStreams(StreamerType type){
        PriorityQueue<Stream> coada = new PriorityQueue<Stream>();
        switch (type){
            case MUZICA:
                for(Streamer singer: StreamerMuzica.getStreamersMuzica()){
                    if(singer.isListener(this))
                        for(Stream stream: singer.getStreams()){
                            if(!streams.contains(stream))
                                coada.add(stream);
                        }
                }
                break;

            case PODCAST:

                for(Streamer podcast: StreamerPodcast.getStreamersPodcast()){

                    if(podcast.isListener(this)) {

                        for (Stream stream : podcast.getStreams()) {

                            if (!streams.contains(stream))
                                coada.add(stream);
                        }
                    }
                }
                break;

            case AUDIOBOOK:
                for(Streamer audiobook: StreamerAudiobook.getStreamersAudiobook()){
                    if(audiobook.isListener(this))
                        for(Stream stream: audiobook.getStreams()){
                            if(!streams.contains(stream))
                                coada.add(stream);
                        }
                }
                break;
            default:
                break;
        }
        return coada;
    }

    public PriorityQueue supriseStreams(StreamerType type){

        PriorityQueue<Stream> coada = new PriorityQueue<Stream>();
        switch (type){
            case MUZICA:
                for(Streamer singer: StreamerMuzica.getStreamersMuzica()){
                    if(!singer.isListener(this))
                        for(Stream stream: singer.getStreams()){
                                coada.add(stream);
                        }
                }
                break;

            case PODCAST:
                for(Streamer podcast: StreamerPodcast.getStreamersPodcast()){
                    if(!podcast.isListener(this))
                        for(Stream stream: podcast.getStreams()){
                                coada.add(stream);
                        }
                }
                break;

            case AUDIOBOOK:
                for(Streamer audiobook: StreamerAudiobook.getStreamersAudiobook()){
                    if(!audiobook.isListener(this))
                        for(Stream stream: audiobook.getStreams()){
                                coada.add(stream);
                        }
                }
                break;
            default:
                break;
        }
        return coada;
    }
}
