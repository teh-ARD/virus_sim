package com.github.teh_ard.utils;

public class MapPoint {
    double x;
    double y;

    public MapPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public void add(MapPoint p2) {
        x += p2.getX();
        y += p2.getY();
    }
}