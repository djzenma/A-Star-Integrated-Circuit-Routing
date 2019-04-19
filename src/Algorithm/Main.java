package Algorithm;



import GUI.Controller;

import java.util.List;


/**
 * Main of the A* Algorithm, where all the backend and the logic happen
 */
public class Main {

    /**
     * @param controller: Interface between the GUI Main class and this Algorithm Main
     */
    public static void main(Controller controller) {

        String cmd;
        List<Node> path = null;
        boolean invalidCells = false;

        // Take Source and Target coordinates
        int[] sourceCoords = Utils.takeCoordinates("Enter The New Source Cell's Coordinates: ", GUI.Main.scanner);
        int[] targetCoords = Utils.takeCoordinates("Enter The New Target Cell's Coordinates: ", GUI.Main.scanner);

        // in case of negative coordinates, terminate
        if(sourceCoords[0] < 0 || sourceCoords[1] < 0 || sourceCoords[2] < 0 ||
                targetCoords[0] < 0 || targetCoords[1] < 0 || targetCoords[2] < 0)
            GUI.Main.exit = true;

        // If first time, initialize the maze grid
        if(GUI.Main.firstTime) {
            GUI.Main.maze = new Maze(GUI.Main.initialization[0], GUI.Main.initialization[1], new Node(sourceCoords[0], sourceCoords[1], sourceCoords[2]), new Node(targetCoords[0], targetCoords[1], targetCoords[2]));
            GUI.Main.firstTime = false;
        }
        // Else, set the new source and target and check if valid
        else {
            try {
                GUI.Main.maze.setSource(sourceCoords[0], sourceCoords[1], sourceCoords[2]);
                GUI.Main.maze.setTarget(targetCoords[0], targetCoords[1], targetCoords[2]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                invalidCells = true;
            }
        }

        // If source and target and valid, find shortest path
        if(!invalidCells)
            path = GUI.Main.maze.findShortestPath();

        //maze.print();

        // Print if the path was found
        if(!invalidCells && path.size() != 0) {
            System.out.println("Path Found!");
            GUI.Main.maze.printPath(path);
            controller.setMaze(GUI.Main.maze.getMaze(), GUI.Main.initialization[0], GUI.Main.initialization[1]);   // Pass the new maze to the GUI
            // Calculate Cost Function
            int cellsCount = path.size();
            int viaCost = GUI.Main.initialization[2];
            int viasCount = calculateNumVias(path);
            int cost = cellsCount + (viasCount * viaCost);
            System.out.println("Cost = " + cost);
            // Get CPU Time
            System.out.println("CPU Time = " + GUI.Main.maze.getCpuTime() + "ns");
        }
        else if(invalidCells) { // Case user entered occupied coordinates
            System.out.println("Invalid cells, check above messages...");
        }
        else {  // Case no Path found
            System.out.println("Path not Found!");
        }


        // make the last path into obstacles then repeat
        if(!invalidCells) {
            int[][] blocks = new int[path.size()][];
            //for (Node n: path) {
            //    System.out.println(n);
            //}
            int i = 0;
            for (Node node : path) {
                blocks[i] = new int[3];
                blocks[i][0] = node.getX();
                blocks[i][1] = node.getY();
                blocks[i][2] = node.getZ();
                i++;
            }
            GUI.Main.maze.setObstacles(blocks);
        }


        controller.setPins(GUI.Main.maze.sourcesList, GUI.Main.maze.targetList);

    }


    /**
     * @param path: The path you want to know the vias number of
     * @return Number of Vias used in the Path
     */
    private static int calculateNumVias(List<Node> path) {
        int lastZ = path.get(0).getZ();
        int vias = 0;

        for (int i = 1; i < path.size(); i++) {
            if(path.get(i).getZ() - lastZ != 0)
                vias++;
            lastZ = path.get(i).getZ();
        }

        return vias;
    }

}
