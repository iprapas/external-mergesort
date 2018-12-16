import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public abstract class OutStream {

    protected OutputStream os;
    protected DataOutputStream dos;
    protected String path;

    public abstract void create() throws IOException;
    public abstract void create(int skip) throws IOException;
    public abstract  void write (int element) throws IOException;
    public abstract void close() throws IOException;

}
