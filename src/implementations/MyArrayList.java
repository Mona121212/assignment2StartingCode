package implementations;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.Arrays;
import utilities.ListADT;
import utilities.Iterator;

/**
 * Simple array-backed implementation of the ListADT interface.
 *
 * @param <E> element type
 */
public class MyArrayList<E> implements ListADT<E>
{
	// constant
    private static final int DEFAULT_CAPACITY = 10;

    // internal array to hold elements
    private Object[] elements;
    // current number of elements
    private int size;

    // default constructor
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    // constructor with initial capacity
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
        	// throw exception for negative capacity
            throw new IllegalArgumentException("initialCapacity must be >= 0");
        }
        // initialize internal array and size
        elements = new Object[Math.max(initialCapacity, DEFAULT_CAPACITY)];
        size = 0;
    }


    // ensure the internal array has at least the specified capacity
    private void ensureCapacity(int minCapacity) {
    	// if current capacity is sufficient, do nothing
        if (elements.length >= minCapacity) return;
        // double the size and add one, or set to minCapacity if larger
        int newCapacity = elements.length * 2 + 1;
        if (newCapacity < minCapacity) newCapacity = minCapacity;
        // resize the internal array
        elements = Arrays.copyOf(elements, newCapacity);
    }

    // get the element at the specified index
    @SuppressWarnings("unchecked")
    private E elementAt(int index) {
        return (E) elements[index];
    }

    // check if index is in range for add operation
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
    }

    // check if index is in range for get, set, remove operations
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
    }


    //size of the list
    @Override
    public int size() {
        return size;
    }

    //clear the list
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) elements[i] = null;
        size = 0;
    }

    //add element at index
    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
    	// check for null
        if (toAdd == null) throw new NullPointerException("null elements not supported"); 
        rangeCheckForAdd(index); // index can be == size for add at end
        ensureCapacity(size + 1); // make sure there's room
        // shift right
        if (index < size) {
            System.arraycopy(elements, index, elements, index + 1, size - index);
        }
        // insert new element
        elements[index] = toAdd;
        size++;
        return true;
    }

    //add element at end
    @Override
    public boolean add(E toAdd) throws NullPointerException {
    	// check for null
        if (toAdd == null) throw new NullPointerException("null elements not supported");
        ensureCapacity(size + 1);
        elements[size++] = toAdd;
        return true;
    }

    //add all elements from another ListADT
    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
    	// check for null
        if (toAdd == null) throw new NullPointerException("toAdd is null");
        // iterate and add each element
        Iterator<? extends E> it = toAdd.iterator();
        // track if any elements were added
        boolean changed = false;
        // add each element from the iterator
        while (it.hasNext()) {
            E elem = it.next();
            add(elem);
            changed = true;
        }
        return changed;
    }

    //get element at index
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        rangeCheck(index);
        return elementAt(index);
    }

    //remove element at index
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
    	// check index range
        rangeCheck(index);
        @SuppressWarnings("unchecked")
        E removed = (E) elements[index];
        int numMoved = size - index - 1;
        // shift left
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        // clear last element
        elements[--size] = null;
        return removed;
    }

    //remove first occurrence of element
    @Override
    public E remove(E toRemove) throws NullPointerException {
    	// check for null
        if (toRemove == null) throw new NullPointerException("null elements not supported");
        // search for the element
        for (int i = 0; i < size; i++) {
        	// if found, remove it
            if (toRemove.equals(elements[i])) {
                return remove(i);
            }
        }
        return null;
    }

    //set element at index
    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
    	// check for null
        if (toChange == null) throw new NullPointerException("null elements not supported");
        // check index range
        rangeCheck(index);
        @SuppressWarnings("unchecked")
        E old = (E) elements[index];
        // replace element
        elements[index] = toChange;
        // return old element
        return old;
    }

    // check if list is empty
    @Override
    public boolean isEmpty() {
    	// return true if size is 0
        return size == 0;
    }

    // check if list contains the specified element
    @Override
    public boolean contains(E toFind) throws NullPointerException {
    	// check for null
        if (toFind == null) throw new NullPointerException("null elements not supported");
        // search for the element
        for (int i = 0; i < size; i++) {
        	// if found, return true
            if (toFind.equals(elements[i])) return true;
        }
        return false;
    }

    // convert list to array
    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] toHold) throws NullPointerException {
    	// check for null
        if (toHold == null) throw new NullPointerException("toHold is null");
        // if toHold is too small, create a new array
        if (toHold.length < size) {
        	// create new array of the same type as toHold
            E[] newArr = (E[]) Array.newInstance(toHold.getClass().getComponentType(), size);
            // copy elements into new array
            System.arraycopy(elements, 0, newArr, 0, size);
            return newArr;
        } else {
        	// copy elements into toHold
            System.arraycopy(elements, 0, toHold, 0, size);
            // if toHold is larger, set next element to null
            if (toHold.length > size) {
            	// mark end of list
                toHold[size] = null;
            }
            return toHold;
        }
    }

    // convert list to array of Objects
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    // return an iterator over the elements in the list
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }


    /**
     * The iterator makes a copy (snapshot) of the current elements array so
     * that changes to the list after the iterator is created do not affect the
     * iteration. The copy is of the internal structure (object references).
     */
    private class ArrayIterator implements Iterator<E> {
    	
    	// snapshot of elements at iterator creation
        private final Object[] snapshot;
        // current position in the iteration
        private int cursor;

        // constructor creates snapshot
        ArrayIterator() {
        	// make a copy of the current elements
            snapshot = Arrays.copyOf(elements, size);
            // start cursor at beginning
            cursor = 0;
        }

        // check if there are more elements
        @Override
        public boolean hasNext() {
        	// return true if cursor is less than snapshot length
            return cursor < snapshot.length;
        }

        // return the next element
        @Override
        @SuppressWarnings("unchecked")
        public E next() throws NoSuchElementException {
        	// if no more elements, throw exception
            if (!hasNext()) {
                throw new NoSuchElementException("no more elements");
            }
            // return current element and advance cursor
            return (E) snapshot[cursor++];
        }
    }
}