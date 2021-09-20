package tetromino;


import java.awt.*;
import java.util.ArrayList;

public abstract class Tetromino {

    protected int spinCounter = 0;

    protected int[][] firstRotation;
    protected int[][] secondRotation;
    protected int[][] thirdRotation;
    protected int[][] fourthRotation;

    protected int[][] coordinates = new int[4][2];
    protected Color color;

    public Color getColor() {
        return color;
    }

    public void setCoordinates(int[][] coordinates) {
        this.coordinates = coordinates;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public void incrementSpinCounter() {
        spinCounter++;
        if (spinCounter == 4) {
            spinCounter = 0;
        }
    }

    public static ArrayList<Tetromino> reloadTetrominos() {
        return new ArrayList<>() {
            {
                add(new ZShape());
                add(new ZShape());
                add(new SShape());
                add(new SShape());
                add(new TShape());
                add(new TShape());
                add(new LShape());
                add(new LShape());
                add(new ReversedLShape());
                add(new ReversedLShape());
                add(new LineShape());
                add(new LineShape());
                add(new SquareShape());
                add(new SquareShape());
            }
        };
    }

    public int[][] getSpinCoordinates() {
        return switch (this.spinCounter) {
            case 0 -> getRotatedCoordinates(this.firstRotation);
            case 1 -> getRotatedCoordinates(this.secondRotation);
            case 2 -> getRotatedCoordinates(this.thirdRotation);
            case 3 -> getRotatedCoordinates(this.fourthRotation);
            default -> null;
        };
    }

    private int[][] getRotatedCoordinates(int[][] positionUpdate) {
        int[][] rotatedCoordinates = new int[4][2];

        for (int i = 0; i < this.coordinates.length; i++) {
            int rowCord = this.coordinates[i][0];
            int colCord = this.coordinates[i][1];
            int rowIncrement = positionUpdate[i][0];
            int colIncrement = positionUpdate[i][1];
            rotatedCoordinates[i][0] = rowCord + rowIncrement;
            rotatedCoordinates[i][1] = colCord + colIncrement;
        }
        return rotatedCoordinates;
    }
}
