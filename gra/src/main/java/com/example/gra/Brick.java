package com.example.gra;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem {
    private static int gridRows;
    private static int gridCols;
    private Color color;

    public enum CrushType {NoCrush, HorizontalCrush, VerticalCrush}

    public Brick(int x,int y,Color color) {
        this.x = (double) x / gridCols;
        this.y = (double) y / gridRows;
        this.width = 1.0 / gridCols;
        this.height = 1.0 / gridRows;

        this.color = color;
    }

    public static void setGridSize(int gridRows, int gridCols) {
        Brick.gridRows = gridRows;
        Brick.gridCols = gridCols;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(this.color);
        graphicsContext.fillRect(
                this.x * canvasWidth,
                this.y * canvasHeight,
                this.width * canvasWidth,
                this.height * canvasHeight
        );
    }

    public CrushType Collision(Ball ball) {
        double brickLeft = this.x;
        double brickRight = this.x + this.width;
        double brickTop = this.y;
        double brickBottom = this.y + this.height;

        boolean intersects = ball.getRight() > brickLeft && ball.getLeft() < brickRight &&
                ball.getTop() < brickBottom && ball.getBottom() > brickTop;

        if(!intersects) return CrushType.NoCrush;

        double dx = Math.abs(((ball.getLeft() + ball.getRight()) / 2) - ((brickLeft + brickRight) / 2));
        double dy = Math.abs(((ball.getTop() + ball.getBottom()) / 2) - ((brickTop + brickBottom) / 2));

        if (2 * dx - this.width / 2 > dy) {
            return CrushType.HorizontalCrush;
        } else {
            return CrushType.VerticalCrush;
        }
    }
}
