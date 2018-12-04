import java.io.FileNotFoundException;
import java.io.IOException;

public class InputStream4 extends InStream{

    public InputStream4(String filepath){
        path = filepath;

    }

    @Override
    public void open() throws FileNotFoundException {

    }

    @Override
    public int read_next() throws IOException {
        return 0;
    }

    @Override
    public boolean end_of_stream() throws IOException {
        return false;
    }

    @Override
    public void close() throws IOException {

    }
}
