package com.example.gra;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class GameCanvas extends Canvas {
    private final GraphicsContext graphicsContext;
    private final Paddle paddle;
    private final Ball ball;
    private boolean gameStarted = false;

    public GameCanvas(double width, double height) {
        super(width, height);
        this.graphicsContext = getGraphicsContext2D();

        GraphicsItem.setCanvasWidthHeight(getWidth(), getHeight());
        this.paddle = new Paddle();
        this.ball = new Ball();

        setOnMouseClicked(e -> gameStarted = true);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(gameStarted){
                    ball.updatePosition();
                }else{
                    ball.setPosition(new Point2D(
                            paddle.x + paddle.width / 2,
                            paddle.y - paddle.height));
                }
                draw();
            }
        };
        timer.start();
    }
    public void draw() {
        this.graphicsContext.setFill(Color.CORNFLOWERBLUE);
        this.graphicsContext.fillRect(0, 0, getWidth(), getHeight());

        this.paddle.draw(graphicsContext);
        setOnMouseMoved(this::handleMouseMoved);
        this.ball.draw(graphicsContext);
    }
    private void handleMouseMoved(MouseEvent mouseEvent) {
        paddle.move(mouseEvent.getX());
        draw();
    }

}
