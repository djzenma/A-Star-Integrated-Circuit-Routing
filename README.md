# Mazen Amr
# A-Star-Routing


## A Star Algorithm for Routing cells' pins using Metal 1, Metal 2, and Metal 3 wires.

## How To Use It:
1. Run the code from the GUI/Main.class using your favorite IDE or command interpreter.
2. You will be prompted to enter the grids width, height and vias cost.
3. Then enter the source and target coordinates (Please note that the coordinates start from index 0).
4. The path will then be displayed on a beautiful GUI. To enter new cells, just press on new cells. 
5. And when you are done just close the window.


## Description

com.AStar class contains the actual functions needed for the A* algorithm to work. It uses the com.Node class.

com.Maze class is an interface between the main and the com.AStar class. I have done it to abstract details from the com.AStar class.

