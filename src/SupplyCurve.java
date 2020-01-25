public class SupplyCurve {
    public static final int BORDER_OFFSET = 100;
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 750;

    //y values are the "real" values (as if coordinate system's origin was bottom left)
    public int x1;  //(x1, y1): bottom left
    public int y1;
    public int x2;  //(x2, y2): top right
    public int y2;

    public int x; //these represent any point on the curve
    public int y;

    public SupplyCurve() {
        x1 = BORDER_OFFSET;
        y1 = BORDER_OFFSET;
        x2 = WINDOW_WIDTH - BORDER_OFFSET;
        y2 = WINDOW_HEIGHT - BORDER_OFFSET;
    }

    public int getX1() {
        return x1;
    }
    public int getY1() { //real values -> graphed values
        return WINDOW_HEIGHT - y1;
    }
    public int getX2() {
        return x2;
    }
    public int getY2() { //real values -> graphed values
        return WINDOW_HEIGHT - y2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPoints(int x1, int y1, int x2, int y2) {
        y1 = WINDOW_HEIGHT - y1;
        y2 = WINDOW_HEIGHT - y2;

        if (x2 >= x1 && y2 >= y1) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    public void setPointsInput(int x, int y) {
        y = WINDOW_HEIGHT - y;
        if (BORDER_OFFSET <= x && x <= WINDOW_WIDTH - BORDER_OFFSET && BORDER_OFFSET < y && y < WINDOW_HEIGHT - BORDER_OFFSET) {
            this.x = x;
            this.y = y;
            double m = (double) (y2 - y1) / (x2 - x1);
            // equation: y - y1 = m ( x - x1 ). in this case, eq x1: param x, eq y1: param y


            //if bottom of supply curve hits the x axis (equation >= 100)
            // (1 / m)(y - y1) + x1 = x, y = 100
            int innerBorder = BORDER_OFFSET; // (100)
            int outerBorder = WINDOW_WIDTH - BORDER_OFFSET;

            int xAxisIntersection = (int) ((1 / m) * (innerBorder - y) + x);
            int upperAxisIntersection = (int) ((1 / m) * (outerBorder - y) + x);

            //swapped because y values inverted
        /*int temp = xAxisIntersection;
        xAxisIntersection = upperAxisIntersection;
        upperAxisIntersection = temp;*/

            System.out.println("xAxisIntersection: " + xAxisIntersection);
            if (xAxisIntersection >= innerBorder) {
                x1 = xAxisIntersection;
                y1 = innerBorder;
            } else { // y = m ( x - x1 ) + y1, x = 100
                System.out.println("IN X AXIS ELSE");
                x1 = innerBorder;
                y1 = (int) (m * (innerBorder - x) + y);
            }

            System.out.println("upperAxisIntersection: " + upperAxisIntersection);
            if (upperAxisIntersection <= outerBorder) {
                x2 = upperAxisIntersection;
                y2 = outerBorder;
            } else {
                System.out.println("IN UPPER AXIS ELSE");

                x2 = outerBorder;
                y2 = (int) (m * (outerBorder - x) + y);
            }
        }
    }
}
