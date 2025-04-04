public class Ellipse extends Shape{
    private Point center;
    private double rx;
    private double ry;

    public Ellipse(Point center,double rx,double ry,Style style){
        super(style);
        this.center=new Point(center.getX(), center.getY());
        this.rx=rx;
        this.ry=ry;
    }

    public Point getCenter() {
        return center;
    }
    public double getRx() {
        return rx;
    }

    public double getRy() {
        return ry;
    }
    @Override
    public String toSvg(double offsetX, double offsetY){
        String result="<ellipse cx=\""+center.getX()+offsetX+"\" cy=\""+center.getY()+offsetY+"\" rx=\""+rx+"\" ry=\""+ry+"\" />";
        return result;
    }
}
