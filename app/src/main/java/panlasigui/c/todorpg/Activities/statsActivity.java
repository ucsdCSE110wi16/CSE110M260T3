package panlasigui.c.todorpg.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import panlasigui.c.todorpg.R;

/**
 * Created by Evan Green on 2/28/2016.
 */

public class statsActivity extends Activity {

    private static final int PROGRESS = 0x1;

    private ProgressBar intProgress = (ProgressBar) findViewById(R.id.progressBar);
    private ProgressBar charmProgress = (ProgressBar) findViewById(R.id.progressBar4);
    private ProgressBar fitProgress = (ProgressBar) findViewById(R.id.progressBar2);
    private ProgressBar healthProgress = (ProgressBar) findViewById(R.id.progressBar3);
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.content_stats);


        // Start lengthy operation in a background thread
    }
    public void updateInt(int prog) {
        new Thread(new Runnable() {
            public void run() {

                // Update the progress bar
                mHandler.post(new Runnable() {
                    public void run() {
                        System.out.println(intProgress.getProgress());
                        intProgress.setProgress(12);
                    }
                });

            }
        }).start();
    }

}



