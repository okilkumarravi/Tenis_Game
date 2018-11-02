package Games;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Game extends JPanel {

    Ball ball = new Ball(this);
    pole pole = new pole(this);
    int i=0,t=0;
    public Game() {
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
                pole.keyReleased(e);
            }
            public void keyPressed(KeyEvent e) {
                pole.keyPressed(e);
            }
        });
        setFocusable(true);
    }

    private void move(){
        ball.move();
        pole.move();
    }
    public void paint(Graphics g) {
      super.paint(g);
        ball.paint(g);
        pole.paint(g);
        i++;
        t=i/10;
        String s;
        s=Integer.toString(t);
        s="your Score: " +s;
        g.drawString(s,10,10);
    }
    public void gameOver() {

        String s=Integer.toString(t);
        s="               Game over\n         your Score is-  "+s+"\n               ThankYou";
        JOptionPane.showMessageDialog(this, s, "Game Over", JOptionPane.YES_NO_OPTION);
       System.exit(ABORT);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Mini Tennis");
        Game game = new Game();
        frame.add(game);
        frame.setBounds(100,100,300, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Object[] options = {"LOW",
                "MEDIUM",
                "HIGH"};
        int t = JOptionPane.showOptionDialog(frame,
                "CHOSE"
                        + "YOUR",
                "LEVEL",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
        System.out.println(t);
        if(t==1)
        t=5;
        if(t==2)
        t=9;
        System.out.println(t);
        int i=0;
        while (true) {

            i++;
            i=i/1000;
            game.move();
            game.repaint();
            Thread.sleep(10-t);
        }
    }
}
class Ball {
     int DIAMETER = 20;
    int x = 0;
    int y = 0;
    int xa = 1;
    int ya = 1;
    private Game game;

    public Ball(Game game) {
        this.game= game;
    }

    void move() {
        if (x + xa < 0)
            xa = 1;
        if (x + xa > game.getWidth() - DIAMETER)
            xa = -1;
        if (y + ya < 0)
            ya = 1;
        if (y + ya > game.getHeight() - DIAMETER)
            game.gameOver();
        if (collision()){
            ya = -1;
            y = game.pole.getTopY() - DIAMETER;
        }
        x = x + xa;
        y = y + ya;
    }

    private boolean collision() {
        return game.pole.getBounds().intersects(getBounds());
    }

    public void paint(Graphics g) {
        g.fillOval(x, y, DIAMETER, DIAMETER);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }
}
class pole {
    int Y = 330;
    int WIDTH = 60;
    int HEIGHT = 10;
    int x = 0;
    int xa = 0;
    private Game game;

    public pole(Game game) {
        this.game = game;
    }

    public void move() {
        if (x + xa > 0 && x + xa < game.getWidth() - WIDTH)
            x = x + xa;
    }
    public void paint(Graphics g) {
        g.fillRect(x, Y, WIDTH, HEIGHT);
    }

    public void keyReleased(KeyEvent e) {
        xa = 0;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            xa = -2;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            xa = 2;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, Y, WIDTH, HEIGHT);
    }

    public int getTopY() {
        return Y;
    }
}
