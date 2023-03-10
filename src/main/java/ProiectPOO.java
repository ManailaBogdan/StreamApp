import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.PriorityQueue;

public class ProiectPOO {

    public static void main(String[] args) {
        if(args == null) {
            System.out.println("Nothing to read here");
            return;
        }


        String pathStremares = "src/main/resources/" + args[0];
        Streamer.streamersFileReader(pathStremares);

        String pathStreams = "src/main/resources/" + args[1];
        Stream.streamsFileReader(pathStreams);

        String pathUsers = "src/main/resources/" + args[2];
        User.userFileReader(pathUsers);

        String pathCommands = "src/main/resources/" + args[3];

        try{

            FileReader fr = new FileReader(pathCommands);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null) {
                String[] command = line.split(" ");
                switch (command[1]){
                    case "LIST":
                        int id = Integer.parseInt(command[0]);
                        User user = User.getUserById(id);
                        if(user != null)
                            user.listStreams();

                        Streamer streamer = Streamer.getStreamerById(id);
                        if(streamer != null)
                            streamer.listStreams();

                        break;
                    case "ADD":
                        streamer = Streamer.getStreamerById(Integer.parseInt(command[0]));
                        Integer streamId = Integer.parseInt(command[3]);
                        Long lenght = Long.parseLong(command[4]);
                        String name = command[5];
                        Date date = new Date();
                        Stream stream = new Stream(streamId,  name, streamer, Long.parseLong("0"), lenght, date);
                        break;
                    case "DELETE":
                        streamer = Streamer.getStreamerById(Integer.parseInt(command[0]));
                        stream = Stream.getStreamById(Integer.parseInt(command[2]));
                        streamer.deleteStream(stream);
                        stream.deleteStream();

                        break;
                    case "LISTEN":
                        user = User.getUserById(Integer.parseInt(command[0]));
                        stream = Stream.getStreamById(Integer.parseInt(command[2]));
                        stream.listenStream(user);

                        break;
                    case "RECOMMEND":
                        user = User.getUserById(Integer.parseInt(command[0]));
                        StreamerType type = null;
                        switch (command[2]){
                            case "SONG":
                                type = StreamerType.MUZICA;
                                break;
                            case "PODCAST":
                                type = StreamerType.PODCAST;
                                break;
                            case "AUDIOBOOK":
                                type = StreamerType.AUDIOBOOK;
                                break;
                            default:
                                break;
                        }
                        Stream.setCompareType(CompareType.NOOFLISTENINGS);

                        PriorityQueue<Stream> recommendedStreams = user.recommendStreams(type);
                        System.out.print("[");
                        for(int i = 0; i < 5; i ++){

                            if(!recommendedStreams.isEmpty()){
                                if(i != 0)
                                    System.out.print(",");
                                System.out.print(recommendedStreams.poll());
                            }

                        }
                        System.out.println("]");

                        break;
                    case "SURPRISE":
                        user = User.getUserById(Integer.parseInt(command[0]));
                        type = null;
                        switch (command[2]){
                            case "SONG":
                                type = StreamerType.MUZICA;
                                break;
                            case "PODCAST":
                                type = StreamerType.PODCAST;
                                break;
                            case "AUDIOBOOK":
                                type = StreamerType.AUDIOBOOK;
                                break;
                            default:
                                break;
                        }
                        Stream.setCompareType(CompareType.DATE);
                        PriorityQueue<Stream> surpriseStreams = user.supriseStreams(type);

                        System.out.print("[");
                        for(int i = 0; i < 3; i ++){

                            if(!surpriseStreams.isEmpty()){
                                if(i != 0)
                                    System.out.print(",");
                                System.out.print(surpriseStreams.poll());
                            }

                        }
                        System.out.println("]");

                        break;
                    default:
                        break;
                }

                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }




    }
}
