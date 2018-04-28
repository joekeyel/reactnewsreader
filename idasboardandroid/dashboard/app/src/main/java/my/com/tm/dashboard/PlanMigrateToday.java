package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class PlanMigrateToday extends AppCompatActivity {

    private ListView listView;
    EditText editext;


    View myView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listplanmigratetoday);

        listView = (ListView) findViewById(R.id.list);

        getJSON();
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_PLAN);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_PROTYPE);
                String b = jo.getString(Config.TAG_TMN);
                String c = jo.getString(Config.TAG_ABBR);
                String d = jo.getString(Config.TAG_STATEEE);
                String e = jo.getString(Config.TAG_OCABINET);
                String f = jo.getString(Config.TAG_NCABINET);
                String g = jo.getString(Config.TAG_TITLE);


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_PROTYPE,a);
                employees.put(Config.TAG_TMN,b);
                employees.put(Config.TAG_ABBR,c);
                employees.put(Config.TAG_STATEEE,d);
                employees.put(Config.TAG_OCABINET,e);
                employees.put(Config.TAG_NCABINET,f);
                employees.put(Config.TAG_TITLE,g);


                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.activity_plan_migrate_today,
                new String[]{Config.TAG_PROTYPE,Config.TAG_TMN,Config.TAG_ABBR,Config.TAG_STATEEE,Config.TAG_OCABINET
                        ,Config.TAG_NCABINET,Config.TAG_TITLE},

                new int[]{R.id.dua, R.id.tiga,  R.id.empat, R.id.lima, R.id.enam, R.id.tujuh,R.id.lapan,
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
                String s = rh.sendGetRequest(Config.URL_GET_PLAN);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
