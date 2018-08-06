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

public class loadpendingorder extends AsyncTask<String,Integer,ArrayList<fibermodel2>> {


        private String name;
        String basket;
        private String mobilephone;
        private  String workgroup;
        private  String userpassword;


        @Override
        protected ArrayList<fibermodel2> doInBackground(String... params) {


            HttpURLConnection conn = null;
            BufferedReader reader = null;



            URL url = null;
            try {

                String query = "?workgroup=" + params[1];
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




                JSONArray pendinglist  = parentObject.getJSONArray("fibolist");

                ArrayList<fibermodel2> listpending = new ArrayList<>();

                for (int i = 0; i < pendinglist.length(); i++) {

                    fibermodel2 fibomodelobject = new fibermodel2();

                    JSONObject finalObject = pendinglist.getJSONObject(i);

                    fibomodelobject.setCustomername(finalObject.getString("customername"));
                    fibomodelobject.setOrderid(finalObject.getString("orderid"));
                    fibomodelobject.setNettype(finalObject.getString("netype"));
                    fibomodelobject.setWorkgroup(finalObject.getString("workgroup"));
                    fibomodelobject.setSiteid(finalObject.getString("siteid"));

                    listpending.add(fibomodelobject);


                }




                return listpending;


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
        protected void onPostExecute(ArrayList<fibermodel2> s) {



            if(login.getInstance()!=null && s!=null) {
                MyActivity.getInstance().pendinglistdialog(s);

            }




        }
    }

