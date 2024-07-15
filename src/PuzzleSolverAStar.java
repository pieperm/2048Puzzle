import java.util.*;

/**
 * PuzzleSolver that utilizes A* search to solve the 2048 puzzle
 */
public class PuzzleSolverAStar extends PuzzleSolver {

    private PriorityQueue<PuzzleGrid> frontier;  // priority queue structure to represent frontier
    private HashMap<PuzzleGrid, Boolean> visitedStates;  // map structure to track visited states in O(1) time'

    /**
     * Constructor to create a PuzzleSolver for the 2048 puzzle using A*
     * @param initialGrid a 2D matrix
     * @param goalTile the tile that is trying to be obtained in the grid
     * @param spawnPool list of tile numbers that spawn at the corners in order
     * @param heuristicType a HeuristicType (ADMISSIBLE or NONADMISSIBLE) that defines which heuristic to apply
     */
    public PuzzleSolverAStar(int[][] initialGrid, int goalTile, int[] spawnPool, HeuristicType heuristicType) {
        super(initialGrid, goalTile, spawnPool);
        if(heuristicType == HeuristicType.ADMISSIBLE) {
            frontier = new PriorityQueue<>(10, new AStarHeuristicAdmissible());
        } else {
            frontier = new PriorityQueue<>(10, new AStarHeuristic());
        }

        frontier.add(puzzleGrid);  // add the initial PuzzleGrid to the priority queue
        visitedStates = new HashMap<>();
        visitedStates.put(puzzleGrid, true);
    }

    /**
     * Overloaded constructor for debug/logging
     * @param initialGrid a 2D matrix
     * @param goalTile the tile that is trying to be obtained in the grid
     * @param spawnPool list of tile numbers that spawn at the corners in order
     * @param heuristicType a HeuristicType (ADMISSIBLE or NONADMISSIBLE) that defines which heuristic to apply
     * @param logging whether to log details about intermediate states
     */
    public PuzzleSolverAStar(int[][] initialGrid, int goalTile, int[] spawnPool, HeuristicType heuristicType, boolean logging) {
        super(initialGrid, goalTile, spawnPool, logging);
        if(heuristicType == HeuristicType.ADMISSIBLE) {
            frontier = new PriorityQueue<>(10, new AStarHeuristicAdmissible());
        } else {
            frontier = new PriorityQueue<>(10, new AStarHeuristic());
        }
        frontier.add(puzzleGrid);  // add the initial PuzzleGrid to the priority queue
        visitedStates = new HashMap<>();
        visitedStates.put(puzzleGrid, true);
    }

    /**
     * Attempts to find a solution to the 2048 puzzle using A* search
     * @return the PuzzleGrid that achieved the goal tile with the minimum number of swipes, or
     * null if there exists no solution that can achieve the goal tile
     */
    @Override
    public PuzzleGrid solve() {
        int statesGenerated = 0;

        while(!frontier.isEmpty()) {
            PuzzleGrid bestGrid = frontier.poll();  // access and pop the top PuzzleGrid from the frontier

            if(bestGrid.checkGoal()) {
                if(logging) {
                    System.out.println(statesGenerated + " states checked");
                }
                return bestGrid;
            } else {
                ArrayList<PuzzleGrid> neighbors = bestGrid.getNeighbors();
                for(PuzzleGrid neighbor : neighbors) {
                    if(!stateVisited(neighbor)) {  // prevent loops by avoiding board states already generated
                        frontier.add(neighbor);  // add the neighbor to the frontier
                        statesGenerated++;
                        visitedStates.put(neighbor, true);  // mark that this state has been visited
                    } // else, this neighbor is a duplicate board state
                }
            }
        }
        return null;  // game over; no solution exists
    }

    /**
     * Checks whether a board state has been visited using the visitedStates HashMap
     * @param puzzleGrid The PuzzleGrid to check; existence in the map is determined by the grid alone,
     *                   so an identical grid means a repeated state
     * @return whether this grid state has been previously generated/visited
     */
    private boolean stateVisited(PuzzleGrid puzzleGrid) {
        return visitedStates.get(puzzleGrid) != null;
    }
}

enum HeuristicType {
    ADMISSIBLE, NONADMISSIBLE
}
