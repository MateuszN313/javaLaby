import java.io.FileWriter;
import java.io.IOException;

public class SvgScene {
    private Shape[] shapes;
    private int count;

    public SvgScene(){
        this.shapes =new Shape[3];
        this.count=0;
    }
    public void addShape(Shape shape){
        for(int i = 0; i<this.shapes.length; i++){
            if(this.shapes[i]==null){
                this.shapes[i]=shape;
                return;
            }
        }

        this.shapes[this.count++]=shape;
        if(this.count==this.shapes.length){
            this.count=0;
        }
    }
    public String toSvg(){
        String result="";
        for(Shape pol : shapes){
            result+=pol.toSvg(0,0)+"\n";
        }
        return result;
    }
    public BoundingBox boundingBox() {
        double maxX = -Double.MAX_VALUE;
        double minX = Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;

        for (Shape shape : this.shapes) {
            if (shape == null) continue;
            if (shape instanceof Polygon pol) {
                for (Point p : pol.getPoints()) {
                    maxX = Math.max(maxX, p.getX());
                    minX = Math.min(minX, p.getX());
                    maxY = Math.max(maxY, p.getY());
                    minY = Math.min(minY, p.getY());
                }
            }

            return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
        }
        return null;
    }
    public void save(String filePath){
        BoundingBox box=this.boundingBox();
        if(box==null){
            System.out.println("no polygons to save");
            return;
        }

        double offsetX = box.x();
        double offsetY = box.y();
        double width = box.width();
        double height = box.height();

        StringBuilder svgContent = new StringBuilder();
        svgContent.append(String.format(
                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%.2f\" height=\"%.2f\" viewBox=\"0 0 %.2f %.2f\">\n",
                width, height, width, height
        ));


        for(Shape shape : this.shapes){
            if(shape==null) continue;
            if(shape instanceof Polygon pol){
                svgContent.append(pol.toSvg(offsetX,offsetY)).append("\n");
            }
            svgContent.append("</svg>");

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(svgContent.toString());
                System.out.println("SVG file saved successfully!");
            } catch (IOException e) {
                System.err.println("Error saving SVG file: " + e.getMessage());
            }
        }
    }
}
