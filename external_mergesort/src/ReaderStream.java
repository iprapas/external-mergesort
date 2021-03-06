public class ReaderStream {

    private InStream is;


    /**
     * Used for Implementation 1,2 because they don't need a buffer argument.
     * @param implementation
     * @param filepath
     */
    public ReaderStream(int implementation, String filepath) {
        switch (implementation) {
            case 1:
                is = new InputStream1(filepath);
                break;
            case 2:
                is = new InputStream2(filepath);
                break;
            default:
                System.out.println("Please select implementation among [1,4]");
        }
    }

    /**
     * Used for Implementation 3,4 as they need a buffer argument.
     * @param implementation
     * @param filepath
     * @param buffersize
     */
    public ReaderStream(int implementation, String filepath, int buffersize) {
        switch (implementation) {
            case 1:
                is = new InputStream1(filepath);
                break;
            case 2:
                is = new InputStream2(filepath);
                break;
            case 3:
                is = new InputStream3(filepath, buffersize);
                break;
            case 4:
                is = new InputStream4(filepath, buffersize);
                break;
            default:
                System.out.println("Please select implementation among [1,4]");
        }
    }

    public InStream getStream() {
         return is;
    }

}
