package tetromino;

import java.awt.*;

public class SShape extends Tetromino{

    public SShape() {
        this.coordinates[0] = new int[]{1, 4};
        this.coordinates[1] = new int[]{1, 5};
        this.coordinates[2] = new int[]{0, 5};
        this.coordinates[3] = new int[]{0, 6};
        this.color = Color.GREEN;

        this.firstRotation = new int[][]{
                {-1, 1}, {0, 0}, {1, 1}, {2, 0}
        };
        this.secondRotation = new int[][]{
                {1, 1}, {0, 0}, {1, -1}, {0, -2}
        };
        this.thirdRotation = new int[][]{
                {1, -1}, {0, 0}, {-1, -1}, {-2, 0}
        };
        this.fourthRotation = new int[][]{
                {-1, -1}, {0, 0}, {-1, 1}, {0, 2}
        };
    }


}
