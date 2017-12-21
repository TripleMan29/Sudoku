import java.io.FileReader;

class Field {
    Cell[][] field;

    Field() {
        field = new Cell[9][9];
    }

    Field(String dir) throws Exception {
        field = new Cell[9][9];
        FileReader reader = new FileReader(dir);
        int c;
        int i = 0;
        while((c = reader.read()) !=- 1){
            if ((c >= '0') && (c <= '9')) {
                field[i / 9][i % 9] = new Cell(c - '0', i / 9, i % 9);
                i++;
            }
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
}
