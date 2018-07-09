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
    fibermodel fiberobject;


    ArrayList<fibermodel> listfiber = new ArrayList();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fiberrowaddnew);

        Intent myIntent = getIntent(); // gets the previously created intent
        siteid = myIntent.getStringExtra("siteid"); // will return "FirstKeyValue"
        updatedby= myIntent.getStringExtra("updatedby");
        index= myIntent.getLongExtra("index",0);


        String routenamestr =  myIntent.getStringExtra("routename");
        String corestr =  myIntent.getStringExtra("core");
        String routeport1str =  myIntent.getStringExtra("routeport1");
        String routeport2str =  myIntent.getStringExtra("routeport2");
        String coreport1str =  myIntent.getStringExtra("coreport1");
        String coreport2str =  myIntent.getStringExtra("coreport2");
        String rowport1str =  myIntent.getStringExtra("rowport1");
        String rowport2str =  myIntent.getStringExtra("rowport2");
        String verport1str =  myIntent.getStringExtra("verport1");
        String verport2str =  myIntent.getStringExtra("verport2");
        String blockport1str =  myIntent.getStringExtra("blockport1");
        String blockport2str =  myIntent.getStringExtra("blockport2");
        String pinport1str =  myIntent.getStringExtra("pinport1");
        String pinport2str =  myIntent.getStringExtra("pinport2");
        String indexport1str =  myIntent.getStringExtra("indexport1");
        String indexport2str =  myIntent.getStringExtra("indexport2");
        String manufactureport1str =  myIntent.getStringExtra("manufactureport1");
        String manufactureport2str =  myIntent.getStringExtra("manufactureport2");
        String typeport1str =  myIntent.getStringExtra("typeport1");
        String typeport2str =  myIntent.getStringExtra("typeport2");



//

        final EditText routenameet = (EditText)findViewById(R.id.routenameet);
        routenameet.setText(routenamestr);

        final EditText coreet = (EditText)findViewById(R.id.coreet);
        coreet.setText(corestr);

        final EditText routeport1et = (EditText)findViewById(R.id.routeport1et);
        routeport1et.setText(routeport1str);
        final EditText routeport2et = (EditText)findViewById(R.id.routeport2et);
        routeport2et.setText(routeport2str);

        final EditText coreport1et = (EditText)findViewById(R.id.coreport1et);
        coreport1et.setText(coreport1str);

        final EditText coreport2et = (EditText)findViewById(R.id.coreport2et);
        coreport2et.setText(coreport2str);

        final EditText rowport1et = (EditText)findViewById(R.id.row1et);
        rowport1et.setText(rowport1str);
        final EditText rowport2et = (EditText)findViewById(R.id.row2et);
        rowport2et.setText(rowport2str);

        final EditText verport1et = (EditText)findViewById(R.id.ver1et);
        verport1et.setText(verport1str);
        final EditText verport2et = (EditText)findViewById(R.id.ver2et);
        verport2et.setText(verport2str);

        final EditText blockport1et = (EditText)findViewById(R.id.block1et);
        blockport1et.setText(blockport1str);
        final EditText blockport2et = (EditText)findViewById(R.id.block2et);
        blockport2et.setText(blockport2str);

        final EditText pinport1et = (EditText)findViewById(R.id.pinport1et);
        pinport1et.setText(pinport1str);
        final EditText pinport2et = (EditText)findViewById(R.id.pinport2et);
        pinport2et.setText(pinport2str);
        final EditText index1et = (EditText)findViewById(R.id.index1et);
        index1et.setText(indexport1str);

        final EditText index2et = (EditText)findViewById(R.id.index2et);
        index2et.setText(indexport2str);

        final EditText manufacture1et = (EditText)findViewById(R.id.manufacture1et);
        manufacture1et.setText(manufactureport1str);
        final EditText manufacture2et = (EditText)findViewById(R.id.manufacture2et);
        manufacture2et.setText(manufactureport2str);

        final EditText type1et = (EditText)findViewById(R.id.type1et);
        type1et.setText(typeport1str);

        final EditText type2et = (EditText)findViewById(R.id.type2et);
        type2et.setText(typeport2str);

        TextView indexclick = (TextView)findViewById(R.id.indexclick);
        indexclick.setText(index.toString());





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

                updatesavesfp();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updatesavesfp() {

        final EditText routename = (EditText)findViewById(R.id.routenameet);

        final EditText core = (EditText)findViewById(R.id.coreet);
        final EditText routeport1 = (EditText)findViewById(R.id.routeport1et);
        final EditText routeport2 = (EditText)findViewById(R.id.routeport2et);
        final EditText coreport1 = (EditText)findViewById(R.id.coreport1et);
        final EditText coreport2 = (EditText)findViewById(R.id.coreport2et);
        final EditText row1 = (EditText)findViewById(R.id.row1et);
        final EditText row2 = (EditText)findViewById(R.id.row2et);
        final EditText ver1 = (EditText)findViewById(R.id.ver1et);
        final EditText ver2 = (EditText)findViewById(R.id.ver2et);
        final EditText block1 = (EditText)findViewById(R.id.block1et);
        final EditText block2 = (EditText)findViewById(R.id.block2et);
        final EditText pinport1 = (EditText)findViewById(R.id.pinport1et);
        final EditText pinport2 = (EditText)findViewById(R.id.pinport2et);
        final EditText index1 = (EditText)findViewById(R.id.index1et);
        final EditText index2 = (EditText)findViewById(R.id.index2et);
        final EditText manufacture1 = (EditText)findViewById(R.id.manufacture1et);
        final EditText manufacture2 = (EditText)findViewById(R.id.manufacture2et);
        final EditText type1 = (EditText)findViewById(R.id.type1et);
        final EditText type2 = (EditText)findViewById(R.id.type2et);


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


}
