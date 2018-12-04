import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class GenerateFile {

    public static void generate(String filename, int elements) throws IOException {
        OutStream output = new OutputStream1(filename);
        output.create();
        for(int i = 0; i < elements; i++){
            Random random = new Random();
            int number = random.nextInt(Integer.MAX_VALUE);
            output.write(number);
        }
        output.close();
    }

}
