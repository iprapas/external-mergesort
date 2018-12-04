import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadAndWrite {


    List<Integer> numbers = new ArrayList<>();
    String input;
    String line;
    int bufferSize;

    public ReadAndWrite() throws IOException {
        read1();
        write1();
    }

    public ReadAndWrite(int impl, int bufferSize, String input) throws IOException {
        this.bufferSize = bufferSize;
        this.input = input;

        switch (impl){
            case 1: read1(); write1();
                break;
            case 2: read2(); write2();
                break;
            case 3: read3(); write3();
                break;
            case 4: read4(); write4();
                break;
            default:
                System.out.println("please specify implementation value between [1,4].");
                System.exit(0);
        }
    }

    /**
     * Input streams.
     * Open (open an existing file for reading),
     * Read next, (read the next element from the stream), and end of stream through exception handling.
     * @throws IOException
     */
    private void read1() throws IOException {
        InputStream is = new FileInputStream( new File(input) );
        DataInputStream ds = new DataInputStream(is);
        while((line = ds.readLine()) != null){
            numbers.add(Integer.parseInt(line));
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
        for(Integer value: numbers) {
            ds.writeBytes(value +"\n");
        }
        ds.close();
    }

    private void read2() throws IOException {
        InputStream is = new FileInputStream( new File(input) );
        BufferedInputStream bis = new BufferedInputStream( is );
        DataInputStream ds = new DataInputStream( bis );
        while((line = ds.readLine()) != null){
            numbers.add(Integer.parseInt(line));
        }
        ds.close();
    }

    private void write2() throws IOException {
        OutputStream is = new FileOutputStream( new File("output.txt" ) );
        BufferedOutputStream bis = new BufferedOutputStream( is );
        DataOutputStream ds = new DataOutputStream( bis );
        for(Integer value: numbers) {
            ds.writeBytes(value +"\n");
        }
        ds.close();
    }

    /**
     * now you equip your streams
     * with a buffer of size B in internal memory. Whenever the buffer becomes empty/full
     * the next B elements are read/written from/to the file.
     * @throws IOException
     */
    private void read3() throws IOException {
        //-----1st way
        BufferedReader br = new BufferedReader(new FileReader(new File(input)),bufferSize);
        while((line = br.readLine()) != null){
            numbers.add(Integer.parseInt(line));
        }

//        InputStream is = new FileInputStream( new File(input) );
//        BufferedInputStream bis = new BufferedInputStream( is,bufferSize );
//        DataInputStream ds = new DataInputStream( bis );
//        while((line = ds.readLine()) != null){
//            numbers.add(Integer.parseInt(line));
//        }

        //-----2nd way
//        InputStream is = new FileInputStream( new File(input ) );
//        BufferedInputStream bis = new BufferedInputStream( is, bufferSize);
//        DataInputStream ds = new DataInputStream( bis );
//        boolean eof = false;
//        while(!eof){
//            try{
//                numbers.add(ds.readInt());
//            } catch (EOFException e) {
//                eof = true;
//            }
//        }
//        ds.close();
    }

    private void write3() throws IOException {
        OutputStream is = new FileOutputStream( new File("output.txt" ) );
        BufferedOutputStream bis = new BufferedOutputStream( is,bufferSize );
        DataOutputStream ds = new DataOutputStream( bis );
        for(Integer value: numbers) {
            ds.writeBytes(value +"\n");
        }
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
