package com.example.oleh.testassignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

public class CubeView extends View {

    private Paint fillPaint;
    private int cubeColor = Color.RED;
    private Paint strokePaint;

    protected Vec3 vertices[];

    protected Face faces[];
    private Face sortedFaces[] = new Face[6];
    private Path path = new Path();

    protected float ax;

    protected float ay;

    protected float az;

    protected float lastTouchX;
    protected float lastTouchY;


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // Initialize the cube geometry.

        // Allow the view to receive touch input.
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastTouchX = event.getX();
            lastTouchY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float dx = (event.getX() - lastTouchX) / 100.0f;
            float dy = (event.getY() - lastTouchY) / 100.0f;
            ax += dy;
            ay -= dx;
            postInvalidate();
        }
        return true;
    }


    public CubeView(Context context) {
        super(context);
        init();
    }

    public CubeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CubeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(cubeColor);
        fillPaint.setStyle(Paint.Style.FILL);

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(3);
        int length = 2;
        vertices = new Vec3[]{
                new Vec3(-length, 1, -1),
                new Vec3(length, 1, -1),
                new Vec3(length, -1, -1),
                new Vec3(-length, -1, -1),
                new Vec3(-length, 1, 1),
                new Vec3(length, 1, 1),
                new Vec3(length, -1, 1),
                new Vec3(-length, -1, 1)
        };
        faces = new Face[]{
                new Face(0, 1, 2, 3 , Color.BLUE),
                new Face(1, 5, 6, 2 , Color.RED),
                new Face(5, 4, 7, 6 , Color.YELLOW),
                new Face(4, 0, 3, 7 , Color.WHITE),
                new Face(0, 4, 5, 1 , Color.CYAN),
                new Face(3, 2, 6, 7 , Color.MAGENTA)};

    }


    @Override
    protected void onDraw(Canvas canvas) {

        Vec3 t[] = new Vec3[8];
//        Double avgZ[] = new Double[6];
//        int order[] = new int[6];

        for (int i = 0; i < 8; i++) {
            // Rotate the vertex around X, next around Y, and then around Z.
            t[i] = vertices[i].rotateX(ax).rotateY(ay).rotateZ(az);

            // Finally, map the vertex from 3D to 2D.
            t[i] = t[i].project(getWidth(), getHeight(), 256, 4);
        }

        // Compute the average Z value of each face.
        for (int i = 0; i < 6; i++) {

            Face face = faces[i];
            face.zOrder = (t[face.left].z + t[face.top].z + t[face.bottom].z + t[face.right].z) / 4;
//            order[i] = i;
        }


        for(int i = 0; i < 6 ; i++){
            sortedFaces[i] = faces[i];
        }

        Arrays.sort(sortedFaces, (l,r)->Double.compare(r.zOrder,l.zOrder));







        for (int i = 0 ; i < sortedFaces.length; i++) {
            Face face = sortedFaces[i];
            path.reset();
            path.moveTo((float) t[face.left].x,    (float) t[face.left].y);
            path.lineTo((float) t[face.top].x,   (float) t[face.top].y);
            path.lineTo((float) t[face.bottom].x, (float) t[face.bottom].y);
            path.lineTo((float) t[face.right].x,  (float) t[face.right].y);
            path.close();

            fillPaint.setColor(face.color);
            canvas.drawPath(path, fillPaint);
            canvas.drawPath(path,strokePaint);
        }
    }


    class Face{
        final int left;
        final int bottom;
        final int top;
        final int right;
        public double zOrder;
        int color;
        public Face(int l,int t, int b,int r,int color){
           this.left = l;
            this.top = t;
            this.bottom = b;
           this.right = r;
           this.color = color;
        }
    }
}

