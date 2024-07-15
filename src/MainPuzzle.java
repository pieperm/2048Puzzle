import java.util.*;

/**
 * Takes data from input file and sends to PuzzleSolver to obtain solution
 */
public class MainPuzzle {

    /**
     * Runs the main puzzle program using PuzzleSolver and PuzzleGrid
     * @param args generic arguments to pass to main
     * @see PuzzleSolver
     * @see PuzzleGrid
     */
    public static void main(String[] args) {

        long startTime = System.nanoTime();

        // read input from file
        InputScanner input = new InputScanner();
        input.read();
        int[][] initialGrid = input.getInitialGrid();
        int goalTile = input.getGoalTile();
        int[] spawnPool = input.getSpawnPool();

        PuzzleSolver puzzleSolver;
        HeuristicType heuristicType;

        // determine type of heuristic based on input from bash script
        if(args[0].equals("") || args[0].equals("0")) {
            heuristicType = HeuristicType.NONADMISSIBLE;
        } else if(args[0].equals("1")) {
            heuristicType = HeuristicType.ADMISSIBLE;
        } else {
            throw new Error("Expected second argument to run.sh to be 0, 1, or nothing");
        }

        puzzleSolver = new PuzzleSolverAStar(initialGrid, goalTile, spawnPool, heuristicType);
        PuzzleGrid solution = puzzleSolver.solve();

        long endTime = System.nanoTime();

        long nsElapsed = endTime - startTime;
        long msElapsed = nsElapsed / 1000;

        // print results to stdout
        System.out.println(msElapsed);
        if(solution != null) {
            ArrayList<Character> moves = solution.getMoves();
            System.out.println(moves.size());
            for(Character move : moves) {
                System.out.print(move);
            }
            System.out.println();
            solution.print();
        }
        // else, game over, no solution was found
    }

}
