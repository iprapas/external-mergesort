import java.io.IOException;

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

    private static final int IMPLEMENTATION = 4; // 0 = Generate file.
    private static final int BUFFERSIZE = 8*1024;
    private static final int ELEMENTS = 100000; //10m
    private static final String FILENAME = "generated_input_"+Integer.toString(ELEMENTS)+".txt";
    private static final String OUTPUTFILENAME= "output_1.txt";


    private static InStream is;
    private static OutStream os;

    public static void main(String[] args) throws IOException {

        startTime = System.currentTimeMillis();
        selectIO();
        execute();
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");

    }

    private static void selectIO() throws IOException {
        switch (IMPLEMENTATION){
            case 1:
                is = new InputStream1(FILENAME);
                os = new OutputStream1(OUTPUTFILENAME);
                break;
            case 2:
                is = new InputStream2(FILENAME);
                os = new OutputStream2(OUTPUTFILENAME);
                break;
            case 3:
                is = new InputStream3(FILENAME,BUFFERSIZE);
                os = new OutputStream3(OUTPUTFILENAME, BUFFERSIZE);
                break;
            case 4:
                is = new InputStream4(FILENAME);
                os = new OutputStream4(OUTPUTFILENAME);
                break;
            default:
                GenerateFile gf = new GenerateFile();
                gf.generate(FILENAME, ELEMENTS);
                //System.out.println("Please select implementation among [1,4]");
        }
    }


    private static void execute() throws IOException {
        os.create();
        is.open();
        while(!is.end_of_stream()){
            os.write(is.read_next());
        }
        is.close();
        os.close();
    }
}