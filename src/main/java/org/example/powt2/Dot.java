package org.example.powt2;

import javafx.scene.paint.Color;

public record Dot(double x, double y, double r, Color color) {
    public static String toMessage(double x, double y, double r, Color color) {
        return x + " " + y + " " + r + " " + color.toString();
    }
    public static Dot fromMessage(String message) {
        String[] tokens = message.split(" ");
        double x = Double.parseDouble(tokens[0]);
        double y = Double.parseDouble(tokens[1]);
        double r = Double.parseDouble(tokens[2]);
        Color color = Color.valueOf(tokens[3]);

        return new Dot(x, y, r, color);
    }
}
