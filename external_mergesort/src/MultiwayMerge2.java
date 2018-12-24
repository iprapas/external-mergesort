import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MultiwayMerge2 {
    private List<InStream> inStreams;
    private int d;
    private int B;
    private int mwCount = 0;
    private int R_IMPLEMENTATION = Main.R_IMPLEMENTATION;
    private int W_IMPLEMENTATION = Main.W_IMPLEMENTATION;

    public MultiwayMerge2(List<InStream> inStreams, int d, int B) throws IOException {
        this.inStreams = inStreams;
        if (d > Main.M){
            this.d = Main.M;
        } else {
            this.d = d;
        }
        this.B = B;
    }

    public void mergeD() throws IOException {
        List<InStream> dStreams = new ArrayList<>();
        String mwMergedFile = String.format("/home/io_prapas/temp/mwmerged/mwmerged%d.txt", mwCount++);
//        PrintWriter writer = new PrintWriter(mwMergedFile + "_visual.txt");
        WriterStream ws = new WriterStream(W_IMPLEMENTATION,mwMergedFile,B);
        OutStream os = ws.getStream();
        os.create();
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
            os.write(qItem.getNumber());
//            writer.println(qItem.getNumber());
            sNum = qItem.getStreamNum();
            is = dStreams.get(sNum);
            if (!is.end_of_stream()) {
                heap.add(new QueueItem(sNum, is.read_next()));
            }
        }
        os.close();
        InStream temp =  new ReaderStream(R_IMPLEMENTATION, mwMergedFile, B).getStream();
        inStreams.add(temp);
//        writer.close();
        return;
    }

    public void finalMerge() throws IOException {
        List<InStream> dStreams;
        WriterStream ws = new WriterStream(W_IMPLEMENTATION,Main.OUTFILE,B);

//        PrintWriter writer = new PrintWriter(Main.OUTFILE + "visual.txt");

        OutStream os = ws.getStream();
        os.create();
        dStreams = inStreams;

        PriorityQueue<QueueItem> heap = new PriorityQueue<>();
        QueueItem qItem;
        int count = 0;
        for (int i = 0; i < dStreams.size(); i++) {
            dStreams.get(i).open();
            qItem = new QueueItem(i, dStreams.get(i).read_next());
            heap.add(qItem);
        }
        while (!heap.isEmpty()) {
            qItem = heap.remove();
            count++;
            os.write(qItem.getNumber());
//            writer.println(qItem.getNumber());
            if (!dStreams.get(qItem.getStreamNum()).end_of_stream()) {
                heap.add(new QueueItem(qItem.getStreamNum(), dStreams.get(qItem.getStreamNum()).read_next()));
            }
        }
//        System.out.println("Final output written in " + Main.OUTFILE);
//        System.out.println("Number of elements written: " + count);
//        writer.close();
        os.close();
    }

    public int getSize() {
        return inStreams.size();
    }
}
