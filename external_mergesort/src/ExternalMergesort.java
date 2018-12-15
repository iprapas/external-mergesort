import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ExternalMergesort {

    private final int N = 1000000;
    private final int M = N/10;
    private final int d = 5;
    private List<InStream> streams;
    private HashMap<Integer,Integer> limits;
    private List<Integer> inMemoryInput;
    private OutStream os;


    public ExternalMergesort() throws IOException {
        streams = new ArrayList<>();
        limits = new HashMap<>();
        inMemoryInput = new ArrayList<>();
        int numStreams = (int) Math.ceil((double)N/M);
        for(int i=0;i<numStreams; i++) {
            limits.put(i,i*M);
        }

        //Open required number of streams pointing at the same input file.
        for(int i=0; i<numStreams; i++){
            InStream is = new InputStream4("generated_input_" + N + ".txt");
            streams.add(is);
        }

        //Open 1 output stream to be used for writing output file
        os = new OutputStream4("generated_input_" + N + ".txt", N);
        os.create();

        //Read the appropriate part of the input file into the corresponding streams.
        int id=0;

        for(InStream is : streams){
            is.open();
            if(id!=0) {
                for (int i = 0; i < limits.get(id); i++) {
                    is.read_next();
                }
            }
            readBatch(is);
            Collections.sort(inMemoryInput);
            writeBatchToFile();
            id++;
        }



        closeAllStreams();
    }

    private void readBatch(InStream is) throws IOException {

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
