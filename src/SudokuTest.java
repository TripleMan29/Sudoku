import org.junit.Test;

import static javafx.application.Application.launch;

public class SudokuTest {
    @Test
    public void printSudokuSolution() throws Exception {
        new Sudoku().printSudokuSolution("Tests/test228.txt");
    }


}