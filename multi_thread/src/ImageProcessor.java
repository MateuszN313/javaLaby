import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageProcessor {
    BufferedImage image;

    public void loadImage(String path) {
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImage(String path) {
        if (this.image == null) throw new IllegalStateException("No image loaded");

        String extension = this.getFileExtension(path);
        if (path == null || extension.isEmpty()) throw new IllegalArgumentException("Path have wrong extension");

        try {
            ImageIO.write(this.image, extension, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 3) {
            return fileName.substring(lastDotIndex + 1);
        }
        return null;
    }

    public void adjustBrightness(int brightnessValue) {
        if (this.image == null) throw new IllegalStateException("No image loaded");

        int[] rgb = new int[this.image.getRaster().getNumBands()];
        for (int y = 0; y < this.image.getHeight(); y++) {
            for (int x = 0; x < this.image.getWidth(); x++) {
                image.getRaster().getPixel(x, y, rgb);
                for (int i = 0; i < rgb.length; i++) {
                    rgb[i] = Math.max(0, Math.min(255, rgb[i] + brightnessValue));
                }
                this.image.getRaster().setPixel(x, y, rgb);
            }
        }
    }

    public void adjustBrightnessMultiThread(int brightnessValue) throws InterruptedException {
        if (this.image == null) throw new IllegalStateException("No image loaded");

        int numCores = Runtime.getRuntime().availableProcessors();
        int numThreads = numCores == 0 ? 1 : numCores;

        int width = this.image.getWidth();
        int height = this.image.getHeight();

        Thread[] threads = new Thread[numThreads];
        int rowsPerThread = height / numThreads;
        int remainingRows = height % numThreads;

        for(int i = 0; i < numThreads; i++) {
            int startRow = rowsPerThread * i;
            int endRow = startRow + rowsPerThread;
            if(i == numThreads - 1) endRow += remainingRows;
            endRow = Math.min(endRow, height);

            threads[i] = new Thread(new BrightnessWorker(this.image, brightnessValue, startRow, endRow, width));
            threads[i].start();
        }
        for(int i=0; i < numThreads; i++){
            threads[i].join();
        }
    }

    public void adjustBrightnessMultiThreadPool(int brightnessValue) throws InterruptedException {
        if (this.image == null) throw new IllegalStateException("No image loaded");

        int numCores = Runtime.getRuntime().availableProcessors();
        int poolSize = numCores == 0 ? 1 : numCores;
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        int width = this.image.getWidth();
        int height = this.image.getHeight();

        for(int y = 0; y < height; y++){
            executor.submit(new RowBrightnessWorker(this.image, brightnessValue, y, width));
        }
        executor.shutdown();
        if(!executor.awaitTermination(1, TimeUnit.HOURS)) {
            executor.shutdownNow();
        }
    }

    private static class BrightnessWorker implements Runnable {
        private final BufferedImage image;
        private final int brightnessValue;
        private final int startY;
        private final int endY;
        private final int width;
        private final int[] rgb;

        public BrightnessWorker(BufferedImage image, int brightnessValue, int startY, int endY, int width) {
            this.image = image;
            this.brightnessValue = brightnessValue;
            this.startY = startY;
            this.endY = endY;
            this.width = width;
            this.rgb = new int[image.getRaster().getNumBands()];
        }

        @Override
        public void run() {
            if (this.image == null) throw new IllegalStateException("No image loaded");

            for (int y = this.startY; y < this.endY; y++) {
                for (int x = 0; x < this.width; x++) {
                    this.image.getRaster().getPixel(x, y, this.rgb);
                    for (int i = 0; i < this.rgb.length; i++) {
                        this.rgb[i] = Math.max(0, Math.min(255, this.rgb[i] + this.brightnessValue));
                    }
                    this.image.getRaster().setPixel(x, y, this.rgb);
                }
            }
        }
    }

    private static class RowBrightnessWorker implements Runnable {
        private final BufferedImage image;
        private final int brightnessValue;
        private final int y;
        private final int width;
        private final int[] rgb;

        public RowBrightnessWorker(BufferedImage image, int brightnessValue, int y, int width) {
            this.image = image;
            this.brightnessValue = brightnessValue;
            this.y = y;
            this.width = width;
            this.rgb = new int[image.getRaster().getNumBands()];
        }

        @Override
        public void run() {
            for (int x = 0; x < this.width; x++) {
                this.image.getRaster().getPixel(x, this.y, this.rgb);
                for (int i = 0; i < this.rgb.length; i++) {
                    this.rgb[i] = Math.max(0, Math.min(255, this.rgb[i] + this.brightnessValue));
                }
                this.image.getRaster().setPixel(x, this.y, this.rgb);
            }
        }
    }
}
