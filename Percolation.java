/* *****************************************************************************
 *  Name:              Sarah Modhfar
 *  Coursera User ID:  f35521d656432242756e03e85802eb89
 *  Last modified:     October 28, 2022
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private static final int TOP = 0;
    private final WeightedQuickUnionUF grid;
    private final boolean[][] full;
    private final int size;
    private final int bottom;
    private int openGrids;


    public Percolation(int N) {

        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("n has to be more then zero!!!? ");
        }
        openGrids = 0;
        size = N;
        grid = new WeightedQuickUnionUF(size * size + 2);
        full = new boolean[size][size];
        bottom = size * size + 1;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkRowsColloms(row, col);
        full[row - 1][col - 1] = true;
        ++openGrids;
        if (row == 1) {
            grid.union(findIndex(row, col), TOP);

        }
        if (row == size) {
            grid.union(findIndex(row, col), bottom);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            grid.union(findIndex(row, col), findIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            grid.union(findIndex(row, col), findIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            grid.union(findIndex(row, col), findIndex(row, col - 1));
        }

        if (col < size && isOpen(row, col + 1)) {
            grid.union(findIndex(row, col), findIndex(row, col + 1));
        }
    }


    public boolean isOpen(int row, int col) {
        checkRowsColloms(row, col);
        return full[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row > 0 && row <= size) && (col > 0 && col <= size)) {
            return grid.find(TOP) == grid.find(size * (row - 1) + col);
        }
        else throw new IllegalArgumentException("Error Found in rows or colloms");
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openGrids;
    }

    private int findIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    private void checkRowsColloms(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Error Found in rows or colloms");

        }

    }

    // does the system percolate?
    public boolean percolates() {
        return grid.find(TOP) == grid.find(bottom);
    }

    // test client (optional)

    public static void main(String[] args) {

    }
}
