package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * Doubly-linked list implementation of the ListADT interface.
 *
 * @param <E> the type of elements stored in this list
 */
public class MyDLL<E> implements ListADT<E> {

	/** Reference to the first node in the list. */
	private MyDLLNode<E> head;

	/** Reference to the last node in the list. */
	private MyDLLNode<E> tail;

	/** Number of elements currently stored in the list. */
	private int size;

	/**
	 * Constructs an empty doubly-linked list.
	 */
	public MyDLL() {
		head = null;
		tail = null;
		size = 0;
	}

	/** {@inheritDoc} */
	@Override
	public int size() {
		return size;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		MyDLLNode<E> current = head;
		while (current != null) {
			MyDLLNode<E> next = current.next;
			current.data = null;
			current.prev = null;
			current.next = null;
			current = next;
		}
		head = null;
		tail = null;
		size = 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element");
		}

		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

		if (isEmpty()) {
			head = newNode;
			tail = newNode;
		} else {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}

		size++;
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {

		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element");
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		// Append at the end
		if (index == size) {
			return add(toAdd);
		}

		// Insert at the head
		if (index == 0) {
			MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
			newNode.next = head;
			if (head != null) {
				head.prev = newNode;
			}
			head = newNode;
			if (tail == null) {
				tail = newNode;
			}
			size++;
			return true;
		}

		// Insert in the middle (before node at position index)
		MyDLLNode<E> current = getNode(index);
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

		newNode.prev = current.prev;
		newNode.next = current;
		current.prev.next = newNode;
		current.prev = newNode;

		size++;
		return true;
	}

	/**
	 * Returns the node at the given index. This method runs in O(n), but uses
	 * bidirectional traversal to start from head or tail depending on index.
	 *
	 * @param index the index of the node to retrieve
	 * @return the node at the specified index
	 * @throws IndexOutOfBoundsException if index is out of range
	 */
	private MyDLLNode<E> getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		MyDLLNode<E> current;

		// If index is in the first half, start from head
		if (index < size / 2) {
			current = head;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
		} else { // Otherwise start from tail
			current = tail;
			for (int i = size - 1; i > index; i--) {
				current = current.prev;
			}
		}

		return current;
	}

	/** {@inheritDoc} */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		MyDLLNode<E> node = getNode(index);
		return node.data;
	}

	/** {@inheritDoc} */
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {

		if (toChange == null) {
			throw new NullPointerException("Cannot set null element");
		}

		MyDLLNode<E> node = getNode(index);
		E oldData = node.data;
		node.data = toChange;
		return oldData;
	}

	/** {@inheritDoc} */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		MyDLLNode<E> nodeToRemove = getNode(index);
		E removedData = nodeToRemove.data;

		// Case 1: only one node
		if (size == 1) {
			head = null;
			tail = null;
		}
		// Case 2: removing head
		else if (nodeToRemove == head) {
			head = head.next;
			head.prev = null;
		}
		// Case 3: removing tail
		else if (nodeToRemove == tail) {
			tail = tail.prev;
			tail.next = null;
		}
		// Case 4: removing a middle node
		else {
			nodeToRemove.prev.next = nodeToRemove.next;
			nodeToRemove.next.prev = nodeToRemove.prev;
		}

		nodeToRemove.prev = null;
		nodeToRemove.next = null;
		nodeToRemove.data = null;

		size--;
		return removedData;
	}

	/** {@inheritDoc} */
	@Override
	public E remove(E toRemove) throws NullPointerException {
		if (toRemove == null) {
			throw new NullPointerException("Cannot remove null element");
		}

		MyDLLNode<E> current = head;
		int index = 0;

		while (current != null) {
			if (current.data.equals(toRemove)) {
				return remove(index);
			}
			current = current.next;
			index++;
		}

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException("Cannot search for null element");
		}

		MyDLLNode<E> current = head;
		while (current != null) {
			if (current.data.equals(toFind)) {
				return true;
			}
			current = current.next;
		}

		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null list");
		}

		Iterator<? extends E> it = toAdd.iterator();
		while (it.hasNext()) {
			add(it.next());
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		MyDLLNode<E> current = head;
		int index = 0;

		while (current != null) {
			result[index] = current.data;
			current = current.next;
			index++;
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public E[] toArray(E[] toHold) throws NullPointerException {
		if (toHold == null) {
			throw new NullPointerException("Array cannot be null");
		}

		if (toHold.length < size) {
			toHold = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
		}

		MyDLLNode<E> current = head;
		int index = 0;
		while (current != null) {
			toHold[index] = current.data;
			current = current.next;
			index++;
		}

		if (toHold.length > size) {
			toHold[size] = null;
		}

		return toHold;
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<E> iterator() {
		return new DLLIterator();
	}

	/**
	 * Iterator implementation for the doubly-linked list.
	 */
	private class DLLIterator implements Iterator<E> {

		/** The current node in the iteration. */
		private MyDLLNode<E> current;

		/**
		 * Constructs an iterator starting at the head of the list.
		 */
		public DLLIterator() {
			current = head;
		}

		/** {@inheritDoc} */
		@Override
		public boolean hasNext() {
			return current != null;
		}

		/** {@inheritDoc} */
		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the list");
			}
			E data = current.data;
			current = current.next;
			return data;
		}
	}
}
