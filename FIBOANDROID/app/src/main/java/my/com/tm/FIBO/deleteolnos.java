package my.com.tm.FIBO;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hasanulisyraf on 14/03/2018.
 */

public class deleteolnos extends AsyncTask<String,Void,String> {

    Context context;

    public deleteolnos(Context applicationContext) {
        this.context = applicationContext;

    }


    @Override
    protected String doInBackground(String... params) {

        String manholeid = params[0];

        String item = params[1];

        String indicator = params[2];
        String createdby = params[3];


        String stringurl = "http://58.27.84.188/deleteolnos.php";

        try {
            URL url = new URL(stringurl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter bufferwriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            String data = URLEncoder.encode("manholeid","UTF-8")+"="+URLEncoder.encode(manholeid,"UTF-8")+
                    "&"+URLEncoder.encode("item","UTF-8")+"="+URLEncoder.encode(item,"UTF-8")+
                    "&"+URLEncoder.encode("indicator","UTF-8")+"="+URLEncoder.encode(indicator,"UTF-8")+
                    "&"+URLEncoder.encode("createdby","UTF-8")+"="+URLEncoder.encode(createdby, "UTF-8");


            bufferwriter.write(data);
            bufferwriter.flush();
            bufferwriter.close();
            os.close();

            InputStream is = conn.getInputStream();
            is.close();

            return "Added";



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
//
//
//    @Override
//    protected void onPostExecute(String result) {
//
//        if(result != null) {
//            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
//        }
//
//
//
//
//    }


}