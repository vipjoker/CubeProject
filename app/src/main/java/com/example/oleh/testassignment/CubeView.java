package com.example.oleh.testassignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.renderscript.Matrix4f;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

public class CubeView extends View {

    private Paint fillPaint;
    private int cubeColor = Color.RED;
    private Paint strokePaint;
    private Paint shadowPaint;

    protected Vec3 vertices[];

    protected Face faces[];
    private Face sortedFaces[] = new Face[6];
    private Path path = new Path();

    protected float ax = 0;

    protected float ay = 50;

    protected float az = 0;
    private int width = 1;
    private int height = 1;
    private int length = 1;
    protected float lastTouchX;
    protected float lastTouchY;
    private Vec3[] translated = new Vec3[8];
    private RectF backgroundRect;


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // Initialize the cube geometry.

        // Allow the view to receive touch input.
        setFocusableInTouchMode(true);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            lastTouchX = event.getX();
//            lastTouchY = event.getY();
//        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            float dx = (event.getX() - lastTouchX) / 100.0f;
//            float dy = (event.getY() - lastTouchY) / 100.0f;
//            ax += dy;
//            ay -= dx;
//            postInvalidate();
//        }
//        return true;
//    }


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

        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE, shadowPaint);
        shadowPaint.setShadowLayer(10, 0, 10, Color.GRAY);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setColor(Color.WHITE);



        updateVertices();
        faces = new Face[]{
                new Face(0, 1, 2, 3, Color.WHITE),
                new Face(1, 5, 6, 2, Color.WHITE),
                new Face(5, 4, 7, 6, Color.WHITE),
                new Face(4, 0, 3, 7, Color.WHITE),
                new Face(0, 4, 5, 1, Color.WHITE),
                new Face(3, 2, 6, 7, Color.WHITE)};

    }

    public void updateVertices(){
        vertices = new Vec3[]{
                new Vec3(-length, width, -height),
                new Vec3(length, width, -height),
                new Vec3(length, -width, -height),
                new Vec3(-length, -width, -height),
                new Vec3(-length, width, height),
                new Vec3(length, width, height),
                new Vec3(length, -width, height),
                new Vec3(-length, -width, height)
        };
    }

    public void setDimmensions(int width,int length,int height){
        this.width = width;
        this.height = height;
        this.length = length;
        updateVertices();
    }


    public void setColor(int color) {
        for (Face f : faces) {
            f.color = color;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {

        int padding = 20;

        int radius = 20;
        if (backgroundRect == null) {
            backgroundRect = new RectF(padding, padding, canvas.getWidth() - padding, canvas.getHeight() - padding);
        }
        canvas.drawRoundRect(backgroundRect, radius, radius, shadowPaint);
//        Double avgZ[] = new Double[6];
//        int order[] = new int[6];
        ay++;
        for (int i = 0; i < 8; i++) {
            // Rotate the vertex around X, next around Y, and then around Z.
            translated[i] = vertices[i].rotateX(ax).rotateY(ay).rotateZ(az);

            // Finally, map the vertex from 3D to 2D.
            translated[i] = translated[i].project(getWidth(), getHeight(), 120, 4);
        }

        // Compute the average Z value of each face.
        for (int i = 0; i < 6; i++) {

            Face face = faces[i];
            face.zOrder = (translated[face.left].z + translated[face.top].z + translated[face.bottom].z + translated[face.right].z) / 4;
//            order[i] = i;
        }


        System.arraycopy(faces, 0, sortedFaces, 0, 6);

        Arrays.sort(sortedFaces, (l, r) -> Double.compare(r.zOrder, l.zOrder));


        for (Face face : sortedFaces) {
            path.reset();
            path.moveTo((float) translated[face.left].x, (float) translated[face.left].y);
            path.lineTo((float) translated[face.top].x, (float) translated[face.top].y);
            path.lineTo((float) translated[face.bottom].x, (float) translated[face.bottom].y);
            path.lineTo((float) translated[face.right].x, (float) translated[face.right].y);
            path.close();

            fillPaint.setColor(face.color);
            canvas.drawPath(path, fillPaint);
            canvas.drawPath(path, strokePaint);
        }
        invalidate();
    }


    class Face {
        final int left;
        final int bottom;
        final int top;
        final int right;
        public double zOrder;
        int color;

        public Face(int l, int t, int b, int r, int color) {
            this.left = l;
            this.top = t;
            this.bottom = b;
            this.right = r;
            this.color = color;
        }
    }
}

