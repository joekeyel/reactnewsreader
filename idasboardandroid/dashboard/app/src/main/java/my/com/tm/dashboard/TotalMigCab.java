package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import my.dashboard.R;

public class TotalMigCab extends AppCompatActivity implements ListView.OnItemClickListener{

    RequestQueue rq;
    String catsubb,catcolo,catfiber,catquality,catrbb,catusp,catre;


    ProgressDialog dialog;
    //view objects
    private String JSON_STRING;
    private TextView subb,colo,fiber,quality,rbb,usp,re,text;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcategorycabinet);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        subb = (TextView)findViewById(R.id.totalsubb);
        text = (TextView)findViewById(R.id.textView10);
//        fiber = (TextView)findViewById(R.id.totalfiber);
//        quality = (TextView)findViewById(R.id.totalquality);
//        rbb = (TextView)findViewById(R.id.totalrbb);
//        usp = (TextView)findViewById(R.id.totalusp);
//        re = (TextView)findViewById(R.id.totalre);

        getJSON();
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_CAT);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_CATPRO);
                String b = jo.getString(Config.TAG_CATTOTAL);


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_CATPRO,a);
                employees.put(Config.TAG_CATTOTAL,b);


                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.activity_total_mig_cab,
                new String[]{Config.TAG_CATPRO,Config.TAG_CATTOTAL},

                new int[]{R.id.satu, R.id.totalsubb});

        listView.setAdapter(adapter);

    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(getApplicationContext(),"Loading Data","Wait...",false,false);
               // loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
             //   loading.dismiss();
             //   loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                JSON_STRING = s;
                //  showData();
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_GET_CAT);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), ListMigCab.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String empId = map.get(Config.TAG_CATPRO).toString();
        intent.putExtra("newsubb", empId);

        Context context = getApplicationContext();
        CharSequence text = empId;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        startActivity(intent);
    }
}
