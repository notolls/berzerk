import java.awt.*;

class Bullet {
    int x, y;
    int speed;
    char direction;

    int bulletSize = 5;

    public Bullet(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = 10;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        if (direction == 'U') y -= speed;
        if (direction == 'D') y += speed;
        if (direction == 'L') x -= speed;
        if (direction == 'R') x += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, bulletSize, bulletSize);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, bulletSize, bulletSize);
    }

    public boolean isOutOfBounds() {
        return x < 0 || x > GameWindow.WIDTH || y < 0 || y > GameWindow.HEIGHT;
    }
}