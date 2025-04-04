public class Style {
    public final String fillColor;
    public final String strokeColor;
    public final double strokeWidth;

    public Style(String fillColor,String strokeColor, double strokeWidth){
        this.fillColor=fillColor;
        this.strokeColor=strokeColor;
        this.strokeWidth=strokeWidth;
    }
    public String toSvg(){
        String result="style=\"fill: "+fillColor+"; stroke: "+strokeColor+"; stroke-width: "+strokeWidth+";\"";
        return result;
    }
}
