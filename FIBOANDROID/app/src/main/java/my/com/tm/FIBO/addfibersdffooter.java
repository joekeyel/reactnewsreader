package my.com.tm.FIBO;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hasanulisyraf on 14/03/2018.
 */

public class addfibersdffooter extends AsyncTask<String,Void,String> {

    Context context;

    public addfibersdffooter(Context applicationContext) {
        this.context = applicationContext;

    }


    @Override
    protected String doInBackground(String... params) {

        String siteid = params[0];
        String routename = params[1];
        String core = params[2];
        String routeport1 = params[3];

        String coreport1 = params[4];

        String row1 = params[5];

        String ver1 = params[6];

        String block1 = params[7];

        String pinport1 = params[8];

        String index1 = params[9];

        String manufacture1 = params[10];

        String type1 = params[11];

        String updateby = params[12];



        String stringurl = "http://58.27.84.188/addnewfibersdffooter.php";

        try {
            URL url = new URL(stringurl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter bufferwriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            String data = URLEncoder.encode("siteid","UTF-8")+"="+URLEncoder.encode(siteid,"UTF-8")+

                    "&"+URLEncoder.encode("routename","UTF-8")+"="+URLEncoder.encode(routename,"UTF-8")+
                    "&"+URLEncoder.encode("core","UTF-8")+"="+URLEncoder.encode(core,"UTF-8")+
                    "&"+URLEncoder.encode("routeport1","UTF-8")+"="+URLEncoder.encode(routeport1,"UTF-8")+
                    "&"+URLEncoder.encode("coreport1","UTF-8")+"="+URLEncoder.encode(coreport1, "UTF-8")+
                    "&"+URLEncoder.encode("row1","UTF-8")+"="+URLEncoder.encode(row1, "UTF-8")+
                    "&"+URLEncoder.encode("ver1","UTF-8")+"="+URLEncoder.encode(ver1, "UTF-8")+
                    "&"+URLEncoder.encode("block1","UTF-8")+"="+URLEncoder.encode(block1, "UTF-8")+
                    "&"+URLEncoder.encode("pinport1","UTF-8")+"="+URLEncoder.encode(pinport1, "UTF-8")+
                    "&"+URLEncoder.encode("index1","UTF-8")+"="+URLEncoder.encode(index1, "UTF-8")+
                    "&"+URLEncoder.encode("manufacture1","UTF-8")+"="+URLEncoder.encode(manufacture1, "UTF-8")+
                    "&"+URLEncoder.encode("type1","UTF-8")+"="+URLEncoder.encode(type1, "UTF-8")+
                    "&"+URLEncoder.encode("updatedby","UTF-8")+"="+URLEncoder.encode(updateby, "UTF-8");


            bufferwriter.write(data);
            bufferwriter.flush();
            bufferwriter.close();
            os.close();

            InputStream is = conn.getInputStream();


            BufferedReader reader = null;

            reader = new BufferedReader(new InputStreamReader(is));

            StringBuffer buffer = new StringBuffer();


            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "");
            }

            String finalJson = buffer.toString();
            is.close();

            return "Updated" + finalJson;



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    @Override
    protected void onPostExecute(String result) {

        if(result != null) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }




    }
}