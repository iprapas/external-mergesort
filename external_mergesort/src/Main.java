import java.io.IOException;
import java.util.Collections;

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
    private static final int BUFFERSIZE = 4 * 1024;
    private static final String FILENAME = "input-test.txt";
    private static final String OUTPUTFILENAME= "output.txt";
    private static final int ELEMENTS = 1000000000; //10m


    public static void main(String[] args) throws IOException {

        GenerateFile gf = new GenerateFile();
        gf.generate(FILENAME, ELEMENTS);

        startTime = System.currentTimeMillis();


//        InStream is = new InputStream3(FILENAME,BUFFERSIZE);
//        OutStream os = new OutputStream3(OUTPUTFILENAME, BUFFERSIZE);
        InStream is = new InputStream2(FILENAME);
        OutStream os = new OutputStream2(OUTPUTFILENAME);
        os.create();
        is.open();
        while(!is.end_of_stream()){
            os.write(is.read_next());
        }
        is.close();
        os.close();

        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");
    }
}