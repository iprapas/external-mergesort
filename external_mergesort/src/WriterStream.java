public class WriterStream {
    private OutStream os;
    public WriterStream(int implementation, String filepath, int buffersize) {
        switch (implementation) {
            case 1:
                os = new OutputStream1(filepath) {
                };
                break;
            case 2:
                os = new OutputStream2(filepath);
                break;
            case 3:
                os = new OutputStream3(filepath, buffersize);
                break;
            case 4:
                os = new OutputStream4(filepath, buffersize);
                break;
            default:
                System.out.println("Please select implementation among [1,4]");

//                    GenerateFile gf = new GenerateFile();
//                    gf.generate(FILENAME, ELEMENTS);
        }
    }
    public OutStream getStream() {
        return os;
    }
}
