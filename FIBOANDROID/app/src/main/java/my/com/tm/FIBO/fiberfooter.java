package my.com.tm.FIBO;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class fiberfooter extends AppCompatActivity {

    ListView lvfibersdf;
    fibersdfadaptor adaptormarker;
    String siteid,updatedby;

    ArrayList<fibermodel> listfiber = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.footerfiberadd);

        Intent myIntent = getIntent(); // gets the previously created intent
        siteid = myIntent.getStringExtra("siteid"); // will return "FirstKeyValue"
        updatedby= myIntent.getStringExtra("updatedby");



        String routenamestr =  myIntent.getStringExtra("routename");
        String corestr =  myIntent.getStringExtra("core");
        String routeport1str =  myIntent.getStringExtra("routeport1");

        String coreport1str =  myIntent.getStringExtra("coreport1");

        String rowport1str =  myIntent.getStringExtra("rowport1");

        String verport1str =  myIntent.getStringExtra("verport1");

        String blockport1str =  myIntent.getStringExtra("blockport1");

        String pinport1str =  myIntent.getStringExtra("pinport1");

        String indexport1str =  myIntent.getStringExtra("indexport1");

        String manufactureport1str =  myIntent.getStringExtra("manufactureport1");

        String typeport1str =  myIntent.getStringExtra("typeport1");

        final EditText routenameet = (EditText)findViewById(R.id.footername);
        routenameet.setText(routenamestr);

        final EditText coreet = (EditText)findViewById(R.id.corefooter);
        coreet.setText(corestr);

        final EditText coreportet = (EditText)findViewById(R.id.coreportfooter);
        coreportet.setText(coreport1str);

        final EditText routeport1et = (EditText)findViewById(R.id.routenameportfooter);
        routeport1et.setText(routeport1str);






        final EditText rowport1et = (EditText)findViewById(R.id.rowportfooter);
        rowport1et.setText(rowport1str);


        final EditText verport1et = (EditText)findViewById(R.id.verportfooter);
        verport1et.setText(verport1str);

        final EditText blockport1et = (EditText)findViewById(R.id.blockportfooter);
        blockport1et.setText(blockport1str);


        final EditText pinport1et = (EditText)findViewById(R.id.pinportfooter);
        pinport1et.setText(pinport1str);

        final EditText index1et = (EditText)findViewById(R.id.indexportfooter);
        index1et.setText(indexport1str);



        final EditText manufacture1et = (EditText)findViewById(R.id.manufactureportfooter);
        manufacture1et.setText(manufactureport1str);


        final EditText type1et = (EditText)findViewById(R.id.typeportfooter);
        type1et.setText(typeport1str);


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


        final EditText routenameet = (EditText)findViewById(R.id.footername);
        String routenamestr =   routenameet.getText().toString();


        final EditText coreet = (EditText)findViewById(R.id.corefooter);
        String corestr =   coreet.getText().toString();

        final EditText routeport1et = (EditText)findViewById(R.id.routenameportfooter);
        String routeportstr =   routeport1et.getText().toString();

        final EditText coreportet = (EditText)findViewById(R.id.coreportfooter);
        String coreportstr =   coreportet.getText().toString();



        final EditText rowport1et = (EditText)findViewById(R.id.rowportfooter);

        String rowteportstr =   rowport1et.getText().toString();

        final EditText verport1et = (EditText)findViewById(R.id.verportfooter);
        String verportstr =   verport1et.getText().toString();

        final EditText blockport1et = (EditText)findViewById(R.id.blockportfooter);
        String blockportstr =   blockport1et.getText().toString();


        final EditText pinport1et = (EditText)findViewById(R.id.pinportfooter);
        String pinportstr =   pinport1et.getText().toString();

        final EditText index1et = (EditText)findViewById(R.id.indexportfooter);
        String indexportstr =   index1et.getText().toString();



        final EditText manufacture1et = (EditText)findViewById(R.id.manufactureportfooter);
        String manufacturestr =   manufacture1et.getText().toString();


        final EditText type1et = (EditText)findViewById(R.id.typeportfooter);
        String typestr =   type1et.getText().toString();


        if(routenamestr.isEmpty()){

            Toast.makeText(getApplicationContext(), "Route Name Cannot be Empty", Toast.LENGTH_LONG).show();

        }else {

            Toast.makeText(getApplicationContext(), siteid, Toast.LENGTH_LONG).show();


            addfibersdffooter updatefiber = new addfibersdffooter(getApplicationContext());
            updatefiber.execute(siteid, routenamestr, corestr, routeportstr, coreportstr, rowteportstr, verportstr, blockportstr, pinportstr, indexportstr, manufacturestr, typestr, updatedby);

        }



    }

}
