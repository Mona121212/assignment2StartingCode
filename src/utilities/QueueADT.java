package utilities;

import java.util.NoSuchElementException;

/**
 * Interface for a generic Queue Abstract Data Type (ADT).
 * Represents a First-In-First-Out (FIFO) collection of elements.
 *
 * @param <T> the type of elements in the queue
 */
public interface QueueADT<T> {

	/**
	 * Returns a new, empty queue of the same concrete implementation type.
	 *
	 * Pre-condition: none.
	 *
	 * Post-condition:
	 * - returned queue is empty (size() == 0)
	 * - returned queue is a distinct object (mutating it does not affect this queue)
	 *
	 * Implementing classes should return a newly constructed instance of their concrete type.
	 *
	 * @return a new empty queue of the same implementation
	 */
	QueueADT<T> newQueue();
	
    /**
     * Adds an item to the end of the queue.
     *
     * Pre-condition:
     * - item != null
     *   - implementations should throw {@link NullPointerException} if item is null
     *
     * Post-condition:
     * - size() increases by 1
     * - peek() (immediately after a successful enqueue) returns the same item if it was the only one
     *
     * @param item the item to add
     * @throws NullPointerException if the item is null
     */
    void enqueue(T item);

    /**
     * Removes and returns the item from the front of the queue.
     *
     * Pre-condition:
     * - !isEmpty()
     *   - implementations should throw {@link NoSuchElementException} if the queue is empty
     *
     * Post-condition:
     * - size() decreases by 1
     * - returned value equals the former front element
     *
     * @return the item removed
     * @throws NoSuchElementException if the queue is empty
     */
    T dequeue();

    /**
     * Returns the item at the front without removing it.
     *
     * Pre-condition:
     * - !isEmpty()
     *   - implementations should throw {@link NoSuchElementException} if the queue is empty
     *
     * Post-condition:
     * - size() is unchanged
     * - returned value equals the current front element
     *
     * @return the item at the front
     * @throws NoSuchElementException if the queue is empty
     */
    T peek();

    /**
     * Checks if the queue is empty.
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - returns true if size() == 0
     *
     * @return true if the queue is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - returns an integer >= 0 representing the current number of elements
     *
     * @return the size of the queue
     */
    int size();

    /**
     * Removes all elements from the queue (Fancy way of doing clear()).
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - size() == 0
     * - if the implementation clears internal storage, references to former elements are dropped
     */
    void dequeueAll();

    /**
     * Returns true if this queue contains the specified element.
     *
     * Pre-condition:
     * - item != null
     *   - implementations may throw {@link NullPointerException} if item is null
     *
     * Post-condition:
     * - returns true if there exists an element e in the queue such that e.equals(item)
     * - size() is unchanged
     *
     * @param item the element to check
     * @return true if the element is in the queue, false otherwise
     * @throws NullPointerException if item is null (implementations may choose to return false instead)
     */
    boolean contains(T item);

    /**
     * Returns an array containing all elements in the queue.
     * The head of the queue is the first element in the array.
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - returned array length equals size()
     * - element at index 0 equals the front element, index 1 the next, etc.
     * - size() is unchanged
     *
     * @return an array of queue elements (head at index 0)
     */
    Object[] toArray();

    /**
     * Returns an array containing all elements in the queue.
     * The head of the queue is the first element in the array.
     *
     * Pre-condition:
     * - copy != null
     *   - implementations should throw {@link NullPointerException} if copy is null
     *
     * Post-condition:
     * - if copy.length >= size(), elements are stored into copy (head at index 0) and copy is returned;
     *   any array slot immediately after the last element may be set to null per array-to-collection conventions
     * - otherwise a new array of the runtime type of copy's component type is returned containing the elements
     * - size() is unchanged
     *
     * @param copy the array into which the elements are to be stored
     * @return the array containing the queue elements
     * @throws NullPointerException if copy is null
     */
    T[] toArray(T[] copy);

    /**
     * Compares this queue to another queue to check if they're equal.
     *
     * Pre-condition:
     * - none (implementations may choose to treat null as not equal)
     *
     * Post-condition:
     * - returns true if both queues have equal size and corresponding elements are equal
     *   when viewed from head to tail
     * - size() is unchanged for either queue
     *
     * @param that the other queue to compare
     * @return true if both queues contain the same elements in the same order (head-to-tail)
     */
    boolean equals(QueueADT<T> that);

    /**
     * Returns an iterator over the elements in this queue.
     * The iterator does not support the remove operation.
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - the returned iterator traverses elements from head to tail
     * - calling next() iterates the same sequence described for {@link #toArray()}
     * - modifications to the queue may or may not be reflected in the iterator depending on the implementation;
     *   document behavior in implementing classes
     *
     * @return an iterator over the queue elements
     */
    java.util.Iterator<T> iterator();
}