package my.com.tm.FIBO;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class fiberdetails extends AppCompatActivity {

    ListView lvfibersdf;
    fibersdfadaptor adaptormarker;

    ArrayList<fibermodel> listfiber = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fiberdetails);

         lvfibersdf = (ListView)findViewById(R.id.lvfibersdf);

        fibermodel start = new fibermodel();
        fibermodel middle = new fibermodel();


        listfiber.add(start);
        listfiber.add(middle);


        View footerView =  ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerfiber, null, false);

         adaptormarker = new fibersdfadaptor(getApplicationContext(),R.layout.fiberrow,listfiber);
        lvfibersdf.setAdapter(adaptormarker);
        lvfibersdf.addFooterView(footerView);
        lvfibersdf.addHeaderView(footerView);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu2, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.addsfp:

                fibermodel newfiber = new fibermodel();
                newfiber.setStatus("NEW");
                listfiber.add(newfiber);
                adaptormarker.notifyDataSetChanged();

                lvfibersdf.post(new Runnable() {
                    @Override
                    public void run() {
                        // Select the last row so it will scroll into view...
                        lvfibersdf.setSelection(adaptormarker.getCount() - 1);
                    }
                });



                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
