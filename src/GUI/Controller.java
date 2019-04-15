package GUI;

import com.Printer;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class Controller implements Printer {
    private String output;
    public int[][][] maze;
    public int rows;
    public int cols;

    // UI
    public GridPane metal1 = new GridPane();
    public GridPane metal2 = new GridPane();
    public GridPane metal3 = new GridPane();

    @Override
    public void result(String output) {
        this.output = output;
    }

    @Override
    public void setMaze(int[][][] maze, int rows, int cols) {
        this.maze = maze;
        this.rows = rows;
        this.cols = cols;
    }

    public void setInMetal1Grid(Label l, int i, int j) {
        if(l.getText().equals("1"))
            l.setStyle("-fx-background-color: #33F6FF; -fx-border-color: black; -fx-font-size: 30;");
        l.setText("   ");
        metal1.add(l, i,j, 1, 1);
    }

    public void setInMetal2Grid(Label l, int i, int j) {
        if(l.getText().equals("2"))
            l.setStyle("-fx-background-color: #FF00C5; -fx-border-color: black; -fx-font-size: 30;");
        l.setText("   ");
        metal2.add(l, i,j, 1,1);
    }

    public void setInMetal3Grid(Label l, int i, int j) {
        if(l.getText().equals("3"))
            l.setStyle("-fx-background-color: #000FFF; -fx-border-color: black; -fx-font-size: 30;");
        l.setText("   ");
        metal3.add(l, i,j,1,1);
    }

}
