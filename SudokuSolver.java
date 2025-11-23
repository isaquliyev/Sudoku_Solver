package hu.advjava.mcpsudoku;

import java.util.Arrays;
import java.util.NoSuchElementException;

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
		SOLVED,
		INVALID,
		SOLVEDUNIQUE,
		SOLVEDTWO,
		SOLVEDTHREE,
		SOLVEDMANY;

		//TODO: add parameters, constructor, getters

		public static State stateFromSols(long sols) {
			return null; //TODO
		}
	}

	/** Defensive deep copy to avoid aliasing */
    public static int[][] deepCopy(int[][] src) {
    	return null; //TODO
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
    	return 0; //TODO
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

    private static boolean isSafe(int[][] board, int row, int col, int num) {
    	return false; //TODO
    }
}
