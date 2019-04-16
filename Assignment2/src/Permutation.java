/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ashok
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> stringsQ = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            stringsQ.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(stringsQ.dequeue());
        }
    }
}
