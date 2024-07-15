import java.util.Comparator;

/**
 * Implementation of a non-optimal heuristic specific to A* search (non-admissible)
 */
public class AStarHeuristic extends Heuristic implements Comparator<PuzzleGrid> {

    /**
     * Compare two PuzzleGrid objects with A* heuristic h(n) + g(n)
     * where h(n) estimates remaining cost and g(n) computes cost of the path so far
     * @see Heuristic
     * @see Comparator
     * @param grid1 The first PuzzleGrid
     * @param grid2 The second PuzzleGrid
     * @return positive number if h-value of grid2 > grid1, negative number if h-value of grid2 < grid1, or 0 if they are equal
     */
    @Override
    public int compare(PuzzleGrid grid1, PuzzleGrid grid2) {
        double value1 = h(grid1) + g(grid1);
        double value2 = h(grid2) + g(grid2);
        return Double.compare(value2, value1);
    }

    /**
     * Computes the cost of the path resulting in the given PuzzleGrid
     * @param grid the PuzzleGrid to evaluate the cost of so far
     * @return the cost of the path using the number of moves (swipes)
     */
    private int g(PuzzleGrid grid) {
        return grid.getMoves().size();
    }

    /**
     * Non-optimal heuristic function for A* search, based on the grid score and path cost
     * @param puzzleGrid the PuzzleGrid to find the heuristic value of
     * @return the grid's heuristic value, where a lower score indicates a board closer to the goal
     */
    @Override
    protected double h(PuzzleGrid puzzleGrid) {
        double gridScore = computeGridScore(puzzleGrid);

        double hvalue = gridScore + g(puzzleGrid);
        return hvalue;
    }
}
