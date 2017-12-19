package com.example.youness.theball;
        import android.graphics.Canvas;
        import android.util.Log;
        import android.view.SurfaceHolder;



public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();
    private int x=0;
    private int i=0;
    // Surface holder pour  acceder a la surface physique
    private SurfaceHolder surfaceHolder;

    private MainGamePanel gamePanel;

    private boolean running;
    public static int a=0;
    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    public static int getscore()
    {
        return a;
    }
    public static void setscore()
    {
       a=0;
    }

    @Override
    public void run() {
        Canvas cv;
        long tickCount = 0L;
        Log.d(TAG, "Starting game loop");

        while (running) {
            cv=null;
            try{
                cv = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    this.gamePanel.update();
                    this.gamePanel.onDraw(cv);
                }
            }
            finally {
                if (cv != null)
                {
                    surfaceHolder.unlockCanvasAndPost(cv);
                }
            }
            tickCount++;
            if(i==0){while(x==0){x=gamePanel.test();}i++;}
            a++;
            // update game state
            // render state to the screen
        }
        Log.d(TAG, "Game loop executed " + tickCount + " times");
    }

}

