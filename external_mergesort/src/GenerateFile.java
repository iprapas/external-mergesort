import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateFile {

    public static void generate(String filename, int N) throws IOException {
        WriterStream output = new WriterStream(2, filename);

        if (N <= 1000) { //also write visual text
            PrintWriter writer = new PrintWriter("visual_input_" + N + ".txt");
            output.getStream().create();
            for (int i = 0; i < N; i++) {
                Random random = new Random();
                Boolean negative = random.nextBoolean();
                int number;

                if (negative) {
                    number = -1 * random.nextInt(Integer.MAX_VALUE);
                } else {
                    number = random.nextInt(Integer.MAX_VALUE);
                }
                output.getStream().write(number);
                writer.println(number);
            }
            output.getStream().close();
            writer.close();
        } else { // do not write visual text
            output.getStream().create();
            for (int i = 0; i < N; i++) {
                Random random = new Random();
                Boolean negative = random.nextBoolean();
                int number;

                if (negative) {
                    number = -1 * random.nextInt(Integer.MAX_VALUE);
                } else {
                    number = random.nextInt(Integer.MAX_VALUE);
                }
                output.getStream().write(number);
            }
            output.getStream().close();
        }
    }

}