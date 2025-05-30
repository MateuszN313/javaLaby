public class Main {
    public static void main(String[] args) {
        ImageProcessor imageProcessor = new ImageProcessor();

        imageProcessor.loadImage("resources/zul.jpg");

        long startTime;
        long endTime;
        long singleThreadTime = 0;
        long multiThreadTime = 0;
        long multiThreadPoolTime = 0;

        startTime = System.currentTimeMillis();
        imageProcessor.adjustBrightness(20);
        endTime = System.currentTimeMillis();

        singleThreadTime = endTime - startTime;

        imageProcessor.saveImage("resources/zul_jasny.jpg");

        try {
            startTime = System.currentTimeMillis();
            imageProcessor.adjustBrightnessMultiThread(-40);
            endTime = System.currentTimeMillis();

            multiThreadTime = endTime - startTime;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        imageProcessor.saveImage("resources/zul_ciemny.jpg");

        try {
            startTime = System.currentTimeMillis();
            imageProcessor.adjustBrightnessMultiThreadPool(20);
            endTime = System.currentTimeMillis();

            multiThreadPoolTime = endTime - startTime;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        imageProcessor.saveImage("resources/zul_normalny.jpg");

        System.out.println("single thread - " + singleThreadTime + " ms\n" +
                "multi thread - " + multiThreadTime + " ms\n" +
                "multi thread pool - " + multiThreadPoolTime + " ms");
    }
}