package GUI;

import Algorithm.Node;
import Algorithm.Printer;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;


public class Controller implements Printer {
    private String output;
    public int[][][] maze;
    public int rows;
    public int cols;

    // UI
    public GridPane metal1 = new GridPane();
    public GridPane metal2 = new GridPane();
    public GridPane metal3 = new GridPane();

    public List<Node> srcPins, targetPins;

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

    public void setPins(List<Node> source, List<Node> target) {
        this.srcPins = source;
        this.targetPins = target;
    }

    public void setInMetal1Grid(Label l, int i, int j) {
        String metalStyle = "-fx-background-color: #33F6FF; -fx-border-color: black; -fx-font-size: 30;";
        String pinStyle = "-fx-background-color: #33F6FF; -fx-border-color: black; -fx-font-size: 20;";
        l.setMinHeight(30.0);
        l.setMaxHeight(30.0);
        l.setMinWidth(30.0);
        l.setMaxWidth(30.0);

        if(l.getText().equals("1"))
            l.setStyle(metalStyle);
        if(this.srcPins.contains(new Node(j,i,0))) {  // case it is a source we write s#, #=source pin number
            l.setStyle(pinStyle);
            l.setText("S" + this.srcPins.indexOf(new Node(j, i, 0)));
        }
        else if(this.targetPins.contains(new Node(j,i,0))) { // case it is a target we write t#, #=source pin number
            l.setStyle(pinStyle);
            l.setText("T" + this.targetPins.indexOf(new Node(j, i, 0)));
        }
        else
            l.setText("   ");
        metal1.add(l, i,j, 1, 1);
    }

    public void setInMetal2Grid(Label l, int i, int j) {
        String metalStyle = "-fx-background-color: #FF00C5; -fx-border-color: black; -fx-font-size: 30;";
        String pinStyle = "-fx-background-color: #FF00C5; -fx-border-color: black; -fx-font-size: 20;";
        l.setMinHeight(30.0);
        l.setMaxHeight(30.0);
        l.setMinWidth(30.0);
        l.setMaxWidth(30.0);

        if(l.getText().equals("2"))
            l.setStyle(metalStyle);
        if(this.srcPins.contains(new Node(j,i,1))) {
            l.setStyle(pinStyle);
            l.setText("S" + this.srcPins.indexOf(new Node(j, i, 1)));
        }
        else if(this.targetPins.contains(new Node(j,i,1))) {
            l.setStyle(pinStyle);
            l.setText("T" + this.targetPins.indexOf(new Node(j, i, 1)));
        }
        else
            l.setText("   ");
        metal2.add(l, i,j, 1,1);
    }

    public void setInMetal3Grid(Label l, int i, int j) {
        String metalStyle = "-fx-background-color: #000FFF; -fx-border-color: black; -fx-font-size: 15;";
        String pinStyle = "-fx-background-color: #000FFF; -fx-border-color: black; -fx-text-fill: #E7E7E7; -fx-font-size: 20;";
        l.setMinHeight(30.0);
        l.setMaxHeight(30.0);
        l.setMinWidth(30.0);
        l.setMaxWidth(30.0);

        if(l.getText().equals("3"))
            l.setStyle(metalStyle);
        if(this.srcPins.contains(new Node(j,i,2))) {
            l.setStyle(pinStyle);
            l.setText("S" + this.srcPins.indexOf(new Node(j, i, 2)));
        }
        else if(this.targetPins.contains(new Node(j,i,2))) {
            l.setStyle(pinStyle);
            l.setText("T" + this.targetPins.indexOf(new Node(j, i, 2)));
        }
        else
            l.setText("   ");
        metal3.add(l, i,j,1,1);
    }

}
