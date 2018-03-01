import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


class Field {

    Cell[][] field;

    Field() {
        field = new Cell[9][9];
    }

    Field(String dir) {
        field = new Cell[9][9];
        ArrayList<Integer> digits = new ArrayList<>();
        digits.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
        FileReader reader = null;
        try {
            reader = new FileReader(dir);
            int c;
            int i = 0;
            while ((c = reader.read()) != -1) {
                if ((c >= '0') && (c <= '9')) {
                    field[i / 9][i % 9] = new Cell(c - '0', i / 9, i % 9);
                    i++;
                }
            }

            if (i != 81) {
                System.out.println("Ошибка");
                System.exit(1);
            }

            for (int k = 0; k < 9; k++) {
                ArrayList<Integer> currentDigits = new ArrayList<>(digits);
                for (int n = 0; n < 9; n++) {
                    if (field[k][n].value == 0);
                    else if (currentDigits.contains(field[k][n].value)) {
                        currentDigits.remove((Integer)field[k][n].value);
                    }
                    else{
                        System.out.println("Ошибка");
                        System.exit(1);
                    }
                }
            }

            for (int k = 0; k < 9; k++) {
                ArrayList<Integer> currentDigits = new ArrayList<>(digits);
                for (int n = 0; n < 9; n++) {
                    if (field[n][k].value == 0);
                    else if (currentDigits.contains(field[n][k].value)) {
                        currentDigits.remove((Integer)field[n][k].value);
                    }
                    else {
                        System.out.println("Ошибка");
                        System.exit(1);
                    }
                }
            }

            for (int k = 0; k < 9; k++) {
                ArrayList<Integer> currentDigits = new ArrayList<>(digits);
                for (int n = 0; n < 9; n++) {
                    if (field[n / 3 + (k / 3) * 3][n % 3 + (k % 3) * 3].value == 0);
                    else if (currentDigits.contains(field[n / 3 + (k / 3) * 3][n % 3 + (k % 3) * 3].value)) {
                        currentDigits.remove((Integer)field[n / 3 + (k / 3) * 3][n % 3 + (k % 3) * 3].value);
                    }
                    else {
                        System.out.println("Ошибка");
                        System.exit(1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void insert(int line, int column, int value) {
        field[line][column].value = value;
        int segmentFirstColumn = column - column % 3;
        int segmentFirstLine = line - line % 3;

        for (int k = 0; k < 9; k++) {
            if (field[line][k].value == 0) {
                field[line][k].possibleValues.remove(value);
            }
            if (field[k][column].value == 0) {
                field[k][column].possibleValues.remove(value);
            }
            if (field[segmentFirstLine + k / 3][segmentFirstColumn + k % 3].value == 0) {
                field[segmentFirstLine + k / 3][segmentFirstColumn + k % 3].possibleValues.remove(value);
            }
        }
    }

    void printSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 0 || j == 3 || j == 6) {
                    System.out.print("|");
                }
                System.out.print(field[i][j].value + "|");
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
        System.out.println();
    }
    public Cell[][] getBoard() {
        return field;
    }
}
