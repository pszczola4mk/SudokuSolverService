package pl.wojo.SudokuSolverService.prashant.sudoku.solver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        log.info("Time taken to solve (ms) : {}", (System.currentTimeMillis() - startTime));
        return values;
    }

    public int[][] solveText(String text) {
        int[][] data = new int[9][9];
        String[] rows = text.split(";");
        int i = 0;
        for (String row : rows) {
            String[] cols = row.split("\\|");
            int j = 0;
            for (String col : cols) {
                if (StringUtils.isNotBlank(col)) {
                    data[i][j] = Integer.parseInt(col);
                }
                j = j + 1;
            }
            i = i + 1;
        }
        SudokuSolverAlgorithm.getInstance().solveSudoku(data);
        return data;
    }


}
