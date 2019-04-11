package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        int [][] blocks = {{6,2,0}};
        Maze maze = new Maze(8,8);
        maze.setSource(1,6);
        maze.setTarget(6,4);
        maze.setBlocks(blocks);
        maze.print();

        List<Node> path = maze.findShortestPath();
        maze.printPath(path);
    }
}
