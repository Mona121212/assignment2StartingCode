package utilities;

import adt.ListADT;
import java.util.NoSuchElementException;
import java.util.Iterator;
public class MyDLL<T> implements ListADT<T> {
    private MyDLLNode<T> head;
    private MyDLLNode<T> tail;
    private int size;
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }
    @Override
    public void add(T element) {
        if (element == null) throw new NullPointerException("Cannot add null element");
        MyDLLNode<T> newNode = new MyDLLNode<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }
    @Override
    public void add(int index, T element) {
        if (element == null) throw new NullPointerException("Cannot add null element");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        MyDLLNode<T> newNode = new MyDLLNode<>(element);
        if (index == size) {
            add(element);
            return;
        }
        if (index == 0) {
            newNode.setNext(head);
            if (head != null) head.setPrev(newNode);
            head = newNode;
            if (tail == null) tail = head;
        } else {
            MyDLLNode<T> current = getNodeAt(index);
            MyDLLNode<T> prev = current.getPrev();
            prev.setNext(newNode);
            newNode.setPrev(prev);
            newNode.setNext(current);
            current.setPrev(newNode);
        }
        size++;
    }
    @Override
    public T remove(int index) {
        if (isEmpty()) throw new NoSuchElementException("List is empty");
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        MyDLLNode<T> current = getNodeAt(index);
        T removed = current.getElement();
        if (current == head && current == tail) {
            head = tail = null;
        } else if (current == head) {
            head = head.getNext();
            head.setPrev(null);
        } else if (current == tail) {
            tail = tail.getPrev();
            tail.setNext(null);
        } else {
            MyDLLNode<T> prev = current.getPrev();
            MyDLLNode<T> next = current.getNext();
            prev.setNext(next);
            next.setPrev(prev);
        }
        size--;
        return removed;
    }
    @Override
    public boolean remove(T element) {
        if (isEmpty()) return false;
        MyDLLNode<T> current = head;
        while (current != null) {
            if (current.getElement().equals(element)) {
                if (current == head) {
                    head = head.getNext();
                    if (head != null) head.setPrev(null);
                } else if (current == tail) {
                    tail = tail.getPrev();
                    if (tail != null) tail.setNext(null);
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return getNodeAt(index).getElement();
    }
    private MyDLLNode<T> getNodeAt(int index) {
        MyDLLNode<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) current = current.getNext();
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.getPrev();
        }
        return current;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        MyDLLNode<T> current = head;
        while (current != null) {
            sb.append(current.getElement());
            if (current.getNext() != null) sb.append(", ");
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private MyDLLNode<T> current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.getElement();
                current = current.getNext();
                return data;
            }
        };
    }
}
