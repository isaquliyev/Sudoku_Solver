# Advanced Java Assignment Fall 2025/2026 - Sudoku solver

## Conditions
- Upload your solution in the lab TMS group, not here.
- All uploads here (in the theory group) will be disregarded, as if they did not exist.
- **Submission format: zip.**
- Only include the necessary source files in the correct directory structure in the zip file.
- If the solution requires other files (such as input files), put them in the root folder of the zip.
- Do not include any other files (e.g. `.class`).

⚠️ Do not include any folder that is not required in the solution.  
For example, if you put everything in an unnecessary top-level folder like `assignment` or `src`, the automatic tester will likely reject your solution as missing.

- Within a couple of minutes after the upload, an autotester will run.  
  Check its output and re-upload if needed.
- See the general assignment conditions in Teams under `General/Files/conditions`.

### Cheating
Cheating is forbidden. If you cheat and are caught, **you will not be graded this semester.**  
This includes using AI and submitting code taken from others’ solutions.

### Code Quality
- Your solution must be of the highest quality possible.
- Use the names of packages/classes/methods exactly as given.
- Follow standard Java coding conventions.
- Name your variables well; use good code structure.
- Apply the learned tools and programming concepts correctly.

### Submission
- Multiple submissions allowed before the deadline.
- If your solution does not solve the whole exercise, **you will not be graded this semester.**
- Your *latest* submission will be evaluated.
- The deadline is strict — even 1 minute late is considered late.

---

## Background
You will implement a Sudoku solver.

- The board is a `9×9` array of ints (`1..9` for filled cells, `0` for empty).
- You can address cells using either `(x,y)` or index `0..80`.

You are provided with an algorithm called **DLX (Knuth’s Algorithm X with Dancing Links)**, which performs most of the solving mechanism.

- You do **not** need to understand DLX internally.
- TODO and ??? indicate code you must fill in.
- Bonus parts are fully optional.

---

## Tasks

### 1. Enumerations in `SudokuSolver`

#### Difficulty enum
- Defines difficulty classes with `minNum` and `maxNum` filled starting cells.
- Implement:
  - `stringToDifficulty(String)`:
    - Return a case-insensitive match by name.
    - Throw `NoSuchElementException` if not found.
  - `numToDifficulty(long)`:
    - Match by number of filled cells.
    - Return `NONE` if no match.

#### State enum
- States:
  - `INVALID = 0`
  - `SOLVEDUNIQUE = 1`
  - `SOLVEDTWO = 2`
  - `SOLVEDTHREE = 3`
  - `SOLVEDMANY = Integer.MAX_VALUE`
  - `SOLVED = -1` (solution exists but total unknown)

Implement:
- `stateFromSols(long solutions)`:
  - `<0` → `SOLVED`
  - `0..3` → matching enum item
  - `>=4` → `SOLVEDMANY`

---

### 2. Implement simpler parts of `SudokuSolver`

#### deepCopy
- Make a deep copy using `Arrays.copyOf` and streams.

#### countFilledCells
- Count non-zero cells.

#### isSafe
Check whether `num` can be placed at `(row, col)`:
- Not in the same row
- Not in the same column
- Not in the same 3×3 box  
  Box starts at `(row - row%3, col - col%3)`.

#### findContent
- Takes a lambda that may return either:
  - A valid value, or
  - Nothing useful
- Repeatedly call the lambda until it returns valid content.
- You may assume it eventually will.

---

### 3. Implement `generate` in `SudokuSolver`
Uses helper `maybeGenerateBoard`:

Process:
1. Create empty 9×9 board.
2. Fill it with a **complete random solution** using `solve` (in `ssdlx`).
3. Randomize the order of all 81 cell indexes.
4. Stream iterations:
   - Start with a deep copy of the full solution.
   - For each index:
     - Remove the value (set to `0`).
     - Check if the puzzle is still uniquely solvable using DLX with solution limit = 2.
     - If not unique, restore the digit.
   - Maximum 81 iterations.

Stop when the board matches required difficulty.

---

### 4. Implement `solve` in `SudokuSolver` (backtracking)
Prepare:
- `cells`: list of all zero-valued cells
- `digits`: values `1..9`, shuffled optionally

Algorithm:
- Use a stack:  
  stack[n] = index `i` meaning `digits[i]` is placed in `cells[n]`.
- Try digits in order and backtrack when needed.

Flow:
1. If stack empty → `INVALID`
2. Otherwise, take current cell:
   - If out of digit options (top is 9):
     - Pop stack
     - Reset the board cell to 0
     - Return undecided
3. Otherwise increment top element → try next digit
4. Check `isSafe`
   - If not safe → return undecided
5. Place digit on board
6. If last cell → `SOLVED`
7. Else push 0 onto stack and continue

---

### 5. Implement load & save
#### If filename ends with `.sud`:
- Use `ObjectInputStream` / `ObjectOutputStream`
- Read/write as `int[][]`

#### Otherwise (text file):
- Format: 9 lines, each with 9 integers `0..9` separated by spaces.
- Validate:
  - Exactly 9×9 numbers
  - All in `0..9`
  - Else throw `IOException`

Remember to close files.

---

### 6. ExampleSudoku.java “???” parts
Implement:
- `findByName`  
  Apply `getName` to each enum item’s name (e.g. `EASY_1 → sudoku://examples/easy-1`)  
  Return matching enum item.  
  Assume it exists.

---

### 7. SudokuSolverTest
For each `ExampleSudoku` sample, check:

1. Given puzzle matches given solution for filled cells  
   (non-zero digits same)
2. Puzzle is solvable
3. Solver’s result matches sample solution

---

### 8. Refactor `SudokuSolverDLX.java` to use lambdas & streams
- 12 methods are marked `@Deprecated`
  - These aren’t truly deprecated — they’re markers.
- Replace at least **4** of these methods with:
  - Fields of functional interface types
  - No loops or recursion
- Reorder methods if needed for initialization
- Remove the annotation from replaced methods

In tests:
- Add case `changedCount` ensuring `countDeprecated <= 8`

---

## Bonus Part 1 (Optional)
A Swing GUI is provided for interactive Sudoku testing.

Includes:
- An MCP (Model Context Protocol) server  
  (spec: 2025-06-18, JSON-RPC 2.0)

Configuration:
1. Use provided all-in-one JAR, or  
2. Use provided Maven `pom.xml`

---

## Bonus Part 2 (Optional)
Use MCP on ChatGPT UI or OpenAI API.

Steps:
1. Run `com.advjava.mcp.MCP`
2. Create a secure tunnel via `serveo.net` or `ssh.localhost.run`
3. Use ChatGPT MCP integration *or* OpenAI API with your key.

---

## Notes
- Valid Sudoku puzzles must have **exactly one** unique solution.
- Proven minimum filled cells for unique solution: **17**
- DLX = Knuth’s Algorithm X with Dancing Links

