import java.io.*;

public class InputStream2 extends InStream {

    private BufferedInputStream bis;

    public InputStream2(String filepath) {
        path = filepath;
    }

    @Override
    public void open() throws FileNotFoundException {
        is = new FileInputStream(new File(path));
        bis = new BufferedInputStream(is);
        ds = new DataInputStream(bis);
    }

    @Override
    public void open(int pos) throws IOException {
        is = new FileInputStream( new File(path) );
        bis = new BufferedInputStream( is );
        ds = new DataInputStream( bis );
        ds.skipBytes(4*pos);

        ds.skipBytes(pos * 4);
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
        bis.close();
    }
}
