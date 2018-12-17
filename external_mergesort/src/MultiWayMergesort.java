import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.PriorityQueue;

public class MultiWayMergesort {

    private List<InStream> streams;
    private List<Integer> limits;
    private OutStream output;
    private PriorityQueue<QueueItem> heap;

    private PrintWriter writer;

    public MultiWayMergesort(List<InStream> streams, List<Integer> limits, OutStream output, int minPos) throws IOException {
        this.streams = streams;
        this.limits = limits;
        this.output = output;
        heap = new PriorityQueue<>();

        writer = new PrintWriter("visual_output_" + "100" + ".txt");

        output.create(minPos);

        //read 1 item from each stream
        for(int i=0; i<streams.size(); i++) {
            streams.get(i).open(limits.get(i));
            if(!streams.get(i).end_of_stream()) {
                QueueItem qi = new QueueItem(i, streams.get(i).read_next());
                heap.add(qi);
            }
        }

        while(!heap.isEmpty()){

            QueueItem outItem = heap.remove(); //min number
            output.write(outItem.getNumber());
            writer.println(outItem.getNumber());

            int selectedStream = outItem.getStreamNum();
            int newNumber;

            if(!streams.get(selectedStream).end_of_stream()){
                newNumber = streams.get(outItem.getStreamNum()).read_next();
                QueueItem newItem = new QueueItem(selectedStream, newNumber);
                heap.add(newItem);
            }

        }

        closeAllStreams();
    }

    private void closeAllStreams() throws IOException {
        output.close();
        writer.close();
        for(int i=0; i<streams.size(); i++){
            streams.get(i).close();
        }
    }

}
