import java.util.*;

/**
 * Representation of the 2048 puzzle
 */
public class PuzzleGrid {

    private int[][] grid;
    private int numRows;
    private int numCols;

    private Integer goalTile;
    private ArrayList<Integer> spawnPool;
    private ArrayList<String> corners;
    private ArrayList<Character> moves;
    private boolean logging = false;

    /**
     * Constructor to create a PuzzleGrid for the 2048 puzzle
     * @param initialGrid a 2D matrix representing the grid
     * @param goalTile the tile that is trying to be obtained in the grid
     * @param spawnPool list of tile numbers that spawn at the corners in order
     */
    public PuzzleGrid(int[][] initialGrid, Integer goalTile, int[] spawnPool) {
        this.grid = initialGrid;
        this.numRows = initialGrid.length;
        this.numCols = initialGrid[0].length;
        this.goalTile = goalTile;

        // convert from int[] to ArrayList
        this.spawnPool = new ArrayList<>(spawnPool.length);
        for(int spawn : spawnPool) {
            this.spawnPool.add(spawn);
        }

        // track the current corner using list: top left, top right, bottom right, bottom left
        this.corners = new ArrayList<>(List.of("TL", "TR", "BR", "BL"));
        this.moves = new ArrayList<>();
    }

    /**
     * Debug version of constructor to log moves
     * @param initialGrid a 2D matrix representing the grid
     * @param goalTile the tile that is trying to be obtained in the grid
     * @param spawnPool list of tile numbers that spawn at the corners in order
     * @param logging whether to print intermediate grids and moves
     */
    public PuzzleGrid(int[][] initialGrid, Integer goalTile, int[] spawnPool, boolean logging) {
        this(initialGrid, goalTile, spawnPool);
        this.logging = logging;
    }

