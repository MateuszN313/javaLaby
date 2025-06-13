package com.example.gra;

import javafx.scene.canvas.GraphicsContext;

public abstract class GraphicsItem {
    protected static double canvasWidth;
    protected static double canvasHeight;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public static void setCanvasWidthHeight(double width, double height) {
        canvasWidth = width;
        canvasHeight = height;
    }

    public abstract void draw(GraphicsContext graphicsContext);
}
