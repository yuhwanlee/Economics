import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class SupplyAndDemand extends JPanel implements MouseListener, MouseMotionListener {
    private JFrame frame;
    static SupplyAndDemand demo = new SupplyAndDemand();
    static SupplyCurve s = new SupplyCurve();
    static DemandCurve d = new DemandCurve();

    JRadioButton supplyButton;
    JRadioButton demandButton;
    JRadioButton priceFloorButton;
    JRadioButton priceCeilingButton;

    JRadioButton shiftButton;
    JRadioButton rotateButton;

    ButtonGroup curves;
    ButtonGroup modes;

    JCheckBox surplusCheckBox;
    JCheckBox DWLCheckBox;
    JCheckBox showPriceFloor;
    JCheckBox showPriceCeiling;


    public int priceFloor = WINDOW_HEIGHT / 2;
    public int priceCeiling = WINDOW_HEIGHT / 2;

    static Font font = new Font("Helvetica Neue", Font.PLAIN, 15);

    public int x = 500;
    public int y = 500;

    public static int equilibriumX;
    public static int equilibriumY;
    public static final int BORDER_OFFSET = 150;
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 1000;

    public SupplyAndDemand() {
        frame = new JFrame("Supply and Demand Demonstration");
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setVisible(true);

        supplyButton = new JRadioButton();
        supplyButton.setText("Supply");

        demandButton = new JRadioButton();
        demandButton.setText("Demand");

        priceFloorButton = new JRadioButton();
        priceFloorButton.setText("Price Floor");
        priceCeilingButton = new JRadioButton("Price Ceiling");

        shiftButton = new JRadioButton();
        shiftButton.setText("Shift");

        rotateButton = new JRadioButton();
        rotateButton.setText("Rotate");


        curves = new ButtonGroup();
        curves.add(supplyButton);
        curves.add(demandButton);
        curves.add(priceFloorButton);
        curves.add(priceCeilingButton);

        this.add(supplyButton);
        this.add(demandButton);
        this.add(priceFloorButton);
        this.add(priceCeilingButton);

        modes = new ButtonGroup();
        modes.add(shiftButton);
        modes.add(rotateButton);

        this.add(shiftButton);
        this.add(rotateButton);


        surplusCheckBox = new JCheckBox("Surplus");
        this.add(surplusCheckBox);

        DWLCheckBox = new JCheckBox("DWL");
        this.add(DWLCheckBox);

        showPriceFloor = new JCheckBox("Show Price Floor");
        this.add(showPriceFloor);

        showPriceCeiling = new JCheckBox("Show Price Ceiling");
        this.add(showPriceCeiling);

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
        //supply curve
        g.setColor(Color.CYAN);
        g.drawLine(s.getX1(), WINDOW_HEIGHT - s.getY1(), s.getX2(), WINDOW_HEIGHT - s.getY2());
        g.drawString("S", s.getX2() + 5, WINDOW_HEIGHT - (s.getY2() + 5));
        //demand curve
        g.setColor(Color.RED);
        g.drawLine(d.getX1(), WINDOW_HEIGHT - d.getY1(), d.getX2(), WINDOW_HEIGHT - d.getY2());
        g.drawString("D", d.getX2() + 5, WINDOW_HEIGHT - (d.getY2() + 5));

        g.setColor(Color.BLACK);
    }
    public void calculateEquilibrium() {
        double supplySlope = (double) (s.getY2() - s.getY1()) / (s.getX2() - s.getX1());
        double demandSlope = (double) (d.getY2() - d.getY1()) / (d.getX2() - d.getX1());
        /*double supplySlope = s.getM();
        double demandSlope = d.getM();*/

        double equilibriumX = (supplySlope * s.getX() - s.getY() - demandSlope * d.getX() + d.getY()) / (supplySlope - demandSlope);
        SupplyAndDemand.equilibriumX = (int) equilibriumX;
        // y = m ( x - x1 ) + y1
        double equilibriumY = supplySlope * (equilibriumX - s.getX()) + s.getY();
        SupplyAndDemand.equilibriumY = (int) equilibriumY;
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
            /*AttributedString qe = new AttributedString("Qe");
            AttributedString pe = new AttributedString("Pe");
            qe.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 1, 2);
            pe.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 1, 2);*/

            g.drawString("Q", equilibriumX - 5, WINDOW_HEIGHT - (BORDER_OFFSET - 15));
            //g.drawString(qe.getIterator(), (int) equilibriumX - 5, WINDOW_HEIGHT - BORDER_OFFSET + 15);
            while (currentX <= equilibriumX - increment) {
                int start = currentX + increment / 4;
                int end = currentX + 3 * increment / 4;
                g.drawLine(start, WINDOW_HEIGHT - equilibriumY, end, WINDOW_HEIGHT - equilibriumY);
                currentX += increment;
            }
            g.drawString("P", BORDER_OFFSET - 15, WINDOW_HEIGHT - (equilibriumY - 7));
            //g.drawString(pe.getIterator(), BORDER_OFFSET - 15, WINDOW_HEIGHT - (int) equilibriumY + 7);

        }
        //System.out.println("(" + equilibriumX + ", " + equilibriumY + ")");
        g.setColor(Color.BLACK);
    }
    public void drawProducerSurplus(Graphics g) {
        //left bound of producer surplus is the end of the leftmost curve
        int leftBound = Math.max(s.getX1(), d.getX1());
        //left corner, left corner up to equilibrium, equilibrium
        g.setColor(new Color(10, 145, 130));

        int xLimit;
        int yLimit;
        if (showPriceFloor.isSelected()) {
            xLimit = d.getXValue(priceFloor);
            yLimit = priceFloor;
        } else if (showPriceCeiling.isSelected()) {
            xLimit = s.getXValue(priceCeiling);
            yLimit = priceCeiling;
        } else {
            xLimit = equilibriumX;
            yLimit = equilibriumY;
        }

        if (showPriceFloor.isSelected()) {
            int[] xValues = new int[]{leftBound, leftBound, xLimit, xLimit};
            int[] yValues = new int[]{WINDOW_HEIGHT - s.getYValue(leftBound), WINDOW_HEIGHT - yLimit, WINDOW_HEIGHT - yLimit, WINDOW_HEIGHT - s.getYValue(xLimit)};
            g.drawPolygon(xValues, yValues, 4);
        } else { // price ceiling and no pf/pc are drawn the same
            int[] xValues = new int[]{leftBound, leftBound, xLimit};
            int[] yValues = new int[]{WINDOW_HEIGHT - s.getYValue(leftBound), WINDOW_HEIGHT - yLimit, WINDOW_HEIGHT - yLimit};
            g.drawPolygon(xValues, yValues, 3);
        }

        double lineSlope = -1;
        int currentX = leftBound;
        int currentY = s.getYValue(leftBound);

        double supplySlope = s.getM();
        int increment = 20;

        while (currentX < xLimit || currentY < yLimit) { //drawing diagonal lines
            double intersectionX = (supplySlope * s.getX() - s.getY() - lineSlope * currentX + currentY) / (supplySlope - lineSlope);
            double intersectionY;
            //if the line intersects supply at a point greater than the horizontal limit, have it hit the vertical line
            if (intersectionX > xLimit) { // x = d.getXVal(pf)
                intersectionX = xLimit;
                intersectionY = lineSlope * (intersectionX - currentX) + currentY;
            } else { //if the line intersects supply before the limit or if PS is a triangle
                intersectionY = supplySlope * (intersectionX - s.getX()) + s.getY();
            }
            // y = m ( x - x1 ) + y1
            g.drawLine(currentX, WINDOW_HEIGHT - currentY, (int) intersectionX, WINDOW_HEIGHT - (int) intersectionY);
            currentY += increment;
            if (currentY >= yLimit) { //the x position gets what "hangs over" the y positions
                currentX += currentY - yLimit;
                currentY = yLimit;
            }
        }

        if (showPriceFloor.isSelected()) {
            if (s.getM() > 0 || priceFloor > s.getY1()) { //or statement: if s is horizontal but there is still surplus
                //drawing the "PS" label, located center of the trapezoid (intersection of drawing medians -> medians)
                g.setColor(new Color(10, 95, 75));

                //coordinates of left median
                int lx = leftBound;
                int ly = (priceFloor + s.getYValue(leftBound)) / 2;
                //coordinates of right median
                int rx = d.getXValue(priceFloor);
                int ry = (priceFloor + s.getYValue(d.getXValue(priceFloor))) / 2;

                //horizontal line slope
                double hm = (double) (ry - ly) / (rx - lx);
                //x value of top median (equation used: x = result)
                int xTop = (leftBound + d.getXValue(priceFloor)) / 2;

                double intersectionX = xTop;
                // y = m ( x - x1 ) + y1
                double intersectionY = hm * (intersectionX - rx) + ry;
                g.drawString("PS", (int) intersectionX - 8, WINDOW_HEIGHT - ((int) intersectionY - 5));

            }
        } else {
            if (s.getM() > 0) {
                //drawing the "PS" label, located in the centroid of the triangle
                g.setColor(new Color(10, 95, 75));
                //using the middles of leftmost and topmost borders
                //first (left border) line -> top right corner
                int lx = leftBound;
                int ly = (yLimit + s.getYValue(leftBound)) / 2;
                double lm = (double) (yLimit - ly) / (xLimit - lx);
                //second (top border) line -> bottom left corner
                int tx = (leftBound + xLimit) / 2;
                int ty = yLimit;

                double tm = (double) (s.getYValue(leftBound) - ty) / (leftBound - tx);

                double intersectionX = (lm * lx - ly - tm * tx + ty) / (lm - tm);
                // y = m ( x - x1 ) + y1
                double intersectionY = lm * (intersectionX - lx) + ly;
                g.drawString("PS", (int) intersectionX - 8, WINDOW_HEIGHT - ((int) intersectionY - 5));

            }
        }
        g.setColor(Color.BLACK);
    }

    public void drawConsumerSurplus(Graphics g) {
       /* if (!showPriceFloor.isSelected()) {*/

        //left bound of consumer surplus is the end of the leftmost curve
        int leftBound = Math.max(s.getX1(), d.getX1());
        g.setColor(new Color(210, 80, 0));

        double lineSlope = 1;
        int currentX = leftBound;
        int currentY = d.getYValue(leftBound);

        double demandSlope = d.getM();
        int increment = 20;

        int xLimit;
        int yLimit;
        if (showPriceFloor.isSelected()) {
            xLimit = d.getXValue(priceFloor);
            yLimit = priceFloor;
        } else if (showPriceCeiling.isSelected()) {
            xLimit = s.getXValue(priceCeiling);
            yLimit = priceCeiling;
        } else {
            xLimit = equilibriumX;
            yLimit = equilibriumY;
        }

        if (showPriceCeiling.isSelected()) { //top left, bottom left, bottom right, top right
            int[] xValues = new int[]{leftBound, leftBound, xLimit, xLimit};
            int[] yValues = new int[]{WINDOW_HEIGHT - d.getYValue(leftBound), WINDOW_HEIGHT - yLimit, WINDOW_HEIGHT - yLimit, WINDOW_HEIGHT - d.getYValue(xLimit)};
            g.drawPolygon(xValues, yValues, 4);
        } else {
            //left corner, left corner down to equilibrium, equilibrium
            int[] xValues = new int[]{leftBound, leftBound, xLimit};
            int[] yValues = new int[]{WINDOW_HEIGHT - d.getYValue(leftBound), WINDOW_HEIGHT - yLimit, WINDOW_HEIGHT - yLimit};
            g.drawPolygon(xValues, yValues, 3);
        }


        while (currentX < xLimit || currentY > yLimit) {
            double intersectionX = (demandSlope * d.getX() - d.getY() - lineSlope * currentX + currentY) / (demandSlope - lineSlope);
            double intersectionY;
            //if the line intersects supply at a point greater than the horizontal limit, have it hit the vertical line
            if (intersectionX > xLimit) { // x = d.getXVal(pf)
                intersectionX = xLimit;
                intersectionY = lineSlope * (intersectionX - currentX) + currentY;
            } else { //if the line intersects supply before the limit or if PS is a triangle
                intersectionY = demandSlope * (intersectionX - d.getX()) + d.getY();
            }
            // y = m ( x - x1 ) + y1
            g.drawLine(currentX, WINDOW_HEIGHT - currentY, (int) intersectionX, WINDOW_HEIGHT - (int) intersectionY);
            currentY -= increment;
            if (currentY <= yLimit) { //the x position gets what "hangs over" the y positions
                currentX += yLimit - currentY;
                currentY = yLimit;
            }
        }

        g.setColor(new Color(155, 65, 0));

        if (showPriceCeiling.isSelected()) {
            if (d.getM() < 0 || priceCeiling < d.getY1()) { //or statement: if s is horizontal but there is still surplus
                //drawing the "PS" label, located center of the trapezoid (intersection of drawing medians -> medians)

                //coordinates of left median
                int lx = leftBound;
                int ly = (yLimit + d.getYValue(leftBound)) / 2;
                //coordinates of right median
                int rx = xLimit;
                int ry = (yLimit + d.getYValue(xLimit)) / 2;

                //horizontal line slope
                double hm = (double) (ry - ly) / (rx - lx);
                //x value of top median (equation used: x = result)
                int xBot = (leftBound + xLimit) / 2;

                double intersectionX = xBot;
                // y = m ( x - x1 ) + y1
                double intersectionY = hm * (intersectionX - rx) + ry;
                g.drawString("CS", (int) intersectionX - 8, WINDOW_HEIGHT - ((int) intersectionY - 5));

            }
        } else {
            if (d.getM() < 0) {
                //drawing the "CS" label, located in the centroid of the triangle
                //using the middles of leftmost and bottommost borders
                //first (left border) line
                int lx = leftBound;
                int ly = (yLimit + d.getYValue(leftBound)) / 2;
                double lm = (double) (yLimit - ly) / (xLimit - lx);
                //second (bottom border) line
                int bx = (leftBound + xLimit) / 2;
                int by = yLimit;
                double bm = (double) (d.getYValue(leftBound) - by) / (leftBound - bx);

                double intersectionX = (lm * lx - ly - bm * bx + by) / (lm - bm);
                // y = m ( x - x1 ) + y1
                double intersectionY = lm * (intersectionX - lx) + ly;
                g.drawString("CS", (int) intersectionX - 8, WINDOW_HEIGHT - ((int) intersectionY - 5));
            }
        }
        /*} else if (showPriceFloor.isSelected()) {
            //left bound of producer surplus is the end of the leftmost curve
            int leftBound = Math.max(s.getX1(), d.getX1());
            //left corner, left corner down to price floor, price floor-demand intersection
            g.setColor(new Color(155, 65, 0));
            int[] xValues = new int[]{leftBound, leftBound, d.getXValue(priceFloor)};
            int[] yValues = new int[]{WINDOW_HEIGHT - d.getYValue(leftBound), WINDOW_HEIGHT - priceFloor, WINDOW_HEIGHT - priceFloor};
            g.drawPolygon(xValues, yValues, 3);

            double lineSlope = 1;
            int currentX = leftBound;
            int currentY = d.getYValue(leftBound);

            double demandSlope = d.getM();
            int increment = 20;

            while (currentX <= d.getXValue(priceFloor) || currentY >= priceFloor) {
                if (currentY >= priceFloor) { //lines that start from the left of consumer surplus

                    double intersectionX = (lineSlope * leftBound - currentY - demandSlope * d.getX() + d.getY()) / (lineSlope - demandSlope);
                    // y = m ( x - x1 ) + y1
                    double intersectionY = demandSlope * (intersectionX - d.getX()) + d.getY();
                    g.drawLine(leftBound, WINDOW_HEIGHT - currentY, (int) intersectionX, WINDOW_HEIGHT - (int) intersectionY);
                    currentY -= increment;
                    if (currentY < priceFloor) { //the x position gets what "hangs over" the y positions
                        currentX += priceFloor - currentY;
                    }
                } else { //lines that start from the bottom of consumer surplus
                    double intersectionX = (lineSlope * currentX - priceFloor - demandSlope * d.getX() + d.getY()) / (lineSlope - demandSlope);
                    // y = m ( x - x1 ) + y1
                    double intersectionY = demandSlope * (intersectionX - d.getX()) + d.getY();
                    g.drawLine(currentX, WINDOW_HEIGHT - priceFloor, (int) intersectionX, WINDOW_HEIGHT - (int) intersectionY);
                    currentX += increment;
                }
            }
        }*/
        g.setColor(Color.BLACK);
    }

    public void calculatePriceFloor() {
        if (priceFloor < equilibriumY) {
            priceFloor = equilibriumY;
        } else if (priceFloor > d.getY1()) {
            priceFloor = d.getY1();
        }
        if (s.getYValue(d.getXValue(priceFloor)) < BORDER_OFFSET) {
            priceFloor = d.getYValue(s.getXValue(BORDER_OFFSET));
        }
    }
    public void calculatePriceCeiling() {
        if (priceCeiling > equilibriumY) {
            priceCeiling = equilibriumY;
        } else if (priceCeiling < s.getY1()) {
            priceCeiling = s.getY1();
        }

        //if the price ceiling-supply intersection up to demand is above the upper border
        if (d.getYValue(s.getXValue(priceCeiling)) > WINDOW_HEIGHT - BORDER_OFFSET) {
            priceCeiling = s.getYValue(d.getXValue(WINDOW_HEIGHT - BORDER_OFFSET));
        }
    }

    public void drawPriceFloor(Graphics g) {
        g.setColor(new Color(168, 0, 255));
        g.drawString("Pf", BORDER_OFFSET - 15, WINDOW_HEIGHT - (priceFloor - 7));
        int increment = 10;
        int currentX = BORDER_OFFSET;
        while (currentX + increment <= WINDOW_WIDTH - BORDER_OFFSET) { //horizontal line
            int start = currentX + increment / 4;
            int end = currentX + 3 * increment / 4;
            g.drawLine(start, WINDOW_HEIGHT - priceFloor, end, WINDOW_HEIGHT - priceFloor);
            currentX += increment;
        }

        int currentY = BORDER_OFFSET;
        while (currentY + increment <= priceFloor) { //line down to x axis
            int start = currentY + increment / 4;
            int end = currentY + 3 * increment / 4;
            g.drawLine(d.getXValue(priceFloor), WINDOW_HEIGHT - start, d.getXValue(priceFloor), WINDOW_HEIGHT - end);
            currentY += increment;
        }
        g.drawString("Qf", d.getXValue(priceFloor) - 5, WINDOW_HEIGHT - (BORDER_OFFSET - 15));


        g.setColor(Color.BLACK);
    }

    public void drawPriceCeiling(Graphics g) {
        g.setColor(new Color(0, 5, 242));

        g.drawString("Pc", BORDER_OFFSET - 15, WINDOW_HEIGHT - (priceCeiling - 7));
        int increment = 10;
        int currentX = BORDER_OFFSET;
        while (currentX + increment <= WINDOW_WIDTH - BORDER_OFFSET) { //horizontal line
            int start = currentX + increment / 4;
            int end = currentX + 3 * increment / 4;
            g.drawLine(start, WINDOW_HEIGHT - priceCeiling, end, WINDOW_HEIGHT - priceCeiling);
            currentX += increment;
        }

        int currentY = BORDER_OFFSET;
        while (currentY + increment <= priceCeiling) { //line down to x axis
            int start = currentY + increment / 4;
            int end = currentY + 3 * increment / 4;
            g.drawLine(s.getXValue(priceCeiling), WINDOW_HEIGHT - start, s.getXValue(priceCeiling), WINDOW_HEIGHT - end);
            currentY += increment;
        }
        g.drawString("Qc", s.getXValue(priceCeiling) - 5, WINDOW_HEIGHT - (BORDER_OFFSET - 15));


        g.setColor(Color.BLACK);
    }

    public void drawDWL(Graphics g) {

        g.setColor(new Color(225, 131, 170));
        //triangle: price floor-demand intersection, down to supply, to equilibrium
        int xStart;
        if (showPriceFloor.isSelected()) {
            xStart = d.getXValue(priceFloor);
        } else { // if price ceiling selected
            xStart = s.getXValue(priceCeiling);
        }
        //top, bottom, right
        int[] xValues = new int[] {xStart, xStart, equilibriumX};
        int[] yValues = new int[] {WINDOW_HEIGHT - d.getYValue(xStart), WINDOW_HEIGHT - s.getYValue(xStart), WINDOW_HEIGHT - equilibriumY};
        g.drawPolygon(xValues, yValues, 3);

        int increment = 10;
        int currentX = xStart + increment;
        while (currentX < equilibriumX) {
            g.drawLine(currentX, WINDOW_HEIGHT - d.getYValue(currentX), currentX, WINDOW_HEIGHT - s.getYValue(currentX));
            currentX += increment;
        }

        if (xStart < equilibriumX) {
            g.setColor(new Color(225, 117, 166));
            //using top and bottom right line to calculate center
            int lx = xStart;
            int ly = (d.getYValue(xStart) + s.getYValue(xStart)) / 2;
            double lm = (double) (equilibriumY - ly) / (equilibriumX - lx);

            //second (bottom right border) line
            int bx = (xStart + equilibriumX) / 2;
            int by = (s.getYValue(xStart) + equilibriumY) / 2;
            double bm = (double) (d.getYValue(xStart) - by) / (xStart - bx);

            double intersectionX = (lm * lx - ly - bm * bx + by) / (lm - bm);
            // y = m ( x - x1 ) + y1
            double intersectionY = lm * (intersectionX - lx) + ly;
            g.drawString("DWL", (int) intersectionX - 11, WINDOW_HEIGHT - ((int) intersectionY - 5));
        }
        
        g.setColor(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        drawCurves(g);
        //the equilibrium point should only change when there is a shift
        if ((!shiftButton.isSelected() && !rotateButton.isSelected()) || shiftButton.isSelected()) {
            calculateEquilibrium();
        }

        drawEquilibrium(g);

        calculatePriceFloor();
        calculatePriceCeiling();

        if (surplusCheckBox.isSelected()) {
            drawProducerSurplus(g);
            drawConsumerSurplus(g);
        }
        if (showPriceFloor.isSelected()) {
            drawPriceFloor(g);
            if (DWLCheckBox.isSelected()) {
                drawDWL(g);
            }
        } else if (showPriceCeiling.isSelected()) {
            drawPriceCeiling(g);
            if (DWLCheckBox.isSelected()) {
                drawDWL(g);
            }
        }
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
                if (supplyButton.isSelected()) {
                    s.shiftCurve(x, y);
                } else if (demandButton.isSelected()) {
                    d.shiftCurve(x, y);
                } else if (priceFloorButton.isSelected()) {
                    priceFloor = y;
                } else if (priceCeilingButton.isSelected()) {
                    priceCeiling = y;
                }
            } else if (rotateButton.isSelected()) {
                if (supplyButton.isSelected()) {
                    s.rotateCurve(x, y);
                } else if (demandButton.isSelected()) {
                    d.rotateCurve(x, y);
                }
            }


            //System.out.println("(" + x + ", " + y + ")");
            //s.setPointsInput(x, y);
            demo.repaint();
        }
    }
}
