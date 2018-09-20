package com.example.oleh.testassignment;

public class Vec3 {
    public double x;
    public double y;
    public double z;


    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 rotateX(double angle) {
        double rad, cosa, sina, yn, zn;

        rad = angle * Math.PI / 180;
        cosa = Math.cos(rad);
        sina = Math.sin(rad);
        yn = this.y * cosa - this.z * sina;
        zn = this.y * sina + this.z * cosa;

        return new Vec3(this.x, yn, zn);
    }

    public Vec3 rotateY(double angle) {
        double rad, cosa, sina, xn, zn;

        rad = angle * Math.PI / 180;
        cosa = Math.cos(rad);
        sina = Math.sin(rad);
        zn = this.z * cosa - this.x * sina;
        xn = this.z * sina + this.x * cosa;

        return new Vec3(xn, this.y, zn);
    }

    public Vec3 rotateZ(double angle) {
        double rad, cosa, sina, xn, yn;

        rad = angle * Math.PI / 180;
        cosa = Math.cos(rad);
        sina = Math.sin(rad);
        xn = this.x * cosa - this.y * sina;
        yn = this.x * sina + this.y * cosa;

        return new Vec3(xn, yn, this.z);
    }

    public Vec3 project(int viewWidth, int viewHeight, float fov, float viewDistance) {
        double factor, xn, yn;

        factor = fov / (viewDistance + this.z);
        xn = this.x * factor + viewWidth / 2;
        yn = this.y * factor + viewHeight / 2;

        return new Vec3(xn, yn, this.z);
    }
}
