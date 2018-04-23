package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import my.dashboard.R;

/**
 * Created by user on 12/12/2017.
 */

public class ListFaultRate extends Fragment implements  ListView.OnClickListener{

    private ListView listView;
    private Button search;
    private String JSON_STRING;

    private String query;
    private Button Searching;
    SearchView searchView;
    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list, container, false);

        listView = (ListView) myView.findViewById(R.id.list );

        getJSON();

        return myView;

    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);


            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_MIG);
                String b = jo.getString(Config.TAG_REG);
                String c = jo.getString(Config.TAG_STATE);
                String d = jo.getString(Config.TAG_TM);
                String e = jo.getString(Config.TAG_NCAB);
                String f = jo.getString(Config.TAG_OCAB);
                String g = jo.getString(Config.TAG_TT);
                String h = jo.getString(Config.TAG_CLOSED);
                String ii = jo.getString(Config.TAG_CAP);
                String j = jo.getString(Config.TAG_FAULT);
                String k = jo.getString(Config.TAG_PRO);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_MIG,a);
                employees.put(Config.TAG_REG,b);
                employees.put(Config.TAG_STATE,c);
                employees.put(Config.TAG_TM,d);
                employees.put(Config.TAG_NCAB,e);
                employees.put(Config.TAG_OCAB,f);
                employees.put(Config.TAG_TT,g);
                employees.put(Config.TAG_CLOSED,h);
                employees.put(Config.TAG_CAP,ii);
                employees.put(Config.TAG_FAULT,j);
                employees.put(Config.TAG_PRO,k);


                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.cardview,
                new String[]{Config.TAG_MIG,Config.TAG_REG,Config.TAG_STATE,Config.TAG_TM,Config.TAG_NCAB
                        ,Config.TAG_OCAB,Config.TAG_TT,Config.TAG_CLOSED,Config.TAG_CAP,Config.TAG_FAULT,Config.TAG_PRO},

                new int[]{R.id.satu, R.id.dua, R.id.tiga, R.id.empat, R.id.lima, R.id.enam, R.id.tujuh
                        , R.id.lapan, R.id.sembilan, R.id.sepuluh, R.id.sebelas});


        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching Data","Wait...",false,false);
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
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_LIST,query);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewEmployee.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(Config.TAG_ID).toString();
        intent.putExtra(Config.EMP_ID,empId);
        startActivity(intent);
    }*/

    @Override
    public void onClick(View v) {

    }
}
