import java.util.*;

/**
 * PuzzleSolver that utilizes iterative deepening to solve the 2048 puzzle
 */
public class PuzzleSolverID extends PuzzleSolver {

    private Stack<PuzzleGrid> frontier;  // stack structure for iterative deepening

    /**
     * Constructor to create a PuzzleSolver for the 2048 puzzle
     * @param initialGrid a 2D matrix
     * @param goalTile the tile that is trying to be obtained in the grid
     * @param spawnPool list of tile numbers that spawn at the corners in order
     */
    public PuzzleSolverID(int[][] initialGrid, int goalTile, int[] spawnPool) {
        super(initialGrid, goalTile, spawnPool);
        frontier = new Stack<>();
        frontier.push(puzzleGrid);  // push the initial puzzle onto the stack
    }

    /**
     * Overloaded constructor for debug/logging
     * @param initialGrid a 2D matrix
     * @param goalTile the tile that is trying to be obtained in the grid
     * @param spawnPool list of tile numbers that spawn at the corners in order
     * @param logging whether to log details about intermediate states
     */
    public PuzzleSolverID(int[][] initialGrid, int goalTile, int[] spawnPool, boolean logging) {
        super(initialGrid, goalTile, spawnPool, logging);
        frontier = new Stack<>();
        frontier.push(puzzleGrid);  // push the initial puzzle onto the stack
    }

    /**
     * Attempts to find a solution to the 2048 puzzle using iterative deepening
     * @return the PuzzleGrid that achieved the goal tile with the minimum number of swipes, or
     * null if there exists no solution that can achieve the goal tile
     */
    @Override
    public PuzzleGrid solve() {

        int currentDepth = 0;
        ArrayList<PuzzleGrid> res = new ArrayList<>();

        do {
            res = boundedDFS(puzzleGrid, currentDepth);
            if(res != null && !res.isEmpty()) {
                return res.get(res.size() - 1);
            }
            currentDepth++;
        } while(res != null);

        return null;  // game over; no solution exists
    }

    /**
     * Performs a bounded depth-first search up to the given depth limit
     * @param start the initial state from which to generate states
     * @param depthLimit the limit at which the goal condition is checked
     * @return the path of the solution, or an empty array if no solution was
     * found at the given depth, or null if the depth was never reached
     */
    public ArrayList<PuzzleGrid> boundedDFS(PuzzleGrid start, int depthLimit) {

        boolean depthReached = false;
        frontier = new Stack<>();
        frontier.push(start);  // push the initial puzzle onto the stack

        while(!frontier.empty()) {
            ArrayList<PuzzleGrid> path = new ArrayList<>();
            path.add(frontier.peek());
            frontier.pop();

            // get the most recent grid added to the path
            PuzzleGrid latestGrid = path.get(path.size() - 1);
            ArrayList<PuzzleGrid> neighbors = latestGrid.getNeighbors();

            if(latestGrid.getMoves().size() == depthLimit) {  // if depth limit is reached
                if(latestGrid.checkGoal()) {
                    return path;  // found a path that obtains the goal tile
                } else if(!neighbors.isEmpty()) {
                    depthReached = true;  // flag that the depth limit has been reached
                }
            } else {
                for(PuzzleGrid neighbor : neighbors) {
                    frontier.push(neighbor);  // push the valid neighbors of the path onto the stack
                }
            }
        }
        if(depthReached) {
            return new ArrayList<>();  // no solution found at depth
        } else {
            return null;  // no solution found, depth not reached
        }
    }
}
