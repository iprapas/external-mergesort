import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static java.lang.Long.min;

public class OutputStream4 extends OutStream{
    private final int bsize;
    private FileChannel fc;
    MappedByteBuffer mem;
    private long memPos;
    private int fileSize;
    private long runningPos;
    int numInts;
    public OutputStream4(String filepath, int bufferSize){
        path = filepath;
        bsize = bufferSize;
    }



    @Override
    public void create() throws IOException {
        fc = new RandomAccessFile(path, "rw").getChannel();
        mem =fc.map(FileChannel.MapMode.READ_WRITE, 0,   bsize);
        runningPos=0;
        memPos=0;
    }

    public void create(int skip) throws IOException {
        int byteSkip = skip*4;
        fc = new RandomAccessFile(path, "rw").getChannel();
        mem =fc.map(FileChannel.MapMode.READ_WRITE, byteSkip,   bsize);
        memPos = byteSkip;
        runningPos=0;
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
        mem = null; //MappedByteBuffer
        System.gc();
        fc.truncate(memPos); //FileChannel
        fc.close();
    }
}
