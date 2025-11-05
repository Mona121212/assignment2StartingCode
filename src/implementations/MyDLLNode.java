package utilities;

public class MyDLLNode<T> {
    private T element;
    private MyDLLNode<T> next;
    private MyDLLNode<T> prev;
    public MyDLLNode(T element) {
        this.element = element;
        this.next = null;
        this.prev = null;
    }
    public T getElement() {
        return element;
    }
    public void setElement(T element) {
        this.element = element;
    }
    public MyDLLNode<T> getNext() {
        return next;
    }
    public void setNext(MyDLLNode<T> next) {
        this.next = next;
    }
    public MyDLLNode<T> getPrev() {
        return prev;
    }
    public void setPrev(MyDLLNode<T> prev) {
        this.prev = prev;
    }
}
