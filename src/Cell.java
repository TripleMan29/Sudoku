import java.util.Arrays;
import java.util.HashSet;

public class Cell {
    int value;
    HashSet<Integer> possibleValues;
    int line;
    int column;

    Cell(int value, int line, int column) {
        this.value = value;
        this.line = line;
        this.column = column;

        if (value == 0) {
            possibleValues = new HashSet<>();
            possibleValues.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
        }
    }
}
