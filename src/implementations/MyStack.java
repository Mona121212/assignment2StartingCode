package implementations;

import java.util.EmptyStackException;

import utilities.Iterator;
import utilities.StackADT;

/**
 * {@code MyStack} is an array-based implementation of the {@link StackADT}
 * interface.
 *
 * <p>
 * The top of the stack is stored at the end of the underlying
 * {@link MyArrayList}.
 *
 * @param <E> the type of elements stored in this stack
 */
public class MyStack<E> implements StackADT<E> {

	/**
	 * Underlying dynamic array list used to store stack elements. The logical top
	 * of the stack is at index {@code list.size() - 1}.
	 */
	private MyArrayList<E> list;

	/**
	 * Constructs an empty stack.
	 */
	public MyStack() {
		list = new MyArrayList<>();
	}

	/**
	 * Pushes an item onto the top of this stack.
	 *
	 * @param toAdd the item to be pushed
	 * @throws NullPointerException if {@code toAdd} is {@code null}
	 */
	@Override
	public void push(E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot push null element");
		}
		// Add to the end, which represents the top of the stack
		list.add(toAdd);
	}

	/**
	 * Removes and returns the object at the top of this stack.
	 *
	 * @return the item popped off the top of this stack
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public E pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.remove(list.size() - 1);
	}

	/**
	 * Looks at the object at the top of this stack without removing it.
	 *
	 * @return the object at the top of this stack
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public E peek() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.get(list.size() - 1);
	}

	/**
	 * Removes all of the elements from this stack. The stack will be empty after
	 * this call returns.
	 */
	@Override
	public void clear() {
		list.clear();
	}

	/**
	 * Returns {@code true} if this stack contains no elements.
	 *
	 * @return {@code true} if this stack contains no elements; {@code false}
	 *         otherwise
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Returns the number of elements in this stack.
	 *
	 * @return the number of elements in this stack
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Returns {@code true} if this stack contains the specified element.
	 *
	 * @param toFind the element whose presence in this stack is to be tested
	 * @return {@code true} if this stack contains the specified element;
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
	 * Returns the 1-based position of an object in this stack. The top-most element
	 * has position {@code 1}.
	 *
	 * @param toFind the desired object
	 * @return the 1-based position from the top, or {@code -1} if not found
	 */
	@Override
	public int search(E toFind) {
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i).equals(toFind)) {
				// position from the top (top is index list.size() - 1)
				return list.size() - i;
			}
		}
		return -1;
	}

	/**
	 * Returns an array containing all of the elements in this stack, in proper
	 * order from top to bottom.
	 *
	 * @return an array containing all elements from top to bottom
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[list.size()];

		// Copy from top (end of list) to bottom (beginning of list)
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(list.size() - 1 - i);
		}

		return array;
	}

	/**
	 * Returns an array containing all of the elements in this stack in proper order
	 * (from top to bottom); the runtime type of the returned array is that of the
	 * specified array. If the stack fits in the specified array, it is returned
	 * therein. Otherwise, a new array is allocated with the runtime type of the
	 * specified array and the size of this stack.
	 *
	 * @param holder the array into which the elements of the stack are to be
	 *               stored, if it is big enough; otherwise, a new array of the same
	 *               runtime type is allocated for this purpose
	 * @return an array containing the elements of the stack from top to bottom
	 * @throws NullPointerException if {@code holder} is {@code null}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E[] toArray(E[] holder) {
		if (holder == null) {
			throw new NullPointerException("Array cannot be null");
		}

		int size = list.size();

		if (holder.length < size) {
			holder = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), size);
		}

		// Copy from top (end of list) to bottom (beginning of list)
		for (int i = 0; i < size; i++) {
			holder[i] = list.get(size - 1 - i);
		}

		// If the array is larger than the number of elements, set element after
		// the last to null as per Java Collections convention.
		if (holder.length > size) {
			holder[size] = null;
		}

		return holder;
	}

	/**
	 * Returns an iterator over the elements in this stack in proper sequence (from
	 * top to bottom).
	 *
	 * @return an iterator over the elements in this stack from top to bottom
	 */
	@Override
	public Iterator<E> iterator() {
		return new StackIterator();
	}

	/**
	 * Iterator implementation for {@link MyStack} that traverses the elements from
	 * top to bottom.
	 */
	private class StackIterator implements Iterator<E> {

		/**
		 * Current index in the underlying list. Starts at the top of the stack.
		 */
		private int currentIndex;

		/**
		 * Constructs an iterator starting at the top of the stack.
		 */
		public StackIterator() {
			currentIndex = list.size() - 1;
		}

		/**
		 * Returns {@code true} if the iteration has more elements.
		 *
		 * @return {@code true} if the iteration has more elements; {@code false}
		 *         otherwise
		 */
		@Override
		public boolean hasNext() {
			return currentIndex >= 0;
		}

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws java.util.NoSuchElementException if the iteration has no more
		 *                                          elements
		 */
		@Override
		public E next() throws java.util.NoSuchElementException {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException("No more elements");
			}
			E element = list.get(currentIndex);
			currentIndex--;
			return element;
		}
	}

	/**
	 * Compares this stack with another stack for equality. Two stacks are
	 * considered equal if they have the same size and contain equal elements in the
	 * same order from top to bottom.
	 *
	 * @param that the stack to compare with
	 * @return {@code true} if the stacks are equal; {@code false} otherwise
	 */
	@Override
	public boolean equals(StackADT<E> that) {
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

			// Null elements should not appear because push disallows null,
			// but this check keeps the comparison robust.
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

	/**
	 * Returns {@code true} if the stack is at capacity.
	 * <p>
	 * For this dynamic array implementation, the stack has no fixed capacity limit,
	 * so this method always returns {@code false}.
	 *
	 * @return {@code false} because this stack has no fixed capacity
	 */
	@Override
	public boolean stackOverflow() {
		return false;
	}
}
