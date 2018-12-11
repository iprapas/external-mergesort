import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;


public class InputStream4 extends InStream{
    private FileChannel fc;
    long bufferSize=1024;
    long fileSize;
    long memPos=0;
    MappedByteBuffer mem;
    public InputStream4(String filepath){
        path = filepath;

    }

    @Override
    public void open() throws IOException {
        fc = new RandomAccessFile(path, "r").getChannel();
        mem =fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
    }

    @Override
    public int read_next() throws IOException {
        memPos +=4;
        return mem.getInt();
    }

    @Override
    public boolean end_of_stream() throws IOException {
        return (memPos>=mem.limit());
    }

    @Override
    public void close() throws IOException {
        return;
    }
}
