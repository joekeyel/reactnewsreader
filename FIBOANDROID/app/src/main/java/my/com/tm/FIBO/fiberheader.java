package my.com.tm.FIBO;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class fiberheader extends AppCompatActivity {

    ListView lvfibersdf;
    fibersdfadaptor adaptormarker;

    ArrayList<fibermodel> listfiber = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.headerfiberadd);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu3, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.delesfp:


                return true;



            case R.id.savefsp:


                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
