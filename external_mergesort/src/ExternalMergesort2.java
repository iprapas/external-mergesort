import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExternalMergesort2 {
    private int M; //memory available
    private int N; //total number of integers
    private int d; //total streams we can merge in one go
    private int B; //size of buffer (for I/Os) 4*M

    private String mergedFileFormat = Main.HOME_DIR + "/temp/merged/merged%d.txt";
    private int R_IMPLEMENTATION = Main.R_IMPLEMENTATION;
    private int W_IMPLEMENTATION = Main.W_IMPLEMENTATION;
    public ExternalMergesort2(String inputFile, int N, int M, int d, int B) throws IOException {
        this.M = M;
        this.N = N;
        this.d = d;
        this.B = B;

        int numStreams = (int) Math.ceil((double) N / M);
        List<InStream> sortedStreams = new ArrayList<>();
        // Get sortedStreams
        for (int i = 0; i < numStreams; i++) {
            ReaderStream rs = new ReaderStream(R_IMPLEMENTATION, inputFile, B);
            InStream is = rs.getStream();
            is.open(i * M);
            is = sortStream(is, i);
            sortedStreams.add(is);
        }

        // Call repeatedly Multiway Mergesort
        MultiwayMerge2 mw = new MultiwayMerge2(sortedStreams, d, B);
        while (mw.getSize() > d) {
            mw.mergeD();
        }
        mw.finalMerge();
    }


    public InStream sortStream(InStream is,int i) throws IOException {
        String mergedFile = String.format(mergedFileFormat,i);
        WriterStream ws = new WriterStream(W_IMPLEMENTATION, mergedFile,B);

        List<Integer> l = new ArrayList<>();
        for (i = 0; i < M; i++) {
            if (!is.end_of_stream()) {
                l.add(is.read_next());
            }
        }
        is.close();

        Collections.sort(l);
        OutStream os = ws.getStream();
        os.create();
        for (i = 0; i < l.size(); i++) {
            os.write(l.get(i));
        }
        os.close();
        ReaderStream rs = new ReaderStream(R_IMPLEMENTATION, mergedFile, B);
        return rs.getStream();
    }

}
