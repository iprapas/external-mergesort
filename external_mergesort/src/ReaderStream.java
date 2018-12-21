public class ReaderStream {

    private InStream is;


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
