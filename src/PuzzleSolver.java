import java.util.*;

/**
 * Abstract class to encapsulate different kinds of solvers (BFS, DFS, etc.)
 */
public abstract class PuzzleSolver {

    protected boolean logging;
    protected PuzzleGrid puzzleGrid;

    public PuzzleSolver(int[][] initialGrid, int goalTile, int[] spawnPool) {
        logging = false;
        this.puzzleGrid = new PuzzleGrid(initialGrid, goalTile, spawnPool);
    }

    public PuzzleSolver(int[][] initialGrid, int goalTile, int[] spawnPool, boolean logging) {
        this.logging = logging;
        this.puzzleGrid = new PuzzleGrid(initialGrid, goalTile, spawnPool, logging);
    }

    /**
     * Attempts to find a solution to the 2048 puzzle using method chosen by subclasses
     * @return the PuzzleGrid that achieved the goal tile with the minimum number of swipes, or
     * null if there exists no solution that can achieve the goal tile
     */
    public abstract PuzzleGrid solve();

}
