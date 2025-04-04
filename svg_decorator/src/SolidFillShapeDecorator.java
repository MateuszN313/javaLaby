public class SolidFillShapeDecorator extends ShapeDecorator{
    private String color;
    public SolidFillShapeDecorator(Shape decoratedShape, String color){
        super(decoratedShape);
        this.color=color;
    }
    @Override
    public String toSvg(String param){
        String formattedAttributes=String.format("fill=\"%s\" %s",color, param);
        return this.decoratedShape.toSvg(formattedAttributes);
    }
}
