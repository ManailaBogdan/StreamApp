import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.util.ArrayList;

public class Streamer {

    private int id;
    private String nume;

    private ArrayList<Stream> streams = new ArrayList<Stream>();
    private ArrayList<User> listeners = new ArrayList<User>();
    private static ArrayList<Streamer> streamers = new ArrayList<Streamer>();

    public Streamer(int id, String nume) {
        this.id = id;
        this.nume = nume;
        streamers.add(this);
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String toString() {
        return "Streamerul " + nume + " are id-ul " + id;
    }

    public static void streamersFileReader(String path){

        Streamer[] streamers = new Streamer[100];
        StreamerFactory streamerFactory = StreamerFactory.getInstance();
        int i = 0;
        try {
            FileReader fr = new FileReader(path);
            CSVReader csvReader = new CSVReaderBuilder(fr).withSkipLines(1).build();
            Streamer streamer;

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                switch (nextRecord[0]) {
                    case "1":
                        streamer = streamerFactory.getStreamer(StreamerType.MUZICA, Integer.parseInt(nextRecord[1]), nextRecord[2]);
                        break;
                    case "2":
                        streamer = streamerFactory.getStreamer(StreamerType.PODCAST, Integer.parseInt(nextRecord[1]), nextRecord[2]);
                        break;
                    case "3":
                        streamer = streamerFactory.getStreamer(StreamerType.AUDIOBOOK, Integer.parseInt(nextRecord[1]), nextRecord[2]);
                        break;
                    default:
                        break;
                }

                i++;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static Streamer getStreamerById(int id){
        for(Streamer streamer : streamers){
            if(streamer != null && streamer.getId() == id){
                return streamer;
            }
        }
        return null;
    }

    public void addStream(Stream stream){
        streams.add(stream);
    }

    public void listStreams(){
        int i = 0;
        System.out.print("[");
        for(Stream stream : streams){
            if(i != 0) {
                System.out.print(",");
            }
            System.out.print(stream);
            i++;
        }
        System.out.println("]");
    }

    public static ArrayList<Streamer> getStreamers() {
        return streamers;
    }

    public void deleteStream(Stream stream){
        streams.remove(stream);
    }

    public void addListener(User user){
        listeners.add(user);
    }

    public boolean isListener(User user){
        return listeners.contains(user);
    }

    public ArrayList<Stream> getStreams() {
        return streams;
    }

    public void printListeners(){

        for(User user : listeners){

            System.err.print(user.getId() + " ");
        }
        System.err.println();

    }


}
