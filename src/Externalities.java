import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Externalities extends JPanel implements MouseListener, MouseMotionListener {
    private JFrame frame;
    static Externalities demo = new Externalities();

    static Font font = new Font("Helvetica Neue", Font.PLAIN, 15);

    public static int supplyOffset = 100;
    public static int demandOffset = 100;

    public static int equilibriumX;
    public static int equilibriumY;

    public int x = 500;
    public int y = 500;
    /*public static final int BORDER_OFFSET = 150;
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 1000;*/
    public static final int BORDER_OFFSET = SupplyAndDemand.BORDER_OFFSET;
    public static final int WINDOW_WIDTH = SupplyAndDemand.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = SupplyAndDemand.WINDOW_HEIGHT;

    static SupplyCurve MSC = new SupplyCurve();
    static SupplyCurve MPC = new SupplyCurve(BORDER_OFFSET + supplyOffset, BORDER_OFFSET, WINDOW_WIDTH - BORDER_OFFSET, WINDOW_WIDTH - BORDER_OFFSET - supplyOffset);
    static DemandCurve MSB = new DemandCurve();
    static DemandCurve MPB = new DemandCurve(BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET - demandOffset, WINDOW_WIDTH - BORDER_OFFSET - demandOffset, BORDER_OFFSET);

    JLabel curvesLabel;
    JRadioButton MSCButton;
    JRadioButton MPCButton;
    JRadioButton MSBButton;
    JRadioButton MPBButton;

    JLabel modesLabel;
    JRadioButton shiftButton;
    JRadioButton rotateButton;

    ButtonGroup curves;
    ButtonGroup modes;

    JLabel featuresLabel;
    JCheckBox MPCCheckBox;
    JCheckBox MPBCheckBox;

    public Externalities() {
        frame = new JFrame("Externalities Demonstration");
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setVisible(true);

        MSCButton = new JRadioButton("MSC");
        MPCButton = new JRadioButton("MPC");
        MSBButton = new JRadioButton("MSB");
        MPBButton = new JRadioButton("MPB");

        curves = new ButtonGroup();
        curves.add(MSCButton);
        curves.add(MPCButton);
        curves.add(MSBButton);
        curves.add(MPBButton);

        this.add(MSCButton);
        this.add(MPCButton);
        this.add(MSBButton);
        this.add(MPBButton);

        shiftButton = new JRadioButton("Shift");
        rotateButton = new JRadioButton("Rotate");

        modes = new ButtonGroup();
        modes.add(shiftButton);
        modes.add(rotateButton);

        this.add(shiftButton);
        this.add(rotateButton);

        MPCCheckBox = new JCheckBox("Show MPC");
        MPBCheckBox = new JCheckBox("Show MPB");

        this.add(MPCCheckBox);
        this.add(MPBCheckBox);

        frame.add(this);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void drawAxes(Graphics g) {
        g.drawLine(BORDER_OFFSET, BORDER_OFFSET, BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET);
        g.drawLine(BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET, WINDOW_WIDTH - BORDER_OFFSET, WINDOW_HEIGHT - BORDER_OFFSET);

        g.setFont(font);
        g.drawString("Price", 10, WINDOW_HEIGHT / 2);
        g.drawString("Quantity", WINDOW_WIDTH / 2, WINDOW_HEIGHT - 25);
    }

    public void drawCurves(Graphics g) {
        //MSC (supply) curve
        g.setColor(Color.CYAN);
        g.drawLine(MSC.getX1(), WINDOW_HEIGHT - MSC.getY1(), MSC.getX2(), WINDOW_HEIGHT - MSC.getY2());
        g.drawString("MSC", MSC.getX2() + 5, WINDOW_HEIGHT - (MSC.getY2() - 5));
        //MSB (demand) curve
        g.setColor(Color.RED);
        g.drawLine(MSB.getX1(), WINDOW_HEIGHT - MSB.getY1(), MSB.getX2(), WINDOW_HEIGHT - MSB.getY2());
        if (MSB.getY2() == BORDER_OFFSET) {
            g.drawString("MSB", MSB.getX2() - 5, WINDOW_HEIGHT - (BORDER_OFFSET - 15));
        } else {
            g.drawString("MSB", MSB.getX2() + 5, WINDOW_HEIGHT - (MSB.getY2() - 5));
        }

        g.setColor(Color.BLACK);
    }

    public void drawMPC(Graphics g) {
        g.setColor(new Color(137, 235, 213));
        g.drawLine(MPC.getX1(), WINDOW_HEIGHT - MPC.getY1(), MPC.getX2(), WINDOW_HEIGHT - MPC.getY2());
        g.drawString("MPC", MPC.getX2() + 5, WINDOW_HEIGHT - (MPC.getY2() - 5));

    }

    public void drawMPB(Graphics g) {
        g.setColor(new Color(255, 114, 119));
        g.drawLine(MPB.getX1(), WINDOW_HEIGHT - MPB.getY1(), MPB.getX2(), WINDOW_HEIGHT - MPB.getY2());
        if (MPB.getY2() == BORDER_OFFSET) {
            g.drawString("MPB", MPB.getX2() - 5, WINDOW_HEIGHT - (BORDER_OFFSET - 15));
        } else {
            g.drawString("MPB", MPB.getX2() + 5, WINDOW_HEIGHT - (MPB.getY2() - 5));
        }
    }

    public void calculateEquilibrium() {
        double supplySlope = (double) (MSC.getY2() - MSC.getY1()) / (MSC.getX2() - MSC.getX1());
        double demandSlope = (double) (MSB.getY2() - MSB.getY1()) / (MSB.getX2() - MSB.getX1());
        /*double supplySlope = s.getM();
        double demandSlope = d.getM();*/

        double equilibriumX = (supplySlope * MSC.getX() - MSC.getY() - demandSlope * MSB.getX() + MSB.getY()) / (supplySlope - demandSlope);
        Externalities.equilibriumX = (int) equilibriumX;
        // y = m ( x - x1 ) + y1
        double equilibriumY = supplySlope * (equilibriumX - MSC.getX()) + MSC.getY();
        Externalities.equilibriumY = (int) equilibriumY;
    }
    public void drawEquilibrium(Graphics g) {
        g.setColor(new Color(25, 120, 0));
        if (BORDER_OFFSET <= equilibriumX && equilibriumX <= WINDOW_WIDTH - BORDER_OFFSET &&
                BORDER_OFFSET <= equilibriumY && equilibriumY <= WINDOW_HEIGHT - BORDER_OFFSET) {
            int currentX = BORDER_OFFSET;
            int currentY = BORDER_OFFSET;

            int increment = 10;
            while (currentY <= equilibriumY - increment) {
                int start = currentY + increment / 4;
                int end = currentY + 3 * increment / 4;
                g.drawLine(equilibriumX, WINDOW_HEIGHT - start,equilibriumX, WINDOW_HEIGHT - end);
                currentY += increment;
            }

            g.drawString("Q", equilibriumX - 5, WINDOW_HEIGHT - (BORDER_OFFSET - 15));
            //g.drawString(qe.getIterator(), (int) equilibriumX - 5, WINDOW_HEIGHT - BORDER_OFFSET + 15);
            while (currentX <= equilibriumX - increment) {
                int start = currentX + increment / 4;
                int end = currentX + 3 * increment / 4;
                g.drawLine(start, WINDOW_HEIGHT - equilibriumY, end, WINDOW_HEIGHT - equilibriumY);
                currentX += increment;
            }
            g.drawString("P", BORDER_OFFSET - 15, WINDOW_HEIGHT - (equilibriumY - 7));
        }
        g.setColor(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        drawCurves(g);

        if (MPCCheckBox.isSelected()) {
            drawMPC(g);
        } else if (MPBCheckBox.isSelected()) {
            drawMPB(g);
        }

        calculateEquilibrium();
        drawEquilibrium(g);

        repaint();
    }

    public static void main(String[] args) {
        demo.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        updateData(e);
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
        updateData(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void updateData(MouseEvent e) {
        if (BORDER_OFFSET < e.getX() - 8 && e.getX() - 8 < WINDOW_WIDTH - BORDER_OFFSET &&
                BORDER_OFFSET < e.getY() - 31 && e.getY() - 31 < WINDOW_HEIGHT - BORDER_OFFSET) {
            x = e.getX() - 8;
            y = e.getY() - 31;
            y = WINDOW_HEIGHT - y;

            if (shiftButton.isSelected()) {
                if (MSCButton.isSelected()) {
                    MSC.shiftCurve(x, y);
                    MPC.shiftCurve(x, y - supplyOffset);
                } else if (MPCButton.isSelected()) {
                    MSC.shiftCurve(x, y + supplyOffset);
                    MPC.shiftCurve(x, y);
                } else if (MSBButton.isSelected()) {
                    MSB.shiftCurve(x, y);
                    MPB.shiftCurve(x, y - demandOffset);
                } else if (MPBButton.isSelected()) {
                    MSB.shiftCurve(x, y + demandOffset);
                    MPB.shiftCurve(x, y);
                }
            } else if (rotateButton.isSelected()) {
                if (MSCButton.isSelected()) {

                } else if (MPCButton.isSelected()) {

                } else if (MSBButton.isSelected()) {

                } else if (MPBButton.isSelected()) {

                }
            }


            //System.out.println("(" + x + ", " + y + ")");
            //s.setPointsInput(x, y);
            demo.repaint();
        }
    }
}
