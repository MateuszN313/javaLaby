package com.example.gra;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GraphicsItem {
    private final Color color = Color.DARKSALMON;
    private Point2D moveVector;
    private double velocity;

    public Ball() {
        this.width = 0.03;
        this.height = 0.05;

        this.moveVector = new Point2D(1,-1).normalize();
        this.velocity = 0.005;
    }

    public void setPosition(Point2D position){
        this.x = position.getX() - this.width / 2;
        this.y = position.getY() - this.height / 2;
    }
    public void updatePosition() {
        this.x += velocity * moveVector.getX();
        this.y += velocity * moveVector.getY();

        if(this.x < 0) this.x = 0.0 + this.width;
        if(this.x + this.width > 1) this.x = 1.0 - this.width;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(this.color);
        graphicsContext.fillOval(
                this.x * canvasWidth,
                this.y * canvasHeight,
                this.width * canvasWidth,
                this.height * canvasHeight
        );
    }
}
