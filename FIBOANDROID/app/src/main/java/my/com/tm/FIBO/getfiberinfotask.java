package my.com.tm.FIBO;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class getfiberinfotask extends AsyncTask<String, Integer, fibermodel3> {


        private String name;
        String basket;
        private String mobilephone;
        private  String workgroup;
        private  String userpassword;


        @Override
        protected fibermodel3 doInBackground(String... params) {


            HttpURLConnection conn = null;
            BufferedReader reader = null;



            URL url = null;
            try {

                String query = "?siteid=" + params[1];
                url = new URL(params[0]+query);
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

                JSONObject parentObject = new JSONObject(finalJson);




                JSONArray pendinglist  = parentObject.getJSONArray("fiboinfo");


                fibermodel3 fibomodelobject = new fibermodel3();
                JSONObject finalObject = pendinglist.getJSONObject(0);

                    fibomodelobject.setCustomername(finalObject.getString("customername"));
                    fibomodelobject.setOrderid(finalObject.getString("orderid"));
                    fibomodelobject.setCustomeraddr(finalObject.getString("adress"));
                    fibomodelobject.setSiteid(finalObject.getString("siteid"));
                    fibomodelobject.setMetroeepenode(finalObject.getString("tmnode"));
                    fibomodelobject.setMetroeepeport(finalObject.getString("epeport_metro_e"));
                fibomodelobject.setMetroeiucport(finalObject.getString("iucport_metro_e"));
                fibomodelobject.setMetroesfptype(finalObject.getString("sfptype_metro_e"));
                fibomodelobject.setFibermainprotect(finalObject.getString("mainprotect_fiber"));
                fibomodelobject.setFibertype(finalObject.getString("fibertype_fiber"));
                fibomodelobject.setFibername(finalObject.getString("fibername"));
                fibomodelobject.setFibercore(finalObject.getString("fibercore"));
                fibomodelobject.setFiberdistance(finalObject.getString("fiberdistance"));











                return fibomodelobject;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }







            return null;
        }

        @Override
        protected void onPostExecute(fibermodel3 s) {



            if(fiberinfo.getInstance()!=null && s!=null) {


                fiberinfo.getInstance().loadinfo(s);

            }




        }
    }

