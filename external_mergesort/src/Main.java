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
    private static int ELEMENTS = 10000000; //10m
    private static int BENCHMARK=1; // 0 GENERATE FILE, 1 I/O TEST, 2 EXTERNAL MERGESORT
    private static String FILENAME = "generated_input_"+Integer.toString(ELEMENTS)+".txt";
    private static String OUTPUTFILENAME= "output_implementation_"+Integer.toString(IMPLEMENTATION)+".txt";


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
            //
        } else if (BENCHMARK==1) {
            benchIO();
        } else {
            //
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");

    }

//    private static void selectIO() throws IOException {
//        switch (IMPLEMENTATION){
//            case 1:
//                is = new InputStream1(FILENAME);
//                os = new OutputStream1(OUTPUTFILENAME);
//                break;
//            case 2:
//                is = new InputStream2(FILENAME);
//                os = new OutputStream2(OUTPUTFILENAME);
//                break;
//            case 3:
//                is = new InputStream3(FILENAME,BUFFERSIZE);
//                os = new OutputStream3(OUTPUTFILENAME, BUFFERSIZE);
//                break;
//            case 4:
//                is = new InputStream4(FILENAME);
//                os = new OutputStream4(OUTPUTFILENAME, ELEMENTS);
//                break;
//            default:
//                GenerateFile gf = new GenerateFile();
//                gf.generate(FILENAME, ELEMENTS);
//                //System.out.println("Please select implementation among [1,4]");
//        }
//    }


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

}