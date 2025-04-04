import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Polygon triangle = new Polygon(new Vec2[]{
                new Vec2(0, 0),
                new Vec2(300, 0),
                new Vec2(150, 250)
        });

        Polygon rectangle = new Polygon(new Vec2[]{
                new Vec2(350, 0),
                new Vec2(750, 0),
                new Vec2(750, 200),
                new Vec2(350, 200)
        });

        Polygon pentagon = new Polygon(new Vec2[]{
                new Vec2(0, 260),
                new Vec2(100, 460),
                new Vec2(300, 560),
                new Vec2(500, 460),
                new Vec2(600, 260)
        });

        Ellipse ellipse = new Ellipse(new Vec2(500, 700), 400, 100);

        SolidFillShapeDecorator filledPentagon=new SolidFillShapeDecorator(pentagon, "red");
        SolidFillShapeDecorator filledEllipse=new SolidFillShapeDecorator(ellipse, "blue");

        StrokeShapeDecorator strokeFilledPentagon=new StrokeShapeDecorator(filledPentagon,"blue",5);
        StrokeShapeDecorator strokeFilledEllipse=new StrokeShapeDecorator(filledEllipse,"red",2);

        SvgScene scene = new SvgScene();
        scene.addShape(triangle);
        scene.addShape(rectangle);
        scene.addShape(strokeFilledPentagon);
        scene.addShape(strokeFilledEllipse);
        scene.save("result.svg");
    }
}
