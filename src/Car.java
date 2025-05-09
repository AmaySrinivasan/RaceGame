// Highway Dodge by Amay Srinivasan

import java.awt.*;

// Represent's a player's car in the game. The car can move left or right between five lanes
// and always stays at a fixed y-value
public class Car {
    private static final int INITIAL_LANE = 2;
    private static final int CAR_WIDTH = 60;
    private static final int CAR_HEIGHT = 100;
    private static final int Y_POSITION = 600;
    // Pre-defined x-values for the lanes
    private final int[] laneX = {50, 160, 260, 360, 485};
    // The car image
    private final Image image;
    // Current lane of the car
    private int lane;

    // Constructer to construct car image using the given car image (From resources)
    public Car(Image image) {
        this.image = image;
        // Car starts in the middle lane
        this.lane = INITIAL_LANE;
    }

    // Draws the car on the window at the proper lane with the dimensions of a car
    public void draw(Graphics g) {
        g.drawImage(image, laneX[lane], Y_POSITION, CAR_WIDTH, CAR_HEIGHT, null);
    }

    // If possible, moves the car left one lane
    public void moveLeft() {
        // Makes sure the car doesn't go off screen
        if (lane > 0) {
            lane--;
        }
    }

    // If possible, moves the car right one lane
    public void moveRight() {
        // Makes sure the car doesn't go off screen
        if (lane < 4) {
            lane++;
        }
    }

    // Returns the current x value of the car
    public int getX() {
        return laneX[lane];
    }

    // Returns the constant y value of the car
    public int getY() {
        return Y_POSITION;
    }
}