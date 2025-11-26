package implementations;

/**
 * Node class for MyDLL (Doubly Linked List)
 * @param <E> The type of element stored in this node
 */

public class MyDLLNode<E> {
	// attributes
	E data;
	MyDLLNode<E> prev;
	MyDLLNode<E> next;
	 /**
     * Constructor 
     * @param data the element to store in this node
     */
	public MyDLLNode(E data) {
		this.data = data;
		this.prev = null;
		this.next = null;
	}
	
	 /**
     * Constructor with links to other nodes
     * @param data the element to store
     * @param prev the previous node
     * @param next the next node
     */
	public MyDLLNode(E data, MyDLLNode<E>prev, MyDLLNode<E> next) {
		this.data = data;
		this.prev = prev;
		this.next = next;
	}

}
