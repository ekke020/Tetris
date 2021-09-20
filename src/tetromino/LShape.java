package tetromino;

import java.awt.*;

public class LShape extends Tetromino {

    public LShape() {
        this.coordinates[0] = new int[]{0, 6};
        this.coordinates[1] = new int[]{1, 6};
        this.coordinates[2] = new int[]{1, 5};
        this.coordinates[3] = new int[]{1, 4};
        this.color = Color.ORANGE;
        this.firstRotation = new int[][]{
                {2, 0}, {1, -1}, {0, 0}, {-1, 1}
        };
        this.secondRotation = new int[][]{
                {0, -2}, {-1, -1}, {0, 0}, {1, 1}
        };
        this.thirdRotation = new int[][]{
                {-2, 0}, {-1, 1}, {0, 0}, {1, -1}
        };
        this.fourthRotation = new int[][]{
                {0, 2}, {1, 1}, {0, 0}, {-1, -1}
        };
    }
}
