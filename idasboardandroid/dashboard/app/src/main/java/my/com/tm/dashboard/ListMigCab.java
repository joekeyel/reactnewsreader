package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import my.dashboard.R;

public class ListMigCab extends AppCompatActivity {

    private ProgressDialog loading;

    private ListView listView;
    EditText editext;
    Button btnsearch,back,home;
    String sitestr;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mig_cab);

        Intent i = getIntent();
        listView = (ListView) findViewById(R.id.list);
        sitestr = i.getStringExtra("newsubb");

        Toast.makeText(getApplicationContext(), sitestr,
                Toast.LENGTH_LONG).show();

        getJSON(sitestr);
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("listbycategory");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString("newsubb");
                String b = jo.getString("oldsubb");
                String c = jo.getString("state");
                String d = jo.getString("migdate");


                HashMap<String,String> employees = new HashMap<>();
                employees.put("newsubb",a);
                employees.put("oldsubb",b);
                employees.put("state",c);
                employees.put("migdate",d);

                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.listactualall,
                new String[]{"newsubb","oldsubb","state","migdate"},

                new int[]{R.id.satu, R.id.dua,R.id.tiga, R.id.empat});

        listView.setAdapter(adapter);

    }


    private void getJSON(final String str){

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
                    String s = null;
                    try {
                        s = rh.sendGetRequest("http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listbycategory.php?Projecttype="+ URLEncoder.encode(str, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return s;
                }
            }
            GetJSON gj = new GetJSON();
            gj.execute();

    }
}
