package my.com.tm.FIBO;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class fiberdetails extends AppCompatActivity {

    ListView lvfibersdf;
    fibersdfadaptor adaptormarker,adaptormarker1;
    String siteid,updatedby;

    ArrayList<fibermodel> listfiber = new ArrayList();
    ArrayList<fibermodel> listfiber2 = new ArrayList();
    ArrayList<fibermodel> listfiber3 = new ArrayList();

    View footerView;
    View headerview;
    LinearLayout wrapheaderview;
    LinearLayout wrapfooterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fiberdetails);


        Intent myIntent = getIntent(); // gets the previously created intent
         siteid = myIntent.getStringExtra("siteid"); // will return "FirstKeyValue"
         updatedby= myIntent.getStringExtra("updatedby");

        footerView =  ((LayoutInflater)fiberdetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerfiber, null, false);
        headerview =  ((LayoutInflater)fiberdetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.headerfiber, null, false);

        wrapheaderview=new LinearLayout(getApplicationContext());
        wrapheaderview.setOrientation(LinearLayout.VERTICAL);


        wrapfooterview=new LinearLayout(getApplicationContext());
        wrapfooterview.setOrientation(LinearLayout.VERTICAL);

        lvfibersdf = (ListView)findViewById(R.id.lvfibersdf);

        lvfibersdf.addHeaderView(wrapheaderview);
        lvfibersdf.addFooterView(wrapfooterview);


        loadfibersdfheader fibersdflistheader = new loadfibersdfheader(getApplicationContext());
        fibersdflistheader.execute("http://58.27.84.188/fibersdflistheader.php",siteid);

        loadfibersdffooter fibersdflistfooter = new loadfibersdffooter(getApplicationContext());
        fibersdflistfooter.execute("http://58.27.84.188/fibersdflistfooter.php",siteid);


        loadfibersdf fibersdflist = new loadfibersdf(getApplicationContext());
        fibersdflist.execute("http://58.27.84.188/fibersdflist.php",siteid);




    }


    public class loadfibersdf extends AsyncTask<String, Integer, ArrayList<fibermodel>> {



        public loadfibersdf(Context applicationContext) {
        }

        @Override
        protected ArrayList<fibermodel> doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader reader = null;

            String query = "?siteid="+params[1];


            try {
                URL url = new URL(params[0] + query);

                conn = (HttpURLConnection) url.openConnection();


                conn.connect();

                InputStream stream = conn.getInputStream();


                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();


                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "");
                }

                String finalJson = buffer.toString();

                System.out.println(finalJson);

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("fiberlist");


                ArrayList<fibermodel> fibermodelArrayList = new ArrayList<>();


                for (int i = 0; i < parentArray.length(); i++) {

                    fibermodel fiberobject = new fibermodel();
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    fiberobject.setRoute(finalObject.getString("routename"));
                    fiberobject.setCore(finalObject.getString("core"));
                    fiberobject.setRouteport1(finalObject.getString("routeport1"));
                    fiberobject.setRouteport2(finalObject.getString("routeport2"));
                    fiberobject.setCoreport1(finalObject.getString("coreport1"));
                    fiberobject.setCoreport2(finalObject.getString("coreport2"));
                    fiberobject.setRowport1(finalObject.getString("row1"));
                    fiberobject.setRowport2(finalObject.getString("row2"));
                    fiberobject.setVerport1(finalObject.getString("ver1"));
                    fiberobject.setVerport2(finalObject.getString("ver2"));
                    fiberobject.setBlockport1(finalObject.getString("block1"));
                    fiberobject.setBlockport2(finalObject.getString("block2"));
                    fiberobject.setPinportport1(finalObject.getString("pinport1"));
                    fiberobject.setPinportport2(finalObject.getString("pinport2"));
                    fiberobject.setIndexport1(finalObject.getString("index1"));
                    fiberobject.setIndexport2(finalObject.getString("index2"));
                    fiberobject.setManufactureport1(finalObject.getString("manufacture1"));
                    fiberobject.setManufacturedport2(finalObject.getString("manufacture2"));
                    fiberobject.setTypeport1(finalObject.getString("type1"));
                    fiberobject.setTypeport2(finalObject.getString("type2"));
                    fiberobject.setUpdateby(finalObject.getString("updatedby"));
                    fiberobject.setIndexclick(finalObject.getString("indexclick"));


                    //add to tt model object array list


                    fibermodelArrayList.add(fiberobject);

                }


                //        String[] sanostring = {"Hasanul","Rohani"};
                return fibermodelArrayList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<fibermodel> fibermodels) {
            super.onPostExecute(fibermodels);




            if(fibermodels != null){
                listfiber.clear();

                listfiber = fibermodels;


        }else
            {

                fibermodel start = new fibermodel();
                listfiber.clear();
                listfiber.add(start);



            }



            lvfibersdf = (ListView)findViewById(R.id.lvfibersdf);



            adaptormarker = new fibersdfadaptor(getApplicationContext(),R.layout.fiberrow,listfiber);
            lvfibersdf.setAdapter(adaptormarker);




            lvfibersdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // go to input new data for fiber spf


                    if(!view.equals(wrapfooterview) && !view.equals(wrapheaderview)) {
                        Intent intent = new Intent(getApplicationContext(), fiberdetailsaddnew.class);


                        intent.putExtra("siteid",siteid);
                        intent.putExtra("updatedby",updatedby);
                        intent.putExtra("index",adapterView.getItemIdAtPosition(i));


                        fibermodel objecttopass = (fibermodel) adaptormarker.getItem((int) adapterView.getItemIdAtPosition(i));

                        intent.putExtra("routename", objecttopass.getRoute() );
                        intent.putExtra("core", objecttopass.getCore() );
                        intent.putExtra("routeport1", objecttopass.getRouteport1() );
                        intent.putExtra("routeport2", objecttopass.getRouteport2() );
                        intent.putExtra("coreport1", objecttopass.getCoreport1() );
                        intent.putExtra("coreport2", objecttopass.getCoreport2() );
                        intent.putExtra("rowport1", objecttopass.getRowport1() );
                        intent.putExtra("rowport2", objecttopass.getRowport2() );
                        intent.putExtra("verport1", objecttopass.getVerport1() );
                        intent.putExtra("verport2", objecttopass.getVerport2() );
                        intent.putExtra("blockport1", objecttopass.getBlockport1() );
                        intent.putExtra("blockport2", objecttopass.getBlockport2() );
                        intent.putExtra("pinport1", objecttopass.getPinportport1() );
                        intent.putExtra("pinport2", objecttopass.getPinportport2() );
                        intent.putExtra("indexport1", objecttopass.getIndexport1() );
                        intent.putExtra("indexport2", objecttopass.getIndexport2() );
                        intent.putExtra("manufactureport1", objecttopass.getManufactureport1() );
                        intent.putExtra("manufactureport2", objecttopass.getManufacturedport2() );
                        intent.putExtra("typeport1", objecttopass.getTypeport1() );
                        intent.putExtra("typeport2", objecttopass.getTypeport2() );



                        startActivity(intent);
                    }

                    if (view.equals(wrapfooterview)) {
                        Intent intent = new Intent(getApplicationContext(), fiberfooter.class);


                        startActivity(intent);
                    }
                    if (view.equals(wrapheaderview)) {
                        Intent intent = new Intent(getApplicationContext(), fiberheader.class);



                        Toast.makeText(getApplicationContext(), siteid, Toast.LENGTH_LONG).show();

                        intent.putExtra("siteid",siteid);
                        intent.putExtra("updatedby",updatedby);

                        if (fibermodels != null) {

                            listfiber2.clear();

                            listfiber2 = fibermodels;



                            intent.putExtra("routename", listfiber2.get(0).getRoute());
                            intent.putExtra("core", listfiber2.get(0).getCore());
                            intent.putExtra("routeport1", listfiber2.get(0).getRouteport1());

                            intent.putExtra("coreport1", listfiber2.get(0).getCoreport1());

                            intent.putExtra("rowport1", listfiber2.get(0).getRowport1());

                            intent.putExtra("verport1", listfiber2.get(0).getVerport1());

                            intent.putExtra("blockport1", listfiber2.get(0).getBlockport1());

                            intent.putExtra("pinport1", listfiber2.get(0).getPinportport1());

                            intent.putExtra("indexport1", listfiber2.get(0).getIndexport1());

                            intent.putExtra("manufactureport1", listfiber2.get(0).getManufactureport1());

                            intent.putExtra("typeport1", listfiber2.get(0).getTypeport1());

                        }

                        startActivity(intent);


                    }




                }
            });


        }


    }


    public class loadfibersdfheader extends AsyncTask<String, Integer, ArrayList<fibermodel>> {



        public loadfibersdfheader(Context applicationContext) {
        }

        @Override
        protected ArrayList<fibermodel> doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader reader = null;

            String query = "?siteid="+params[1];


            try {
                URL url = new URL(params[0] + query);

                conn = (HttpURLConnection) url.openConnection();


                conn.connect();

                InputStream stream = conn.getInputStream();


                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();


                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "");
                }

                String finalJson = buffer.toString();

                // System.out.println(finalJson);

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("fiberlist");


                ArrayList<fibermodel> fibermodelArrayList = new ArrayList<>();


                for (int i = 0; i < parentArray.length(); i++) {

                    fibermodel fiberobject = new fibermodel();
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    fiberobject.setRoute(finalObject.getString("routename"));
                    fiberobject.setCore(finalObject.getString("core"));
                    fiberobject.setRouteport1(finalObject.getString("routeport1"));

                    fiberobject.setCoreport1(finalObject.getString("coreport1"));
                     fiberobject.setRowport1(finalObject.getString("row1"));

                    fiberobject.setVerport1(finalObject.getString("ver1"));

                    fiberobject.setBlockport1(finalObject.getString("block1"));

                    fiberobject.setPinportport1(finalObject.getString("pinport1"));

                    fiberobject.setIndexport1(finalObject.getString("index1"));

                    fiberobject.setManufactureport1(finalObject.getString("manufacture1"));

                    fiberobject.setTypeport1(finalObject.getString("type1"));

                    fiberobject.setUpdateby(finalObject.getString("updatedby"));



                    //add to tt model object array list


                    fibermodelArrayList.add(fiberobject);

                }

                return fibermodelArrayList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<fibermodel> fibermodels) {
            super.onPostExecute(fibermodels);





                lvfibersdf = (ListView) findViewById(R.id.lvfibersdf);



                if (fibermodels != null) {
                    listfiber2.clear();

                    listfiber2 = fibermodels;

                    TextView routename = (TextView) headerview.findViewById(R.id.headerroutename);
                    routename.setText(listfiber2.get(0).getRoute());
                    TextView core = (TextView) headerview.findViewById(R.id.headercoretv);
                    core.setText(listfiber2.get(0).getCore());


                    TextView routeport = (TextView) headerview.findViewById(R.id.headerrouteporttv);
                    routeport.setText(listfiber2.get(0).getRouteport1());
                    TextView coreporttv = (TextView) headerview.findViewById(R.id.headercoreporttv);
                    coreporttv.setText(listfiber2.get(0).getCoreport1());
                    TextView row = (TextView) headerview.findViewById(R.id.headerrowtv);
                    row.setText(listfiber2.get(0).getRowport1());
                    TextView ver = (TextView) headerview.findViewById(R.id.headervertv);
                    ver.setText(listfiber2.get(0).getVerport1());
                    TextView block = (TextView) headerview.findViewById(R.id.headerblocktv);
                    block.setText(listfiber2.get(0).getBlockport1());
                    TextView pinport = (TextView) headerview.findViewById(R.id.headerpinporttv);
                    pinport.setText(listfiber2.get(0).getPinportport1());
                    TextView index = (TextView) headerview.findViewById(R.id.headerindextv);
                    index.setText(listfiber2.get(0).getIndexport1());
                    TextView manufacture = (TextView) headerview.findViewById(R.id.headermanufacturetv);
                    manufacture.setText(listfiber2.get(0).getManufactureport1());
                    TextView type = (TextView) headerview.findViewById(R.id.headertypetv);
                    type.setText(listfiber2.get(0).getTypeport1());



                }else{

                    TextView routeport1 = (TextView) headerview.findViewById(R.id.headerroutename);
                    routeport1.setText("Header Route");
                }





               wrapheaderview.removeAllViews();
               wrapheaderview.addView(headerview);


                lvfibersdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // go to input new data for fiber spf

                        if(!view.equals(wrapfooterview) && !view.equals(wrapheaderview)) {
                            Intent intent = new Intent(getApplicationContext(), fiberdetailsaddnew.class);


                            intent.putExtra("siteid",siteid);
                            intent.putExtra("updatedby",updatedby);
                            intent.putExtra("index",adapterView.getItemIdAtPosition(i));






                            startActivity(intent);
                        }

                        if (view.equals(wrapfooterview)) {
                            Intent intent = new Intent(getApplicationContext(), fiberfooter.class);


                            startActivity(intent);
                        }
                        if (view.equals(wrapheaderview)) {
                            Intent intent = new Intent(getApplicationContext(), fiberheader.class);
                            Toast.makeText(getApplicationContext(), siteid, Toast.LENGTH_LONG).show();

                            intent.putExtra("siteid",siteid);
                            intent.putExtra("updatedby",updatedby);

                            if (fibermodels != null) {

                                listfiber2.clear();

                                listfiber2 = fibermodels;



                                intent.putExtra("routename", listfiber2.get(0).getRoute());
                                intent.putExtra("core", listfiber2.get(0).getCore());
                                intent.putExtra("routeport1", listfiber2.get(0).getRouteport1());

                                intent.putExtra("coreport1", listfiber2.get(0).getCoreport1());

                                intent.putExtra("rowport1", listfiber2.get(0).getRowport1());

                                intent.putExtra("verport1", listfiber2.get(0).getVerport1());

                                intent.putExtra("blockport1", listfiber2.get(0).getBlockport1());

                                intent.putExtra("pinport1", listfiber2.get(0).getPinportport1());

                                intent.putExtra("indexport1", listfiber2.get(0).getIndexport1());

                                intent.putExtra("manufactureport1", listfiber2.get(0).getManufactureport1());

                                intent.putExtra("typeport1", listfiber2.get(0).getTypeport1());

                            }

                            startActivity(intent);
                        }


                    }
                });


            }



    }

    public class loadfibersdffooter extends AsyncTask<String, Integer, ArrayList<fibermodel>> {



        public loadfibersdffooter(Context applicationContext) {
        }

        @Override
        protected ArrayList<fibermodel> doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader reader = null;

            String query = "?siteid="+params[1];


            try {
                URL url = new URL(params[0] + query);

                conn = (HttpURLConnection) url.openConnection();


                conn.connect();

                InputStream stream = conn.getInputStream();


                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();


                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "");
                }

                String finalJson = buffer.toString();

                // System.out.println(finalJson);

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("fiberlist");


                ArrayList<fibermodel> fibermodelArrayList = new ArrayList<>();


                for (int i = 0; i < parentArray.length(); i++) {

                    fibermodel fiberobject = new fibermodel();
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    fiberobject.setRoute(finalObject.getString("routename"));
                    fiberobject.setCore(finalObject.getString("core"));
                    fiberobject.setRouteport1(finalObject.getString("routeport1"));

                    fiberobject.setCoreport1(finalObject.getString("coreport1"));
                    fiberobject.setRowport1(finalObject.getString("row1"));

                    fiberobject.setVerport1(finalObject.getString("ver1"));

                    fiberobject.setBlockport1(finalObject.getString("block1"));

                    fiberobject.setPinportport1(finalObject.getString("pinport1"));

                    fiberobject.setIndexport1(finalObject.getString("index1"));

                    fiberobject.setManufactureport1(finalObject.getString("manufacture1"));

                    fiberobject.setTypeport1(finalObject.getString("type1"));

                    fiberobject.setUpdateby(finalObject.getString("updatedby"));


                    //add to tt model object array list


                    fibermodelArrayList.add(fiberobject);

                }

                return fibermodelArrayList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<fibermodel> fibermodels) {
            super.onPostExecute(fibermodels);





            lvfibersdf = (ListView) findViewById(R.id.lvfibersdf);



            if (fibermodels != null) {
                listfiber3.clear();

                listfiber3 = fibermodels;
                TextView routename = (TextView) footerView.findViewById(R.id.footernametv);
                routename.setText(listfiber3.get(0).getRoute());
                TextView core = (TextView) footerView.findViewById(R.id.footercoretv);
                core.setText(listfiber3.get(0).getCore());


                TextView routeport = (TextView) footerView.findViewById(R.id.footerrouteporttv);
                routeport.setText(listfiber3.get(0).getRouteport1());
                TextView coreporttv = (TextView) footerView.findViewById(R.id.footercoreporttv);
                coreporttv.setText(listfiber3.get(0).getCoreport1());
                TextView row = (TextView) footerView.findViewById(R.id.footerrowporttv);
                row.setText(listfiber3.get(0).getRowport1());
                TextView ver = (TextView) footerView.findViewById(R.id.footerverporttv);
                ver.setText(listfiber3.get(0).getVerport1());
                TextView block = (TextView) footerView.findViewById(R.id.footerblockporttv);
                block.setText(listfiber3.get(0).getBlockport1());
                TextView pinport = (TextView) footerView.findViewById(R.id.footerpinporttv);
                pinport.setText(listfiber3.get(0).getPinportport1());
                TextView index = (TextView) footerView.findViewById(R.id.footerindexporttv);
                index.setText(listfiber3.get(0).getIndexport1());
                TextView manufacture = (TextView) footerView.findViewById(R.id.footermanufactureporttv);
                manufacture.setText(listfiber3.get(0).getManufactureport1());
                TextView type = (TextView) footerView.findViewById(R.id.footertypeporttv);
                type.setText(listfiber3.get(0).getTypeport1());


            }else{

                TextView routeport1 = (TextView) footerView.findViewById(R.id.footernametv);
                routeport1.setText("Footer Route");
            }






            wrapfooterview.removeAllViews();
            wrapfooterview.addView(footerView);


            lvfibersdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // go to input new data for fiber spf

                    if(!view.equals(wrapfooterview) && !view.equals(wrapheaderview)) {
                        Intent intent = new Intent(getApplicationContext(), fiberdetailsaddnew.class);


                        intent.putExtra("siteid",siteid);
                        intent.putExtra("updatedby",updatedby);
                        intent.putExtra("index",adapterView.getItemIdAtPosition(i));



                        startActivity(intent);
                    }

                    if (view.equals(wrapfooterview)) {
                        Intent intent = new Intent(getApplicationContext(), fiberfooter.class);


                        startActivity(intent);
                    }
                    if (view.equals(wrapheaderview)) {
                        Intent intent = new Intent(getApplicationContext(), fiberheader.class);


                        startActivity(intent);
                    }


                }
            });


        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu2, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.addsfp:


                    fibermodel newfiber = new fibermodel();
                    newfiber.setStatus("NEW");
                    listfiber.add(newfiber);
                    adaptormarker.notifyDataSetChanged();

                    lvfibersdf.post(new Runnable() {
                        @Override
                        public void run() {
                            // Select the last row so it will scroll into view...
                            lvfibersdf.setSelection(adaptormarker.getCount()-1 );

                        }
                    });


                return true;

            case R.id.reloadsfp:




                loadfibersdfheader fibersdflistheader = new loadfibersdfheader(getApplicationContext());
                fibersdflistheader.execute("http://58.27.84.188/fibersdflistheader.php",siteid);

                loadfibersdffooter fibersdflistfooter = new loadfibersdffooter(getApplicationContext());
                fibersdflistfooter.execute("http://58.27.84.188/fibersdflistfooter.php",siteid);


                loadfibersdf fibersdflist = new loadfibersdf(getApplicationContext());
                fibersdflist.execute("http://58.27.84.188/fibersdflist.php",siteid);


                return true;

            case R.id.reporting:



                Intent intent = new Intent(getApplicationContext(), finalreport.class);


                intent.putExtra("siteid",siteid);

                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadsfp() {

    }


}
