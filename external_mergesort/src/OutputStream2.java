import java.io.*;

public class OutputStream2 extends OutStream {

    private BufferedOutputStream bos;

    public OutputStream2(String filepath){
        path = filepath;

    }

    @Override
    public void create() throws FileNotFoundException {
        os = new FileOutputStream( new File("output.txt" ) );
        bos = new BufferedOutputStream( os );
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
