import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Highway Dodge by Amay Srinivasan
// The class for the front end and GUI, involving the window, animation, and inputs
public class GameViewer extends JFrame implements KeyListener, ActionListener {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;
    private static final int FONT_SIZE = 20;
    private static final int DELAY = 20;
    private final Image background;
    private final Image raceCar;
    private final Image obstacle;
    private final Game game;
    private final Timer timer;
    public int backgroundy1 = 0;
    public int backgroundy2 = -WINDOW_HEIGHT;

    // Constructor to set up the game window, the images, the game logic, and to have
    // the game loop start
    public GameViewer() {
        // Sets up the Window
        setTitle("Highway Dodge");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        // for the inputs
        addKeyListener(this);
        // Loads in all of the images from resources
        background = new ImageIcon(getClass().getResource("/Background.png")).getImage();
        raceCar = new ImageIcon(getClass().getResource("/RaceCar.png")).getImage();
        obstacle = new ImageIcon(getClass().getResource("/obstacle.png")).getImage();
        // Initializes all the game logic
        game = new Game(raceCar, obstacle);
        // Creates and stats the game timer for the window loop to keep updating
        timer = new Timer(DELAY, this);
        timer.start();
        // Shows the window
        setVisible(true);
    }

    // Scrolls through the background to make it seem like it is moving
    public void scrollBackground() {
        backgroundy1 += game.getScrollSpeed();
        backgroundy2 += game.getScrollSpeed();
        // Wraps the background when it moves off screen
        if (backgroundy1 >= WINDOW_HEIGHT) {
            backgroundy1 = backgroundy2 - WINDOW_HEIGHT;
        }
        // Also wraps the background around when it moves off screen
        if (backgroundy2 >= WINDOW_HEIGHT) {
            backgroundy2 = backgroundy1 - WINDOW_HEIGHT;
        }
    }

    // Paints the game on the window (either the game or gameover screen)
    public void paint(Graphics g) {
        drawGame(g);
    }

    // Actually draws all of the game elements/pieces including the car, obstacles, background
    // score and the gameover screen
    public void drawGame(Graphics g) {
        // Draws the two looping background to make it the illusion of moving effect
        g.drawImage(background, 0, backgroundy1, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        g.drawImage(background, 0, backgroundy2, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        // Draws all of the obstacles
        for (Obstacle obstacle : game.getObstacles()) {
            obstacle.draw(g);
        }
        // Draws the player's car
        game.getCar().draw(g);
        // Draws the player's score and speed
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        g.drawString("Score: " + game.getScore(), 10, 40);
        g.drawString("Speed: " + game.getScrollSpeed(), 480, 40);
        // If the game is over, draws the game over screen
        if (game.isGameOver()) {
            // Draws the game over
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 180, 350);
            // Draws the final score and restart
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Final Score: " + game.getScore(), 220, 400);
            g.drawString("Press SPACE to restart", 180, 450);
        }
    }

    // Called based on the 20ms delay in order to update the game and repaint every 20ms
    @Override
    public void actionPerformed(ActionEvent e) {
        // Updates and repaints game(and scrolls)
        game.update();
        scrollBackground();
        repaint();
    }

    // Overridden method
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Handles all of the input logic
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        // After the game is over, if they press space, it restarts and resets
        if (game.isGameOver() && key == KeyEvent.VK_SPACE) {
            game.restart();
            backgroundy1 = 0;
            backgroundy2 = -WINDOW_HEIGHT;
        }
        // If the game isn't over, they can play using computer input
        else if (!game.isGameOver()) {
            // If they click the left arrow or A, move the car left
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                game.moveCarLeft();
            }
            // If they click the right arrow or D, move the car right
            else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                game.moveCarRight();
            }
            // If they click the up arrow or w, speed the car/game up
            else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                game.increaseSpeed();
            }
        }
    }

    // Overridden method
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
