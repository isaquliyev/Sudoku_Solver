package hu.advjava.mcpsudoku

import org.junit.Test;
import org.junit.Assert;
import java.util.Arrays;

/**
 * JUnit tests for every {@link ExampleSudoku} entry.
 */
public final class SudokuSolverTest {

    @Test
    public void testEasy1() {
        testExample(ExampleSudoku.EASY_1);
    }

    @Test
    public void testMedium1() {

        testExample(ExampleSudoku.MEDIUM_1);
    }

    @Test
    public void testHard1() {
        testExample(ExampleSudoku.HARD_1);
    }

    @Test
    public void testExpert1() {
        testExample(ExampleSudoku.EXPERT_1);
    }

    @Test
    public void testMaster1() {
        testExample(ExampleSudoku.MASTER_1);
    }

    @Test
    public void testExtreme1() {
        testExample(ExampleSudoku.EXTREME_1);
    }

    private void testExample(ExampleSudoku example) {
        assertPuzzleMatchesSolution(example);
        assertPuzzleSolvable(example);
    }

    private void assertPuzzleMatchesSolution(ExampleSudoku example) {
        int[][] puzzle = example.getPuzzle();
        int[][] solution = example.getSolution();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int given = puzzle[row][col];
                if (given != 0) {
                    Assert.assertEquals(
                        example.name() + " mismatch at (" + row + "," + col + ")",
                        solution[row][col],
                        given);
                }
            }
        }
    }

    private void assertPuzzleSolvable(ExampleSudoku example) {
        int[][] board = example.getPuzzle();
        SudokuSolver.State state = SudokuSolver.solve(board, false);

        Assert.assertEquals(
            example.name() + " should be solvable",
            SudokuSolver.State.SOLVED,
            state);

        int[][] solution = example.getSolution();
        Assert.assertArrayEquals(
            example.name() + " solver output should match solution",
            solution,
            board);
    }
}

