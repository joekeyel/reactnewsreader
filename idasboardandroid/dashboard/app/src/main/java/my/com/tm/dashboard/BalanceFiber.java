package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import my.com.tm.dashboard.R;

public class BalanceFiber extends AppCompatActivity {


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
                getJSON();
//                Intent Intent = new Intent(view.getContext(), BalanceFiber.class);
//                view.getContext().startActivity(Intent);
            }
        });


        getJSON();

    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("fiber");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString("oldfiber");
                String b = jo.getString("newfiber");
                String c = jo.getString("state");
                String d = jo.getString("planvdsl");



                HashMap<String,String> employees = new HashMap<>();
                employees.put("oldfiber",a);
                employees.put("newfiber",b);
                employees.put("state",c);
                employees.put("planvdsl",d);

                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.listactualall,
                new String[]{"oldfiber","newfiber","state","planvdsl"},

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
//                loading = ProgressDialog.show(getApplicationContext(),"Loading Data","Wait...",false,false);
                //    loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                //   loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                JSON_STRING = s;
                //  showData();
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_BAL_FIBER);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void showEmployees(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("fiber");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString("oldfiber");
                String b = jo.getString("newfiber");
                String c = jo.getString("state");
                String d = jo.getString("planvdsl");



                HashMap<String,String> employees = new HashMap<>();
                employees.put("oldfiber",a);
                employees.put("newfiber",b);
                employees.put("state",c);
                employees.put("planvdsl",d);

                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.listactualall,
                new String[]{"oldfiber","newfiber","state","planvdsl"},

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
//                loading = ProgressDialog.show(getApplicationContext(),"Loading Data","Wait...",false,false);
                //    loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                //   loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                JSON_STRING = s;
                //  showData();
                showEmployees();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest("http://58.27.84.166/mcconline/MCC%20Online%20V3/query_searchbalance2.php"+"?newfiber="+tmnode);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}

