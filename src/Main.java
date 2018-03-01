import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;


public class Main extends Application {
    int i;
    //   static int counter = 0;
    static Label label;
    static Sudoku sudoku;

    static Field field = new Field("Tests/test228.txt");

    static Pane root = new Pane();

    private static Cells[][] board = new Cells[3][3];

    static void repaint(Field tempField) {
        field = tempField;
        root.getChildren().clear();
        fsa();
    }

    static Parent fsa() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                label = new Label();


                root.getChildren().add(label);

                label.setTranslateY(i * 45 + 20);
                if (j == 0) label.setTranslateX(20);
                else label.setTranslateX(j * 45 + 20);

                label.setFont(Font.font(25));
                label.setMinWidth(45);
                label.setMinHeight(45);
                label.setStyle("-fx-border-color: black;");
                label.setAlignment(Pos.CENTER);
                label.setTextFill(Color.RED);
                if (field.field[i][j].value != 0) label.setText(field.field[i][j].value + "");
                else label.setText("");

            }
        }

        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                Cells tile = new Cells();
                tile.setTranslateX(n * 135 + 20);
                tile.setTranslateY(m * 135 + 20);

                root.getChildren().add(tile);

                board[n][m] = tile;
            }
        }
        return root;
    }




    @Override

    public void start(Stage primaryStage) throws Exception {


        primaryStage.setTitle("sudokuSolver");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);


        Button btn = new Button();
        btn.setText("Next");
        btn.setTranslateX(360);
        btn.setTranslateY(440);

        btn.setPrefSize(70, 20);



        btn.setOnAction(event -> {
            try {
                i = 0;
                sudoku.printSudokuSolution("Tests/test228.txt");

                    Timeline timeline = new Timeline(
                            new KeyFrame(
                                    Duration.millis(50),
                                    event1 -> {

                                        repaint(Sudoku.fields.get(i));
                                        i++;
                                       // System.out.println(Sudoku.fields.size());
                                    }
                            )
                    );
                    timeline.setCycleCount(Sudoku.fields.size());
                    timeline.play();


            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(fsa());

        root.getChildren().addAll(btn);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        sudoku = new Sudoku();
        launch(args);
    }
}

class Cells extends StackPane {
    private Text text = new Text();

    public Cells() {
        Rectangle border = new Rectangle(135, 135);
        border.setFill(null);
        border.setStrokeWidth(4);
        border.setStroke(Color.BLACK);

        text.setFont(Font.font(72));

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

    }
}
