package GUI;

import Algorithm.Node;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;


public class Main extends Application {
    private static Controller controller;

    @Override
    public void start(Stage primaryStage){
        //Parent root = FXMLLoader.load(getClass().getResource("GUI/sample.fxml"));
        primaryStage.setTitle("A* Routing");
        controller = new Controller();
        GridPane gridContainer = new GridPane();

        Algorithm.Main.main(controller);   // Take inputs and Run the A* Algorithm

        int[][][] maze = controller.maze;
        int rows = controller.rows;
        int cols = controller.cols;

        Label[][] metal1 = new Label[rows][];
        Label[][] metal2 = new Label[rows][];
        Label[][] metal3 = new Label[rows][];
        String nodeStyle = "-fx-border-color: black; -fx-font-size: 30;";
        String labelStyle = "-fx-font-size: 30;";

        for(int i=0; i<rows; i++) {
            metal1[i] = new Label[cols];
            metal2[i] = new Label[cols];
            metal3[i] = new Label[cols];
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                metal1[i][j] = new Label(Integer.toString(maze[i][j][0]));
                metal2[i][j] = new Label(Integer.toString(maze[i][j][1]));
                metal3[i][j] = new Label(Integer.toString(maze[i][j][2]));

                metal1[i][j].setStyle(nodeStyle);
                metal2[i][j].setStyle(nodeStyle);
                metal3[i][j].setStyle(nodeStyle);

                controller.setInMetal1Grid(metal1[i][j], j,i);
                controller.setInMetal2Grid(metal2[i][j], j,i);
                controller.setInMetal3Grid(metal3[i][j], j,i);
            }
        }
        Label m1 = new Label("Metal 1");
        Label m2 = new Label("Metal 2");
        Label m3 = new Label("Metal 3");
        m1.setStyle(labelStyle);
        m2.setStyle(labelStyle);
        m3.setStyle(labelStyle);

        gridContainer.add(controller.metal1, 0, 0);
        gridContainer.add(m1, 0, 1);

        gridContainer.add(controller.metal2, 1, 0);
        gridContainer.add(m2, 1, 1);

        gridContainer.add(controller.metal3, 2, 0);
        gridContainer.add(m3, 2, 1);

        gridContainer.setHgap(20.0);
        gridContainer.setAlignment(Pos.CENTER);


        primaryStage.setScene(new Scene(gridContainer, 1024, 516));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
