import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


class Sudoku {

    private int counter = 0;

    private Field field;
    //mb
    public static ArrayList<Field> fields;
    //

    void printSudokuSolution(String dir) throws Exception {

        fields = new ArrayList<Field>();

            field = new Field(dir);

            field = sudoku(field);

            field.printSudoku();
    }

    private void identPossibleValues (Field field, Cell cell) {
        int line = cell.line;
        int column = cell.column;

        int segmentFirstColumn = column - column % 3;
        int segmentFirstLine = line - line % 3;

        for (int i = 0; i < 9; i++) {
            int lineValue = field.field[line][i].value;
            int columnValue = field.field[i][column].value;
            int segmentValue = field.field[segmentFirstLine + i / 3][segmentFirstColumn + i % 3].value;

            if (lineValue != 0) {
                cell.possibleValues.remove(lineValue);
            }
            if (columnValue != 0) {
                cell.possibleValues.remove(columnValue);
            }
            if (segmentValue != 0) {
                cell.possibleValues.remove(segmentValue);
            }
        }
    }

    private int getUniqueValue (Field field, Cell cell) {
        int line = cell.line;
        int column = cell.column;

        boolean isUnique;

        for (int currentValue : cell.possibleValues) {

            int segmentFirstColumn = column - column % 3;
            int segmentFirstLine = line - line % 3;

            isUnique = true;
            for (int k = 0; k < 9; k++) {
                if (k != column) {
                    if (field.field[line][k].value == 0 && field.field[line][k].possibleValues.contains(currentValue)) {
                        isUnique = false;
                        break;
                    }
                }
            }
            if (isUnique) return currentValue;

            isUnique = true;
            for (int k = 0; k < 9; k++) {
                if (k != line) {
                    if (field.field[k][column].value == 0 && field.field[k][column].possibleValues.contains(currentValue)) {
                        isUnique = false;
                        break;
                    }
                }
            }
            if (isUnique) return currentValue;

            isUnique = true;
            for (int k = 0; k < 9; k++) {
                if (((segmentFirstLine + k / 3) != line) || ((segmentFirstColumn + k % 3) != column)) {
                    if (field.field[segmentFirstLine + k / 3][segmentFirstColumn + k % 3].value == 0 && field.field[segmentFirstLine + k / 3][segmentFirstColumn + k % 3].possibleValues.contains(currentValue)) {
                        isUnique = false;
                        break;
                    }
                }
            }
            if (isUnique) return currentValue;
        }
        return 0;
    }

    private Field sudoku(Field OldField) throws Exception{

        Field field = new Field();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field.field[i][j] = new Cell(OldField.field[i][j].value, i, j);
            }
        }
      //  paint(field);
        //mb
        field.printSudoku();
        fields.add(field);
        //
      //  Thread.sleep(1);


        boolean isFindSinglePossibleValue = true;
        boolean isFindUniquePossibleValue = true;

        while (isFindUniquePossibleValue) {
            isFindUniquePossibleValue = false;

            while (isFindSinglePossibleValue) {
                isFindSinglePossibleValue = false;

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (field.field[i][j].value == 0) {
                            identPossibleValues(field, field.field[i][j]);

                            if (field.field[i][j].possibleValues.size() == 1) {
                                int value = field.field[i][j].possibleValues.iterator().next();
                                field.insert(i, j, value);
                                isFindSinglePossibleValue = true;

                               // paint(field);
                                field.printSudoku();
                                fields.add(field);
                             //   Thread.sleep(1);


                            }
                        }
                    }
                }
            }

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (field.field[i][j].value == 0) {
                        int uniqueValue = getUniqueValue(field, field.field[i][j]);
                        if (uniqueValue != 0) {
                            field.insert(i, j, uniqueValue);
                            isFindUniquePossibleValue = true;

                           // paint(field);
                            //
                            field.printSudoku();
                            fields.add(field);
                            //
                          //  Thread.sleep(1);


                        }
                    }
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (field.field[i][j].value == 0) {
                    for (int selectedValue : field.field[i][j].possibleValues) {
                        field.field[i][j].value = selectedValue;
                        Field solution = sudoku(field);
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

    Field getField() {
        return field;
    }

  //  private void paint(Field tempField){
  //      Main.repaint(tempField);
  //  }


}
