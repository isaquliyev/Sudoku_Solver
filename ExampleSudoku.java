package hu.advjava.mcpsudoku;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/** Example puzzles */
public enum ExampleSudoku {
    EASY_1(
        board(
            4,9,6, 0,7,0, 0,0,0,
            0,0,8, 9,0,3, 0,0,0,
            7,5,0, 8,0,2, 0,1,9,

            0,0,0, 0,0,0, 0,2,5,
            0,0,1, 7,2,0, 4,9,0,
            8,0,7, 0,9,0, 3,6,0,

            0,8,0, 0,0,7, 9,0,0,
            0,6,0, 0,5,4, 0,3,0,
            0,0,0, 2,8,9, 1,5,6
        ),
        board(
            4,9,6, 5,7,1, 2,8,3,
            2,1,8, 9,6,3, 5,7,4,
            7,5,3, 8,4,2, 6,1,9,

            6,4,9, 3,1,8, 7,2,5,
            5,3,1, 7,2,6, 4,9,8,
            8,2,7, 4,9,5, 3,6,1,

            1,8,5, 6,3,7, 9,4,2,
            9,6,2, 1,5,4, 8,3,7,
            3,7,4, 2,8,9, 1,5,6
        )
    ),
    MEDIUM_1(
        board(
            3,0,0, 8,5,0, 0,0,0,
            5,1,4, 7,2,0, 0,6,9,
            0,6,8, 9,1,0, 5,2,3,

            0,0,0, 0,0,5, 0,0,7,
            0,7,6, 0,9,0, 0,0,0,
            9,4,0, 0,0,0, 6,3,0,

            0,0,1, 0,0,9, 7,5,0,
            4,0,0, 0,6,0, 0,0,0,
            0,0,0, 0,3,1, 0,8,6
        ),
        board(
            3,9,2, 8,5,6, 4,7,1,
            5,1,4, 7,2,3, 8,6,9,
            7,6,8, 9,1,4, 5,2,3,

            8,2,3, 6,4,5, 1,9,7,
            1,7,6, 3,9,8, 2,4,5,
            9,4,5, 1,7,2, 6,3,8,

            6,3,1, 2,8,9, 7,5,4,
            4,8,9, 5,6,7, 3,1,2,
            2,5,7, 4,3,1, 9,8,6
        )
    ),
    HARD_1(
        board(
            3,0,0, 0,2,0, 0,0,0,
            7,0,0, 0,0,0, 0,0,3,
            8,0,0, 4,0,0, 0,6,0,

            0,8,7, 0,4,2, 1,0,0,
            0,0,0, 0,6,8, 2,7,0,
            2,5,0, 0,0,7, 8,0,0,

            1,6,0, 0,0,0, 9,8,0,
            0,0,0, 0,8,5, 0,0,0,
            0,3,0, 2,1,0, 6,0,0
        ),
        board(
            3,4,5, 9,2,6, 7,1,8,
            7,2,6, 8,5,1, 4,9,3,
            8,9,1, 4,7,3, 5,6,2,

            6,8,7, 3,4,2, 1,5,9,
            9,1,3, 5,6,8, 2,7,4,
            2,5,4, 1,9,7, 8,3,6,

            1,6,2, 7,3,4, 9,8,5,
            4,7,9, 6,8,5, 3,2,1,
            5,3,8, 2,1,9, 6,4,7
        )
    ),
    EXPERT_1(
        board(
            1,7,2, 5,8,4, 0,0,0,
            0,0,5, 0,0,0, 8,0,4,
            0,0,4, 0,0,0, 0,0,0,

            0,2,1, 0,6,3, 0,0,0,
            3,5,0, 0,0,0, 0,2,0,
            0,0,0, 2,0,1, 0,3,7,

            0,0,0, 0,0,6, 0,4,0,
            2,0,3, 9,4,0, 0,8,0,
            0,4,0, 0,7,2, 0,0,5
        ),
        board(
            1,7,2, 5,8,4, 3,6,9,
            9,3,5, 6,2,7, 8,1,4,
            8,6,4, 1,3,9, 5,7,2,

            7,2,1, 4,6,3, 9,5,8,
            3,5,6, 7,9,8, 4,2,1,
            4,8,9, 2,5,1, 6,3,7,

            5,9,7, 8,1,6, 2,4,3,
            2,1,3, 9,4,5, 7,8,6,
            6,4,8, 3,7,2, 1,9,5
        )
    ),
    MASTER_1(
        board(
            0,6,0, 0,1,0, 5,0,0,
            1,0,0, 0,0,5, 0,7,0,
            0,9,8, 0,0,4, 0,0,0,

            8,0,0, 0,0,0, 0,0,0,
            0,4,0, 0,0,0, 0,0,6,
            0,0,0, 0,0,3, 0,9,1,

            9,0,0, 3,0,2, 0,0,0,
            0,0,7, 5,0,8, 0,0,3,
            0,0,0, 1,0,0, 2,0,4
        ),
        board(
            7,6,4, 2,1,9, 5,3,8,
            1,3,2, 6,8,5, 4,7,9,
            5,9,8, 7,3,4, 1,6,2,

            8,7,1, 9,2,6, 3,4,5,
            3,4,9, 8,5,1, 7,2,6,
            2,5,6, 4,7,3, 8,9,1,

            9,1,5, 3,4,2, 6,8,7,
            4,2,7, 5,6,8, 9,1,3,
            6,8,3, 1,9,7, 2,5,4
        )
    ),
    EXTREME_1(
        board(
            2,3,0, 7,6,0, 0,1,0,
            0,6,0, 0,0,3, 0,0,0,
            0,0,0, 0,0,5, 7,0,0,

            0,0,0, 0,3,0, 0,0,0,
            5,0,0, 0,0,0, 0,0,9,
            0,9,0, 6,1,0, 4,0,0,

            0,0,8, 0,0,0, 0,2,0,
            0,5,0, 4,9,0, 1,0,0,
            0,0,0, 0,0,7, 0,0,0
        ),
        board(
            2,3,9, 7,6,8, 5,1,4,
            7,6,5, 1,4,3, 8,9,2,
            1,8,4, 9,2,5, 7,6,3,

            6,4,7, 5,3,9, 2,8,1,
            5,2,1, 8,7,4, 6,3,9,
            8,9,3, 6,1,2, 4,5,7,

            4,7,8, 3,5,1, 9,2,6,
            3,5,2, 4,9,6, 1,7,8,
            9,1,6, 2,8,7, 3,4,5
        )
    );

    private final int[][] puzzle;
    private final int[][] solution;

    ExampleSudoku(int[][] puzzle, int[][] solution) {
        this.puzzle = SudokuSolver.deepCopy(puzzle);
        this.solution = SudokuSolver.deepCopy(solution);
    }

    public int[][] getPuzzle() {
        return SudokuSolver.deepCopy(puzzle);
    }

    public int[][] getSolution() {
        return SudokuSolver.deepCopy(solution);
    }

    public static final BiFunction<String, Function<String, String>, Optional<ExampleSudoku>> findByName =
        (expected, getName) ->
            Arrays.stream(values())
                .filter(example -> Objects.equals(getName.apply(example.name()), expected))
                .findFirst();

    public static final Function<int[][], int[][]> deepCopyBoard = SudokuSolver::deepCopy;

    private static int[][] board(int... values) {
        if (values.length != 81) throw new IllegalArgumentException("Boards must contain exactly 81 cells");
        
        int[][] board = new int[9][9];
        for (int i = 0; i < values.length; i++) {
            board[i / 9][i % 9] = values[i];
        }
        return board;
    }
}

