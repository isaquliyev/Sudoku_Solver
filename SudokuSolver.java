package hu.advjava.mcpsudoku;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class SudokuSolver {
	public static enum Difficulty {
		NONE(39, 81),
		EASY(36, 38),
		MEDIUM(33, 35),
		HARD(29, 32),
		EXPERT(26, 28),
		MASTER(23, 25),
		EXTREME(17, 22),
		IMPOSSIBLE(0, 16);

		private final long minNum;
		private final long maxNum;

		private Difficulty(long minNum, long maxNum) {
			this.minNum = minNum;
			this.maxNum = maxNum;
		}

		public long getMinNum() { return minNum; }

		public long getMaxNum() { return maxNum; }

		public static Difficulty stringToDifficulty(String difficulty) {
			if (difficulty == null) 
				throw new NoSuchElementException("Difficulty string is null");
			return Arrays.stream(values())
					.filter(d -> d.name().equalsIgnoreCase(difficulty))
					.findFirst()
					.orElseThrow(() -> new NoSuchElementException("No difficulty found for: " + difficulty));
		}

		public static Difficulty numToDifficulty(long num) {
    return Arrays.stream(values())
            .filter(d -> d.minNum <= num && num <= d.maxNum)
            .findFirst()
            .orElse(NONE);
		}
	}

	public static enum State {
		SOLVED(-1),
		INVALID(0),
		SOLVEDUNIQUE(1),
		SOLVEDTWO(2),
		SOLVEDTHREE(3),
		SOLVEDMANY(Integer.MAX_VALUE);

		private final int value;

		private State(int value) {
			this.value = value;
		}

		public int getValue() { return value; }

		public static State stateFromSols(long sols) {
			if (sols < 0) return SOLVED;
			return switch (sols) {
				case 0 -> INVALID;
				case 1 -> SOLVEDUNIQUE;
				case 2 -> SOLVEDTWO;
				case 3 -> SOLVEDTHREE;
				default -> SOLVEDMANY;
			};
		}
	}

	/** Defensive deep copy to avoid aliasing */
    public static int[][] deepCopy(int[][] src) {
    	if (src == null) return null;
    	return Arrays.stream(src)
    			.map(row -> Arrays.copyOf(row, row.length))
    			.toArray(int[][]::new);
    }

    /**
     * Load a Sudoku board from a file (.txt = text, .sud = binary)
     */
    public static int[][] load(File file) throws IOException, ClassNotFoundException {
    	return null; //TODO
    }

    /**
     * Save a Sudoku board to a file (.txt = text, .sud = binary)
     */
    public static void save(File file, int[][] board) throws IOException {
    	//TODO
    }

    public static long countFilledCells(int[][] board) {
    	if (board == null) return 0;
    	return Arrays.stream(board)
    			.flatMapToInt(Arrays::stream)
    			.filter(cell -> cell != 0)
    			.count();
    }

    public static ????? findContent(?????? lambda) {
    	// TODO
    }

    public static int[][] generate(String difficulty) {
    	try {
    		var diff = Difficulty.stringToDifficulty(difficulty);
	    	var ssdlx = new SudokuSolverDLX(true);

	    	return findContent(() ->  maybeGenerateBoard(diff, ssdlx));
    	} catch (Exception e) { return new int[9][9]; }
    }

    private static /* maybe a board, maybe nothing */ maybeGenerateBoard(Difficulty diff, SudokuSolverDLX ssdlx) {
    	// TODO
    }

	public static State solveCount(int[][] board) {
    	return State.stateFromSols(new SudokuSolverDLX(false).countSolutions.applyAsLong(board, 4L));
    }

    record Remaining(int cellIdx, Iterator<Integer> digits) {}

	public static State solve(int[][] board, boolean randomize) {
        var cells  = // TODO [0,0], [0,1], ..., [8,8] as a modifiable list
        		     // in random order if `randomize` is on
		var digits = // 1,...,9 as a list; in random order if `randomize` is on

        // Trivial case: no blanks → already solved
        if (cells.isEmpty()) return State.SOLVED;

        var stack = new Stack<Remaining>();
        stack.add(new Remaining(0, nums.iterator()));

		return findContent(() -> maybeSolve(board, cells, digits, stack));
	}

    private static /* maybe a State, maybe nothing */ maybeSolve(int[][] board, List<int[]> cells, List<Integer> nums, Stack<Integer> stack) {
    	// TODO
    }

    /**
     * Checks board is safe or not. That means given num can be placed in [row][col]
     * That's based on Sudoku rules.
     * If given num is in given column, row or in 3x3 box then board is safe.
     * @param board 2D array with int values that represent board and its values.
     * @param row Given row
     * @param col Given column
     * @param num The number which will be checked that can be placed in [row][col]
     * @return True if it is possible to place num in board[row][col], otherwise False.
     */
    private static boolean isSafe(int[][] board, int row, int col, int num) {
    	boolean inRow = IntStream.range(0, 9).filter(c -> c != col).anyMatch(c -> board[row][c] == num);
    	
    	boolean inCol = IntStream.range(0, 9).filter(r -> r != row).anyMatch(r -> board[r][col] == num);
    	
    	int startRow = row - row % 3;
    	int startColumn = col - col % 3;
    	boolean inBox = IntStream.range(startRow, startRow + 3)
    			.flatMap(r -> IntStream.range(startColumn, startColumn + 3)
    					.filter(c -> !(r == row && c == col))
    					.map(c -> board[r][c]))
    			.anyMatch(cell -> cell == num);
    	
    	return !inRow && !inCol && !inBox;
    }
}
