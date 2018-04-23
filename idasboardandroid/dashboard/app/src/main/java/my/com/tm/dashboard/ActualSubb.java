package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import my.dashboard.R;

public class ActualSubb extends AppCompatActivity {


    private ListView listView;
    EditText editext;

    View myView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_subb);

        editext = (EditText) findViewById(R.id.search);
        final Button searching =(Button) findViewById(R.id.btnsearch);
        final Button viewall =(Button) findViewById(R.id.viewall);

        listView = (ListView) findViewById(R.id.list);

        searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getJSONs(editext.getText().toString().toUpperCase());

            }
        });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSON();}
        });

        getJSON();

    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<ModelActualSubb> listactual = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("Subb");

            for(int i = 0; i<result.length(); i++){

                //GatedModel gatedModel = new GatedModel();
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString("newsubb");
                String b = jo.getString("oldsubb");
                String c = jo.getString("state");
                String d = jo.getString("migdate");
                String e = jo.getString("onplan");

                ModelActualSubb gatedModel = new ModelActualSubb();

                gatedModel.setNewc(a);
                gatedModel.setOld(b);
                gatedModel.setRegion(c);
                gatedModel.setMigdate(d);
                gatedModel.setPlan(e);

                listactual.add(gatedModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final AdapterActualSubb sanoAdapter = new AdapterActualSubb(getApplicationContext(),R.layout.listnewactual,listactual);
        final ListView sanoview = (ListView) findViewById(R.id.list);
        sanoview.setAdapter(sanoAdapter);

        listView.setAdapter(sanoAdapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_GET_ACT_SUBB);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showEmployees(){
        JSONObject jsonObject = null;
        ArrayList<ModelActualSubb> listactual = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("Subb");

            for(int i = 0; i<result.length(); i++){

                //GatedModel gatedModel = new GatedModel();
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString("newsubb");
                String b = jo.getString("oldsubb");
                String c = jo.getString("state");
                String d = jo.getString("migdate");
                String e = jo.getString("onplan");

                ModelActualSubb gatedModel = new ModelActualSubb();

                gatedModel.setNewc(a);
                gatedModel.setOld(b);
                gatedModel.setRegion(c);
                gatedModel.setMigdate(d);
                gatedModel.setPlan(e);

                listactual.add(gatedModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final AdapterActualSubb sanoAdapter = new AdapterActualSubb(getApplicationContext(),R.layout.listnewactual,listactual);
        final ListView sanoview = (ListView) findViewById(R.id.list);
        sanoview.setAdapter(sanoAdapter);

        listView.setAdapter(sanoAdapter);


    }


    private void getJSONs(final String tmnode){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                JSON_STRING = s;
                showEmployees();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest("http://58.27.84.166/mcconline/MCC%20Online%20V3/query_searchactual1.php"+"?newsubb="+tmnode);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}

