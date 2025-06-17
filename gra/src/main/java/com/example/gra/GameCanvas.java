package com.example.gra;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameCanvas extends Canvas {
    private final GraphicsContext graphicsContext;
    private final Paddle paddle;
    private final Ball ball;
    private boolean gameStarted = false;
    private AnimationTimer timer;
    private long lastFrameTime = 0;
    private List<Brick> bricks = new ArrayList<>();

    public GameCanvas(double width, double height) {
        super(width, height);
        this.graphicsContext = getGraphicsContext2D();

        GraphicsItem.setCanvasWidthHeight(getWidth(), getHeight());
        this.paddle = new Paddle();
        this.ball = new Ball();
        loadLevel();

        setOnMouseClicked(e -> gameStarted = true);
        setupAnimationLoop();
    }

    public void draw() {
        this.graphicsContext.setFill(Color.CORNFLOWERBLUE);
        this.graphicsContext.fillRect(0, 0, getWidth(), getHeight());

        this.paddle.draw(graphicsContext);
        setOnMouseMoved(this::handleMouseMoved);

        this.ball.draw(graphicsContext);

        for (Brick brick : bricks) {
            brick.draw(graphicsContext);
        }
    }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        paddle.move(mouseEvent.getX());
        draw();
    }

    private void setupAnimationLoop() {
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastFrameTime == 0) {
                    lastFrameTime = now;
                    return;
                }
                double elapsedSeconds = (now - lastFrameTime) / 1_000_000_000.0;
                lastFrameTime = now;

                if(gameStarted){
                    ball.updatePosition(elapsedSeconds);

                    if(shouldBallBounceHorizontally()) ball.bounceHorizontally();
                    if(shouldBallBounceVertically()) ball.bounceVertically();
                    if(shouldBallBounceFromPaddle()) {
                        double paddleCenter = paddle.x + paddle.width / 2;
                        double ballCenter = ball.getLeft() + ball.width / 2;
                        double offset = (ballCenter - paddleCenter) / (paddle.width / 2);

                        ball.bounceFromPaddle(offset);
                    }

                    for(Brick brick : bricks) {
                        Brick.CrushType crushType = brick.Collision(ball);
                        if(crushType != Brick.CrushType.NoCrush){
                            if(crushType == Brick.CrushType.HorizontalCrush) ball.bounceHorizontally();
                            if(crushType == Brick.CrushType.VerticalCrush) ball.bounceVertically();
                            bricks.remove(brick);
                            break;
                        }
                    }

                    if(shouldLose()) lose();
                    if(shouldWin()) win();
                }else{
                    ball.setPosition(new Point2D(
                            paddle.x + paddle.width / 2,
                            paddle.y - paddle.height / 2));
                }
                draw();
            }
        };
        timer.start();
    }

    private void loadLevel(){
        this.bricks.clear();
        Brick.setGridSize(20, 10);
        for(int row = 0; row < 7; row++){
            for(int col = 0; col < 10; col++){
                this.bricks.add(new Brick(col, row, Color.FIREBRICK));
            }
        }
    }

    private boolean shouldBallBounceHorizontally() {
        return (this.ball.x <= 0 || this.ball.x + this.ball.width >= 1);
    }
    private boolean shouldBallBounceVertically() {
        return this.ball.y <= 0;
    }
    private boolean shouldBallBounceFromPaddle() {
        return ((this.ball.x + this.ball.width >= this.paddle.x && this.ball.x <= this.paddle.x + this.paddle.width) &&
                (this.ball.y + this.ball.height >= this.paddle.y) && this.ball.y <= this.paddle.y + this.paddle.height);
    }
    private boolean shouldLose(){
        return this.ball.y >= 1;
    }
    private boolean shouldWin(){
        return this.bricks.isEmpty();
    }

    private void lose(){
        System.out.println("hahaha looseeeeer fuck you");
        this.gameStarted = false;
        loadLevel();
    }
    private void win(){
        System.out.println("WINNER WINNER CHICKER DINNER");
        this.gameStarted = false;
        loadLevel();
    }
}
