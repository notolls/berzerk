import javax.swing.*;

public class  GameWindow extends JFrame {

    static int WIDTH = 1350;
    static int HEIGHT = 1080;
    public GameWindow() {

        setTitle("Berzerk");
        setSize(WIDTH, HEIGHT);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        Logic logic = new Logic();
        this.add(logic);
        setVisible(true);

    }
//    public showEndGameScreen() {
//        GameWindow.dispose();
//        JButton restartButton = new JButton("Restart");
//    }



    public static void main(String[] args) {
        new GameWindow();
    }
}
