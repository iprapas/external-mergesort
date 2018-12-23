import java.io.IOException;
import java.util.ArrayList;

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
    private static final boolean CMD_RUN = false;
    private static int N = 1000; //total integers on input
    private static int M = 1000; //memory available
    private static int d = 100; //total streams we can merge in one go
    private static int IMPLEMENTATION = 3;
    private static int BUFFERSIZE = 4* 1024;
    private static int K = 30; // number of streams
    private static int BENCHMARK = 2; // 0 GEN FILE & RUN, 1 I/O TEST
    private static String INPUTFILE = "generated_input_" + N + ".txt";
    private static String OUTFILE = "final_output/output.txt";
    private static String OUTPUTFILENAME = "output_implementation_" + IMPLEMENTATION + ".txt";
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

        if (BENCHMARK == 0) {
            for (int i = 1; i <= 30; i++) {
                GenerateFile gf = new GenerateFile();
                gf.generate("input_" + (int) Math.pow(N, i) + ".txt", (int) Math.pow(N, i));
            }
        } else if (BENCHMARK == 1) {

            //create 30 input files of size N -not timed.
            for (int i = 1; i <= 10; i++) {
                GenerateFile gf = new GenerateFile();
                gf.generate("input/input_" + i + ".txt", N);
            }

            ArrayList<Long> times = new ArrayList<>();
            for (int k = 0; k < 5; k++) { //run implementation 5 times and take avg.
                startTime = System.currentTimeMillis();
                benchIO(K);
                endTime = System.currentTimeMillis();
                times.add(endTime - startTime);
            }
            System.out.println("AVG(" + IMPLEMENTATION + "): " + getAverage(times) + "ms on N=" + N + ", B=" + BUFFERSIZE + ", k=" + K);

        } else {
            GenerateFile gf = new GenerateFile();
            gf.generate(INPUTFILE, N);
            startTime = System.currentTimeMillis();
            ExternalMergesort2 em = new ExternalMergesort2(INPUTFILE, N, M, d, BUFFERSIZE);
            endTime = System.currentTimeMillis();
            System.out.println("Time: " + (endTime - startTime) + "ms");
            System.out.println(testCorrectness());
        }
    }

    /**
     * Returns the average value of an arraylist of long numbers.
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