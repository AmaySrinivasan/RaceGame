import java.awt.*;
public class Car {
    private int lane = 2;
    private final int y = 600;
    private final int[] laneX = {60, 160, 260, 360, 460};
    private Image image;
    private int width = 60;
    private int height = 100;

    public Car(Image image) {
        this.image = image;
    }

    public void draw(Graphics g) {
        g.drawImage(image, laneX[lane], y, width, height, null);
    }

    public void moveLeft() {
        if (lane > 0) lane--;
    }

    public void moveRight() {
        if (lane < 4) lane++;
    }
    public int getX() {
        return laneX[lane];
    }
    public int getY () {
        return y;
    }
}