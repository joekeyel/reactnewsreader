package my.com.tm.dashboard;

/**
 * Created by user on 5/12/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import my.com.tm.dashboard.R;

public class Starting extends AppCompatActivity {

    private ImageView GeoOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting);

        GeoOps = (ImageView) findViewById(R.id.GeoOps);

        Animation TMGeoOps = AnimationUtils.loadAnimation(this,R.anim.fade);
        GeoOps.startAnimation(TMGeoOps);
//        getSupportActionBar().setShowHideAnimationEnabled(false);

        final Intent i = new Intent(this,Start.class);
        Thread timer = new Thread() {

            public void run () {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}