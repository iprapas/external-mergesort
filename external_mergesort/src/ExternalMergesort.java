import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExternalMergesort {

    private int M; //memory available
    private int N; //total number of integers
    private int d; //total streams we can merge in one go
    private int B; //size of buffer (for I/Os) 4*M

    private List<ReaderStream> streams;
    private List<Integer> limits;
    private List<Integer> inMemoryInput;
    private WriterStream os;


    public ExternalMergesort(String inputFile, int N, int M, int d, int B) throws IOException {
        this.M = M;
        this.N = N;
        this.d = d;
        this.B = B;
        streams = new ArrayList<>();
        inMemoryInput = new ArrayList<>();
        limits = new ArrayList<>();
        int numStreams = (int) Math.ceil((double) N / M);

        //Open required number of streams pointing at the same input file.
        for (int i = 0; i < numStreams; i++) {
            ReaderStream is = new ReaderStream(2,inputFile,4*M);
            streams.add(is);
            limits.add(i * M);
        }

        //Open 1 output stream to be used for writing computed output
        os = new WriterStream(4,inputFile, 4*M);
        os.getStream().create();

        //Read the appropriate part of the input file into the corresponding streams.
        readSortWriteBatches();

        while (streams.size() > 1) {
            if (streams.size() > d) {
                List<ReaderStream> streamsToMerge = new ArrayList<>();
                List<Integer> limitsToMerge = new ArrayList<>();

                int minPos = Integer.MAX_VALUE;
                for (int i = 0; i < d; i++) {

                    if (limits.get(0) < minPos) {
                        minPos = limits.get(0);
                    }
                    limitsToMerge.add(limits.remove(0));
                    streamsToMerge.add(streams.remove(0));
                }

                if(!limits.isEmpty()) {
                    limitsToMerge.add(limits.get(0));
                }

                MultiWayMergesort mwm = new MultiWayMergesort(streamsToMerge, limitsToMerge, os, minPos, inputFile, M); //merge-sort the d first streams
                streams.add(new ReaderStream(2,inputFile, 4 * M)); //return the merged stream
                limits.add(minPos); //assign minPos to limits array to match where the stream points at

            } else { //final synchronous step
                MultiWayMergesort mwm = new MultiWayMergesort(streams, limits, os, 0, inputFile, M); //perform final phase merge-sort.
                closeAllStreams();
                streams.clear();
            }
        }
    }

    /**
     * For every stream sequentially it reads all its integers and sort it in memory.
     * Then it writes them back to file in sorted order.
     * @throws IOException
     */
    private void readSortWriteBatches() throws IOException {
        int id = 0;
        for (ReaderStream is : streams) {
            readBatch(is, id * M);
            Collections.sort(inMemoryInput);
            writeBatchToFile();
            is.getStream().close();
            id++;
        }
    }

    /**
     * Reads a segment of M integers according to the proper starting position in the file
     * and inserts them into an in-memory arraylist.
     *
     * @param is
     * @param startPos
     * @throws IOException
     */
    private void readBatch(ReaderStream is, int startPos) throws IOException {
        is.getStream().open(startPos);
        for (int i = 0; i < M; i++) {
            if (!is.getStream().end_of_stream()) {
                inMemoryInput.add(is.getStream().read_next());
            }
        }
    }

    /**
     * Writes a sorted segment of M integers back into the file, starting from the proper position.
     *
     * @throws IOException
     */
    private void writeBatchToFile() throws IOException {
        for (Integer i : inMemoryInput) {
            os.getStream().write(i);
        }
        inMemoryInput = new ArrayList<>(); //clear in-memory integers
    }

    /**
     * Closing all open read and write streams.
     *
     * @throws IOException
     */
    private void closeAllStreams() throws IOException {
        for (ReaderStream is : streams) {
            is.getStream().close();
        }
        os.getStream().close();
    }

}
