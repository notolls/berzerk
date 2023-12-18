import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;

class Player {
    int x, y;
    int speed = 5;
    int size = 50;
    char directiona;
    ArrayList<Bullet> bullets = new ArrayList<>(); // List to store the player's bullets
    int bulletSpeed = 10;
    int bulletSize = 5;
    int score=0;
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
    public void updateBullets() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);

            for (int j = Logic.getEnemies().size() - 1; j >= 0; j--) {
                Enemy enemy = Logic.getEnemies().get(j);
                if (bulletHitsEnemy(bullet, enemy)) {
                    bullets.remove(i);
                    Logic.getEnemies().remove(j);
                    // Add scoring logic
                    break;
                }
            }

            if (Logic.getMap().isWall(bullet.getX(), bullet.getY())) {
                bullets.remove(i);
            }

            if (bullet.isOutOfBounds()) {
                bullets.remove(i);
            }
            bullet.move();
        }
    }

    private boolean bulletHitsEnemy(Bullet bullet, Enemy enemy) {
        return bullet.getBounds().intersects(enemy.getBounds());
    }

    public void move(char direction, Map map) {
        int nextX = x, nextY = y;

        if (direction == 'U') {
            nextY -= speed;
            directiona = 'U';
        }
        if (direction == 'D'){
            nextY += speed;
            directiona = 'D';
        }
        if (direction == 'L'){
            nextX -= speed;
            directiona = 'L';
        }
        if (direction == 'R'){
            nextX += speed;
            directiona = 'R';
        }
        if (map.isEnd(nextX, nextY) ) {
            System.exit(0);

        }

        if (!map.isWall(nextX, nextY) ) {
            x = nextX;
            y = nextY;

        } else{
            System.exit(0);
        }

    }

    public void draw(Graphics g)  throws Exception  {

        BufferedImage PlayerImage = ImageIO.read(new FileInputStream("src/Resource/Player.png"));
        g.drawImage(PlayerImage,x, y, size, size, null);
    }


    public void shoot() {
        Bullet bullet = new Bullet(x + size / 2, y + size / 2, directiona);
        bullets.add(bullet);
    }

//    public void updateBullets() {
//        for (Bullet bullet : bullets) {
//            bullet.move();
//        }
//    }

    public void drawBullets(Graphics g) {
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }
//    private boolean bulletHitsEnemy(Bullet bullet, Enemy enemy) {
//        return bullet.getBounds().intersects(enemy.getBounds());
//    }
}