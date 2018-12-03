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
    private static final int IMPLEMENTATION = 1;
    private static final String FILENAME = "input.txt";

    public static void main(String[] args) throws IOException {

        startTime = System.currentTimeMillis();
        ReadAndWrite rw = new ReadAndWrite(IMPLEMENTATION, FILENAME);
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");
        System.out.println("Read " + rw.numbers.size() + " numbers");
    }
}