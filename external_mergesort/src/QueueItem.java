import java.util.Comparator;

public class QueueItem implements Comparable {

    private int streamNum;
    private int number;

    public QueueItem(int streamNum, int number){
        this.streamNum = streamNum;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getStreamNum() {
        return streamNum;
    }

    @Override
    public int compareTo(Object o2) {
        QueueItem item2 = (QueueItem) o2;
        if(this.getNumber() < item2.getNumber()){ //first item is smaller
            return -1;
        } else if (this.getNumber() > item2.getNumber()){ //first item is larger
            return 1;
        } else { //both items are equal
            return 0;
        }
    }
}
