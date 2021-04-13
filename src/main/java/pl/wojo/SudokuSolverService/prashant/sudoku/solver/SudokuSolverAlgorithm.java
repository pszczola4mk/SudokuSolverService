package pl.wojo.SudokuSolverService.prashant.sudoku.solver;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SudokuSolverAlgorithm {

	private static SudokuSolverAlgorithm instance;
	private int[][] result = new int[9][9];

	public static SudokuSolverAlgorithm getInstance() {
		if (instance == null) {
			instance = new SudokuSolverAlgorithm();
		}
		return instance;
	}

	public void solveSudoku(int[][] board) {
		int res = solveSudoku(board, 0, 0);
		if (res == 1) {
			log.info("Jest rozwiązanie");
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					board[i][j] = this.result[i][j];
				}
			}
		} else {
			log.warn("Brak rozwiązania");
		}
	}

	private int solveSudoku(int[][] board, int x, int y) {
		if (board[x][y] == 0) {
			int[] allowed = findAllowed(board, x, y);//znajdz dopuszczalne liczby
			if (allowed.length == 0) {
				//nic nie znalazł, brak rozwiązania
				return 0;
			}
			for (int c : allowed) {
				if (c != 0) {
					int[][] copy = copy(board);
					copy[x][y] = c;
					int result = solveSudoku(copy, x, y);
					if (result == 1) {//mam rozwiązanie
						board = copy;
						return 1;
					}
				}
			}
			return 0;
		} else {//gdy uzupełnione to idź dalej
			if (x == 8) {
				if (y == 8) {
					this.result = board;
					return 1;//koniec, przeszedł wszystkie pola
				}
				y = y + 1;
			} else {
				if (y == 8) {
					x = x + 1;
					y = 0;
				} else {
					y = y + 1;
				}
			}
			return solveSudoku(board, x, y);
		}
	}

	private int[][] copy(int[][] board) {
		int[][] copy = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}

	private int[] findAllowed(int[][] board, int x, int y) {
		int finalY = y;
		int[] allowed = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		for (int c : board[x]) {
			clear(allowed, c);
		}
		for (int c : Arrays.stream(board).map(tab -> tab[finalY]).collect(Collectors.toList())) {
			clear(allowed, c);
		}
		int xMod = x % 3;
		int yMod = y % 3;
		for (int i = x - xMod; i < x - xMod + 3; i++) {
			for (int j = y - yMod; j < y - yMod + 3; j++) {
				int c = board[i][j];
				clear(allowed, c);
			}
		}
		return allowed;
	}

	private void clear(int[] allowed, int c) {
		if (c != 0) {
			allowed[c - 1] = 0;
		}
	}
}
