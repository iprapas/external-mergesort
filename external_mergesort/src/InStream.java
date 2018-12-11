import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class InStream {

    protected String path;
    protected InputStream is;
    protected DataInputStream ds;

    public abstract void open() throws IOException;
    public abstract  int read_next () throws IOException;
    public abstract boolean end_of_stream() throws IOException;
    public abstract void close() throws IOException;
}
