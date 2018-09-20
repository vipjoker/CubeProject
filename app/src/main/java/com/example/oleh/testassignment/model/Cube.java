package com.example.oleh.testassignment.model;

public class Cube {
    private int color;
    private int width;
    private int heigth;
    private int length;

    public Cube(int color, int width, int heigth, int length) {
        this.color = color;
        this.width = width;
        this.heigth = heigth;
        this.length = length;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
