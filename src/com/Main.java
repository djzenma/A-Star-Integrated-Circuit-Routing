package com;


import GUI.Controller;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(Controller controller) {
        Scanner scanner = new Scanner(System.in);
        int[] initialization = Input.takeGridDimsAndViaCost(scanner);

        Maze maze = null;

        boolean firstTime = true;

        String cmd;
        while(true) {
            List<Node> path = null;
            boolean invalidCells = false;

            // Take Source and Target coordinates
            int[] sourceCoords = Input.takeCoordinates("Enter The New Source Cell's Coordinates: ", scanner);
            int[] targetCoords = Input.takeCoordinates("Enter The New Target Cell's Coordinates: ", scanner);
            // If first time, initialize the maze grid
            if(firstTime) {
                maze = new Maze(initialization[0], initialization[1], new Node(sourceCoords[0], sourceCoords[1], sourceCoords[2]), new Node(targetCoords[0], targetCoords[1], targetCoords[2]));
                firstTime = false;
            }
            // Else, set the new source and target
            else {
                try {
                    maze.setSource(sourceCoords[0], sourceCoords[1], sourceCoords[2]);
                    maze.setTarget(targetCoords[0], targetCoords[1], targetCoords[2]);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    invalidCells = true;
                }
            }

            // If source and target and valid, find shortest path
            if(!invalidCells)
                path = maze.findShortestPath();

            maze.print();

            // Print if the path was found
            if(!invalidCells && path.size() != 0) {
                System.out.println("Path Found!");
                maze.printPath(path);
                controller.setMaze(maze.getMaze(), initialization[0], initialization[1]);
            }
            else if(invalidCells) {
                System.out.println("Invalid cells, check above messages...");
            }
            else {
                System.out.println("Path not Found!");
            }

            // Ask to enter new cells
            System.out.println("Want to enter new cells? y/n");
            cmd = scanner.next();
            if(cmd.toLowerCase().equals("n")) {
                controller.setPins(maze.sourcesList, maze.targetList);
                break;
            }
            else {
                // make the last path into obstacles then repeat
                if(!invalidCells) {
                    int[][] blocks = new int[path.size()][];
                    for (Node n: path) {
                        System.out.println(n);
                    }
                    int i = 0;
                    for (Node node : path) {
                        blocks[i] = new int[3];
                        blocks[i][0] = node.getX();
                        blocks[i][1] = node.getY();
                        blocks[i][2] = node.getZ();
                        i++;
                    }
                    maze.setBlocks(blocks);
                }
            }
        }




        System.out.println("Have a nice day!");
        scanner.close();
    }

}
