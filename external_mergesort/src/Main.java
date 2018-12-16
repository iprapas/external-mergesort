import java.io.IOException;
import java.util.ArrayList;

/**
 * Université Libre de Bruxelles (ULB)
 * Master Program in Big Data Management & Analytics (BDMA)
 *
 * Course: Database Systems Architecture
 * Project Assignment: Algorithms in Secondary Memory
 *
 * Authors:
 * Sokratis Papadopoulos (000476296)
 * Ioannis Prapas (000473813)
 * Carlos Martinez Lorenzo (000477671)
 *
 * Date: January 2019
 *
 * In this assignment we are asked to implement an external-memory merge-sort algorithm
 * and examine its performance under different parameters.
 *
 * As a warm-up exercise we explore several different ways to read data from,
 * and write data to secondary memory. The overall goal of the assignment
 * is to get real-world experience with the performance of external-memory algorithms.
 *
 */

public class Main {
    private static long startTime;
    private static long endTime;

    private static final boolean CMD_RUN = false;
    private static int IMPLEMENTATION = 2; // 0 = Generate file.
    private static int BUFFERSIZE = 4*100000;
    private static int ELEMENTS = 1000000;
    private static int BENCHMARK=2; // 0 GENERATE FILE, 1 I/O TEST, 2 EXTERNAL MERGESORT
    private static String FILENAME = "generated_input_"+ ELEMENTS +".txt";
    private static String OUTPUTFILENAME= "output_implementation_" + IMPLEMENTATION +".txt";


    private static InStream is;
    private static OutStream os;

    public static void main(String[] args) throws IOException {

        if (CMD_RUN) {
            if (args.length != 4) {
                System.out.println("Please enter 4 arguments: <IO implementation> <buffersize> <infile> <outfile> ");
                System.exit(0);
            } else {
                IMPLEMENTATION = Integer.parseInt(args[0]);
                BUFFERSIZE = Integer.parseInt(args[1]);
                FILENAME = args[2];
                OUTPUTFILENAME = args[3];
            }
        }

        startTime = System.currentTimeMillis();

        if (BENCHMARK==0){
            GenerateFile gf = new GenerateFile();
            gf.generate(FILENAME, ELEMENTS);
        } else if (BENCHMARK==1) {
            benchIO();
        } else {
            ExternalMergesort em = new ExternalMergesort();
            readAndPrint();
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");

    }

    private static void benchIO() throws IOException {
        ArrayList<InStream> inputstreams = new ArrayList();
        ArrayList<OutStream> outputstreams = new ArrayList();
        for (int i = 0; i < 30; i++) {
            String infile = "input/input (" + (i + 1) + ").txt";
            String outfile = "output/output_" + (i + 1) + ".txt";

            ReaderStream rs = new ReaderStream(IMPLEMENTATION, infile, BUFFERSIZE);
            WriterStream ws = new WriterStream(IMPLEMENTATION, outfile, BUFFERSIZE);
            is = rs.getStream();
            os = ws.getStream();
            os.create();
            is.open();

            inputstreams.add(is);
            outputstreams.add(os);
        }

        int N = 10000000;
        int i = 0;
        while (i < N) {
            for (int j = 0; j < 30; j++) {
                is = inputstreams.get(j);
                os = outputstreams.get(j);
                os.write(is.read_next());

            }
            i++;
        }

        for (i = 0; i < 30; i++) {
            outputstreams.get(i).close();
            inputstreams.get(i).close();

        }
    }

    private static void readAndPrint() throws IOException {
        ArrayList<Integer> test = new ArrayList<>();
        is = new InputStream2(FILENAME);
        is.open();
        while(!is.end_of_stream()){
            test.add(is.read_next());
        }
        is.close();
        System.out.println("0 --> " + test.get(0));
        System.out.println("1 --> " + test.get(1));
        System.out.println("100000 --> " + test.get(100000));
        System.out.println("100001 --> " + test.get(100001));
        System.out.println("200000 --> " + test.get(200000));
        System.out.println("200001 --> " + test.get(200001));
        System.out.println("300000 --> " + test.get(300000));
        System.out.println("300001 --> " + test.get(300001));
        System.out.println("400000 --> " + test.get(400000));
        System.out.println("400001 --> " + test.get(400001));
    }


}