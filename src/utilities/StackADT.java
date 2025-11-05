package utilities;

import java.util.NoSuchElementException;

/**
 * Interface for a generic Stack Abstract Data Type (ADT).
 * Represents a Last-In-First-Out (LIFO) collection of elements.
 *
 *
 * @param <T> the type of elements in the stack
 */
public interface StackADT<T> {
	
	/**
	 * Returns a new, empty stack of the same concrete implementation type.
	 *
	 * Pre-condition: none.
	 *
	 * Post-condition:
	 * - returned stack is empty (size() == 0)
	 * - returned stack is a distinct object (mutating it does not affect this stack)
	 *
	 * Implementing classes should return a newly constructed instance of their concrete type.
	 *
	 * @return a new empty stack of the same implementation
	 */
	StackADT<T> newStack();
	
    /**
     * Adds an item to the top of the stack.
     *
     * Pre-condition:
     * - item != null
     *   - implementations should throw {@link NullPointerException} if item is null
     *
     * Post-condition:
     * - size() increases by 1
     * - peek() (immediately after a successful push) returns an element equal to item
     *
     * @param item the item to add
     * @throws NullPointerException if the item is null
     */
    void push(T item);

    /**
     * Removes and returns the item from the top of the stack.
     *
     * Pre-condition:
     * - !isEmpty()
     *   - implementations should throw {@link NoSuchElementException} if the stack is empty
     *
     * Post-condition:
     * - size() decreases by 1
     * - the returned value equals the former top element
     *
     * @return the item removed
     * @throws NoSuchElementException if the stack is empty
     */
    T pop();

    /**
     * Returns the item at the top without removing it.
     *
     * Pre-condition:
     * - !isEmpty()
     *   - implementations should throw {@link NoSuchElementException} if the stack is empty
     *
     * Post-condition:
     * - size() is unchanged
     * - the returned value equals the current top element
     *
     * @return the item at the top
     * @throws NoSuchElementException if the stack is empty
     */
    T peek();

    /**
     * Checks if the stack is empty.
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - returns true if size() == 0
     *
     * @return true if the stack is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the stack.
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - returns an integer >= 0 representing the current number of elements
     *
     * @return the size of the stack
     */
    int size();

    /**
     * Removes all elements from the stack.
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - size() == 0
     * - if the implementation clears internal storage, references to former elements are dropped
     */
    void clear();

    /**
     * Returns true if this stack contains the specified element.
     *
     * Pre-condition:
     * - item != null
     *   - implementations may throw {@link NullPointerException} if item is null
     *
     * Post-condition:
     * - returns true if there exists an element e in the stack such that e.equals(item)
     * - size() is unchanged
     *
     * @param item the element to check
     * @return true if the element is in the stack, false otherwise
     * @throws NullPointerException if item is null (implementations may choose to return false instead)
     */
    boolean contains(T item);

    /**
     * Returns the 1-based position of the element from the top of the stack.
     *
     * Pre-condition:
     * - item != null
     *   - implementations may throw {@link NullPointerException} if item is null
     *
     * Post-condition:
     * - returns the 1-based distance from the top to the first matching element (top element is position 1)
     * - returns -1 if no matching element is found
     * - size() is unchanged
     *
     * @param item the element to search for
     * @return position from the top (1-based), or -1 if not found
     * @throws NullPointerException if item is null (implementations may choose to return -1 instead)
     */
    int search(T item);

    /**
     * Returns an array containing all elements in the stack.
     * The top of the stack is the first element in the array.
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - returned array length equals size()
     * - element at index 0 equals the top element, index 1 the next element, etc.
     * - size() is unchanged
     *
     * @return an array of stack elements (top at index 0)
     */
    Object[] toArray();

    /**
     * Returns an array containing all elements in the stack.
     * The top of the stack is the first element in the array.
     *
     * Pre-condition:
     * - copy != null
     *   - implementations should throw {@link NullPointerException} if copy is null
     *
     * Post-condition:
     * - if copy.length >= size(), elements are stored into copy (top at index 0) and copy is returned;
     *   any array slot immediately after the last element may be set to null per array-to-collection conventions
     * - otherwise a new array of the runtime type of copy's component type is returned containing the elements
     * - size() is unchanged
     *
     * @param copy the array into which the elements are to be stored
     * @return the array containing the stack elements
     * @throws NullPointerException if copy is null
     */
    T[] toArray(T[] copy);

    /**
     * Compares this stack to another stack for equality.
     *
     * Pre-condition:
     * - none (implementations may choose to treat null as not equal)
     *
     * Post-condition:
     * - returns true iff both stacks have equal size and corresponding elements are equal
     *   when viewed from top to bottom
     * - size() is unchanged for either stack
     *
     * @param that the other stack to compare
     * @return true if both stacks contain the same elements in the same order (top-to-bottom)
     */
    boolean equals(StackADT<T> that);

    /**
     * Returns an iterator over the elements in this stack.
     * The iterator does not support the remove operation.
     *
     * Pre-condition: none.
     *
     * Post-condition:
     * - the returned iterator traverses elements from top to bottom
     * - calling next() iterates the same sequence described for {@link #toArray()}
     * - modifications to the stack may or may not be reflected in the iterator depending on the implementation;
     *   document behavior in implementing classes
     *
     * @return an iterator over the stack elements
     */
    java.util.Iterator<T> iterator();
}
