package hu.advjava.mcpsudoku;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

        public long getMinNum() {
            return minNum;
        }

        public long getMaxNum() {
            return maxNum;
        }

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

        public int getValue() {
            return value;
        }

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

    /**
     * Defensive deep copy to avoid aliasing
     */
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

    public static <T> T findContent(Supplier<T> lambda) {
        return Stream.generate(lambda)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow();
    }

    public static int[][] generate(String difficulty) {
        try {
            var diff = Difficulty.stringToDifficulty(difficulty);
            var ssdlx = new SudokuSolverDLX(true);

            return findContent(() -> maybeGenerateBoard(diff, ssdlx));
        } catch (Exception e) {
            return new int[9][9];
        }
    }

    private static int[][] maybeGenerateBoard(Difficulty diff, SudokuSolverDLX ssdlx) {
        int[][] board = new int[9][9];

        if (!ssdlx.solve(board)) return null;

        List<Integer> indices = IntStream.range(0, 81)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(indices);

        int[][] puzzle = deepCopy(board);

        indices.stream()
                .takeWhile(i -> countFilledCells(puzzle) > diff.getMinNum())
                .forEach(i -> {
                    int row = i / 9;
                    int col = i % 9;
                    int savedValue = puzzle[row][col];

                    puzzle[row][col] = 0;

                    if (ssdlx.countSolutions(deepCopy(puzzle), 2L) != 1) puzzle[row][col] = savedValue;
                });

        int filled = countFilledCells(puzzle);
        return (filled >= diff.getMinNum() && filled <= diff.getMaxNum()) ? puzzle : null;
    }

    public static State solveCount(int[][] board) {
        return State.stateFromSols(new SudokuSolverDLX(false).countSolutions.applyAsLong(board, 4L));
    }

    record Remaining(int cellIdx, Iterator<Integer> digits) {
    }

    public static State solve(int[][] board, boolean randomize) {
        var cells = IntStream.range(0, 81)
                .mapToObj(i -> new int[]{i / 9, i % 9})
                .filter(pos -> board[pos[0]][pos[1]] == 0)
                .collect(Collectors.toList());

        if (randomize) Collections.shuffle(cells);

        if (cells.isEmpty()) return State.SOLVED;

        Supplier<Iterator<Integer>> digitSupplier = () -> {
            var digits = IntStream.rangeClosed(1, 9)
                    .boxed()
                    .collect(Collectors.toList());
            if (randomize) Collections.shuffle(digits);
            return digits.iterator();
        };

        var stack = new Stack<Remaining>();
        stack.add(new Remaining(0, digitSupplier.get()));

        return findContent(() -> maybeSolve(board, cells, digitSupplier, stack));
    }

    private static State maybeSolve(int[][] board, List<int[]> cells, Supplier<Iterator<Integer>> digitSupplier, Stack<Remaining> stack) {
        if (stack.isEmpty()) return State.INVALID;

        Remaining current = stack.peek();
        int cellIdx = current.cellIdx();
        Iterator<Integer> digits = current.digits();
    	
        if (!digits.hasNext()) {
            stack.pop();
            if (cellIdx < cells.size()) {
                int[] cell = cells.get(cellIdx);
                board[cell[0]][cell[1]] = 0;
            }
            return null;
        }

        int digit = digits.next();

        int[] cell = cells.get(cellIdx);
        int row = cell[0];
        int col = cell[1];

        if (!isSafe(board, row, col, digit)) return null;

        board[row][col] = digit;

        if (cellIdx == cells.size() - 1) return State.SOLVED;


        stack.push(new Remaining(cellIdx + 1, digitSupplier.get()));
        return null;
    }

    /**
     * Checks board is safe or not. That means given num can be placed in [row][col]
     * That's based on Sudoku rules.
     * If given num is in given column, row or in 3x3 box then board is safe.
     *
     * @param board 2D array with int values that represent board and its values.
     * @param row   Given row
     * @param col   Given column
     * @param num   The number which will be checked that can be placed in [row][col]
     * @return True if it is possible to place num in board[row][col], otherwise False.
     */
    private static boolean isSafe(int[][] board, int row, int col, int num) {
        return IntStream.range(0, 9).noneMatch(c -> c != col && board[row][c] == num)
                && IntStream.range(0, 9).noneMatch(r -> r != row && board[r][col] == num)
                && IntStream.range(0, 9).noneMatch(i -> {
            int r = row - row % 3 + i / 3;
            int c = col - col % 3 + i % 3;
            return !(r == row && c == col) && board[r][c] == num;
        });
    }
}
