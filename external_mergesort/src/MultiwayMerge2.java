import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MultiwayMerge2 {
    private List<InStream> inStreams;
    private int d;
    private int B;
    private static int lalala = 0;

    public MultiwayMerge2(List<InStream> inStreams, int d, int B) throws IOException {
        this.inStreams = inStreams;
        this.d = d;
        this.B = B;
    }

    public void mergeD() throws IOException {
        List<InStream> dStreams = new ArrayList<>();
        String mwMergedFile = String.format("mwmerged/mwmerged%d.txt", lalala++);
        //PrintWriter writer = new PrintWriter(mwMergedFile + "visual.txt");
        WriterStream ws = new WriterStream(3, mwMergedFile, B);
        OutStream os = ws.getStream();
        os.create();
        int count = 0;
        // take first d streams
        InStream is;
        for (int i = 0; i < d; i++) {
            is = inStreams.remove(0);
            is.open();
            dStreams.add(is);
        }
        PriorityQueue<QueueItem> heap = new PriorityQueue<>();
        QueueItem qItem;
        // read one element from each stream and put it into the queue
        for (int i = 0; i < d; i++) {
            is = dStreams.get(i);
            qItem = new QueueItem(i, is.read_next());
            heap.add(qItem);
        }
        int sNum;
        while (!heap.isEmpty()) {
            qItem = heap.remove();
            count++;
            os.write(qItem.getNumber());
            //writer.println(qItem.getNumber());
            sNum = qItem.getStreamNum();
            is = dStreams.get(sNum);
            if (!is.end_of_stream()) {
                heap.add(new QueueItem(sNum, is.read_next()));
            }
        }
        os.close();
        InStream temp = new ReaderStream(4, mwMergedFile, B).getStream();
        inStreams.add(temp);
//        System.out.println(count + " filesize " + temp.getFileSize());
        //writer.close();
        return;
    }

    public void finalMerge() throws IOException {
//        int count = 0 ;
//        System.out.println("Start final merge");
//        for (InStream stream : inStreams) {
//            stream.open();
//            while (!stream.end_of_stream()) {
//                stream.read_next();
//                count++;
//            }
////            System.out.println("Final Merge");
//            System.out.println(count);
//        }
//        return;
        List<InStream> dStreams;
        String mwMergedFile = "final_output/output.txt";
        WriterStream ws = new WriterStream(3, mwMergedFile, B);
        //PrintWriter writer = new PrintWriter("final_output/visual_output.txt");
        OutStream os = ws.getStream();
        os.create();
        dStreams = inStreams;

        PriorityQueue<QueueItem> heap = new PriorityQueue<>();
        QueueItem qItem;
        int count = 0;
        int count_added = 0;
        for (int i = 0; i < dStreams.size(); i++) {
            dStreams.get(i).open();
            qItem = new QueueItem(i, dStreams.get(i).read_next());
            heap.add(qItem);
            count_added++;
        }
        while (!heap.isEmpty()) {
            qItem = heap.remove();
            count++;
            os.write(qItem.getNumber());
            //writer.println(qItem.getNumber());
            if (!dStreams.get(qItem.getStreamNum()).end_of_stream()) {
                heap.add(new QueueItem(qItem.getStreamNum(), dStreams.get(qItem.getStreamNum()).read_next()));
                count_added++;
            }
        }
        System.out.println("Final output written in " + mwMergedFile);
        System.out.println(count + " " + count_added);
        //writer.close();
        os.close();
        return;

    }

    public int getSize() {
        return inStreams.size();
    }
}
