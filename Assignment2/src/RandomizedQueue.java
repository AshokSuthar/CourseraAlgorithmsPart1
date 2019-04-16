
import edu.princeton.cs.algs4.StdRandom;
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
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int MINIMUM_STORAGE_SIZE = 2;
    private int size;
    private Item[] itemsArr;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 0;
        itemsArr = (Item[]) new Object[MINIMUM_STORAGE_SIZE];
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
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isFull()) {
            resize(itemsArr.length);
        }
        itemsArr[size++] = item;
    }

    private boolean isFull() {
        return itemsArr.length == size;
    }

    private void resize(int len) {
        Item[] newItemsArr = (Item[]) new Object[len * 2];
        for (int i = 0; i < size; i++) {
            newItemsArr[i] = itemsArr[i];
        }
        itemsArr = newItemsArr;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int indexToRemove = StdRandom.uniform(size);
        Item item = itemsArr[indexToRemove];
        itemsArr[indexToRemove] = itemsArr[size - 1];
        itemsArr[size - 1] = null;
        size--;
        if (isStorageOversized()) {
            resize(itemsArr.length / 2);
        }
        return item;
    }

    private boolean isStorageOversized() {
        return itemsArr.length > MINIMUM_STORAGE_SIZE && size <= itemsArr.length / 4;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int indexToShow = StdRandom.uniform(size);
        return itemsArr[indexToShow];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private final Item[] randomItems;
        private int index;

        public ArrayIterator() {
            randomItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                randomItems[i] = itemsArr[i];
            }
            StdRandom.shuffle(randomItems);
        }

        public boolean hasNext() {
            return index < randomItems.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (index >= size) {
                throw new NoSuchElementException();
            }
            return randomItems[index++];
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(5);
        rq.enqueue(7);
        for (int item : rq) {
            System.out.println(item);
        }
    }

}
