public class Polygon extends Shape{
    private Point[] points;

    public Point[] getPoints() {
        return points;
    }

    public Style getStyle() { return style; }

    public Polygon(Point[] points, Style style){
        super(style);
        this.points=new Point[points.length];
        for(int i=0;i< points.length;i++) {
            this.points[i] = new Point(points[i].getX(), points[i].getY());
        }
    }
    public Polygon(Point[] points){
        this(points, new Style("none","black",1.0));
    }
    public Polygon(Polygon old){
        super(old.style);
        this.points=new Point[old.points.length];
        for(int i=0;i<old.points.length;i++) {
            this.points[i] = new Point(old.points[i].getX(), old.points[i].getY());
        }
    }
    @Override
    public String toString() {
        String result="";
        for(Point p : points){
            result+=p+" ";
        }
        return result;
    }
    public String toSvg(double offsetX, double offsetY){
        String result="<polygon points=\"";
        for(int i=0;i<points.length;i++) {
            result += (points[i].getX()+offsetX) + "," + (points[i].getY()+offsetY);
            if(i< points.length-1){
                result+=" ";
            }
        }
        result+="\" "+this.style.toSvg()+" />";
        return result;
    }
    public static Polygon square(Segment diagonal, Style style){
        Segment perpendicular = diagonal.perpendicular(diagonal.length());
        Point[] points=new Point[4];
        double midX=(diagonal.getP1().getX() + diagonal.getP2().getX())/2;
        double midY=(diagonal.getP1().getY() + diagonal.getP2().getY())/2;

        points[0]=diagonal.getP1();
        points[1]=diagonal.getP2();
        points[2]=new Point(midX+perpendicular.getP1().getX(),midY+perpendicular.getP1().getY());
        points[3]=new Point(midX+perpendicular.getP2().getX(),midY+perpendicular.getP2().getY());

        return new Polygon(points,style);
    }
}
