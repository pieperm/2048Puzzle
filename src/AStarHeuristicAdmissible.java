import java.util.Comparator;

/**
 * Implementation of an admissible heuristic specific to A* search
 */
public class AStarHeuristicAdmissible extends Heuristic implements Comparator<PuzzleGrid> {

    /**
     * Compare two PuzzleGrid objects with A* heuristic h(n) + g(n)
     * where h(n) estimates remaining cost and g(n) computes cost of the path so far
     * @see Heuristic
     * @see Comparator
     * @param grid1 The first PuzzleGrid
     * @param grid2 The second PuzzleGrid
     * @return positive number if h-value of grid1 > grid2, negative number if h-value of grid1 < grid2, or 0 if they are equal
     */
    @Override
    public int compare(PuzzleGrid grid1, PuzzleGrid grid2) {
        double value1 = h(grid1) + g(grid1);
        double value2 = h(grid2) + g(grid2);
        return Double.compare(value1, value2);
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
     * Heuristic function for A* search, based on the grid score and path cost
     * @param puzzleGrid the PuzzleGrid to find the heuristic value of
     * @return the grid's heuristic value, where a lower score indicates a board closer to the goal
     */
    @Override
    protected double h(PuzzleGrid puzzleGrid) {

        int[][] grid = puzzleGrid.getGrid();

        double hvalue = puzzleGrid.getGoalTile();

        int maxTile = 0;
        int row = 0;
        int col = 0;
        for(int r = 0; r < grid.length; r++) {
            for(int c = 0; c < grid[r].length; c++) {
                int tile = grid[r][c];  // check if tile is a power of 2, otherwise it can't contribute to solution
                boolean isPowerOf2 = tile > 0 && (int)Math.ceil(log2(tile)) == (int)Math.floor(log2(tile));
                if(tile > maxTile && isPowerOf2) {
                    maxTile = tile;  // update the max tile
                    row = r;  // keep track of where the max tile was found
                    col = c;
                }
            }
        }

        if(maxTile != 0) {  // prevent division by 0
            hvalue = maxTile / hvalue;  // divide the goal by the maximum tile found to estimate number of remaining swipes
            for (int r = 0; r < grid.length; r++) {  // check to see if there is another one of these tiles
                for (int c = 0; c < grid[r].length; c++) {
                    int tile = grid[r][c];
                    boolean sameTile = r == row && c == col;  // ensure the tile is not the same as found above
                    if (tile == maxTile && !sameTile) {
                        hvalue = hvalue / 2;  // should take half as many moves since there are two of these tiles already
                    }
                }
            }
        }

        return hvalue;
    }

    /**
     * Use change of base to compute log base 2 of a number
     * @param num the number to compute log base 2 of
     * @return log base 2 of num
     */
    private double log2(double num) {
        return Math.log(num) / Math.log(2);
    }
}
