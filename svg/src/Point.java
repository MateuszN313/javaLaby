public class Point {
    private double x;
    private double y;

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public Point(double x,double y){
        this.x=x;
        this.y=y;
    }
    public Point(){
        this.x=0;
        this.y=0;
    }

    public String toString(){
        return "("+this.x+","+this.y+")";
    }
    public String toSvg(){
        return "<circle r=\"45\" cx=\""+this.x+"\" cy=\""+this.y+"\" fill=\"red\" />";
    }
    public void translate(double dx,double dy){
        this.x+=dx;
        this.y+=dy;
    }
    public Point translated(double dx,double dy){
        Point p=new Point();
        p.x=this.x+dx;
        p.y=this.y+dy;
        return p;
    }
}
