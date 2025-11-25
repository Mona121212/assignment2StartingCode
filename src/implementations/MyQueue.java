package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * {@code MyQueue} is a doubly-linked-list-based implementation of the
 * {@link QueueADT} interface using {@link MyDLL} as the underlying data
 * structure.
 *
 * <p>
 * The front of the queue corresponds to index {@code 0} in the underlying list,
 * and the rear of the queue corresponds to index {@code size() - 1}.
 * </p>
 *
 * @param <E> the type of elements in this queue
 */
public class MyQueue<E> implements QueueADT<E> {

	/**
	 * Underlying doubly linked list used to store the queue elements. The front of
	 * the queue is at index 0.
	 */
	private MyDLL<E> list;

	/**
	 * Constructs an empty queue.
	 */
	public MyQueue() {
		list = new MyDLL<>();
	}

	// ========== Core Queue Operations ==========

	/**
	 * Adds an item to the rear (end) of this queue.
	 *
	 * @param toAdd the item to be added to the queue
	 * @throws NullPointerException if {@code toAdd} is {@code null}
	 */
	@Override
	public void enqueue(E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot enqueue null element");
		}
		// Add to the end of the list (rear of the queue)
		list.add(toAdd);
	}

	/**
	 * Removes and returns the item at the front of the queue.
	 *
	 * @return the item at the front of the queue
	 * @throws EmptyQueueException if the queue is empty
	 */
	@Override
	public E dequeue() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException("Queue is empty");
		}
		// Remove from index 0 (front of the queue)
		return list.remove(0);
	}

	/**
	 * Returns the item at the front of the queue without removing it.
	 *
	 * @return the item at the front of the queue
	 * @throws EmptyQueueException if the queue is empty
	 */
	@Override
	public E peek() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException("Queue is empty");
		}
		// Get element at index 0 (front of the queue)
		return list.get(0);
	}

	/**
	 * Removes all items from this queue. The queue will be empty after this call
	 * returns.
	 */
	@Override
	public void dequeueAll() {
		list.clear();
	}

	// ========== Helper Methods ==========

	/**
	 * Returns {@code true} if this queue contains no elements.
	 *
	 * @return {@code true} if this queue is empty; {@code false} otherwise
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Returns the number of elements in this queue.
	 *
	 * @return the current size of the queue
	 */
	@Override
	public int size() {
		return list.size();
	}

	// ========== Query Methods ==========

	/**
	 * Returns {@code true} if this queue contains the specified element.
	 *
	 * @param toFind the element whose presence in this queue is to be tested
	 * @return {@code true} if this queue contains the specified element;
	 *         {@code false} otherwise
	 * @throws NullPointerException if {@code toFind} is {@code null}
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException("Cannot search for null element");
		}
		return list.contains(toFind);
	}

	/**
	 * Returns the 1-based position of the specified object in this queue, starting
	 * from the front.
	 *
	 * <p>
	 * The element at the front of the queue has position {@code 1}.
	 * </p>
	 *
	 * @param toFind the desired object
	 * @return the 1-based position from the front, or {@code -1} if not found
	 */
	@Override
	public int search(E toFind) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(toFind)) {
				// Convert 0-based index to 1-based position
				return i + 1;
			}
		}
		return -1;
	}

	// ========== Array Conversion ==========

	/**
	 * Returns an array containing all of the elements in this queue in proper order
	 * (from front to rear).
	 *
	 * @return an array containing all elements from front to rear
	 */
	@Override
	public Object[] toArray() {
		// Assumes MyDLL.toArray() already returns elements from front to rear
		return list.toArray();
	}

	/**
	 * Returns an array containing all of the elements in this queue in proper order
	 * (from front to rear); the runtime type of the returned array is that of the
	 * specified array. If the queue fits in the specified array, it is returned
	 * therein. Otherwise, a new array is allocated with the runtime type of the
	 * specified array and the size of this queue.
	 *
	 * @param holder the array into which the elements of the queue are to be
	 *               stored, if it is big enough; otherwise, a new array of the same
	 *               runtime type is allocated for this purpose
	 * @return an array containing the elements of this queue from front to rear
	 * @throws NullPointerException if {@code holder} is {@code null}
	 */
	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		if (holder == null) {
			throw new NullPointerException("Array cannot be null");
		}
		// Delegate to MyDLL's array conversion (front to rear order)
		return list.toArray(holder);
	}

	// ========== Iterator ==========

	/**
	 * Returns an iterator over the elements in this queue in proper sequence (from
	 * front to rear).
	 *
	 * @return an iterator from front to rear
	 */
	@Override
	public Iterator<E> iterator() {
		// Use the iterator provided by the underlying doubly linked list
		return list.iterator();
	}

	// ========== Comparison ==========

	/**
	 * Compares this queue with another queue for equality. Two queues are
	 * considered equal if they have the same size and contain equal elements in the
	 * same order from front to rear.
	 *
	 * @param that the queue to compare with
	 * @return {@code true} if the queues are equal; {@code false} otherwise
	 */
	@Override
	public boolean equals(QueueADT<E> that) {
		if (that == null) {
			return false;
		}

		if (this.size() != that.size()) {
			return false;
		}

		Iterator<E> thisIt = this.iterator();
		Iterator<E> thatIt = that.iterator();

		while (thisIt.hasNext()) {
			E thisElement = thisIt.next();
			E thatElement = thatIt.next();

			// By design, enqueue does not allow null, so null elements should not appear.
			if (thisElement == null) {
				if (thatElement != null) {
					return false;
				}
			} else if (!thisElement.equals(thatElement)) {
				return false;
			}
		}

		return true;
	}

	// ========== Capacity Check ==========

	/**
	 * Returns {@code true} if the queue is at capacity.
	 *
	 * <p>
	 * For a linked structure implementation, the queue does not have a fixed
	 * capacity limit (other than memory), so this method always returns
	 * {@code false}.
	 * </p>
	 *
	 * @return {@code false} because this queue has no fixed capacity
	 */
	@Override
	public boolean isFull() {
		return false;
	}
}
