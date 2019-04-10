package com.company;

public class Main {

    public static void main(String[] args) {
        Maze maze = new Maze(4,4);
        maze.setSource(0,0);
        maze.setTarget(3,3);
        maze.print();
    }
}
