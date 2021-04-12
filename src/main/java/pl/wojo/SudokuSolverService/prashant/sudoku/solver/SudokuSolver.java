package pl.wojo.SudokuSolverService.prashant.sudoku.solver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> solveImage(byte[] file) {
        try {
            long startTime = System.currentTimeMillis();
            Sudoku sudoku = ImageManipulator.convertToSudoku(file, false);
            if (sudoku == null) {
                log.info("Sorry, we could not identify the Sudoku puzzle from the given image..");
                return null;
            }
            SolveRoutines.display_grid_from_object(sudoku);

            HashMap<String, String> values = SolveRoutines.loadSudoku(sudoku);
            if (values == null) {
                log.info("Sorry, looks like this puzzle can not be solved..");
                return null;
            }
            SolveRoutines.display_values(SolveRoutines.search(values));
            log.info("\nTime taken to solve (ms) : {}\n", (System.currentTimeMillis() - startTime));
            return values;
        } catch (IOException e) {
            log.error(e.toString());
            return null;
        }

    }


}
