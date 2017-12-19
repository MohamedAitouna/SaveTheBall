package com.example.youness.theball;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
public class Ball {
    private Bitmap bitmap;
    private int x;
    private int y;
    private boolean touched;

    public Ball(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }
    public int getX()
    {
        return this.x;
    }
    public void setX(int a)
    {
        this.x=a;
    }

    public int getY()
    {
        return this.y;
    }
    public void setY(int a)
    {
        this.y=a;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public boolean isTouched() {
        return touched;
    }
    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
    //

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
    }

}
