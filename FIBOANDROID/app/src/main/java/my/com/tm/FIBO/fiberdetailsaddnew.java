package my.com.tm.FIBO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class fiberdetailsaddnew extends AppCompatActivity {

    ListView lvfibersdf;
    fibersdfadaptor adaptormarker;
    String siteid,updatedby;
    Long index;

    ArrayList<fibermodel> listfiber = new ArrayList();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fiberrowaddnew);

        Intent myIntent = getIntent(); // gets the previously created intent
        siteid = myIntent.getStringExtra("siteid"); // will return "FirstKeyValue"
        updatedby= myIntent.getStringExtra("updatedby");
        index= myIntent.getLongExtra("index",0);

        TextView indexclick = (TextView)findViewById(R.id.indexclick);
        indexclick.setText(index.toString());


        final EditText routename = (EditText)findViewById(R.id.routename);
        final EditText core = (EditText)findViewById(R.id.core);
        final EditText routeport1 = (EditText)findViewById(R.id.routeport1);
        final EditText routeport2 = (EditText)findViewById(R.id.routeport2);
        final EditText coreport1 = (EditText)findViewById(R.id.coreport1);
        final EditText coreport2 = (EditText)findViewById(R.id.coreport2);
        final EditText row1 = (EditText)findViewById(R.id.row1);
        final EditText row2 = (EditText)findViewById(R.id.row2);
        final EditText ver1 = (EditText)findViewById(R.id.ver1);
        final EditText ver2 = (EditText)findViewById(R.id.ver2);
        final EditText block1 = (EditText)findViewById(R.id.block1);
        final EditText block2 = (EditText)findViewById(R.id.block2);
        final EditText pinport1 = (EditText)findViewById(R.id.pinport1);
        final EditText pinport2 = (EditText)findViewById(R.id.pinport2);
        final EditText index1 = (EditText)findViewById(R.id.index1);
        final EditText index2 = (EditText)findViewById(R.id.index2);
        final EditText manufacture1 = (EditText)findViewById(R.id.manufacture1);
        final EditText manufacture2 = (EditText)findViewById(R.id.manufacture2);
        final EditText type1 = (EditText)findViewById(R.id.type1);
        final EditText type2 = (EditText)findViewById(R.id.type2);

        Button updatedata = (Button)findViewById(R.id.updatesdf);

        updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String routenamestr = routename.getText().toString();
                String corestr = core.getText().toString();
                String routeport1str = routeport1.getText().toString();
                String routeport2str = routeport2.getText().toString();
                String coreport1str = coreport1.getText().toString();
                String coreport2str = coreport2.getText().toString();
                String row1str = row1.getText().toString();
                String row2str = row2.getText().toString();
                String ver1str = ver1.getText().toString();
                String ver2str = ver2.getText().toString();
                String block1str = block1.getText().toString();
                String block2str = block2.getText().toString();
                String pinport1str = pinport1.getText().toString();
                String pinport2str = pinport2.getText().toString();
                String index1str = index1.getText().toString();
                String index2str = index2.getText().toString();
                String manufacture1str = manufacture1.getText().toString();
                String manufacture2str = manufacture2.getText().toString();
                String type1str = type1.getText().toString();
                String type2str = type2.getText().toString();

                if(routenamestr.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Route Name Cannot be Empty", Toast.LENGTH_LONG).show();

                }else {

                    addfibersdf updatefiber = new addfibersdf(getApplicationContext());
                    updatefiber.execute(siteid, routenamestr, corestr, routeport1str, routeport2str, coreport1str, coreport2str, row1str, row2str, ver1str, ver2str, block1str, block2str, pinport1str, pinport2str, index1str, index2str, manufacture1str, manufacture2str, type1str, type2str, updatedby, index.toString());

                }

            }
        });




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
