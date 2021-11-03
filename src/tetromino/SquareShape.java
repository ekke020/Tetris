package tetromino;


import java.awt.*;

public class SquareShape extends Tetromino {

    public SquareShape() {
        this.coordinates[0] = new int[]{1, 5};
        this.coordinates[1] = new int[]{1, 6};
        this.coordinates[2] = new int[]{2, 5};
        this.coordinates[3] = new int[]{2, 6};
        this.color = Color.YELLOW;

        this.firstRotation = new int[][]{
                {0, 0}, {0, 0}, {0, 0}, {0, 0}
        };
        this.secondRotation = new int[][]{
                {0, 0}, {0, 0}, {0, 0}, {0, 0}
        };
        this.thirdRotation = new int[][]{
                {0, 0}, {0, 0}, {0, 0}, {0, 0}
        };
        this.fourthRotation = new int[][]{
                {0, 0}, {0, 0}, {0, 0}, {0, 0}
        };
    }

}
