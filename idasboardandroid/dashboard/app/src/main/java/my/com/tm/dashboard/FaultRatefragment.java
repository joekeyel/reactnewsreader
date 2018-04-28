package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class FaultRatefragment extends Fragment implements ListView.OnItemClickListener{

    private ProgressDialog loading;


    private ListView listView;
    EditText editext;


    View myView;
    private String JSON_STRING;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_faultratefragment, container, false);

        final Button searching =(Button) myView. findViewById(R.id.btnsearch);
        editext = (EditText)myView. findViewById(R.id.edittextsearch);

            listView = (ListView) myView.findViewById(R.id.list);
            listView.setOnItemClickListener(this);

        searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getJSONtmnode(editext.getText().toString());

            }
        });

        getJSON();

        return myView;

        }

    private void showtmnode(){
        String text = editext.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(text)){
            Toast.makeText(getActivity(),"Please enter tm node / region / state",Toast.LENGTH_LONG).show();
            return;
        }

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY1);

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
//        private void getData() {
//
//            String id = editext.getText().toString().trim();
//            if (id.equals("")) {
//
//                Toast.makeText(getActivity(), "Please enter TM NODE", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);
//
//            String url = Config.URL_LIST + "?tmnode=" + editext.getText().toString().trim();
//
//            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    loading.dismiss();
//                    //showData();
//                }
//            },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(),"No Data Found",Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//
//    }


        private void showEmployee(){
            JSONObject jsonObject = null;
            ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
            try {
                jsonObject = new JSONObject(JSON_STRING);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY1);

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
                    loading = ProgressDialog.show(getActivity(),"Loading Data","Wait...",false,false);
                    loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                    JSON_STRING = s;
                   //  showData();
                    showEmployee();
                }

                @Override
                protected String doInBackground(Void... params) {
                    RequestHandler3 rh = new RequestHandler3();
                    String s = rh.sendGetRequest(Config.URL_GET_ALL);
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
                showtmnode();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_LIST + "?tmnode="+tmnode);
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



