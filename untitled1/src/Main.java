public class Main {
    public static void main(String[] args) {
        ImageProcessor imageProcessor = new ImageProcessor();

        imageProcessor.loadImage("resources/zul.jpg");
        imageProcessor.saveImage("resources/zul2.jpg");
        imageProcessor.adjustBrightness(20);
        imageProcessor.saveImage("resources/zul3jasny.jpg");

    }
}