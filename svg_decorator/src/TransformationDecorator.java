public class TransformationDecorator extends ShapeDecorator{
    private String transform;

    public TransformationDecorator(Shape decoratedShape, String transform) {
        super(decoratedShape);
        this.transform = transform;
    }
    @Override
    public String toSvg(String param){
        return this.decoratedShape.toSvg(String.format("transform=\"%s\" %s",this.transform,param));
    }
    public static class Builder{
        private String transform="";

        public Builder translate(Vec2 translation){
            this.transform=String.format("translate(%f %f) ",translation.x(),translation.y());
            return this;
        }
        public Builder rotate(float angle, Vec2 center){
            this.transform=String.format("rotate(%f %f %f) ",angle,center.x(),center.y());
            return this;
        }
        public Builder scale(Vec2 scaleFactor){
            this.transform=String.format("scale(%f %f) ",scaleFactor.x(),scaleFactor.y());
            return this;
        }
    }
}
