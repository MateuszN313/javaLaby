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
        this.velocity = 0.4;
    }

    public void setPosition(Point2D position){
        this.x = position.getX() - this.width / 2;
        this.y = position.getY() - this.height / 2;
    }
    public void updatePosition(double elapsedSesconds) {
        this.x += this.velocity * this.moveVector.getX() * elapsedSesconds;
        this.y += this.velocity * this.moveVector.getY() * elapsedSesconds;
    }

    public double getTop() {
        return this.y;
    }
    public double getBottom() {
        return this.y + this.height;
    }
    public double getLeft() {
        return this.x;
    }
    public double getRight() {
        return this.x + this.width;
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

    public void bounceHorizontally() {
        this.moveVector = new Point2D(this.moveVector.getX() * -1, this.moveVector.getY());
    }
    public void bounceVertically() {
        this.moveVector = new Point2D(this.moveVector.getX(), this.moveVector.getY() * -1);
    }
    public void bounceFromPaddle(double offset) {
        double maxAngle = Math.toRadians(60);
        double angle = offset * maxAngle;

        double dx = Math.sin(angle);
        double dy = -Math.cos(angle);

        moveVector = new Point2D(dx, dy).normalize();
    }
}
