import java.io.*;

public class OutputStream1 extends OutStream {

    /**
     * Upon OutputStream1 instance creation the constructor
     * only receives the filepath as an argument and assigns
     * its value to the path attribute of the class (inherited by its superclass).
     *
     * @param filepath
     */
    public OutputStream1(String filepath) {
        path = filepath;
    }

    /**
     * Initiates the OutputStream attribute as a new FileOutputStream getting
     * as parameter a new File(path), where path is the one assigned on constructor.
     * Then, it initiates a DataOutputStream wrapping the previously defined
     * FileOutputStream in order to be ready to write the output file in
     * “int by int” fashion (instead of writing binary data, like OutputStream does).
     *
     * @throws FileNotFoundException
     */
    @Override
    public void create() throws FileNotFoundException {
        os = new FileOutputStream(new File(path));
        dos = new DataOutputStream(os);
    }

    /**
     * Opens the DataOutputStream as described above, though skipbytes is not supported.
     *
     * @param skip
     * @throws IOException
     */
    @Override
    public void create(int skip) throws IOException {
        create();
    }

    /**
     * Simply accepts an integer as an argument and writes it into the output file.
     *
     * @param element
     * @throws IOException
     */
    @Override
    public void write(int element) throws IOException {
        dos.writeInt(element);
    }

    /**
     * closes the DataOutputStream (there is no need to close OutputStream too,
     * as by closing DataOutputStream, java will also close the relevant OutputStream.)
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        dos.close();
    }

}