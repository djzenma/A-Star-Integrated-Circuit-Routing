# Mazen Amr
# A-Star-Routing

## Project For the Course Digital Logic Design II in the American University in Cairo.
## A Star Algorithm for Routing cells' pins using Metal 1, Metal 2, and Metal 3 wires.

## How To Use It:
From the src directory:
1. Run the code from the GUI/Main.class using your favorite IDE or command interpreter.
2. You will be prompted to enter the grids width, height and vias cost in the console.
3. Then enter the source and target coordinates (Please note that the coordinates start from index 0).
4. The path will then be displayed on a beautiful GUI and both the cost and the CPU Time will be in the console (will make it in the GUI later). To enter new cells, just press on new cells from the GUI and repeat step 3.
5. And when you are done just close the window.

## Documentation
In the Documentation directory, go to index.html and a JavaDoc of all my classes will be available.

## Description

The project contains 2 packages:
1. Algorithm package: \
Algorithm.AStar class contains the actual functions needed for the A* algorithm to work. It uses the Algorithm.Node class. \
Algorithm.Maze class is an interface between the main and the com.AStar class. I have done it to abstract details from the Algorithm.AStar class.\
Algorithm.Utils class has a set of helper functions to improve the readability of the other main classes.

2. GUI Package: \
GUI.Main class runs the Algorithm.Main to have the result of the algorithm and displays it on screen.
GUI.Controller is a medium for the Algorithm.Main to transfer data to the GUI.Main.
