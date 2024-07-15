import java.util.*;

/**
 * PuzzleSolver that utilizes breadth-first search to solve the 2048 puzzle
 */
public class PuzzleSolverBFS extends PuzzleSolver {

    private Queue<PuzzleGrid> queue;  // queue structure for BFS

    /**
     * Constructor to create a PuzzleSolver for the 2048 puzzle
     * @param initialGrid a 2D matrix
     * @param goalTile the tile that is trying to be obtained in the grid
     * @param spawnPool list of tile numbers that spawn at the corners in order
     */
    public PuzzleSolverBFS(int[][] initialGrid, int goalTile, int[] spawnPool) {
        super(initialGrid, goalTile, spawnPool);
        queue = new LinkedList<>();
        queue.add(puzzleGrid);  // push the initial puzzle into the queue
    }

    /**
     * Constructor to create a PuzzleSolver for the 2048 puzzle
     * @param initialGrid a 2D matrix
     * @param goalTile the tile that is trying to be obtained in the grid
     * @param spawnPool list of tile numbers that spawn at the corners in order
     * @param logging whether to log intermediate states while solving the puzzle
     */
    public PuzzleSolverBFS(int[][] initialGrid, int goalTile, int[] spawnPool, boolean logging) {
        super(initialGrid, goalTile, spawnPool, logging);
        queue = new LinkedList<>();
        queue.add(puzzleGrid);  // push the initial puzzle into the queue
    }

    /**
     * Attempts to find a solution to the 2048 puzzle at the beginning of the queue using breadth-first search
     * Appends the PuzzleGrids that result from swiping up, down, left, and right (if they are valid moves)
     * Pops off the front PuzzleGrid once all its moves have been processed
     * @return the PuzzleGrid that achieved the goal tile with the minimum number of swipes, or
     * null if there exists no solution that can achieve the goal tile
     */
    @Override
    public PuzzleGrid solve() {
        while(!queue.isEmpty()) {  // continually process the first PuzzleGrid

            if(queue.element().getMoves().size() > 7) {
                break;
            }

            // if the goal tile is reached, return the solved grid
            boolean goalTileAchieved = queue.element().checkGoal();
            if(goalTileAchieved) {
                return queue.element();
            }

            // test the moves for up, down, left, and right, and append to BFS queue
            PuzzleGrid moveUp = new PuzzleGrid(queue.element());
            boolean upValid = moveUp.move('U');
            if(upValid) {
                queue.add(moveUp);
            }

            PuzzleGrid moveDown = new PuzzleGrid(queue.element());
            boolean downValid = moveDown.move('D');
            if(downValid) {
                queue.add(moveDown);
            }

            PuzzleGrid moveLeft = new PuzzleGrid(queue.element());
            boolean leftValid = moveLeft.move('L');
            if(leftValid) {
                queue.add(moveLeft);
            }

            PuzzleGrid moveRight = new PuzzleGrid(queue.element());
            boolean rightValid = moveRight.move('R');
            if(rightValid) {
                queue.add(moveRight);
            }

            queue.remove();  // pop the first element of the BFS queue
        }

        return null;  // game over; no solution exists
    }
}
