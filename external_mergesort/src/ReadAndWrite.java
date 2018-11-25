import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadAndWrite {

    List<Integer> integers = new ArrayList<>();

    public ReadAndWrite() throws IOException {
        read1();
        write1();


    }


    /**
     * Input streams.
     * Open (open an existing file for reading),
     * Read next, (read the next element from the stream), and end of stream through exception handling.
     * @throws IOException
     */
    private void read1() throws IOException {

        InputStream is = new FileInputStream( new File("integers.txt" ) );
        DataInputStream ds = new DataInputStream(is);
        boolean eof = false;

        while(!eof){
            try{
                integers.add(ds.readInt());
            } catch (EOFException e) {
                eof = true;
            }
        }

        ds.close();
    }

    /**
     * Output streams.
     * Create (create a new file), write (write all elements to the stream), close (close the stream).
     * @throws IOException
     */
    private void write1() throws IOException {

        OutputStream os = new FileOutputStream( new File("output.txt" ) );
        DataOutputStream ds = new DataOutputStream(os);
        for(Integer value: integers) {
            ds.writeInt(value);
        }
        ds.close();
    }

    private void read2() throws IOException {
        InputStream is = new FileInputStream( new File("integers.txt" ) );
        BufferedInputStream bis = new BufferedInputStream( is );
        DataInputStream ds = new DataInputStream( bis );
        ds.readInt();

        ds.close();
    }

    private void write2() throws IOException {
        OutputStream is = new FileOutputStream( new File("output.txt" ) );
        BufferedOutputStream bis = new BufferedOutputStream( is );
        DataOutputStream ds = new DataOutputStream( bis );
        ds.writeInt(2);

        ds.close();
    }

    private void read3() throws IOException {
        //now you equip your streams
        //with a buffer of size B in internal memory. Whenever the buffer becomes empty/full
        //the next B elements are read/written from/to the file.
        InputStream is = new FileInputStream( new File("integers.txt" ) );
        DataInputStream ds = new DataInputStream(is);
        ds.readInt();

        ds.close();
    }

    private void write3() throws IOException {
        //now you equip your streams
        //with a buffer of size B in internal memory. Whenever the buffer becomes empty/full
        //the next B elements are read/written from/to the file.
        OutputStream os = new FileOutputStream( new File("output.txt" ) );
        DataOutputStream ds = new DataOutputStream(os);
        ds.writeInt(2);

        ds.close();
    }

    private void read4(){
        //For this particular implementation you are required to do a little research on what
        //memory mapping actually is; you are expected to explain this concept in your report.
        //new FileChannel();

    }

    private void write4(){
        //For this particular implementation you are required to do a little research on what
        //memory mapping actually is; you are expected to explain this concept in your report.
        //new FileChannel();

    }
}
