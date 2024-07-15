/**
 * Abstract parent class for defining common heuristics
 */
public abstract class Heuristic {

    /**
     * Abstract method for subclasses to implement custom heuristic based on search method
     * @param puzzleGrid the PuzzleGrid to find the heuristic value of
     * @return A double representing the puzzle's heuristic value
     */
    protected abstract double h(PuzzleGrid puzzleGrid);

    /**
     * Calculates the "score" of a PuzzleGrid, which is the sum of all its tiles plus the number of empty tiles
     * @param puzzleGrid the PuzzleGrid to compute the score of
     * @return the score of the grid as an integer
     */
    protected int computeGridScore(PuzzleGrid puzzleGrid) {
        int[][] grid = puzzleGrid.getGrid();

        int total = 0;  // sum of the board tiles
        int emptyTiles = 0;  // number of empty tiles
        for(int[] row : grid) {
            for (int tile : row) {
                if (tile == 0) {
                    emptyTiles++;
                } else {
                    total += tile;
                }
            }
        }

        int gridScore = total + emptyTiles;
        return gridScore;
    }

}
