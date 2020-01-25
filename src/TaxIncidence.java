import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TaxIncidence extends JPanel implements MouseListener {
    private JFrame frame;
    static TaxIncidence taxDemo = new TaxIncidence();
    static SupplyCurve s = new SupplyCurve();
    static DemandCurve d = new DemandCurve();

    JRadioButton supplyButton;
    JRadioButton demandButton;

    ButtonGroup buttonGroup;

    public int x = 500;
    public int y = 500;

    public static final int BORDER_OFFSET = 100;
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 1000;

    public TaxIncidence() {
        frame = new JFrame("Tax Incidence Demonstration");
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setVisible(true);

        supplyButton = new JRadioButton();
        supplyButton.setText("Supply");

        demandButton = new JRadioButton();
        demandButton.setText("Demand");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(supplyButton);
        buttonGroup.add(demandButton);

        this.add(supplyButton);
        this.add(demandButton);


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
        g.setColor(Color.CYAN);
        g.drawLine(s.getX1(), s.getY1(), s.getX2(), s.getY2());
        g.drawString("S", s.getX2() + 5, s.getY2() + 5);
        //demand curve
        g.setColor(Color.RED);
        g.drawLine(d.getX1(), d.getY1(), d.getX2(), d.getY2());
        g.drawString("D", d.getX2() + 5, d.getY2() + 5);

        g.setColor(Color.BLACK);
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
        if (supplyButton.isSelected()) {
            s.setPointsInput(x, y);
        } else if (demandButton.isSelected()) {
            d.setPointsInput(x, y);
        }

        //System.out.println("(" + x + ", " + y + ")");
        //s.setPointsInput(x, y);
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
