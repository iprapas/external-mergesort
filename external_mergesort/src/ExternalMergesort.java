import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExternalMergesort {

    private int M; //memory available
    private int N; //total number of integers
    private int d; //total streams we can merge in one go
    private int B;

    private List<InStream> streams;
    private List<Integer> limits;
    private List<Integer> inMemoryInput;
    private OutStream os;
    //private PrintWriter writer;


    public ExternalMergesort(String inputFile, int N, int M, int d, int B) throws IOException {
        this.M = M;
        this.N = N;
        this.d = d;
        this.B = B;
        int numStreams = (int) Math.ceil((double)N/M);

        //writer = new PrintWriter("visual_output_" + N + ".txt");


        streams = new ArrayList<>();
        inMemoryInput = new ArrayList<>();
        limits = new ArrayList<>();

        //Open required number of streams pointing at the same input file.
        for(int i=0; i<numStreams; i++){
            InStream is = new InputStream4(inputFile, 4*M);
            streams.add(is);
            limits.add(i*M);
        }

        //Open 1 output stream to be used for writing computed output
        os = new OutputStream4(inputFile, 4*M);
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
            if(streams.size()> d){
                List<InStream> streamsToMerge = new ArrayList<>();
                List<Integer> limitsToMerge = new ArrayList<>();

                int minPos = Integer.MAX_VALUE;
                for(int i=0; i<d; i++) {

                    if(limits.get(0) < minPos){
                        minPos = limits.get(0);
                    }
                    limitsToMerge.add(limits.remove(0));

                    streamsToMerge.add(streams.remove(0));
                }
                MultiWayMergesort mwm = new MultiWayMergesort(streamsToMerge,limitsToMerge,os, minPos); //merge-sort the d first streams
                streams.add(new InputStream4(inputFile, 4*M)); //return the merged stream
                limits.add(minPos); //assign minPos to limits array to match where the stream points at

            }else{ //final synchronous step
                MultiWayMergesort mwm = new MultiWayMergesort(streams, limits, os, 0); //perform final phase merge-sort.
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
            //writer.println(i);
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
        //writer.close();
    }

}
