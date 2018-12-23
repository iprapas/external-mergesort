import java.io.*;

public class InputStream1 extends InStream {

    /**
     * Upon InputStream1 instance creation the constructor only receives
     * the filepath as an argument and assigns its value to the path attribute
     * of the class (inherited by its superclass).
     * @throws FileNotFoundException
     */
    public InputStream1(String filepath) {
        path = filepath;
    }


    /**
     * Initiates the InputStream attribute as a new FileInputStream
     * getting as parameter a new File(path), where path is the one
     * assigned on constructor. Then, it initiates a DataInputStream
     * wrapping the previously defined FileInputStream in order to be
     * ready to read the input file in “int by int” fashion
     * (instead of reading binary data, like InputStream does).
     * @throws FileNotFoundException
     */
    @Override
    public void open() throws FileNotFoundException {
        is = new FileInputStream(new File(path));
        ds = new DataInputStream(is);
    }

    /**
     * opens the DataInputStream as described above but will in addition skip
     * and discard the specified amount of integers (multiplied by 4 because
     * each integer occupies 4 bytes and function accepts number of bytes as argument).
     * @param pos
     * @throws IOException
     */
    @Override
    public void open(int pos) throws IOException {
        open();
        ds.skipBytes(pos * 4);
    }

    /**
     * Simply reads and returns the next integer from the input file.
     * @return next integer of input file
     * @throws IOException
     */
    @Override
    public int read_next() throws IOException {
        return ds.readInt();
    }

    /**
     * Checks if there is more data to read of if the end of stream
     * is reached. To do that is uses the available() function,
     * which returns the estimation of number of bytes left.
     * @return true if end of stream is reached, else false.
     * @throws IOException
     */
    @Override
    public boolean end_of_stream() throws IOException {
        return (ds.available() == 0);
    }

    /**
     * closes the DataInputStream (there is no need to close InputStream too,
     * as by closing DataInputStream, java will also close the relevant InputStream.)
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        ds.close();
    }

}

