import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TaxIncidence extends JPanel implements MouseListener {
    private JFrame frame;
    static TaxIncidence taxDemo = new TaxIncidence();
    static SupplyCurve s = new SupplyCurve();

    public int x = 500;
    public int y = 500;

    public static final int BORDER_OFFSET = 100;
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 750;

    public TaxIncidence() {
        frame = new JFrame("Tax Incidence Demonstration");
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setVisible(true);

        frame.add(this);
        frame.addMouseListener(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void drawAxes(Graphics g) {
        g.drawLine(BORDER_OFFSET, BORDER_OFFSET, BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET);
        g.drawLine(BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET, WINDOW_WIDTH - BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET);

        g.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
        g.drawString("Price", 10, WINDOW_HEIGHT / 2);
        g.drawString("Quantity", WINDOW_WIDTH / 2, WINDOW_HEIGHT - 25);
    }

    public void drawCurves(Graphics g) {
        //supply curve
        g.drawLine(s.getX1(), s.getY1(), s.getX2(), s.getY2());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        drawCurves(g);
        repaint();
    }

    public static void main(String[] args) {
        taxDemo.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX() - 8;
        y = e.getY() - 31;
        //System.out.println("(" + x + ", " + y + ")");
        s.setPointsInput(x, y);
        taxDemo.repaint();
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