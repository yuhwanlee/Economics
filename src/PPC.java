import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PPC extends JPanel implements MouseListener, MouseMotionListener {
    private JFrame frame;
    static PPC ppc = new PPC();

    public static final int BORDER_OFFSET = 100;
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 1000;
    public static final int PPC_LENGTH = 600;

    public int x = 500;
    public int y = 500;

    public PPC() {
        frame = new JFrame("PPC Demonstration");
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setVisible(true);

        frame.add(this);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void drawCurve(Graphics g) {
        g.drawArc(BORDER_OFFSET - PPC_LENGTH, WINDOW_HEIGHT - (BORDER_OFFSET + PPC_LENGTH),  //TODO: correct arc, but too complex for now
                PPC_LENGTH * 2, PPC_LENGTH * 2, 0, 90);
        /*g.drawLine(BORDER_OFFSET, WINDOW_HEIGHT - (BORDER_OFFSET + PPC_LENGTH),
                BORDER_OFFSET + PPC_LENGTH, WINDOW_HEIGHT - BORDER_OFFSET);*/
    }

    public void drawAxes(Graphics g) {
        g.drawLine(BORDER_OFFSET, BORDER_OFFSET, BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET);
        g.drawLine(BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET, WINDOW_WIDTH - BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET);

        g.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
        g.drawString("Good 1", 10, WINDOW_HEIGHT / 2);
        g.drawString("Good 2", WINDOW_WIDTH / 2, WINDOW_HEIGHT - 25);
    }

    public void drawPointer(Graphics g) {
        int x = this.x;
        int y = WINDOW_HEIGHT - this.y;

        if (BORDER_OFFSET <= x && x <= WINDOW_WIDTH - BORDER_OFFSET
            && BORDER_OFFSET <= y && y <= WINDOW_HEIGHT - BORDER_OFFSET) {
            double theta;
            //calculate the angle of the point clicked (arctan) & select the point on the circle with that angle
            x -= BORDER_OFFSET;
            y -= BORDER_OFFSET;
            if (x > 0) {  //if theta != 90
                theta = Math.atan((double) y / x);
            } else {
                theta = Math.PI / 2;
            }
            /*System.out.println("x: " + x);
            System.out.println("y: " + y);
            System.out.println("theta: " + theta * 180 / Math.PI);*/
            x = (int) (PPC_LENGTH * Math.cos(theta) + BORDER_OFFSET);
            y = (int) (PPC_LENGTH * Math.sin(theta) + BORDER_OFFSET);

            g.setColor(Color.RED);
            for (int i = 0; i <= 1; i++) {
                g.drawLine(x + i, WINDOW_HEIGHT - y, x + 5 + i, WINDOW_HEIGHT - y + 5);
                g.drawLine(x + i, WINDOW_HEIGHT - y, x + 5 + i, WINDOW_HEIGHT - y - 5);
                g.drawLine(x + i, WINDOW_HEIGHT - y, x - 5 + i, WINDOW_HEIGHT - y + 5);
                g.drawLine(x + i, WINDOW_HEIGHT - y, x - 5 + i, WINDOW_HEIGHT - y - 5);
            }
            g.setColor(Color.BLACK);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        drawCurve(g);
        drawPointer(g);
        repaint();
    }

    public static void main(String[] args) {
        ppc.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        x = e.getX() - 8;
        y = e.getY() - 31;
        //to clip the value
        x = Math.max(BORDER_OFFSET, Math.min(WINDOW_WIDTH - BORDER_OFFSET, x));
        y = Math.max(BORDER_OFFSET, Math.min(WINDOW_WIDTH - BORDER_OFFSET, y));

        //System.out.println("(" + x + ", " + y + ")");
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

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX() - 8;
        y = e.getY() - 31;
        //to clip the value
        x = Math.max(BORDER_OFFSET, Math.min(WINDOW_WIDTH - BORDER_OFFSET, x));
        y = Math.max(BORDER_OFFSET, Math.min(WINDOW_WIDTH - BORDER_OFFSET, y));
        //System.out.println("(" + x + ", " + y + ")");
        ppc.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
