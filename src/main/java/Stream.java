import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Stream implements Comparable<Stream>{

    private int id;
    private String nume;

    private StreamType type;
    private Streamer streamer;
    private Long nrStreams;
    private Long lenght;
    private Date date;

    public static CompareType compareType = CompareType.DATE;

    private ArrayList<User> observers = new ArrayList<User>();

    private static ArrayList<Stream> streams = new ArrayList<Stream>();

    public Stream(int id, String nume, Streamer streamer, Long nrStreams, Long lenght, Date date) {
        this.id = id;
        this.nume = nume;

        this.streamer = streamer;
        this.nrStreams = nrStreams;
        this.lenght = lenght;
        this.date = date;
        streams.add(this);
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public Streamer getStreamer() {
        return streamer;
    }

    public Long getNrStreams() {
        return nrStreams;
    }

    public Long getLenght() {
        return lenght;
    }

    public Date getDate() {
        return date;
    }

    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-YYYY");
        String strDate = dateFormatter.format(date);

        Long ms = lenght * 1000;
        Date time = new Date(ms);
        SimpleDateFormat timeFormatter;
        if(ms < 36000000)
            timeFormatter = new SimpleDateFormat("mm:ss");
        else
            timeFormatter = new SimpleDateFormat("HH:mm:ss");

        String strTime = timeFormatter.format(time);

        return "{" +
                "\"id\":\"" + id +
                "\",\"name\":\"" + nume + '\"' +
                ",\"streamerName\":\"" + streamer.getNume() +
                "\",\"noOfListenings\":\"" + nrStreams +
                "\",\"length\":\"" + strTime +
                "\",\"dateAdded\":\"" + strDate +
                "\"}";
    }

    public static ArrayList<Stream> getStreams(){
        return streams;
    }

    public static  void streamsFileReader (String path){

        ArrayList<Streamer> streamers = Streamer.getStreamers();
        Stream[] streams = new Stream[100];

        int i = 0;

        try {
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(path)).withSkipLines(1).build();
            String[] nextLine;
            Stream stream;
            while ((nextLine = csvReader.readNext()) != null) {

                Streamer streamer = Streamer.getStreamerById(Integer.parseInt(nextLine[4]));
                Date date = new Date(Long.parseLong(nextLine[6]) * 1000);
                stream = new Stream(Integer.parseInt(nextLine[1]), nextLine[7], streamer, Long.parseLong(nextLine[3]), Long.parseLong(nextLine[5]), date);

                if(stream != null)
                    streamer.addStream(stream);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Stream getStreamById(int id){
        for(Stream stream : streams){
            if(stream.getId() == id)
                return stream;
        }
        return null;
    }

    public static void addStream(Stream stream){
        streams.add(stream);
    }


    public void addObserver(User user){
        observers.add(user);
    }

    public void removeObserver(User user){
        observers.remove(user);
    }

    public void notifyObservers(){
        for(User user : observers){
            user.update(this);
        }
    }

    public void deleteStream(){
        notifyObservers();
        streams.remove(this);
    }

    public void listenStream(User user){
        nrStreams++;
        user.addStream(this);

    }



    public int compareTo(Stream stream) {
        switch (compareType){
            case NOOFLISTENINGS:
                if(nrStreams > stream.getNrStreams())
                    return -1;
                else if(nrStreams < stream.getNrStreams())
                    return 1;
                else
                    return 0;

            case DATE:
                if(date.after(stream.getDate()))
                    return -1;
                else if(date.before(stream.getDate()))
                    return 1;
                else  if(nrStreams > stream.getNrStreams())
                    return -1;
                else if(nrStreams < stream.getNrStreams())
                    return 1;
                else
                    return 0;
            default:
                return 0;
        }
    }

    public static void setCompareType(CompareType type){
        compareType = type;
    }
}

