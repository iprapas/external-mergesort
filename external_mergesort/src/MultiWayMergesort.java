import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MultiWayMergesort {

    private List<ReaderStream> streams;
    private List<Integer> limits;
    private Integer[] integersRead;
    private WriterStream output;
    private PriorityQueue<QueueItem> heap;

    private PrintWriter writer;

    public MultiWayMergesort(List<ReaderStream> streams, List<Integer> limits, WriterStream output, int minPos, String inputFile, int M) throws IOException {
        this.streams = streams;
        this.limits = limits;
        this.output = output;
        heap = new PriorityQueue<>();

        writer = new PrintWriter("visual_" + inputFile);

        output.getStream().create(minPos);

        integersRead = new Integer[streams.size()];
        for(int i=0; i<streams.size(); i++){
            integersRead[i] = 0;
        }

        //read 1 item from each stream
        for(int i=0; i<streams.size(); i++) {
            streams.get(i).getStream().open(limits.get(i));
            if(!streams.get(i).getStream().end_of_stream()) { //no need to check end of stream now...
                QueueItem qi = new QueueItem(i, streams.get(i).getStream().read_next());
                heap.add(qi);
                integersRead[i]+=1;
            }
        }

        while(!heap.isEmpty()){

            QueueItem outItem = heap.remove(); //min number
            output.getStream().write(outItem.getNumber());
            System.out.print("Removed: " + outItem.getNumber() + " from stream " + outItem.getStreamNum());
            writer.println(outItem.getNumber());

            int selectedStream = outItem.getStreamNum();

            if(selectedStream == (streams.size()-1) && streams.size() == limits.size()){ //last stream
                if (!streams.get(selectedStream).getStream().end_of_stream()) {
                    int newNumber = streams.get(selectedStream).getStream().read_next();
                    QueueItem newItem = new QueueItem(selectedStream, newNumber);
                    heap.add(newItem);
                    integersRead[selectedStream]+=1;
                    System.out.println(" --- New: " + newNumber +  " from stream " + outItem.getStreamNum());
                }
            } else {
                if (!streams.get(selectedStream).getStream().end_of_stream() && integersRead[selectedStream] < (limits.get(selectedStream + 1) - limits.get(selectedStream))) {
                    int newNumber = streams.get(selectedStream).getStream().read_next();
                    QueueItem newItem = new QueueItem(selectedStream, newNumber);
                    heap.add(newItem);
                    integersRead[selectedStream] += 1;
                    System.out.println(" --- New: " + newNumber + " from stream " + outItem.getStreamNum());
                }
            }
        }

        closeAllStreams();
    }

    private void closeAllStreams() throws IOException {
        output.getStream().close();
        writer.close();
        for(int i=0; i<streams.size(); i++){
            streams.get(i).getStream().close();
        }
    }

}
