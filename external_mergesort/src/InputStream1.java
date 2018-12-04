import java.io.*;

public class InputStream1 extends InStream {

    public InputStream1(String filepath){
        path = filepath;
    }

    @Override
    public void open() throws FileNotFoundException {
        is = new FileInputStream( new File(path));
        ds = new DataInputStream(is);
    }

    @Override
    public int read_next() throws IOException {
        return ds.readInt();
    }

    @Override
    public boolean end_of_stream() throws IOException {
        return (ds.available() == 0);
    }

    @Override
    public void close() throws IOException {
        ds.close();
        is.close();
    }


}
