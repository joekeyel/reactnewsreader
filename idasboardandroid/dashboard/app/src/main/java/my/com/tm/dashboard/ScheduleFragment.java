package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class ScheduleFragment extends Fragment implements ListView.OnItemClickListener {

    private ProgressDialog loading;

    private ListView listView;
    EditText editext;


    View myView;
    private String JSON_STRING;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_schedule_fragment, container, false);

        final Button searching = (Button) myView.findViewById(R.id.btnsearch);
        editext = (EditText) myView.findViewById(R.id.edittextsearch);

        listView = (ListView) myView.findViewById(R.id.list);
        listView.setOnItemClickListener(this);

//        searching.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                getJSONtmnode(editext.getText().toString());
//
//            }
//        });
        getJSON();
        return myView;
    }


    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_SCHEDULE);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
              //  String a = jo.getString(Config.TAG_NO);
               // String b = jo.getString(Config.TAG_REGION);
                String c = jo.getString(Config.TAG_STATEAND);
                String d = jo.getString(Config.TAG_SITENAME);
                String e = jo.getString(Config.TAG_OLDCABINET);
                String f = jo.getString(Config.TAG_NEWCABINET);
                String g = jo.getString(Config.TAG_PTYPE);
                String h = jo.getString(Config.TAG_MIGDATE);
                String ii = jo.getString(Config.TAG_STOPMONITORING);
                String j = jo.getString(Config.TAG_CKC1);
                String k = jo.getString(Config.TAG_CKC2);
                String l = jo.getString(Config.TAG_PMW);
                String m = jo.getString(Config.TAG_STATUSCABINET);
                String n = jo.getString(Config.TAG_SUMMARYTAIL);


                HashMap<String,String> employees = new HashMap<>();
              //  employees.put(Config.TAG_NO,a);
               // employees.put(Config.TAG_REGION,b);
                employees.put(Config.TAG_STATEAND,c);
                employees.put(Config.TAG_SITENAME,d);
                employees.put(Config.TAG_OLDCABINET,e);
                employees.put(Config.TAG_NEWCABINET,f);
                employees.put(Config.TAG_PTYPE,g);
                employees.put(Config.TAG_MIGDATE,h);
                employees.put(Config.TAG_STOPMONITORING,ii);
                employees.put(Config.TAG_CKC1,j);
                employees.put(Config.TAG_CKC2,k);
                employees.put(Config.TAG_PMW,l);
                employees.put(Config.TAG_STATUSCABINET,m);
                employees.put(Config.TAG_SUMMARYTAIL,n);

                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.listschedule,
                new String[]{Config.TAG_STATEAND,Config.TAG_SITENAME,Config.TAG_OLDCABINET
                        ,Config.TAG_NEWCABINET,Config.TAG_PTYPE,Config.TAG_MIGDATE,Config.TAG_STOPMONITORING,Config.TAG_CKC1,Config.TAG_CKC2
                        ,Config.TAG_PMW,Config.TAG_STATUSCABINET,Config.TAG_SUMMARYTAIL},

                new int[]{R.id.dua, R.id.tiga, R.id.empat, R.id.lima, R.id.enam, R.id.tujuh
                        , R.id.lapan, R.id.sembilan, R.id.sepuluh, R.id.sebelas, R.id.duabelas, R.id.tigabelas});

        listView.setAdapter(adapter);

    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Loading Data","Wait...",false,false);
                loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_GET_SCHEDULE);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    private void getJSONtmnode(final String tmnode){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override

            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Loading Data","Wait...",false,false);
                loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                JSON_STRING = s;
             //   showtmnode();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_GET_SCHEDULE + "?region="+tmnode);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//    Intent intent = new Intent(getActivity(), ListFaultRate.class);
//    HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
//    String empId = map.get(Config.TAG_TM).toString();
//    intent.putExtra(Config.EMP_ID,empId);
//    startActivity(intent);
    }


}
