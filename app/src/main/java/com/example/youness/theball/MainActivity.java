package com.example.youness.theball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // full screen
        setContentView(new MainGamePanel(this));
        Log.d(TAG, "View added");
    }
    @Override
    protected void onDestroy() {
        Intent i = new Intent(getApplicationContext(), Main22Activity.class);
        startActivity(i);
        Log.d(TAG, "Destroying...");
        super.onDestroy();

    }
    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }
}
