import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Logic extends JPanel implements ActionListener, KeyListener {
    Timer gameTimer;
    Player player;
     static ArrayList<Enemy> enemies = new ArrayList<>();
     static Map map;
     boolean gameOver = false;
    static ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    public static Map getMap() {
        return map;
    }
    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Logic() {
        player = new Player(ThreadLocalRandom.current().nextInt(51,GameWindow.WIDTH-100), ThreadLocalRandom.current().nextInt(51,GameWindow.HEIGHT-100));   // Start player at center

        createEnemies(10);
        gameTimer = new Timer(10, this);
        map = new Map();
        gameTimer.start();

        this.addKeyListener(this);
        this.setFocusable(true);
    }

    public void createEnemies(int numEnemies) {
        for (int i = 0; i < numEnemies; i++) {
            int x = ThreadLocalRandom.current().nextInt(51,GameWindow.WIDTH-100);
            int y = ThreadLocalRandom.current().nextInt(51,GameWindow.HEIGHT-100);
            enemies.add(new Enemy(x, y));
        }
    }
    public void updateEnemies() {
        for (Enemy enemy : enemies) {
            enemy.chase(player, map);
        }
    }

    public void drawEnemies(Graphics g) throws Exception {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        map.draw(g);
        try {
            player.draw(g);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            drawEnemies(g);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        player.drawBullets(g);
        drawEnemyBullets(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void drawEnemyBullets(Graphics g) {
        for (EnemyBullet bullet : enemyBullets) {
            bullet.draw(g);
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            updateEnemies();
            for (Enemy enemy : enemies) {
                if (player.getBounds().intersects(enemy.getBounds())) {
                    gameOver = true;
                }
            }

            player.updateBullets();
            updateEnemyBullets();
            for (EnemyBullet bullet : enemyBullets)
            {
                if(player.getBounds().intersects(bullet.getBounds())) {
                    gameOver = true;
                }
            }
            repaint();
        }else {
            System.exit(0);
        }
    }

    public void updateEnemyBullets() {
        for (int i = enemyBullets.size() - 1; i >= 0; i--) {
            EnemyBullet bullet = enemyBullets.get(i);
            if (Logic.getMap().isWall(bullet.getX(), bullet.getY())) {
                enemyBullets.remove(i);
            }
            bullet.move();
            if (bullet.isOutOfBounds()) {
                enemyBullets.remove(i);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) player.move('U', map);
        if (e.getKeyCode() == KeyEvent.VK_DOWN) player.move('D', map);
        if (e.getKeyCode() == KeyEvent.VK_LEFT) player.move('L', map);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.move('R', map);
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.shoot();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}