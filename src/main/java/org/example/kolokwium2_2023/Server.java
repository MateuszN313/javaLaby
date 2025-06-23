package org.example.kolokwium2_2023;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server extends Application {
    private static final int PORT = 5000;
    private static int kernelSize;
    private static BufferedImage image;

    @Override
    public void start(Stage stage) throws Exception {
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(15);
        slider.setOrientation(Orientation.VERTICAL);

        kernelSize = (int) slider.getValue();

        Label label = new Label(Integer.toString(kernelSize));

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if((int) slider.getValue() % 2 != 0) kernelSize = (int) slider.getValue();
            label.setText(Integer.toString(kernelSize));
        });

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.getChildren().addAll(slider, label);

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Set kernel size");
        stage.setScene(scene);
        stage.show();
    }

    public static void send(String path, Socket socket) {
        try {
            File file = new File(path);
            FileInputStream input = new FileInputStream(file);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            byte[] buffer = new byte[8192];
            int count;
            output.writeLong(file.length());
            while ((count = input.read(buffer)) != -1)
                output.write(buffer, 0, count);

            output.flush();

            System.out.println("File sent.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void receive(Socket socket, String path) {
        try {
            File file = new File(path);
            file.createNewFile();

            DataInputStream input = new DataInputStream(socket.getInputStream());
            FileOutputStream output = new FileOutputStream(path);

            byte[] buffer = new byte[8192];
            int count;
            int receivedSize = 0;
            long fileSize = input.readLong();

            while (receivedSize < fileSize) {
                count = input.read(buffer);
                output.write(buffer, 0, count);
                System.out.println(count);
                receivedSize += count;
            }
            output.close();

            System.out.println("File received.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void blur() {
        try {
            int width = image.getWidth();
            int height = image.getHeight();

            int numCores = Runtime.getRuntime().availableProcessors();
            Thread[] threads = new Thread[numCores];
            int rowsPerThread = height / numCores;
            int remainingRows = height % numCores;

            for(int i = 0; i < numCores; i++) {
                int startRow = rowsPerThread * i;
                int endRow = startRow + rowsPerThread;
                if(i == numCores - 1) endRow += remainingRows;
                endRow = Math.min(endRow, height);

                threads[i] = new Thread(new BlurWorker(image, kernelSize, startRow, endRow, width));
                threads[i].start();
            }
            for(int i=0; i < numCores; i++){
                threads[i].join();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static class BlurWorker implements Runnable {
        private final BufferedImage image;
        private final int kernelSize;
        private final int startY;
        private final int endY;
        private final int width;
        private final int[] rgb;

        public BlurWorker(BufferedImage image, int kernelSize, int startY, int endY, int width) {
            this.image = image;
            this.kernelSize = kernelSize;
            this.startY = startY;
            this.endY = endY;
            this.width = width;
            this.rgb = new int[image.getRaster().getNumBands()];
        }

        @Override
        public void run() {
            for(int y = this.startY; y < this.endY; y++) {
                for(int x = 0; x < this.width; x++) {
                    int sumr = 0;
                    int sumg = 0;
                    int sumb = 0;
                    for(int boxY = y - kernelSize / 2; boxY < y + kernelSize / 2; boxY++){
                        for(int boxX = x - kernelSize / 2; boxX < x + kernelSize / 2; boxX++) {

                            if(boxX < kernelSize / 2 || boxX > width - kernelSize / 2 ||
                                    boxY < kernelSize / 2 || boxY > image.getHeight() - kernelSize / 2) continue;

                            this.image.getRaster().getPixel(boxX, boxY, this.rgb);
                            sumr += this.rgb[0];
                            sumg += this.rgb[1];
                            sumb += this.rgb[2];
                        }
                    }
                    this.rgb[0] = sumr / (int) Math.pow(kernelSize, 2);
                    this.rgb[1] = sumg / (int) Math.pow(kernelSize, 2);
                    this.rgb[2] = sumb / (int) Math.pow(kernelSize, 2);
                    this.image.getRaster().setPixel(x, y, this.rgb);
                }
            }
        }
    }

    public static void saveImage(String path) {
        try {
            ImageIO.write(image, "png", new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        try(ServerSocket listener = new ServerSocket(PORT)) {
            while(true) {
                Socket socket = listener.accept();

                String filePath;
                receive(socket, filePath = "images/"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) +".png");
                image = ImageIO.read(new File(filePath));

                launch(args);
                blur();
                saveImage(filePath);

                send(filePath, socket);

                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
