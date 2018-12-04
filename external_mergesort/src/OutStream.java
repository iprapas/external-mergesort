import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public abstract class OutStream {

    protected OutputStream os;
    protected DataOutputStream dos;
    protected String path;

    public abstract void create() throws FileNotFoundException;
    public abstract  void write (int element) throws IOException;
    public abstract void close() throws IOException;

}
