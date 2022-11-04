/* *****************************************************************************
 *  Name:              Sarah Modhfar
 *  Coursera User ID:  f35521d656432242756e03e85802eb89
 *  Last modified:     October 28, 2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int experimentsCounter;
    private final double[] equations;

    /**
     * Performs T independent computational experiments on an N-by-N grid.
     */

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("Given N <= 0 || T <= 0");
        }
        experimentsCounter = t;
        equations = new double[experimentsCounter];
        for (int expNum = 0; expNum < experimentsCounter; expNum++) {
            Percolation pr = new Percolation(n);
            int openedSites = 0;
            while (!pr.percolates()) {
                int i = StdRandom.uniformInt(1, n + 1);
                int j = StdRandom.uniformInt(1, n + 1);
                if (!pr.isOpen(i, j)) {
                    pr.open(i, j);
                    openedSites++;
                }
            }
            double fraction = (double) openedSites / (n * n);
            equations[expNum] = fraction;
        }
    }

    /**
     * Sample mean of percolation threshold.
     */

    public double mean() {
        return StdStats.mean(equations);
    }

    /**
     * Sample standard deviation of percolation threshold.
     */

    public double stddev() {
        return StdStats.stddev(equations);
    }

    /**
     * Returns lower bound of the 95% confidence interval.
     */

    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(experimentsCounter));
    }

    /**
     * Returns upper bound of the 95% confidence interval.
     */

    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(experimentsCounter));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
