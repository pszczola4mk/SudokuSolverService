package pl.wojo.SudokuSolverService.prashant.sudoku.solver;

import lombok.extern.slf4j.Slf4j;
import pl.wojo.SudokuSolverService.prashant.sudoku.image.ImageManipulator;

@Slf4j
public class SudokuSolver {

    private static SudokuSolver instance;

    public static SudokuSolver getInstance() {
        if (instance == null) {
            instance = new SudokuSolver();
        }
        return instance;
    }

    public int[][] solveImage(byte[] file) {
        long startTime = System.currentTimeMillis();
        log.info("Starting conversion");
        Sudoku sudoku = ImageManipulator.convertToSudoku(file, false);
        if (sudoku == null) {
            log.info("Sorry, we could not identify the Sudoku puzzle from the given image..");
            return null;
        }
        log.info("Got sudoku");
        int[][] values = sudoku.getData();
        log.info("Start resolving");
        SudokuSolverAlgorithm.getInstance().solveSudoku(values);
        if (values == null) {
            log.info("Sorry, looks like this puzzle can not be solved..");
            return null;
        }
        log.info("\nTime taken to solve (ms) : {}\n", (System.currentTimeMillis() - startTime));
        return values;

    }


}
