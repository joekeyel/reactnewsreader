package my.com.tm.FIBO;


import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getworkgroup extends AsyncTask<String,Integer,String> {


        private String name;
        String basket;
        private String mobilephone;
        private  String workgroup;
        private  String userpassword;


        @Override
        protected String doInBackground(String... params) {


            HttpURLConnection conn = null;
            BufferedReader reader = null;



            URL url = null;
            try {

                String query = "?email=" + params[1];
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




                workgroup = parentObject.getString("result");






                return workgroup;


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
        protected void onPostExecute(String s) {



            if(login.getInstance()!=null && s!=null) {


                login.getInstance().updateUI(FirebaseAuth.getInstance().getCurrentUser(),workgroup);

            }




        }
    }

