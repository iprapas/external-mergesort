import java.io.*;
import java.nio.channels.FileChannel;

public class ReadAndWrite {

    public ReadAndWrite(){


    }


    private void read1() throws IOException {
        //input streams
        //open (open an existing file for reading),
        //read next, (read the next element from the stream), and end of stream
        //(a boolean operation that returns true if the end of stream has been reached).

        InputStream is = new FileInputStream( new File("input.data" ) );
        DataInputStream ds = new DataInputStream(is);
        ds.readInt();

        ds.close();
    }

    private void write1() throws IOException {
        // output streams
        //create (create a new file), write (write an element to the stream), close (close the stream).
        OutputStream os = new FileOutputStream( new File("output.data" ) );
        DataOutputStream ds = new DataOutputStream(os);
        ds.writeInt(2);

        ds.close();
    }

    private void read2() throws IOException {
        InputStream is = new FileInputStream( new File("input.data" ) );
        BufferedInputStream bis = new BufferedInputStream( is );
        DataInputStream ds = new DataInputStream( bis );
        ds.readInt();

        ds.close();
    }

    private void write2() throws IOException {
        OutputStream is = new FileOutputStream( new File("output.data" ) );
        BufferedOutputStream bis = new BufferedOutputStream( is );
        DataOutputStream ds = new DataOutputStream( bis );
        ds.writeInt(2);

        ds.close();
    }

    private void read3() throws IOException {
        //now you equip your streams
        //with a buffer of size B in internal memory. Whenever the buffer becomes empty/full
        //the next B elements are read/written from/to the file.
        InputStream is = new FileInputStream( new File("input.data" ) );
        DataInputStream ds = new DataInputStream(is);
        ds.readInt();

        ds.close();
    }

    private void write3() throws IOException {
        //now you equip your streams
        //with a buffer of size B in internal memory. Whenever the buffer becomes empty/full
        //the next B elements are read/written from/to the file.
        OutputStream os = new FileOutputStream( new File("output.data" ) );
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
