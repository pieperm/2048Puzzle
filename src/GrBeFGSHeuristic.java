import java.util.Comparator;
import java.util.PriorityQueue;

public class GrBeFGSHeuristic extends Heuristic implements Comparator<PuzzleGrid> {

    /**
     * Compare two PuzzleGrid objects with greedy best-first search heuristic h(n)
     * @see Heuristic
     * @see Comparator
     * @param grid1 The first PuzzleGrid
     * @param grid2 The second PuzzleGrid
     * @return positive number if h-value of grid2 > grid1, negative number if h-value of grid2 < grid1, or 0 if they are equal
     */
    @Override
    public int compare(PuzzleGrid grid1, PuzzleGrid grid2) {
        double value1 = h(grid1);
        double value2 = h(grid2);
        return Double.compare(value2, value1);
    }

    /**
     * Heuristic function for greedy best-first search, based on the grid score alone
     * @param puzzleGrid the PuzzleGrid to find the heuristic value of
     * @return the grid's heuristic value, where a higher h-value is a better board
     */
    @Override
    protected double h(PuzzleGrid puzzleGrid) {
        return computeGridScore(puzzleGrid);
    }
}
