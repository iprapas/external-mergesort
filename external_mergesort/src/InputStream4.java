import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;

import static java.lang.Math.min;


public class InputStream4 extends InStream{
    private final int bsize;
    private FileChannel fc;
    long fileSize;
    long memPos=0;
    long runningPos=0;
    MappedByteBuffer mem;
    public InputStream4(String filePath, int bufferSize){
        path = filePath;
        bsize = bufferSize;
    }

    @Override
    public void open() throws IOException {
        fc = new RandomAccessFile(path, "rw").getChannel();
        fileSize = fc.size();
        mem =fc.map(FileChannel.MapMode.READ_WRITE, 0, bsize);
    }

    @Override
    public void open(int skip) throws IOException {
        int byteSkip = skip*4;
        fc = new RandomAccessFile(path, "rw").getChannel();
        fileSize = fc.size();
        mem =fc.map(FileChannel.MapMode.READ_WRITE, byteSkip, bsize);
        memPos= byteSkip;
    }

    @Override
    public int read_next() throws IOException {


        if (runningPos>=mem.limit()) {
            mem =fc.map(FileChannel.MapMode.READ_WRITE, memPos, min(bsize, fileSize-memPos));
            runningPos=0;
        }
        runningPos +=4;
        memPos+=4;
        int nextInt = mem.getInt();
        return nextInt;

    }

    @Override
    public boolean end_of_stream() throws IOException {
        return (memPos>=fileSize);
    }

    @Override
    public void close() throws IOException {
        return;
    }
}
