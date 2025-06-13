package com.example.gra;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends GraphicsItem{
    private final Color color = Color.DARKMAGENTA;
    public Paddle() {
        this.width = 0.13;
        this.height = 0.03;
        this.x = 0.5;
        this.y = 1.0 - this.height - 0.05;
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

    public void move(double mouseX) {
        double relativeX = mouseX / canvasWidth;
        this.x = relativeX - this.width / 2;

        if(this.x < 0) this.x = 0.0 + this.width;
        if(this.x + this.width > 1) this.x = 1.0 - this.width;
    }
}
