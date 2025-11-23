package hu.advjava.mcpsudoku;

public class SudokuSolver {
	public static enum Difficulty {
		NONE is between 39-81
		EASY is between 36-38
		MEDIUM is between 33-35
		HARD is between 29-32
		EXPERT is between 26-28
		MASTER is between 23-25
		EXTREME is between 17-22
		IMPOSSIBLE is between  0-16

		//TODO: add parameters, constructor, getters

		public static Difficulty stringToDifficulty(String difficulty) {
			return null; //TODO
		}

		public static Difficulty numToDifficulty(long num) {
			return null; //TODO
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
