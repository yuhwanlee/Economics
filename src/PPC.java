import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PPC extends JPanel implements MouseListener {
    private JFrame frame;
    static PPC ppc = new PPC();

    public static final int BORDER_OFFSET = 100;
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 750;
    public static final int PPC_LENGTH = 450;

    public int x = 500;
    public int y = 500;

    public PPC() {
        frame = new JFrame("PPC Demonstration");
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setVisible(true);

        frame.add(this);
        frame.addMouseListener(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        drawCurve(g);
        drawPointer(g);
        repaint();
    }

    public void drawCurve(Graphics g) { //curve will intersect at (0, 400) and (400, 0)
        /*//REFERENCE LINES FOR WHERE CURVE NEEDS TO BE
        g.setColor(Color.RED);
        g.drawLine(100, 650, 100, 200);
        g.drawLine(100, 650, 550, 650);
        g.setColor(Color.BLACK);*/

        // g.drawArc(100, WINDOW_HEIGHT - 500, 400, 400, 0, 360);
        /*g.drawArc(BORDER_OFFSET - PPC_LENGTH, WINDOW_HEIGHT - (BORDER_OFFSET + PPC_LENGTH),  //TODO: correct arc, but too complex for now
                PPC_LENGTH * 2, PPC_LENGTH * 2, 0, 90);*/
        g.drawLine(BORDER_OFFSET, WINDOW_HEIGHT - (BORDER_OFFSET + PPC_LENGTH),
                BORDER_OFFSET + PPC_LENGTH, WINDOW_HEIGHT - BORDER_OFFSET);
    }

    public void drawAxes(Graphics g) {
        g.drawLine(BORDER_OFFSET, BORDER_OFFSET, BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET);
        g.drawLine(BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET, WINDOW_WIDTH - BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET);

        g.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
        g.drawString("Good 1", 10, WINDOW_HEIGHT / 2);
        g.drawString("Good 2", WINDOW_WIDTH / 2, WINDOW_HEIGHT - 25);
    }

    public void drawPointer(Graphics g) {
        g.setColor(Color.RED);
        for (int i = 0; i <= 1; i++) {
            g.drawLine(x + i, y, x + 5 + i, y + 5);
            g.drawLine(x + i, y, x + 5 + i, y - 5);
            g.drawLine(x + i, y, x - 5 + i, y + 5);
            g.drawLine(x + i, y, x - 5 + i, y - 5);
        }
        g.setColor(Color.BLACK);
    }

    public static void main(String[] args) {
        ppc.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("HERE");
        x = e.getX() - 8;
        y = e.getY() - 31;
        System.out.println("(" + x + ", " + y + ")");
        ppc.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
