package my.com.tm.FIBO;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by hasanulisyraf on 04/07/2018.
 */

public class finalreport extends AppCompatActivity{


    String siteid;
    int heightwebview;
    WebView graphview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalreport);

         siteid = getIntent().getStringExtra("siteid");

        graphview = (WebView)findViewById(R.id.webviewreport);

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

       graphview.setVerticalScrollBarEnabled(false);





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



                ScrollView graphview = (ScrollView) findViewById(R.id.layoutTxtDescription);

                File file = saveBitMap(this, graphview);    //which view you want to pass that view as parameter
                if (file != null) {
                    Log.i("TAG", "Drawing saved to the gallery!");
                    Toast.makeText(finalreport.this, "File Save to your gallery", Toast.LENGTH_SHORT)
                            .show();


                } else {
                    Log.i("TAG", "Oops! Image could not be saved.");
                    Toast.makeText(finalreport.this, "Oops! Image could not be saved.", Toast.LENGTH_SHORT)
                            .show();
                }




                return true;

            case R.id.sharepdf:
                ScrollView graphview2 = (ScrollView) findViewById(R.id.layoutTxtDescription);

                File file2 = saveBitMap(this, graphview2);    //which view you want to pass that view as parameter
                if (file2 != null) {


                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/jpeg");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse(file2.getAbsolutePath()));
                    share.setPackage("com.whatsapp");//package name of the app
                    startActivity(Intent.createChooser(share, "Share Image"));

                } else {
                    Log.i("TAG", "Oops! Image could not be saved.");
                    Toast.makeText(finalreport.this, "Oops! Image could not be saved.", Toast.LENGTH_SHORT)
                            .show();
                }





                return true;


            default:
                return super.onOptionsItemSelected(item);
        }



}

    private File saveBitMap(Context context, ScrollView drawView){
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"FIBO");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("ATG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() +File.separator+ siteid+"_"+System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);


        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        return pictureFile;
    }
    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(ScrollView view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getChildAt(0).getWidth(), view.getChildAt(0).getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[] { path },null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
