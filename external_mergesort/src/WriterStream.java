public class WriterStream {

    private OutStream os;

    /**
     * Used for Implementation 1,2 because they don't need a buffer argument.
     *
     * @param implementation
     * @param filepath
     */
    public WriterStream(int implementation, String filepath) {
        switch (implementation) {
            case 1:
                os = new OutputStream1(filepath) {
                };
                break;
            case 2:
                os = new OutputStream2(filepath);
                break;
            default:
                System.out.println("Please select implementation among [1,4]");
        }
    }

    /**
     * Used for Implementation 3,4 as they need a buffer argument.
     *
     * @param implementation
     * @param filepath
     * @param buffersize
     */
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
        }
    }

    public OutStream getStream() {
        return os;
    }

}
