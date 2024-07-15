/**
 * Test suite for using debug mode on the PuzzleSolver
 * Logs intermediate steps in reaching a solution
 */
public class PuzzleTester {

    public static void main(String[] args) {

        InputScanner input = new InputScanner();
        input.read();
        int[][] initialGrid = input.getInitialGrid();
        int goalTile = input.getGoalTile();
        int[] spawnPool = input.getSpawnPool();

        PuzzleSolver puzzleSolver = new PuzzleSolverAStar(initialGrid, goalTile, spawnPool, HeuristicType.NONADMISSIBLE, false);
        PuzzleGrid solution = puzzleSolver.solve();
        System.out.println("\nSolution from A*: Completed in " + solution.getMoves().size() + " moves");
        solution.print();

        System.out.println("---- Steps: ----");

        PuzzleGrid testGrid = new PuzzleGrid(initialGrid, goalTile, spawnPool, false);
        for(Character move : solution.getMoves()) {
            System.out.println("\nMoving " + move);
            testGrid.move(move);
        }
        System.out.println("Final grid:");
        testGrid.print();

    }

}
