import java.util.*;

public class GridView {
    public static void main(String[] args) {



    System.out.println("A B C - create a new grid with Width A, Height B and Character C to fill grid");

    Scanner scan = new Scanner(System.in);
    int width = scan.nextInt();
    int height = scan.nextInt();

    char c = scan.next().charAt(0);
    char [][] grid = new char [width][height];

for (int col = 0; col < height - 1; col++) {
        for (int row = 0; row < width - 1; row++) {
            grid[col][row] = c;
            System.out.print(grid[col][row]);
        }
        System.out.print("\n");
    }

    }
}
