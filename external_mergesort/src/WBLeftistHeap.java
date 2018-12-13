public class WBLeftistHeap <T extends Comparable<? super T>> implements Heap<T>{

	protected static class Tree<E>{
		E element;
		int weight;
		Tree<E> left;
		Tree<E> right;
		int streamIndex;
	}
	

	
	protected Tree<T> root;
	
	public  WBLeftistHeap() {
		root = null;
	}
	
	@Override
	public boolean isEmpty() {
		return root == null;
	}
	
	public static <T> int weight(Tree <T> tree) {
		return tree == null ? 0 : tree.weight;
	}

	@Override
	public int size() {
		return isEmpty()? 0: root.weight;
	}


	@Override
	public void insert(T x) {
		Tree<T> tree = new Tree<>();
		tree.element = x;
		tree.weight = 1;
		tree.left = null;
		tree.right = null;
		root = merge(root,tree);
	}

	@Override
	public T minElement() {
		if (isEmpty())
			throw new RuntimeException("minElement on Empty heap");
		
		return root.element;
	}
	
	public int getStreamIndex () {
		if (isEmpty())
			throw new RuntimeException("minElement on Empty heap");
		
		return root.streamIndex;
	}

	@Override
	public void deleteMinElement() {
		if (isEmpty())
			throw new RuntimeException("minElement on Empty heap");

		root = merge(root.left,root.right);
	}
	
	
	

	public static <T extends Comparable <? super T>> Tree<T> merge(Tree<T> h1, Tree<T> h2){
		if (h1==null)
			return h2;
		if (h2 == null)
			return h1;
		
		if(h2.element.compareTo(h1.element)<0) {
			Tree<T> aux = h1;
			h1 = h2;
			h2 = aux;
		}
		
		h1.right = merge(h1.right,h2);
		
		int weightLeft = weight(h1.left);
		int weightRight = weight(h1.right);
		h1.weight = weightLeft + weightRight + 1;
		
		 if (weightLeft <weightRight) {
				Tree<T> aux = h1;
				h1 = h2;
				h2 = aux;
		 }
		 
		 return h1;		
	}
	
//	This implementation is the same but less efficient
//
//	private static  <T> Tree<T> node(T x, Tree<T> h1, Tree<T> h2){
//	int w1 = h1.weight;
//	int w2 = h2.weight;
//	
//	Tree<T> tree = new Tree<T>();
//	tree.element = x;
//	tree.weight = w1 + w2 + 1;
//	if (w1>w2) {
//		tree.left = h1;
//		tree.right = h2;
//	}else {
//		tree.left = h2;
//		tree.left = h1;	
//	}
//	return tree;
//}
//	
//	public static <T extends Comparable <? super T>> Tree<T> merge(Tree<T> h1, Tree<T> h2){
//		if (h1==null)
//			return h2;
//		if (h2 == null)
//			return h1;
//		
//		T x1 = h1.element;
//		T x2 = h2.element;
//		
//		if (x1.compareTo(x2)<= 0) 
//			return node(x1,h1.left,merge(h1.right,h2));
//		else
//			return node(x2,h2.left,merge(h2.right,h1));		
//		
//	}
	

}
