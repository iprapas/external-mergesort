import java.io.*;

public class OutputStream1 extends OutStream {

    public OutputStream1(String filepath) {
        path = filepath;
    }

    @Override
    public void create() throws FileNotFoundException {
        os = new FileOutputStream(new File(path));
        dos = new DataOutputStream(os);
    }

    @Override
    public void create(int skip) throws IOException {
        create();
    }

    @Override
    public void write(int element) throws IOException {
        dos.writeInt(element);
    }

    @Override
    public void close() throws IOException {
        dos.close();
    }

}