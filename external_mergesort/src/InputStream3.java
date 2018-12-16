import java.io.*;

public class InputStream3 extends InStream{

    private BufferedInputStream bis;
    private int bufferSize;

    public InputStream3(String filepath, int bufferSize){
        this.bufferSize = bufferSize;
        path = filepath;
    }

    @Override
    public void open() throws FileNotFoundException {
        is = new FileInputStream( new File(path));
        bis = new BufferedInputStream( is, bufferSize);
        ds = new DataInputStream(bis);
    }

    @Override
    public void open(int pos) throws IOException {

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
