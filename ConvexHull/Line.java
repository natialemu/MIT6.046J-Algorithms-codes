public class Line {
    private double slope;
    private double yIntercept;
    private double xIntercept;

    public Line(Point p1, Point p2){
        slope = (p2.getY() - p1.getY())/(p2.getX() - p1.getX());
        yIntercept = p1.getY() - slope*p1.getX();
        xIntercept = (0 - yIntercept)/slope;

    }

    public Line(Point p, double slope){
        this.slope = slope;
        if (this.slope  == Double.POSITIVE_INFINITY){
            yIntercept = Double.POSITIVE_INFINITY;
        }else{
            yIntercept = p.getY() - slope*p.getX();
        }

        xIntercept = (0 - yIntercept)/slope;

    }

    public double getSlope() {
        return slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public double getyIntercept() {
        return yIntercept;
    }

    public void setyIntercept(double yIntercept) {
        this.yIntercept = yIntercept;
    }

    public double getxIntercept() {
        return xIntercept;
    }

    public void setxIntercept(double xIntercept) {
        this.xIntercept = xIntercept;
    }

    public Point intersects(Line l2){
        double xIntersection = 0;
        double yIntersection = 0;
        if(this.slope == Double.POSITIVE_INFINITY ){
            yIntersection =l2.evaluate(xIntercept);
            xIntersection = xIntercept;



        }else if(l2.getSlope() == Double.POSITIVE_INFINITY){
            yIntersection = evaluate(l2.getxIntercept());
            xIntersection = l2.getxIntercept();

        }else{
            xIntersection = (l2.getyIntercept() - getyIntercept())/(getSlope() - l2.getSlope());
            yIntersection = (getyIntercept()*l2.getSlope() - l2.getyIntercept()*getSlope())/(l2.getSlope() - getSlope());

        }
        return new Point(xIntersection,yIntersection);
    }

    private double evaluate(double x){
        return slope*x + yIntercept;
    }

}
