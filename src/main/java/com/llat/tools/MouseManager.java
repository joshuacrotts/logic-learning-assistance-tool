package com.llat.tools;

public class MouseManager {

    public static int LEFT = 0;
    public static int RIGHT = 1;

    private double curX = 0;
    private double curY = 0;

    public MouseManager(double _curX, double _curY) {
        this.curX = _curX;
        this.curY = _curY;
    }

    public double getXDirection(double _inputX) {
        if (_inputX < curX) {
            return MouseManager.LEFT;
        }
        return MouseManager.RIGHT;
    }

    public double getCurX() {
        return this.curX;
    }

    public void setCurX(double _curX) {
        this.curX = _curX;
    }

    public double getCurY() {
        return this.curY;
    }

    public void setCurY(double _curY) {
        this.curY = _curY;
    }
}
