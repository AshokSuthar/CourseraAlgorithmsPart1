/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ashok
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private boolean[][] siteStatus; // Keeps track if a site is open or closed
    private final WeightedQuickUnionUF uf;
    // We have a second WeightedQuickUnionUF object for checking fullness so as to not run into the backwash issue.
    private final WeightedQuickUnionUF full;
    private final int virtualTopIndex;
    private final int virtualBottomIndex;
    private int num = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n; // how many sites each row of grid has. Used to later compute site from row and col index.
        int gridSize = n * n + 2; // all the sites in the matrix + 2 Virtual sites
        uf = new WeightedQuickUnionUF(gridSize);
        full = new WeightedQuickUnionUF(gridSize - 1);
        this.siteStatus = new boolean[n][n]; // create array to keep track of open sites
        virtualTopIndex = 0;
        virtualBottomIndex = size * size + 1;

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > size || row < 1) {
            throw new IllegalArgumentException();
        }
        if (col > size || col < 1) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            // No need to open this again as it's already open
            return;
        }
        num++; // increase open site count
        int index = findIndex(row, col);
        siteStatus[row - 1][col - 1] = true;
        if (row == 1) {
            uf.union(index, virtualTopIndex);
            full.union(findIndex(row, col), virtualTopIndex);
        }
        if (row == size) {
            uf.union(index, virtualBottomIndex);
        }
        connectOpenNeighbor(row, col);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > size || row < 1) {
            throw new IllegalArgumentException();
        }
        if (col > size || col < 1) {
            throw new IllegalArgumentException();
        }
        return siteStatus[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > size || row < 1) {
            throw new IllegalArgumentException();
        }
        if (col > size || col < 1) {
            throw new IllegalArgumentException();
        }
        int index = findIndex(row, col);
        return full.connected(index, virtualTopIndex);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return num;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTopIndex, virtualBottomIndex);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 5000;
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            p.open(row, col);
        }
        System.out.println("% of Open Sites: " + ((double) p.numberOfOpenSites() / (n * n)) * 100);

    }

    // returns index using row and col
    private int findIndex(int row, int col) {
        /* 
        returning +1 cuz first index,(0) is VirtualTop 
        so row 0, col 0 , element will have index 1 
        */
        return size * (row - 1) + col;
    }

    private void connectOpenNeighbor(int row, int col) {
        int index = findIndex(row, col);
        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            int topNeighbourIndex = findIndex(row - 1, col);
            uf.union(index, topNeighbourIndex);
            full.union(index, topNeighbourIndex);
        }
        if (col < size && isOpen(row, col + 1)) {
            int rightNeighbourIndex = findIndex(row, col + 1);
            uf.union(index, rightNeighbourIndex);
            full.union(index, rightNeighbourIndex);
        }
        if (row < size && isOpen(row + 1, col)) {
            int bottomNeighbourIndex = findIndex(row + 1, col);
            uf.union(index, bottomNeighbourIndex);
            full.union(index, bottomNeighbourIndex);
        }
        if (col - 1 >= 1 && isOpen(row, col - 1)) {
            int leftNeighbourIndex = findIndex(row, col - 1);
            uf.union(index, leftNeighbourIndex);
            full.union(index, leftNeighbourIndex);
        }

    }

}
