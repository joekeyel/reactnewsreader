package my.com.tm.FIBO;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by hasanulisyraf on 04/07/2018.
 */

public class finalreport extends AppCompatActivity{


    String siteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalreport);

         siteid = getIntent().getStringExtra("siteid");

        WebView graphview = (WebView)findViewById(R.id.webviewreport);

        graphview.getSettings().setJavaScriptEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            graphview.getSettings().setAllowUniversalAccessFromFileURLs(true);
            graphview.getSettings().setAllowFileAccessFromFileURLs(true);
        }

        graphview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        graphview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        graphview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        graphview.setVerticalScrollBarEnabled(true);


        graphview.getSettings().setJavaScriptEnabled(true);



        String query="siteid="+siteid;
        graphview.loadUrl("http://58.27.84.188/fibersdflisttable.php?"+query);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu4, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.downloadpdf:

                String query="siteid="+siteid;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://58.27.84.188/fibersdflisttable.php?"+query));

                startActivity(browserIntent);

                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
