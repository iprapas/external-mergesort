import java.io.IOException;
import java.util.List;

public class MultyWayMerge {

	private List<InStream> inputStreamList;
	private OutStream output;
	private WBLeftistHeap<Integer> heap;
	
	public MultyWayMerge(List<InStream> inputStreamList, OutStream output) throws IOException {
		this.inputStreamList = inputStreamList;
		this.output = output;
		this.output.create();
	}
	
	public void merge() throws IOException {
		
		initializeHeap(); 
		
		while (HasMoreElements() || heap.size() > 0) {
			int element = heap.minElement();
			int index = heap.getStreamIndex();
			heap.deleteMinElement();
			
			output.write(element);
			
			if (!inputStreamList.get(index).end_of_stream())
				heap.insert(element);
			
		}
		
		closeAllStreams();
			 
	}
	
	
	private void closeAllStreams() throws IOException {
		output.close();
		
		for(InStream stream : inputStreamList) 
			stream.close();
	}

	private void initializeHeap() throws IOException {
		 heap = new WBLeftistHeap<>();

		for (int index = 0; index < inputStreamList.size(); index++) 
			if (!inputStreamList.get(index).end_of_stream())
				heap.insert(inputStreamList.get(index).read_next());
	}

	private boolean HasMoreElements() throws IOException {
		for (InStream input : inputStreamList) 
			if(!input.end_of_stream()) 
				return true;
		
		return false;
	}
	
}
