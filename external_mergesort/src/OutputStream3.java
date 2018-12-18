import java.io.*;

public class OutputStream3 extends OutStream {

    private int bufferSize;
    private BufferedOutputStream bos;

    public OutputStream3(String filepath, int bufferSize){
        this.bufferSize = bufferSize;
        path = filepath;
    }

    @Override
    public void create() throws FileNotFoundException {
        os = new FileOutputStream( new File(path ) );
        bos = new BufferedOutputStream( os , bufferSize);
        dos = new DataOutputStream( bos );
    }

    @Override
    public void create(int skip) throws IOException {
        os = new FileOutputStream( new File(path ) );
        bos = new BufferedOutputStream( os , bufferSize);
        dos = new DataOutputStream( bos );
    }

    @Override
    public void write(int element) throws IOException {
        dos.writeInt(element);
    }

    @Override
    public void close() throws IOException {
        dos.close();
        bos.close();
        os.close();
    }
}
