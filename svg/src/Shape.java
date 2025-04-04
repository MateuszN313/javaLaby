abstract class Shape {
    protected Style style;
    public Shape(Style style){
        this.style=new Style(style.fillColor,style.strokeColor, style.strokeWidth);
    }
    abstract String toSvg(double offsetX, double offsetY);
}
