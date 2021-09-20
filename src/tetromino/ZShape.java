package tetromino;

import java.awt.*;

public class ZShape extends Tetromino{

    public ZShape() {
        this.coordinates[0] = new int[]{0, 4};
        this.coordinates[1] = new int[]{0, 5};
        this.coordinates[2] = new int[]{1, 5};
        this.coordinates[3] = new int[]{1, 6};
        this.color = Color.RED;

        this.firstRotation = new int[][]{
                {0, 2}, {1, 1}, {0, 0}, {1, -1}
        };
        this.secondRotation = new int[][]{
                {2, 0}, {1, -1}, {0, 0}, {-1, -1}
        };
        this.thirdRotation = new int[][]{
                {0, -2}, {-1, -1}, {0, 0}, {-1, 1}
        };
        this.fourthRotation = new int[][]{
                {-2, 0}, {-1, 1}, {0, 0}, {1, 1}
        };
    }
}
