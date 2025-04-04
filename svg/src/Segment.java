public class Segment {
    private Point p1;
    private Point p2;

    public void setP1(Point p1) {
        this.p1 = p1;
    }
    public void setP2(Point p2) {
        this.p2 = p2;
    }
    public Point getP1() {
        return p1;
    }
    public Point getP2() {
        return p2;
    }
    public Segment(Point p1, Point p2){
        this.p1=new Point(p1.getX(),p1.getY());
        this.p2=new Point(p2.getX(),p2.getY());
    }

    @Override
    public String toString() {
        return p1.toString()+" - "+p2.toString();
    }

    public double length(){
        return Math.sqrt(Math.pow(this.p1.getX()-this.p2.getX(),2)+Math.pow(this.p1.getY()-this.p2.getY(),2));
    }
    public Segment perpendicular(double length){
        double dx=this.p2.getX()+this.p1.getX();
        double dy=this.p2.getY()+this.p1.getY();

        double perpendicularDx=-dy;
        double perpendicularDy=dx;

        double perpendicularLength = Math.sqrt(perpendicularDx * perpendicularDx + perpendicularDy * perpendicularDy);
        perpendicularDx /= perpendicularLength;
        perpendicularDy /= perpendicularLength;

        double midX = (this.p1.getX() + this.p2.getX()) / 2;
        double midY = (this.p1.getY() + this.p2.getY()) / 2;

        Point newStart = new Point(midX + perpendicularDx, midY + perpendicularDy);
        Point newEnd = new Point(midX - perpendicularDx, midY - perpendicularDy);

        return new Segment(newStart, newEnd);
    }

    public static Segment longest(Segment arr[]){
        Segment max=arr[0];

        for(Segment s : arr){
            if(s.length()> max.length()){
                max=s;
            }
        }
        return max;
    }
}
