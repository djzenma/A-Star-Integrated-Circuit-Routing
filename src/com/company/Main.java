package com.company;


import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] initialization = Input.takeGridDimsAndViaCost(scanner);
        int[] sourceCoords = Input.takeCoordinates("Enter The New Source Cell's Coordinates: ", scanner);
        int[] targetCoords = Input.takeCoordinates("Enter The New Target Cell's Coordinates: ", scanner);


        //int [][] blocks = {{4,4,1}};
        Maze maze = new Maze(initialization[0],initialization[1]);
        //maze.setBlocks(blocks);

        String cmd;
        while(true) {
            maze.setSource(sourceCoords[0], sourceCoords[1]);
            maze.setTarget(targetCoords[0], targetCoords[1]);
            maze.print();

            List<Node> path = maze.findShortestPath();
            maze.printPath(path);

            System.out.println("Want to enter new cells? y/n");
            cmd = scanner.next();
            if(cmd.equals("n"))
                break;
            else {
                sourceCoords = Input.takeCoordinates("Enter The New Source Cell's Coordinates: ", scanner);
                targetCoords = Input.takeCoordinates("Enter The New Target Cell's Coordinates: ", scanner);
            }

        }
        System.out.println("Have a nice day!");
        scanner.close();
    }
}
