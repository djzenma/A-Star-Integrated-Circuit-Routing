package Algorithm;

import java.util.Scanner;

public class Input {

    public static int[] takeGridDimsAndViaCost(Scanner reader) {
        System.out.print("Grid_Width: ");
        int w = reader.nextInt();

        System.out.print("Grid_Height: ");
        int h = reader.nextInt();

        System.out.print("Via_Cost: ");
        int via = reader.nextInt();

        return new int[]{w, h, via};
    }

    public static int[] takeCoordinates(String msg, Scanner reader) {
        System.out.println(msg);
        System.out.print("x: ");
        int x = reader.nextInt();

        System.out.print("y: ");
        int y = reader.nextInt();

        System.out.print("z: ");
        int z = reader.nextInt();

        return new int[]{x, y, z};
    }


}
