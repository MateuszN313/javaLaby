public class Main {
    public static void main(String[] args) {
//        Point[] points=new Point[3];
//        for(int i=0;i<3;i++){
//            points[i]=new Point(i,i);
//        }
//        Polygon pol1=new Polygon(points);
//        System.out.println(pol1.toString());
//
//        Polygon pol2=new Polygon(pol1);
//        System.out.println(pol2.toString());
//
//        System.out.println();
//        pol1.getPoints()[0].setX(2);
//
//        Polygon pol3=new Polygon(pol2);
//        pol3.getPoints()[1].setX(3);
//
//
//        SvgScene svg1=new SvgScene();
//        svg1.addPolygon(pol1);
//        svg1.addPolygon(pol2);
//        svg1.addPolygon(pol3);
//
//        System.out.println(svg1.toSvg());
//        svg1.save("plik.svg");

        Style s=new Style("skyblue","cadetblue",2);
        System.out.println(s.toSvg());

        Point p1=new Point(11,12);
        Point p2=new Point(21,22);


        Polygon square=Polygon.square(new Segment(p1,p2),s);
        System.out.println(square.toSvg(0,0));

        Ellipse ellipse=new Ellipse(p1,20,10,s);
        System.out.println(ellipse.toSvg(0,0));
    }
}