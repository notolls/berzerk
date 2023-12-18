import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.concurrent.ThreadLocalRandom;

class Enemy {
    int x, y;
    int speed = 3;
    int size = 50;
    int chasetime = 0;
    char directiona = 'U' ;
    private int shootCooldown = ThreadLocalRandom.current().nextInt(60,400);
    int chaseCooldown = ThreadLocalRandom.current().nextInt(5,20);
    private int currentCooldown = 0;

    public void shoot() {
        if (currentCooldown == 0) {
            EnemyBullet bullet = new EnemyBullet(x, y, directiona);
            Logic.enemyBullets.add(bullet);
            currentCooldown = shootCooldown; // Reset the cooldown
        } else {
            currentCooldown--;
        }
    }


    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void chase(Player player, Map map) {
        shoot();
        chasetime++;
        if(chasetime%chaseCooldown==0) {
            int nextX = x, nextY = y;
            if (x < player.x){
                nextX += speed;
                directiona = 'R';
            }
            if (x > player.x) {
                nextX -= speed;
                directiona = 'L';
            }
            if (y < player.y) {
                nextY += speed;
                directiona = 'D';
            }
            if (y > player.y) {
                nextY -= speed;
                directiona = 'U';
            }
            if (!map.isWall(nextX, nextY)) {
                x = nextX;
                y = nextY;
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
    public void draw(Graphics g) throws Exception {
        BufferedImage Enemyimage = ImageIO.read(new FileInputStream("src/Resource/berzerkenemy.png"));

        g.drawImage(Enemyimage,x, y, size, size, null);
    }
}