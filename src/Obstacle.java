import java.awt.*;

// Highway Dodge by Amay Srinivasan
// Class for an obstacle that moves down the screen(so that it looks like we are coming at
// the obstacle) Spawns in one of the five lanes and checks for collision
public class Obstacle {
    private static final int OBSTACLE_WIDTH = 60;
    private static final int OBSTACLE_HEIGHT = 100;
    private static final int SCREEN_HEIGHT = 800;
    // Predefined x values for the lanes
    private final int[] laneX = {50, 160, 260, 360, 485};
    // The lane number
    private final int lane;
    // The y value/position
    private int y;
    // The obstacle image
    private final Image image;

    // Constructor to construct the obstacle image(from resources) in the proper lane
    public Obstacle(int lane, Image image) {
        this.lane = lane;
        this.image = image;
        // Starts the obstacle from just where it begins to be visible
        this.y = -OBSTACLE_HEIGHT;
    }

    // Moves the obstacle down the screen at the proper speed
    public void move(int speed) {
        y += speed;
    }

    // Checks whether the obstacle has moved off the screen (true if off, false otherwise)
    public boolean isOffScreen() {
        return y > SCREEN_HEIGHT;
    }

    // Draws the obstacles on the screen at the proper values and dimensions
    public void draw(Graphics g) {
        g.drawImage(image, laneX[lane], y, OBSTACLE_WIDTH, OBSTACLE_HEIGHT, null);
    }

    // Checks whether the obstacle collides with the car
    public boolean collidesWith(Car car) {
        int carX = car.getX();
        int carY = car.getY();
        // Returns true if they intesect, and false otherwise
        return laneX[lane] < carX + OBSTACLE_WIDTH && laneX[lane] + OBSTACLE_WIDTH > carX && y < carY + OBSTACLE_HEIGHT && y + OBSTACLE_HEIGHT > carY;
    }
}
