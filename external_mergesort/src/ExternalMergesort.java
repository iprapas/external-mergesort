import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExternalMergesort {

    private final int N = 1000000;
    private final int M = N/10;
    private final int d = 5;
    private List<InStream> streams;
    private List<Integer> limits;
    private List<Integer> inMemoryInput;
    private OutStream os;

    private final String INPUTFILE = "generated_input_" + N + ".txt";


    public ExternalMergesort() throws IOException {
        int numStreams = (int) Math.ceil((double)N/M);
        streams = new ArrayList<>();
        inMemoryInput = new ArrayList<>();
        limits = new ArrayList<>();

        //Open required number of streams pointing at the same input file.
        for(int i=0; i<numStreams; i++){
            InStream is = new InputStream4(INPUTFILE, M);
            streams.add(is);
            limits.add(i*M);
        }

        //Open 1 output stream to be used for writing computed output
        os = new OutputStream4(INPUTFILE, M);
        os.create();

        //Read the appropriate part of the input file into the corresponding streams.
        int id=0;
        for(InStream is : streams){
            readBatch(is,id*M);
            Collections.sort(inMemoryInput);
            writeBatchToFile();
            is.close();
            id++;
        }

        while(streams.size()>1){
            if(streams.size()>d){
                List<InStream> streamsToMerge = new ArrayList<>();

                int minPos = Integer.MAX_VALUE;
                for(int i=0; i<d; i++) {

                    if(limits.get(0) < minPos){
                        minPos = limits.get(0);
                    }
                    limits.remove(0);

                    streamsToMerge.add(streams.remove(0));
                }
                MultiWayMerge mwm = new MultiWayMerge(streamsToMerge,os, minPos); //merge-sort the d first streams
                streams.add(new InputStream4(INPUTFILE, M)); //return the merged stream
                limits.add(minPos); //assign minPos to limits array to match where the stream points at

            }else{ //final synchronous step
                MultiWayMerge mwm = new MultiWayMerge(streams,os, 0); //perform final phase merge-sort.
                closeAllStreams();
                streams.clear();
            }
        }
    }

    /**
     * Reads a segment of M integers according to the proper starting position in the file
     * and inserts them into an in-memory arraylist.
     * @param is
     * @param startPos
     * @throws IOException
     */
    private void readBatch(InStream is, int startPos) throws IOException {
        is.open(startPos);
        for(int i=0; i<M ;i++) {
            if(!is.end_of_stream()) {
                inMemoryInput.add(is.read_next());
            }
        }
    }

    /**
     * Writes a sorted segment of M integers back into the file, starting from the proper position.
     * @throws IOException
     */
    private void writeBatchToFile() throws IOException {
        for(Integer i : inMemoryInput) {
            os.write(i);
        }
        inMemoryInput = new ArrayList<>(); //clear in-memory integers
    }

    /**
     * Closing all open read and write streams.
     * @throws IOException
     */
    private void closeAllStreams() throws IOException {
        for(InStream is : streams){
            is.close();
        }
        os.close();
    }

}
