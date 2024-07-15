import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Helper class to read puzzle input from a file and extract initial grid, goal tile, and spawn pool
 */
public class InputScanner {

    private int[][] initialGrid;
    private int goalTile;
    private int[] spawnPool;

    /**
     * Read from file using Scanner to assign initialGrid, goalTile, and spawnPool
     * @see Scanner
     */
    public void read() {

        Scanner input = new Scanner(System.in);

        goalTile = input.nextInt();
        int width = input.nextInt();
        int height = input.nextInt();
        input.nextLine();
        String spawnPoolStr = input.nextLine();

        initialGrid = new int[height][width];

        // convert string of spawn pool to int[]
        String[] poolChars = spawnPoolStr.split(" ");
        spawnPool = Stream.of(poolChars).mapToInt(Integer::parseInt).toArray();

        // read in initial grid state
        for(int row = 0; row < height; row++) {
            String tileRowStr = input.nextLine();
            String[] tileChars = tileRowStr.split(" ");
            int[] tileRow = Stream.of(tileChars).mapToInt(Integer::parseInt).toArray();
            initialGrid[row] = tileRow;
        }

        input.close();
    }

    /**
     * Getter for initialGrid
     * @return the initial state of the grid as a 2D matrix
     */
    public int[][] getInitialGrid() {
        return initialGrid;
    }

    /**
     * Getter for goalTile
     * @return the number of the goal tile
     */
    public int getGoalTile() {
        return goalTile;
    }

    /**
     * Getter for spawnPool
     * @return a list of integers representing the order to spawn tiles
     */
    public int[] getSpawnPool() {
        return spawnPool;
    }
}
