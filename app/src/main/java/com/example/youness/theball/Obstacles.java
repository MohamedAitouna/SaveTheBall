package com.example.youness.theball;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.content.Context;
import android.util.DisplayMetrics;

public class Obstacles {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int directionX; // direction
    private int directionY;
    public Obstacles(Bitmap img,int a,int b)
    {
        this.bitmap=img;
        this.x=a;
        this.y=b;
        this.directionX=1;
        this.directionY=1;
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
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
    void update()
    {
        x=x+5*directionX;
        y=y+5*directionY;
    }
    void changedirectionX()
    {
        this.directionX=-this.directionX;
    }
    void changedirectionY()
    {
        this.directionY=-this.directionY;
    }
    void setDirectionX(int n)
    {
        this.directionX=n;
    }
    void setDirectionY(int n)
    {
        this.directionY=n;
    }
}
