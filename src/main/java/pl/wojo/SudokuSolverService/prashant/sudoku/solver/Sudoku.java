package pl.wojo.SudokuSolverService.prashant.sudoku.solver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sudoku {

    private int[][] data;

    public Sudoku() {}

    public Sudoku(int[][] data) {
        setData(data);
    }

    public void setData(int[][] data) {
        if (data.length != 9 || data[0].length != 9) {
            log.debug("Not a valid sudoku (9x9). Returning..");
            return;
        }
        this.data = data;
    }

    public int[][] getData() {
        return data;
    }

    public String getDataAsString() {
        if (data == null) {
            return "Empty sudoku";
        }
        String result = "|";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //board[i][j] = this.result[i][j];
                result = result + this.data[i][j] + "|";
            }
            result = result + ";";
        }
        return result;
    }
}