    /**
     * Copy constructor for a PuzzleGrid
     * @param puzzleGrid the PuzzleGrid to copy to another memory location
     */
    public PuzzleGrid(PuzzleGrid puzzleGrid) {
        this.numRows = puzzleGrid.numRows;
        this.numCols = puzzleGrid.numCols;

        // copy to new memory locations
        this.grid = new int[numRows][numCols];
        int[][] srcGrid = puzzleGrid.getGrid();
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                this.grid[row][col] = srcGrid[row][col];
            }
        }

        this.goalTile = puzzleGrid.goalTile;

        // make clones to avoid using the same reference
        this.spawnPool = (ArrayList<Integer>) puzzleGrid.spawnPool.clone();
        this.corners = (ArrayList<String>) puzzleGrid.corners.clone();
        this.moves = (ArrayList<Character>) puzzleGrid.moves.clone();
        this.logging = puzzleGrid.logging;
    }

    /**
     * Completes a "swipe" in a given direction, moving and merging tiles accordingly
     * @param direction up (U), down (D), left (L), or right (R)
     * @return whether the attempted move actually changed the board
     */
    public boolean move(char direction) {

        // make a copy of the initial grid
        int[][] beforeMove = new int[numRows][numCols];
        for(int i = 0; i < this.grid.length; i++) {
            for(int j = 0; j < this.grid[i].length; j++) {
                beforeMove[i][j] = this.grid[i][j];
            }
        }

        switch(direction) {
            case 'U':
                if(numRows > 1) {
                    moveUp();
                    mergeUp();
                    moveUp();
                }
                break;
            case 'D':
                if(numRows > 1) {
                    moveDown();
                    mergeDown();
                    moveDown();
                }
                break;
            case 'L':
                if(numCols > 1) {
                    moveLeft();
                    mergeLeft();
                    moveLeft();
                }
                break;
            case 'R':
                if(numCols > 1) {
                    moveRight();
                    mergeRight();
                    moveRight();
                }
                break;
            default:
                throw new Error("Received invalid move direction");
        }

        // if the two arrays are different, then add a new tile
        if(!Arrays.deepEquals(beforeMove, this.grid)) {
            moves.add(direction);
            addNewTile();

            if(logging) {
                System.out.println(moves);
                print();
                System.out.println();
            }

            return true;
        } else {  // otherwise, the move is invalid; the grid did not change

            if(logging) {
                System.out.println("Grid unchanged by move");
                print();
                System.out.println();
            }

            return false;
        }
    }

    /**
     * Helper function for moving tiles up into empty spaces
     */
    private void moveUp() {
        for(int col = 0; col < numCols; col++) {  // shift tiles up in each column independently
            int row = 1;
            while(row < numRows) {  // work top to bottom
                while(row > 0 && !isEmpty(row, col) && isEmpty(row - 1, col)) {  // continually shift into empty spaces above
                    this.grid[row - 1][col] = this.grid[row][col];
                    this.grid[row][col] = 0;
                    row--;
                }
                row++;
            }
        }
    }

    /**
     * Helper function for merging tiles when moving up
     */
    private void mergeUp() {
        for(int row = 1; row < numRows; row++) {  // work top to bottom
            for(int col = 0; col < numCols; col++) {
                if(this.grid[row - 1][col] == this.grid[row][col]) {
                    // if the tile above is equal, merge them into the upper tile
                    this.grid[row - 1][col] = 2 * this.grid[row][col];
                    this.grid[row][col] = 0;
                }
            }
        }
    }

    /**
     * Helper function for moving tiles down into empty spaces
     */
    private void moveDown() {
        for(int col = 0; col < numCols; col++) {  // shift tiles down in each column independently
            int row = numRows - 2;
            while(row >= 0) {  // work bottom to top
                while(row < numRows - 1 && !isEmpty(row, col) && isEmpty(row + 1, col)) {  // continually shift into empty spaces below
                    this.grid[row + 1][col] = this.grid[row][col];
                    this.grid[row][col] = 0;
                    row++;
                }
                row--;
            }
        }
    }

    /**
     * Helper function for merging tiles when moving down
     */
    private void mergeDown() {
        for(int row = numRows - 2; row >= 0; row--) {  // work bottom to top
            for(int col = 0; col < numCols; col++) {
                if(this.grid[row + 1][col] == this.grid[row][col]) {
                    // if the tile below is equal, merge them into the lower tile
                    this.grid[row + 1][col] = 2 * this.grid[row][col];
                    this.grid[row][col] = 0;
                }
            }
        }
    }

    /**
     * Helper function for moving tiles left into empty spaces
     */
    private void moveLeft() {
        for(int row = 0; row < numRows; row++) {  // shift tiles left in each row independently
            int col = 1;
            while(col < numCols) {  // work left to right
                while(col > 0 && !isEmpty(row, col) && isEmpty(row, col - 1)) {  // continually shift into empty spaces to the left
                    this.grid[row][col - 1] = this.grid[row][col];
                    this.grid[row][col] = 0;
                    col--;
                }
                col++;
            }
        }
    }

    /**
     * Helper function for merging tiles when moving left
     */
    private void mergeLeft() {
        for(int col = 1; col < numCols; col++) {  // work left to right
            for(int row = 0; row < numRows; row++) {
                if(this.grid[row][col - 1] == this.grid[row][col]) {
                    // if the tile to the left is equal, merge them into the leftmost tile
                    this.grid[row][col - 1] = 2 * this.grid[row][col];
                    this.grid[row][col] = 0;
                }
            }
        }
    }

    /**
     * Helper function for moving tiles right into empty spaces
     */
    private void moveRight() {
        for(int row = 0; row < numRows; row++) {  // shift tiles right in each row independently
            int col = numCols - 2;
            while(col >= 0) {  // work right to left
                while(col < numCols - 1 && !isEmpty(row, col) && isEmpty(row, col + 1)) {  // continually shift into empty spaces to the right
                    this.grid[row][col + 1] = this.grid[row][col];
                    this.grid[row][col] = 0;
                    col++;
                }
                col--;
            }
        }
    }

    /**
     * Helper function for merging tiles when moving right
     */
    private void mergeRight() {
        for(int col = numCols - 2; col >= 0; col--) {  // work right to left
            for(int row = 0; row < numRows; row++) {
                if(this.grid[row][col + 1] == this.grid[row][col]) {
                    // if the tile to the right is equal, merge them into the rightmost tile
                    this.grid[row][col + 1] = 2* this.grid[row][col];
                    this.grid[row][col] = 0;
                }
            }
        }
    }

    /**
     * Attempts to spawn the first tile in the spawn pool in one of the four corners
     * Begins with the last unused corner (initially top left) and works clockwise
     * Rotates the tiles in the spawn pool if the first tile is spawned
     * Updates the grid with 0 or 1 new entries per call (0 if all corners are filled)
     */
    private void addNewTile() {

        // rotates the spawn pool and current corner to the left to move off of previous corner and pool tile
        int spawnTile = spawnPool.get(0);
        this.corners = new ArrayList<>(List.of("TL", "TR", "BR", "BL"));
        int spawnAttempts = 0;

        // continue attempting to spawn until all 4 corners have been tried
        while(spawnAttempts < 4) {

            // check the current corner and attempt to spawn there
            if (corners.get(0).equals("TL")) {
                spawnAttempts++;
                if (isEmpty(0, 0)) {
                    // insert the current spawn tile
                    this.grid[0][0] = spawnTile;
                    // rotate to the next spawn tile and corner for the next time a tile is added
                    Collections.rotate(spawnPool, -1);
                    Collections.rotate(corners, -1);
                    return;  // stop attempting to spawn tiles
                } else {  // tile is already filled, rotate to the next corner
                    Collections.rotate(corners, -1);
                }
            }

            if (corners.get(0).equals("TR")) {
                spawnAttempts++;
                if (isEmpty(0, numCols - 1)) {
                    this.grid[0][numCols - 1] = spawnTile;
                    Collections.rotate(spawnPool, -1);
                    Collections.rotate(corners, -1);
                    return;
                } else {
                    Collections.rotate(corners, -1);
                }
            }

            if (corners.get(0).equals("BR")) {
                spawnAttempts++;
                if (isEmpty(numRows - 1, numCols - 1)) {
                    this.grid[numRows - 1][numCols - 1] = spawnTile;
                    Collections.rotate(spawnPool, -1);
                    Collections.rotate(corners, -1);
                    return;
                } else {
                    Collections.rotate(corners, -1);
                }
            }

            if (corners.get(0).equals("BL")) {
                spawnAttempts++;
                if (isEmpty(numRows - 1, 0)) {
                    this.grid[numRows - 1][0] = spawnTile;
                    Collections.rotate(spawnPool, -1);
                    Collections.rotate(corners, -1);
                    return;
                } else {
                    Collections.rotate(corners, -1);
                }
            }
            // otherwise, do not spawn a tile and do not rotate the spawn pool
        }
    }

    /**
     * Checks whether the goal tile has been achieved
     * @return true if the goal tile occurs at least once in the grid, false otherwise
     */
    public boolean checkGoal() {
        for(int[] row : this.grid) {
            for(Integer tile : row) {
                if(tile.equals(this.goalTile)) {
                    return true;
                }
            }
        }
        return false;  // goal tile does not exist in grid
    }

    /**
     * Getter for the grid member variable
     * @return the 2048 grid
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * Getter for the moves member variable
     * @return the list of moves performed on the puzzle at that time
     */
    public ArrayList<Character> getMoves() {
        return moves;
    }

    /**
     * Getter for the goal tile member variable
     * @return the number of the goal tile
     */
    public Integer getGoalTile() {
        return goalTile;
    }

    /**
     * Checks whether a given cell in the grid is empty
     * @param row the index of the row to check
     * @param col the index of the column to check
     * @return true if the cell is empty (contains 0) and false otherwise (contains 2, 4, 8, ...)
     */
    private boolean isEmpty(int row, int col) {
        return this.grid[row][col] == 0;
    }

    /**
     * Prints the 2048 grid in the standard format to stdout
     */
    public void print() {
        for(int[] row : this.grid) {
            for(int tile = 0; tile < row.length; tile++) {
                System.out.print(row[tile]);
                if(tile < row.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Finds the neighbors of a given grid that are a result of valid moves
     * @return a list of PuzzleGrids from moving up, down, left, and right (if valid)
     */
    public ArrayList<PuzzleGrid> getNeighbors() {

        ArrayList<PuzzleGrid> neighbors = new ArrayList<>(4);
        char[] directions = {'U', 'D', 'L', 'R'};

        // try each of the four directions
        for(char direction : directions) {
            PuzzleGrid newGrid = new PuzzleGrid(this);  // copy over the puzzle grid
            boolean directionValid = newGrid.move(direction);
            if(directionValid) {  // if it's a valid move, then it's a valid neighbor
                neighbors.add(newGrid);
            }
        }

        return neighbors;
    }

    /**
     * Overrides Object.equals for PuzzleGrids, checking if two are equivalent based on their grids
     * @param obj The object (likely a PuzzleGrid) to compare to the calling object
     * @return whether the two PuzzleGrids have identical grids
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PuzzleGrid)) {  // ensure obj is actually a PuzzleGrid
            return false;
        }

        PuzzleGrid otherGrid = (PuzzleGrid) obj;  // cast Object to PuzzleGrid
        return Arrays.deepEquals(grid, otherGrid.grid);  // determine whether grids are identical
    }

    /**
     * Overrides hashCode for PuzzleGrids, generating a unique hash code based on the grid
     * Used for hashing PuzzleGrids in a HashMap based only on grid state
     * @return a unique hash code that corresponds to a unique grid; that is, every unique grid has its own unique hash code
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }
}
