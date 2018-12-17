import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateFile {

    public static void generate(String filename, int N) throws IOException {
        OutStream output = new OutputStream1(filename);
        PrintWriter writer = new PrintWriter("visual_input_" + N + ".txt");
        output.create();
        for(int i = 0; i < N; i++){
            Random random = new Random();
            //long bnumber = Integer.MAX_VALUE + 1 - Integer.MIN_VALUE + Integer.MIN_VALUE;
            int number = random.nextInt(Integer.MAX_VALUE);
                output.write(number);
                writer.println(number);
        }
        output.close();
        writer.close();
    }

}