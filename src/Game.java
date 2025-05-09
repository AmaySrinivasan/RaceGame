import java.awt.*;
import java.util.ArrayList;

// Highway Dodge by Amay Srinivasan
// Class for all of the backend logic such as the score, speed, collisions, and obstacle spawning
// logic
public class Game {
    private static final int INITIAL_SCROLL_SPEED = 5;
    private static final int INITIAL_OBSTACLE_FREQUENCY = 50;
    private static final int MILESTONE_INTERVAL = 500;
    private static final int MIN_OBSTACLE_FREQUENCY = 20;
    private final Car car;
    private final ArrayList<Obstacle> obstacles;
    private final Image obstacleImage;
    private int score;
    private int scrollSpeed;
    private boolean gameOver;
    private int obstacleDelayer;
    private int obstacleFrequency;
    private int milestone;

    // Constructor for the game with the images
    public Game(Image carImage, Image obstacleImage) {
        this.car = new Car(carImage);
        this.obstacles = new ArrayList<>();
        this.obstacleImage = obstacleImage;
        // Initializes all of the game's variables
        restart();
    }

    // Main, so creates the game window and starts the game
    public static void main(String[] args) {
        new GameViewer();
    }

    // Updates the game every frame, updates the obstacle movement, the collision, the score
    // and the difficulty
    public void update() {
        // Checks if the game is over
        if (gameOver) {
            return;
        }
        // Increases the score every frame, but the faster you are going, the score increases
        // faster. (divides by 5 because I don't want it to scale that much)
        score += 1 + (scrollSpeed / 5);
        // Updates each obstacle
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            // Makes sure the obstacles are moving at the correct difficulty
            obstacle.move(scrollSpeed);
            // Checks for collision
            if (obstacle.collidesWith(car)) {
                // Ends the game if there is a collision
                gameOver = true;
                return;
            }
            // Checks whether an obstacle moves off screen
            if (obstacle.isOffScreen()) {
                // If it has moved off screen, remove it
                obstacles.remove(i);
                // Prevents skipping the next obstacle
                i--;
            }
        }
        // Makes sure the obstacle spawns based on a timed delay
        obstacleDelayer++;
        if (obstacleDelayer >= obstacleFrequency) {
            obstacleDelayer = 0;
            spawnObstacle();
        }
        // Increases difficulty as the score increases (every 500)
        if (score >= milestone && obstacleFrequency > MIN_OBSTACLE_FREQUENCY) {
            // Increases the frequency of obstacle spawning
            obstacleFrequency -= 5;
            // Moves the milestone once it is reached each time
            milestone += MILESTONE_INTERVAL;
        }
    }

    // Spawns in a new obstacle in a random lane
    private void spawnObstacle() {
        // Randomly picks a lane 1-5(actually lanes 0-4)
        int lane = (int) (Math.random() * 5);
        // Creates a new obstacle in that lane
        obstacles.add(new Obstacle(lane, obstacleImage));
    }

    // Returns the car
    public Car getCar() {
        return this.car;
    }

    // Returns the arraylist of all the obstacles
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    // Resets the game for each new play through
    public void restart() {
        obstacles.clear();
        score = 0;
        scrollSpeed = INITIAL_SCROLL_SPEED;
        gameOver = false;
        obstacleDelayer = 0;
        obstacleFrequency = INITIAL_OBSTACLE_FREQUENCY;
        milestone = MILESTONE_INTERVAL;
    }

    // Moves the car left
    public void moveCarLeft() {
        car.moveLeft();
    }

    // Moves the car right
    public void moveCarRight() {
        car.moveRight();
    }

    // Increases speed of the car(really the game/background)
    public void increaseSpeed() {
        scrollSpeed++;
    }

    // Returns a player's score
    public int getScore() {
        return score;
    }

    // Returns the current speed
    public int getScrollSpeed() {
        return scrollSpeed;
    }

    // Checks whether the game is over
    public boolean isGameOver() {
        return gameOver;
    }

}
