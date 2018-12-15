import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class OutputStream4 extends OutStream{
    private final int bsize;
    private FileChannel fc;
    MappedByteBuffer mem;
    private long memPos=0;
    private long runningPos=0;
    int numInts;
    public OutputStream4(String filepath, int bufferSize){
        path = filepath;
        bsize = bufferSize;
    }

    @Override
    public void create() throws IOException {
        fc = new RandomAccessFile(path, "rw").getChannel();
        mem =fc.map(FileChannel.MapMode.READ_WRITE, 0,   bsize);
    }

    public void create(int skip) throws IOException {
        int byteSkip = skip*4;
        fc = new RandomAccessFile(path, "rw").getChannel();
        mem =fc.map(FileChannel.MapMode.READ_WRITE, byteSkip,   bsize);
        memPos = byteSkip;
    }

    @Override
    public void write(int element) throws IOException {

        if (runningPos>=mem.limit()) {
            mem =fc.map(FileChannel.MapMode.READ_WRITE, memPos, bsize);
            runningPos =0;
        }
        memPos += 4;
        runningPos +=4;
        mem.putInt(element);
    }

    @Override
    public void close() throws IOException {
        return;
    }
}
