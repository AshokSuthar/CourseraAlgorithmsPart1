
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ashok
 */
public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] percolationResults; // stores all return threshold values.
    private final int trials; // no. of trials done
    // perform trials independent experiments on an n-by-n grid

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.percolationResults = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }
            this.percolationResults[i] = ((double) p.numberOfOpenSites() / (n * n));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.percolationResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.percolationResults);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double confidenceFraction = (CONFIDENCE_95 * stddev()) / Math.sqrt(this.trials);
        return mean() - confidenceFraction;

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double confidenceFraction = (CONFIDENCE_95 * stddev()) / Math.sqrt(this.trials);
        return mean() + confidenceFraction;
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }

}
