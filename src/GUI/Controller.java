package GUI;

import Algorithm.Node;
import Algorithm.Printer;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


/**
 *  Interface between the Algorithm's Main Class and the GUI's Main Class
 */
public class Controller implements Printer {
    private String output;
    public int[][][] maze;
    public int rows;
    public int cols;

    // UI
    public GridPane metal1 = new GridPane();
    public GridPane metal2 = new GridPane();
    public GridPane metal3 = new GridPane();

    public static String[] hexColors = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    public static int currentColor = 0;

    public List<Node> srcPins, targetPins;

    public Controller() {
        this.srcPins = new ArrayList<>();
        this.targetPins = new ArrayList<>();
    }

    /**
     * @param output Saves the result string, which is the maze but in a string format
     */
    @Override
    public void result(String output) {
        this.output = output;
    }

    /** Sets the new Maze when a new path is created
     * @param maze The 3d Grid
     * @param rows The Grid's number of rows
     * @param cols The Grid's number of columns
     */
    @Override
    public void setMaze(int[][][] maze, int rows, int cols) {
        this.maze = maze;
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Used to display on the GUI where are all the pins placed
     * @param source List of all the Sources that have been placed in the grids
     * @param target List of all the Targets that have been placed in the grids
     */
    public void setPins(List<Node> source, List<Node> target) {
        this.srcPins = source;
        this.targetPins = target;
    }

    /**
     * @param l The Label which contains if it is a M1 wire ("1") or empty ("0")
     * @param i coordinate of the label in the Metal 1 Grid
     * @param j coordinate of the label in the Metal 1 Grid
     */
    public void setInMetal1Grid(Label l, int i, int j) {
        String metalStyle = "-fx-background-color: " + " #33F6FF;" + "-fx-border-color: black; -fx-font-size: 30;";
        String pinStyle = "-fx-background-color: #33F6FF; -fx-border-color: black; -fx-font-size: 20;";
        l.setMinHeight(30.0);
        l.setMaxHeight(30.0);
        l.setMinWidth(30.0);
        l.setMaxWidth(30.0);

        if(l.getText().equals("1"))
            l.setStyle(metalStyle);
        if(this.srcPins != null && this.srcPins.contains(new Node(j,i,0))) {  // case it is a source we write s#, #=source pin number
            l.setStyle(pinStyle);
            l.setText("S" + this.srcPins.indexOf(new Node(j, i, 0)));
        }
        else if(this.targetPins != null && this.targetPins.contains(new Node(j,i,0))) { // case it is a target we write t#, #=source pin number
            l.setStyle(pinStyle);
            l.setText("T" + this.targetPins.indexOf(new Node(j, i, 0)));
        }
        else
            l.setText("   ");
        metal1.add(l, i,j, 1, 1);
    }

    /**
     * @param l The Label which contains if it is a M2 wire ("2") or empty ("0")
     * @param i coordinate of the label in the Metal 2 Grid
     * @param j coordinate of the label in the Metal 2 Grid
     */
    public void setInMetal2Grid(Label l, int i, int j) {
        String metalStyle = "-fx-background-color: #FF00C5; -fx-border-color: black; -fx-font-size: 30;";
        String pinStyle = "-fx-background-color: #FF00C5; -fx-border-color: black; -fx-font-size: 20;";
        l.setMinHeight(30.0);
        l.setMaxHeight(30.0);
        l.setMinWidth(30.0);
        l.setMaxWidth(30.0);

        if(l.getText().equals("2"))
            l.setStyle(metalStyle);
        if(this.srcPins != null && this.srcPins.contains(new Node(j,i,1))) {
            l.setStyle(pinStyle);
            l.setText("S" + this.srcPins.indexOf(new Node(j, i, 1)));
        }
        else if(this.targetPins != null && this.targetPins.contains(new Node(j,i,1))) {
            l.setStyle(pinStyle);
            l.setText("T" + this.targetPins.indexOf(new Node(j, i, 1)));
        }
        else
            l.setText("   ");
        metal2.add(l, i,j, 1,1);
    }

    /**
     * @param l The Label which contains if it is a M3 wire ("3") or empty ("0")
     * @param i coordinate of the label in the Metal 3 Grid
     * @param j coordinate of the label in the Metal 3 Grid
     */
    public void setInMetal3Grid(Label l, int i, int j) {
        String metalStyle = "-fx-background-color: #000FFF; -fx-border-color: black; -fx-font-size: 15;";
        String pinStyle = "-fx-background-color: #000FFF; -fx-border-color: black; -fx-text-fill: #E7E7E7; -fx-font-size: 20;";
        l.setMinHeight(30.0);
        l.setMaxHeight(30.0);
        l.setMinWidth(30.0);
        l.setMaxWidth(30.0);

        if(l.getText().equals("3"))
            l.setStyle(metalStyle);
        if(this.srcPins != null && this.srcPins.contains(new Node(j,i,2))) {
            l.setStyle(pinStyle);
            l.setText("S" + this.srcPins.indexOf(new Node(j, i, 2)));
        }
        else if(this.targetPins != null && this.targetPins.contains(new Node(j,i,2))) {
            l.setStyle(pinStyle);
            l.setText("T" + this.targetPins.indexOf(new Node(j, i, 2)));
        }
        else
            l.setText("   ");
        metal3.add(l, i,j,1,1);
    }

}
