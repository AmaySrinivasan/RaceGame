import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class GameViewer extends JFrame implements KeyListener, ActionListener {
    private Image background;
    private Image raceCar;
    private Image obstacle;
    private Game game;
    public int backgroundy1 = 0;
    public int backgroundy2 = -800;
    private Timer timer;
    private final int DELAY = 20;
    public GameViewer() {
        setTitle("Highway Dodge");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        addKeyListener(this);

        background = new ImageIcon(getClass().getResource("/Background.png")).getImage();
        raceCar = new ImageIcon(getClass().getResource("/RaceCar.png")).getImage();
        obstacle = new ImageIcon(getClass().getResource("/obstacle.png")).getImage();

        game = new Game(raceCar, obstacle);
        timer = new Timer(DELAY, this);
        timer.start();
        setVisible(true);
    }
    public void scrollBackground() {
        backgroundy1 += game.getScrollSpeed();
        backgroundy2 += game.getScrollSpeed();
        if (backgroundy1 >= 800) {
            backgroundy1 = backgroundy2 - 800;
        }
        if (backgroundy2 >= 800) {
            backgroundy2 = backgroundy1 - 800;
        }
    }
    public void paint(Graphics g) {
        drawGame(g);
    }
    public void drawGame(Graphics g) {
        g.drawImage(background, 0, backgroundy1, 600, 800, null);
        g.drawImage(background, 0, backgroundy2, 600, 800, null);
        for (Obstacle obstacle: game.getObstacles()) {
            obstacle.draw(g);
        }
        game.getCar().draw(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + game.getScore(), 10, 30);
        g.drawString("Speed: " + game.getScrollSpeed(), 480, 30);

        if (game.isGameOver()) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 800);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 180, 350);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Final Score: " + game.getScore(), 220, 400);
            g.drawString("Press SPACE to restart", 180, 450);
        }
    }
    @Override
    public void actionPerformed (ActionEvent e) {
        game.update();
        scrollBackground();
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (game.isGameOver() && key == KeyEvent.VK_SPACE) {
            game.restart();
            backgroundy1 = 0;
            backgroundy2 = -800;
        } else if (!game.isGameOver()) {
            if (key == KeyEvent.VK_LEFT) {
                game.moveCarLeft();
            } else if (key == KeyEvent.VK_RIGHT) {
                game.moveCarRight();
            } else if (key == KeyEvent.VK_UP) {
                game.increaseSpeed();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main (String[] args) {
        new GameViewer();
    }


}
