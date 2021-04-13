package pl.wojo.SudokuSolverService.solver;

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pl.wojo.SudokuSolverService.business.Base64Coder;
import pl.wojo.SudokuSolverService.prashant.sudoku.solver.SudokuSolver;

@Slf4j
public class SudokuSolverTest {

	@Test
	public void solveImageTest() {
		String path = "src/test/resources/sudoku_2.png";
		File file = new File(path);
		byte[] bytes = Base64Coder.readFile(file);
		//System.loadLibrary("opencv-320");
		int[][] result = SudokuSolver.getInstance().solveImage(bytes);
		for (int i = 0; i < 9; i++) {
			String line = "";
			for (int j = 0; j < 9; j++) {
				line = line + result[i][j] + "|";
			}
			log.info(line);
		}
	}
}
