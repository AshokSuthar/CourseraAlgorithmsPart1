

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Check why linkedList random iterator implemented here doesn't work properly.
 * @author ashok
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Node head;
    private Node tail;

    private class Node<Item> {

        Item value;
        Node next;
        Node prev;

        Node(Item item) {
            this.value = item;
            this.next = null;
            this.prev = null;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (head == null) {
            head = new Node<>(item);
            tail = head;
        } else {
            Node<Item> temp = new Node<>(item);
            temp.next = head;
            head.prev = temp;
            head = temp;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        int indexToRemove = StdRandom.uniform(1, size + 1);
        Node<Item> curr = head;
        int count = 1;
        while (curr != null && count != indexToRemove) {
            curr = curr.next;
            count++;
        }
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        size--;
        return curr.value;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int indexToShow = StdRandom.uniform(1, size + 1);
        Node<Item> curr = head;
        int count = 1;
        while (curr != null && count != indexToShow) {
            curr = curr.next;
            count++;
        }
        return curr.value;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node<Item> curr = head;
        private int[] randomOrder;
        private int count;
        private int index;

        public ListIterator() {
            randomOrder = new int[size];
            for (int i = 0; i < size; i++) {
                randomOrder[i] = i;
            }
            StdRandom.shuffle(randomOrder);
            for (int i = 0; i < size; i++) {
                System.out.print(randomOrder[i]);
            }
            System.out.println();
            index = 0;
        }

        public boolean hasNext() {
            return curr != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() { //whats the prob with you!!!
            if(curr == null || index+1 > size){
                throw new NoSuchElementException();
            }
            curr = head;
            count = 1;
            while (count != randomOrder[index] + 1) {
                curr = curr.next;
                count++;
            }
            index++;
            return curr.value;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        //rq.enqueue(3);
        for (int item : rq) {
            System.out.println(item);
        }
        System.out.println(rq.sample());
    }

}
