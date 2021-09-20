package tetromino;

import java.awt.*;

public class TShape extends Tetromino {

    public TShape() {
        this.coordinates[0] = new int[]{0, 5};
        this.coordinates[1] = new int[]{1, 4};
        this.coordinates[2] = new int[]{1, 5};
        this.coordinates[3] = new int[]{1, 6};
        this.color = Color.MAGENTA;

        this.firstRotation = new int[][]{
                {1, 1}, {-1, 1}, {0, 0}, {1, -1}
        };
        this.secondRotation = new int[][]{
                {1, -1}, {1, 1}, {0, 0}, {-1, -1}
        };
        this.thirdRotation = new int[][]{
                {-1, -1}, {1, -1}, {0, 0}, {-1, 1}
        };
        this.fourthRotation = new int[][]{
                {-1, 1}, {-1, -1}, {0, 0}, {1, 1}
        };
    }

}
