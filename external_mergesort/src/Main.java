import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Université Libre de Bruxelles (ULB)
 * Master Program in Big Data Management & Analytics (BDMA)
 * <p>
 * Course: Database Systems Architecture
 * Project Assignment: Algorithms in Secondary Memory
 * <p>
 * Authors:
 * Sokratis Papadopoulos (000476296)
 * Ioannis Prapas (000473813)
 * Carlos Martinez Lorenzo (000477671)
 * <p>
 * Date: January 2019
 * <p>
 * In this assignment we are asked to implement an external-memory merge-sort algorithm
 * and examine its performance under different parameters.
 * <p>
 * As a warm-up exercise we explore several different ways to read data from,
 * and write data to secondary memory. The overall goal of the assignment
 * is to get real-world experience with the performance of external-memory algorithms.
 */

public class Main {
    private static long startTime;
    private static long endTime;
    private static final boolean CMD_RUN = true;

    public static int IMPLEMENTATION = 4;
    public static int R_IMPLEMENTATION = 4;
    public static int W_IMPLEMENTATION = 4;
    private static int N = 100000; //total integers on input
    public static int M = 1000; //memory available
    private static int d = 100; //total streams we can merge in one go
    private static int K = 10; // number of streams

    private static int BUFFERSIZE = 4000;

    private static int BENCHMARK = 2; // 0 GEN FILE, 1 I/O TEST, 2 EXTERNAL MERGESORT, 3 INTERNAL SORT
    private static String INPUTFILE = "generated_input_" + N + ".txt";
    private static String OUTPUTFILENAME = "output_implementation_" + IMPLEMENTATION + ".txt";
    public static String INPUT_IO_DIR = "input_io";
    public static String OUTPUT_IO_DIR = "output_io";
    public static String OUTFILE = "temp\\final_output\\output.txt";
    private static String INPUT_DIR = "inputs\\";
    private static InStream is;
    private static OutStream os;

    public static void main(String[] args) throws IOException {

        if (CMD_RUN) { //initiate parameters from command line

            if (args.length == 0) {
                //use all default values
            } else {
                BENCHMARK = Integer.parseInt(args[0]);

                switch (BENCHMARK) {
                    case 0: // no parameters to initialize
                        break;

                    case 1: // parameters to run benchmark for Read/Write implementations
                        if (args.length != 5) {
                            System.out.println("Please enter 5 arguments: <0,1,2,3> <IO implementation> <buffersize> <N> <K Streams>");
                            System.exit(0);
                        }
                        IMPLEMENTATION = Integer.parseInt(args[1]);
                        BUFFERSIZE = Integer.parseInt(args[2]);
                        N = Integer.parseInt(args[3]);
                        K = Integer.parseInt(args[4]);
                        break;

                    case 2: //parameters to run external mergesort
                        if (args.length != 6) {
                            System.out.println("Please enter 6 arguments: <Bench 0,1,2,3> <IO implementation> <buffersize> <N numbers> <M memory> <Dway merge>"); //<inputfile> <outfile>
                            System.exit(0);
                        }
                        IMPLEMENTATION = Integer.parseInt(args[1]);
                        R_IMPLEMENTATION = IMPLEMENTATION;
                        W_IMPLEMENTATION = IMPLEMENTATION;
                        BUFFERSIZE = Integer.parseInt(args[2]);
                        N = Integer.parseInt(args[3]);
                        M = Integer.parseInt(args[4]);
                        d = Integer.parseInt(args[5]);
                        //INPUTFILE = args[6];
                        //OUTFILE = args[7];
                        break;

                    case 3: //run in memory sort
                        if (args.length != 4) {
                            System.out.println("Please enter 4 arguments: <Bench 0,1,2,3> <IO implementation> <buffersize> <N numbers>"); //<inputfile> <outfile>
                            System.exit(0);
                        }
                        IMPLEMENTATION = Integer.parseInt(args[1]);
                        R_IMPLEMENTATION = IMPLEMENTATION;
                        W_IMPLEMENTATION = IMPLEMENTATION;
                        BUFFERSIZE = Integer.parseInt(args[2]);
                        N = Integer.parseInt(args[3]);
                        break;

                    default:
                        System.out.println("Please enter 6 arguments: <Bench 0,1,2,3> <IO implementation> <buffersize> <N numbers> <M memory> <Dway merge>"); //<inputfile> <outfile>
                        System.exit(0);
                }
            }
        }

        //ALL PARAMETERS HAVE BEEN INITIALIZED (either by command line or within IDE - class attributes)

        if (BENCHMARK == 0) { //generate 8 input files with 10 100 1000 10000 ... 10m integers
            for (int i = 1; i <= 8; i++) {
                GenerateFile gf = new GenerateFile();
                gf.generate(INPUT_DIR + "input_" + (int) Math.pow(10, i) + ".txt", (int) Math.pow(10, i));
            }
            System.out.println("8 files generated with 10, 100 ... 10m integers");

        } else if (BENCHMARK == 1) { //benchmark the 4 read/write implementations
            startTime = System.currentTimeMillis();
            benchIO(K);
            System.out.println(System.currentTimeMillis() - startTime + " ms");

        } else if (BENCHMARK == 2) { //external mergesort
            GenerateFile gf = new GenerateFile();
            gf.generate(INPUTFILE, N);
            System.out.println("File Generated");

            startTime = System.currentTimeMillis();
            ExternalMergesort em = new ExternalMergesort(INPUTFILE, N, M, d, BUFFERSIZE);
            endTime = System.currentTimeMillis();
            System.out.println("Time: " + (endTime - startTime) + "ms");
            System.out.println("External mergesort on I/O " + IMPLEMENTATION + " Buffer size: " + BUFFERSIZE + " N: " + N +" M: " + M + " d: " + d); //<inputfile> <outfile>
//            System.out.println(testCorrectness());

        } else if (BENCHMARK == 3) { //in memory sort
            GenerateFile gf = new GenerateFile();
            gf.generate(INPUTFILE, N);

            ReaderStream rs = new ReaderStream(R_IMPLEMENTATION, INPUTFILE, BUFFERSIZE);
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
            System.out.println("Internal sorting on I/O " + IMPLEMENTATION + " Buffer size: " + BUFFERSIZE + " N: " + N); //<inputfile> <outfile>
            WriterStream ws = new WriterStream(W_IMPLEMENTATION, OUTPUTFILENAME, BUFFERSIZE);
            OutStream os = ws.getStream();
            os.create();
            for (int n : l) {
                os.write(n);
            }
            os.close();
        }

    }

    /**
     * Benchmarks the performance of a given read/write implementation.
     *
     * @param k
     * @throws IOException
     */
    private static void benchIO(int k) throws IOException {
        ArrayList<InStream> inputstreams = new ArrayList();
        ArrayList<OutStream> outputstreams = new ArrayList();

        for (int i = 0; i < k; i++) {
            String infile = INPUT_IO_DIR + "/input_" + i + ".txt";
            String outfile = OUTPUT_IO_DIR + "/output_" + i + ".txt";

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

    /**
     * Tests if the output is correct. To do that it compares each number with its following,
     * making sure that the first one is smaller than the second one.
     *
     * @return
     * @throws IOException
     */
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


}