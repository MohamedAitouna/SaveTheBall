package com.example.youness.theball;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import java.util.ArrayList;
import java.util.*;
import android.content.Intent;

public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();
	
    private Ball ball;
    private Bitmap exit;
    private ArrayList<Obstacles> obstacles=new ArrayList<Obstacles>();  ;
    private MainThread thread;

    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // create the game loop thread
        thread = new MainThread(getHolder(), this);


        // make the GamePanel focusable so it can handle events
        setFocusable(true);

        ball = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.bluegray), 50, 50);
        exit = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        // Remplir l'array list
        // à refaire
        Random rand = new Random();
        int i,n,m;
        for(i=1;i<=6;i++) {
            n = rand.nextInt(400)+150; // problem
            m = rand.nextInt(400)+150; // problem
            obstacles.add(new Obstacles(BitmapFactory.decodeResource(getResources(), R.drawable.obst), n, m));
            n=rand.nextInt(2);
            m=rand.nextInt(2);
            if(n==0)
                n=-1;
            if(m==0)
                m=-1;
            obstacles.get(i-1).setDirectionX(n);
            obstacles.get(i-1).setDirectionY(m);
        }
        Log.d(TAG, "obstacle width: "+obstacles.get(0).getBitmap().getWidth()+" obstacle height: "+obstacles.get(0).getBitmap().getHeight());
        Log.d(TAG, "Ball width: "+ball.getBitmap().getWidth()+" ball height: "+ball.getBitmap().getHeight()+" !! "+getWidth());

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and
        // we can safely start the game loop
        Iterator<Obstacles> it = obstacles.iterator();
        while (it.hasNext()) {
            Obstacles temp=it.next();
           Random rand = new Random();
            int x = rand.nextInt(getWidth()-100)+50; // problem
            int y = rand.nextInt(getHeight()-100)+50; // problem
            temp.setX(x);
            temp.setY(y);
        }
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // delegating event handling to the droid
            ball.handleActionDown((int)event.getX(), (int)event.getY());

            // check if in the lower part of the screen we exit
            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures
            if (ball.isTouched()) {
                // the droid was picked up and is being dragged
                ball.setX((int)event.getX());
                ball.setY((int)event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (ball.isTouched()) {
                ball.setTouched(false);
            }
        }
        return true;
    }

    public int test(){
        if (ball.isTouched()) return 1;else return 0;}
    // fonction de
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(exit, getWidth()/2-40, getHeight() -50 ,null);
        ball.draw(canvas);
        Iterator<Obstacles> it = obstacles.iterator();
        while (it.hasNext()) {
            Obstacles temp=it.next();

           /* Random rand = new Random();
            int x = rand.nextInt(getWidth()-100)+50; // problem
            int y = rand.nextInt(getHeight()-100)+50; // problem
            temp.setX(x);
            temp.setY(y);*/

            temp.draw(canvas);
        }

    }
    void update()
    {

        Iterator<Obstacles> it = obstacles.iterator();
        while (it.hasNext())
        {

            Obstacles temp=it.next();
            temp.update();
            if(temp.getX() + temp.getBitmap().getWidth() > getWidth() || temp.getX()  < 0)
                temp.changedirectionX();
            if(temp.getY() + temp.getBitmap().getHeight() > getHeight() || temp.getY()  < 0)
                temp.changedirectionY();

            if ( (int)java.lang.Math.abs(temp.getX() - ball.getX()) < (ball.getBitmap().getWidth()+temp.getBitmap().getWidth())/2
                    &&
                    (int)java.lang.Math.abs(temp.getY() - ball.getY()) < (ball.getBitmap().getHeight()- temp.getBitmap().getHeight())/2
                    )
            {
                Log.d(TAG, "Update and Collision" );
                // collision
                thread.setRunning(false);
               ((Activity)getContext()).finish();

            }
        }

    }

}

