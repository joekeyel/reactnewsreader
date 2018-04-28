package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import my.com.tm.dashboard.R;

public class PlanFiber extends AppCompatActivity {


    private ListView listView;
    EditText editext;

    View myView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_fiber);

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
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_PLAN_FIBER);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_E);
                String b = jo.getString(Config.TAG_F);
                String c = jo.getString(Config.TAG_G);
                String d = jo.getString(Config.TAG_H);



                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_E,a);
                employees.put(Config.TAG_F,b);
                employees.put(Config.TAG_G,c);
                employees.put(Config.TAG_H,d);

                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.listactualall,
                new String[]{Config.TAG_E,Config.TAG_F,Config.TAG_G,Config.TAG_H},

                new int[]{R.id.satu,R.id.dua, R.id.tiga,  R.id.empat
                });

        listView.setAdapter(adapter);

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
                String s = rh.sendGetRequest(Config.URL_GET_PLAN_FIBER);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    private void showEmployees(){
        String gate = editext.getText().toString().trim();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(gate)){
            Toast.makeText(getApplicationContext(),"Please enter cabinet id",Toast.LENGTH_LONG).show();
            return;
        }
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("searchfiber");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_E);
                String b = jo.getString(Config.TAG_F);
                String c = jo.getString(Config.TAG_G);
                String d = jo.getString(Config.TAG_H);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_E,a);
                employees.put(Config.TAG_F,b);
                employees.put(Config.TAG_G,c);
                employees.put(Config.TAG_H,d);

                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.listactualall,
                new String[]{Config.TAG_E,Config.TAG_F,Config.TAG_G,Config.TAG_H},

                new int[]{R.id.satu,R.id.dua, R.id.tiga,  R.id.empat
                });

        listView.setAdapter(adapter);

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
                String s = rh.sendGetRequest("http://58.27.84.166/mcconline/MCC%20Online%20V3/query_searchfiber.php"+"?newfiber="+tmnode);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}

