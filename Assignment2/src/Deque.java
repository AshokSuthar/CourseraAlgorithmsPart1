
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ashok
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    private class Node<Item> {

        Item value;
        Node<Item> next;
        Node<Item> prev;

        Node(Item item) {
            this.value = item;
            this.next = null;
            this.prev = null;
        }
    }

    // construct an empty deque
    public Deque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.head == null) {
            head = new Node<Item>(item);
            tail = head;

        } else {
            Node<Item> temp = new Node<Item>(item);
            temp.next = this.head;
            this.head.prev = temp;
            this.head = temp;
        }
        size++; // increment size

    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.tail == null) {
            tail = new Node<Item>(item);
            head = tail;
        } else {
            Node<Item> temp = new Node<Item>(item);
            this.tail.next = temp;
            temp.prev = this.tail;
            this.tail = temp;
        }
        size++; // increment size
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> temp = this.head;
        if (head == tail) {
            this.head = null;
            tail = head;
        } else {
            this.head = this.head.next;
            this.head.prev = null;
        }
        size--;
        return temp.value;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> temp = this.tail;
        if (head == tail) {
            this.tail = null;
            head = tail;
        } else {
            this.tail = this.tail.prev;
            this.tail.next = null;
        }
        size--;
        return temp.value;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node<Item> curr = head;

        public boolean hasNext() {
            return curr != null;
        }

        public Item next() {
            if (curr == null) {
                throw new NoSuchElementException();
            }
            Item item = curr.value;
            curr = curr.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(2);
        d.addLast(1);
        for (int item : d) {
            System.out.print(item + " ");
        }
        System.out.println("Size: " + d.size());
    }

}
