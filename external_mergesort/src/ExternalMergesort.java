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


    public ExternalMergesort() throws IOException {
        streams = new ArrayList<>();
        inMemoryInput = new ArrayList<>();
        int numStreams = (int) Math.ceil((double)N/M);
        limits = new ArrayList<>();

        //Open required number of streams pointing at the same input file.
        for(int i=0; i<numStreams; i++){
            InStream is = new InputStream4("generated_input_" + N + ".txt", M);
            streams.add(is);
            limits.add(i*M);
        }

        //Open 1 output stream to be used for writing output file
        os = new OutputStream4("generated_input_" + N + ".txt", M);
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
                MultiWayMerge mwm = new MultiWayMerge(streamsToMerge,os, minPos);

                //return the stream
                streams.add(new InputStream4("generated_input_" + N + ".txt", M));
                //assign minPos to limits array
                limits.add(minPos);


            }else{ //final synchronous sort
                MultiWayMerge mwm = new MultiWayMerge(streams,os, 0);
                streams.clear();
            }


        }

        closeAllStreams();
    }

    private void readBatch(InStream is, int startPos) throws IOException {
        is.open(startPos);
        for(int i=0; i<M ;i++) {
            if(!is.end_of_stream()) {
                inMemoryInput.add(is.read_next());
            }
        }
    }

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
