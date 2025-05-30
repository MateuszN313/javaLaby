import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

public class ImageProcessor implements Runnable{
    BufferedImage image;

    public void loadImage(String path) {
        try{
            this.image = ImageIO.read(new File(path));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void saveImage(String path) {
        if(this.image == null) throw new IllegalStateException("No image loaded");

        String extension = this.getFileExtension(path);
        if(path == null || extension.isEmpty()) throw new IllegalArgumentException("Path have wrong extension");

        try{
            ImageIO.write(this.image, extension, new File(path));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if(lastDotIndex > 0 && lastDotIndex < fileName.length() - 3){
            return fileName.substring(lastDotIndex + 1);
        }
        return null;
    }
    public void adjustBrightness(int brightnessValue) {
        if(this.image == null) throw new IllegalStateException("No image loaded");

        int rgb[];
        for (int x = 0; x < this.image.getWidth(); x++) {
            for (int y = 0; y < this.image.getHeight(); y++) {
                rgb = image.getRaster().getPixel(x, y, new int[3]);
                int red = Math.max(0, Math.min(255, rgb[0] + brightnessValue));
                int green = Math.max(0, Math.min(255, rgb[0] + brightnessValue));
                int blue = Math.max(0, Math.min(255, rgb[0] + brightnessValue));
                int arr[] = { red, green, blue };
                image.getRaster().setPixel(x, y, arr);
            }
        }
    }

    @Override
    public void run() {

    }
}
