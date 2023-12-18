import java.awt.*;
import java.util.ArrayList;

class EnemyBullet {
    int x;
    int y;
    int speed;
    char direction;
    int bulletSize=10;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public EnemyBullet(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;


        speed = 2;
    }

    public void move() {
        if (direction == 'U') y -= speed;
        if (direction == 'D') y += speed;
        if (direction == 'L') x -= speed;
        if (direction == 'R') x += speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, bulletSize, bulletSize);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, bulletSize, bulletSize);
    }
    public boolean isOutOfBounds() {
        return x < 0 || x > GameWindow.WIDTH || y < 0 || y > GameWindow.HEIGHT;
    }
}