import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class OutputStream4 extends OutStream{
    private FileChannel fc;
    MappedByteBuffer mem;
    long memPos=0;
    public OutputStream4(String filepath){
        path = filepath;
    }

    @Override
    public void create() throws IOException {
        fc = new RandomAccessFile(path, "rw").getChannel();
        mem =fc.map(FileChannel.MapMode.READ_WRITE, 0,   4*100000);
    }

    @Override
    public void write(int element) throws IOException {
        mem.putInt(element);
        memPos += 4;
//        if (mempos>=mem.limit()) {
//            mem =fc.map(FileChannel.MapMode.READ_WRITE, mempos, 128*1024);
//        }
    }

    @Override
    public void close() throws IOException {
        return;
    }
}
