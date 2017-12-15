import java.io.FileReader;
import java.util.ArrayList;

public class Sudoku {
    private static class Cell {
        private int value;
        private ArrayList<Integer> possibleValues;
        private int line;
        private int column;

        Cell(int value, int line, int column) {
            this.value = value;
            this.line = line;
            this.column = column;

            if (value == 0) {
                possibleValues = new ArrayList<>();
                possibleValues.add(1);
                possibleValues.add(2);
                possibleValues.add(3);
                possibleValues.add(4);
                possibleValues.add(5);
                possibleValues.add(6);
                possibleValues.add(7);
                possibleValues.add(8);
                possibleValues.add(9);
            }
        }

        private void identPossibleValues (Cell[][] field) {
            int segmentFirstColumn = column - column % 3;
            int segmentFirstLine = line - line % 3;

            for (int i = 0; i < 9; i++) {
                int lineValue = field[line][i].value;
                int columnValue = field[i][column].value;
                int segmentValue = field[segmentFirstLine + i / 3][segmentFirstColumn + i % 3].value;

                if (lineValue != 0) {
                    possibleValues.remove((Integer)lineValue);
                }
                if (columnValue != 0) {
                    possibleValues.remove((Integer)columnValue);
                }
                if (segmentValue != 0) {
                    possibleValues.remove((Integer)segmentValue);
                }
            }
        }

        private int getUniqueValue (Cell[][] field) {
            int currentValue;
            boolean isUnique;

            for (int i = 0; i < possibleValues.size(); i++) {
                currentValue = possibleValues.get(i);

                int segmentFirstColumn = column - column % 3;
                int segmentFirstLine = line - line % 3;

                isUnique = true;
                for (int k = 0; k < 9; k++) {
                    if (k != i) {
                        if (field[line][k].value == 0 && field[line][k].possibleValues.contains(currentValue)) {
                            isUnique = false;
                            break;
                        }
                    }
                }
                if (isUnique) return currentValue;

                isUnique = true;
                for (int k = 0; k < 9; k++) {
                    if (k != i) {
                        if (field[k][column].value == 0 && field[k][column].possibleValues.contains(currentValue)) {
                            isUnique = false;
                            break;
                        }
                    }
                }
                if (isUnique) return currentValue;

                isUnique = true;
                for (int k = 0; k < 9; k++) {
                    if (k != i) {
                        if (field[segmentFirstLine + k / 3][segmentFirstColumn + k % 3].value == 0 && field[segmentFirstLine + k / 3][segmentFirstColumn + k % 3].possibleValues.contains(currentValue)) {
                            isUnique = false;
                            break;
                        }
                    }
                }
                if (isUnique) return currentValue;
            }
            return 0;
        }
    }

    static void printSudokuSolution(String dir) throws Exception {
        Cell[][] field = new Cell[9][9];
        FileReader reader = new FileReader(dir);
        int c;
        int i = 0;
        while((c = reader.read()) != -1){
            if ((c >= '0') && (c <= '9')) {
                field[i / 9][i % 9] = new Cell(c - '0', i / 9, i % 9);
                i++;
            }
        }

        field = sudoku(field);

        for (i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 0 || j == 3 || j == 6) {
                    System.out.print("|");
                }
                if (field != null) {
                    System.out.print(field[i][j].value + "|");
                }
            }
            if (i == 2 || i == 5) {
                System.out.println();
                for (int j = 0; j < 8; j++) {
                    System.out.print("--");
                    if (j == 2 || j == 4) {
                        System.out.print("||");
                    }
                    if (j == 3) {
                        System.out.print("-");
                    }
                }
            }
            System.out.println();
        }
    }

    private static Cell[][] sudoku(Cell[][] OldField) {
        Cell[][] field = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = new Cell(OldField[i][j].value, i, j);
            }
        }

        boolean isFindSinglePossibleValue = true;
        boolean isFindUniquePossibleValue = true;

        while (isFindUniquePossibleValue) {
            isFindUniquePossibleValue = false;

            while (isFindSinglePossibleValue) {
                isFindSinglePossibleValue = false;

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (field[i][j].value == 0) {
                            field[i][j].identPossibleValues(field);

                            if (field[i][j].possibleValues.size() == 1) {
                                field[i][j].value = field[i][j].possibleValues.get(0);
                                isFindSinglePossibleValue = true;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (field[i][j].value == 0) {
                        int uniqueValue = field[i][j].getUniqueValue(field);
                        if (uniqueValue != 0) {
                            field[i][j].value = uniqueValue;
                            isFindUniquePossibleValue = true;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (field[i][j].value == 0) {
                    for (int k = 0; k < field[i][j].possibleValues.size(); k++) {
                        field[i][j].value = field[i][j].possibleValues.get(k);
                        Cell[][] solution = sudoku(field);
                        if (solution != null) {
                            return solution;
                        }
                    }
                    return null;
                }
            }
        }
        return field;
    }
}
