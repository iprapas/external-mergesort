import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static int IMPLEMENTATION = 2;
    public static int R_IMPLEMENTATION = 4;
    public static int W_IMPLEMENTATION = 3;
    private static int N = 10000000; //total integers on input
    private static int M = 100000; //memory available
    private static int d = 100; //total streams we can merge in one go
    private static int K = 30; // number of streams

    private static int BUFFERSIZE = 4*10000;

    private static int BENCHMARK=3; // 0 GEN FILE & RUN, 1 I/O TEST
    private static String INPUTFILE = "generated_input_"+ N +".txt";
    private static String OUTPUTFILENAME= "output_implementation_" + IMPLEMENTATION +".txt";
    private static String OUTFILE = "final_output/output.txt";
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
                INPUTFILE = args[2];
                OUTPUTFILENAME = args[3];
            }
        }

        if (BENCHMARK == 0) { //generate 8 input files with 10 100 1000 10000 ... 10m integers
            for (int i = 1; i <= 8; i++) {
                GenerateFile gf = new GenerateFile();
                gf.generate("input_" + (int) Math.pow(10, i) + ".txt", (int) Math.pow(10, i));
            }
        } else if (BENCHMARK == 1) { //benchmark the 4 read/write implementations

            //create 30 input files of size N -not timed.
            for (int i = 1; i <= K; i++) {
                GenerateFile gf = new GenerateFile();
                gf.generate("input/input_" + i + ".txt", N);
            }

            ArrayList<Long> times = new ArrayList<>();
            for (int m = 0; m < 5; m++) { //run implementation 5 times and take avg.
                startTime = System.currentTimeMillis();
                benchIO(K);
                endTime = System.currentTimeMillis();
                times.add(endTime - startTime);
            }
            System.out.println("AVG(" + IMPLEMENTATION + "): " + getAverage(times) + "ms on N=" + N + ", B=" + BUFFERSIZE + ", k=" + K);

        } else if (BENCHMARK == 2) { //external mergesort
            GenerateFile gf = new GenerateFile();
            gf.generate(INPUTFILE, N);
            System.out.println("File Generated");

            startTime = System.currentTimeMillis();
            ExternalMergesort2 em = new ExternalMergesort2(INPUTFILE, N, M, d, BUFFERSIZE);
            endTime = System.currentTimeMillis();
            System.out.println("Time: " + (endTime - startTime) + "ms");
            System.out.println(testCorrectness());

        } else if (BENCHMARK == 3) { //in memory sort
            GenerateFile gf = new GenerateFile();
            gf.generate(INPUTFILE, N);
            startTime = System.currentTimeMillis();
            ReaderStream rs = new ReaderStream(4, INPUTFILE, BUFFERSIZE);
            InStream is = rs.getStream();
            is.open();
            List<Integer> l = new ArrayList<>();
            while (!is.end_of_stream()) {
                l.add(is.read_next());
            }
            is.close();
            long start = System.currentTimeMillis();
            Collections.sort(l);
            System.out.println(System.currentTimeMillis() - start + " ms (Internal)");
            WriterStream ws = new WriterStream(3, OUTPUTFILENAME, BUFFERSIZE);
            OutStream os = ws.getStream();
            os.create();
            for (int n : l) {
                os.write(n);
            }
            os.close();
        }

    }

    /**
     * Returns the average value of an arraylist of long numbers.
     *
     * @param times
     * @return
     */
    private static Long getAverage(ArrayList<Long> times) {
        long sum = 0;
        for (Long time : times) {
            sum += time;
        }
        return sum / times.size();
    }

    private static void benchIO(int k) throws IOException {
        ArrayList<InStream> inputstreams = new ArrayList();
        ArrayList<OutStream> outputstreams = new ArrayList();

        for (int i = 1; i <= k; i++) {
            String infile = "input/input_" + i + ".txt";
            String outfile = "output/output_" + i + ".txt";

            ReaderStream rs = new ReaderStream(IMPLEMENTATION, infile, BUFFERSIZE);
            WriterStream ws = new WriterStream(IMPLEMENTATION, outfile, BUFFERSIZE);
            is = rs.getStream();
            os = ws.getStream();
            os.create();
            is.open();

            inputstreams.add(is);
            outputstreams.add(os);
        }

        int i = 0;
        while (i < N) {
            for (int j = 0; j < k; j++) {
                is = inputstreams.get(j);
                os = outputstreams.get(j);
                os.write(is.read_next());
            }
            i++;
        }

        for (i = 0; i < k; i++) {
            outputstreams.get(i).close();
            inputstreams.get(i).close();
        }
    }

    private static boolean testCorrectness() throws IOException {
        is = new InputStream4(OUTFILE, BUFFERSIZE);
        is.open();
        int pValue = is.read_next();
        while (!is.end_of_stream()) {
            int value = is.read_next();
            if (pValue > value) {
                System.out.println(pValue + " " + value);
                is.close();
                return false;
            } else {
                pValue = value;
            }
        }
        is.close();
        return true;
    }


}