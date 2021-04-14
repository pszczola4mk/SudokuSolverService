package pl.wojo.SudokuSolverService.controller;

import java.util.Base64;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wojo.SudokuSolverService.business.Base64Coder;
import pl.wojo.SudokuSolverService.model.Image;
import pl.wojo.SudokuSolverService.prashant.sudoku.solver.Sudoku;
import pl.wojo.SudokuSolverService.prashant.sudoku.solver.SudokuSolver;

@Slf4j
@RestController
public class ImageController {

	@GetMapping("/echo/{text}")
	public ResponseEntity<String> echo(@PathVariable String text) {
		log.info("echo - start {}", text);
		return ResponseEntity.ok("{\"result\": \"echo " + text + " date " + new Date() + "\"}");
	}

	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	public ResponseEntity<String> uploadImg(@RequestBody Image image) {
		log.info("/POST request with " + image.getName());
		byte[] bytes = Base64.getDecoder().decode(image.getData());
		int[][] sudoku = SudokuSolver.getInstance().solveImage(bytes);
		String result = new Sudoku(sudoku).getDataAsString();
		log.info("Solved sudoku: " + result);
		return ResponseEntity.ok("{\"result\": \"" + result + "\"}");
	}
}
