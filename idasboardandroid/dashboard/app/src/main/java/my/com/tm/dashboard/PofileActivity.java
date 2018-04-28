package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

import my.com.tm.dashboard.R;

public class PofileActivity extends Fragment implements View.OnClickListener {

    RequestQueue rq;
    String plansubb,actualsubb,planfiber,actualfiber,planspine,actualspine,unresolved,balancesubb,balancecolo,balancefiber;
    String totaltt,ttmonitoring,plantodays,migrated,handedover,nis;
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    ProgressDialog dialog;
    //view objects

    private TextView psubb,asubb,pfiber,afiber,textViewUserEmail,pspine,aspine;
    private TextView un,tt,monitor,balsubb,balfiber,balspine,plantoday;
    private TextView textview6,tst,textt,textView22,mitos,mint,actualfiberr,actualf;
    private TextView plansubbbb,plansubbb,actualsub,actualsubbb,planfibers,planfiberz;
    private TextView plancolo,planspines,actualcolo,actualspines,handed,niss,b,a,l;
   // private TextView
    private EditText cpass;

    private ImageView refreshs;
    int num1,num2,num3,subt;
    private Button buttonLogout,btnchange;
    private String JSON_STRING;

    View myView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pofile, container, false);


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = (TextView) myView.findViewById(R.id.time);
        textViewDate.setText(currentDate);

        refreshs = (ImageView)myView.findViewById(R.id.refresh);


        textview6 = (TextView)myView.findViewById(R.id.textView6);
        tst = (TextView)myView.findViewById(R.id.tst);
        textt = (TextView)myView.findViewById(R.id.textt);
        textView22 = (TextView)myView.findViewById(R.id.textView22);
        mitos = (TextView)myView.findViewById(R.id.mitos);
        mint = (TextView)myView.findViewById(R.id.mint);

        plantoday = (TextView) myView.findViewById(R.id.mint);
        psubb = (TextView) myView.findViewById(R.id.plansubb);
        asubb = (TextView) myView.findViewById(R.id.actualsubb) ;
        pfiber = (TextView) myView.findViewById(R.id.plansfiber);

        actualfiberr = (TextView) myView.findViewById(R.id.actualfiberr);
        afiber = (TextView) myView.findViewById(R.id.actualfiber);
        pspine = (TextView) myView.findViewById(R.id.planspine);
        aspine = (TextView) myView.findViewById(R.id.actualspine);
        un = (TextView) myView.findViewById(R.id.rst);
        tt = (TextView) myView.findViewById(R.id.textr);
        monitor = (TextView) myView.findViewById(R.id.tst);
        plansubbb = (TextView) myView.findViewById(R.id.plansubbb);
        plansubbbb = (TextView) myView.findViewById(R.id.plansubb);
        actualsub = (TextView)myView.findViewById(R.id.actualsubb);
        actualsubbb = (TextView)myView.findViewById(R.id.actualsubbb);
        planfibers = (TextView)myView.findViewById(R.id.plansfiber);
        planfiberz = (TextView)myView.findViewById(R.id.planfiber);
        plancolo = (TextView)myView.findViewById(R.id.plancolo);
        planspines = (TextView)myView.findViewById(R.id.planspine);

        actualcolo = (TextView)myView.findViewById(R.id.actualcolo);

        balsubb = (TextView) myView.findViewById(R.id.balancesubb);
        balfiber = (TextView) myView.findViewById(R.id.balancefiber);
        balspine = (TextView) myView.findViewById(R.id.balancespine);

        b = (TextView) myView.findViewById(R.id.bala);
        a = (TextView) myView.findViewById(R.id.balan);
        l = (TextView) myView.findViewById(R.id.balanc);

        handed = (TextView) myView.findViewById(R.id.handed);

        textViewUserEmail = (TextView) myView.findViewById(R.id.textViewUserEmail);

        textview6.setOnClickListener(this);
        tst.setOnClickListener(this);
        textt.setOnClickListener(this);
        textView22.setOnClickListener(this);
        mitos.setOnClickListener(this);
        mint.setOnClickListener(this);
        plansubbb.setOnClickListener(this);
        plansubbbb.setOnClickListener(this);
        actualsub.setOnClickListener(this);
        actualsubbb.setOnClickListener(this);
        planfiberz.setOnClickListener(this);
        planfibers.setOnClickListener(this);
        actualfiberr.setOnClickListener(this);
        afiber.setOnClickListener(this);
        plancolo.setOnClickListener(this);
        planspines.setOnClickListener(this);
        actualcolo.setOnClickListener(this);
        aspine.setOnClickListener(this);
        balsubb.setOnClickListener(this);
        balspine.setOnClickListener(this);
        balfiber.setOnClickListener(this);
        b.setOnClickListener(this);
        a.setOnClickListener(this);
        l.setOnClickListener(this);

        refreshs.setOnClickListener(this);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
           // finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

//        //initializing views


        rq = Volley.newRequestQueue(getActivity());
        sendrequest();
        sendrequest1();
        sendrequest2();
        sendrequest3();
        sendrequest4();
        sendrequest5();
        sendrequest6();
        sendrequest7();
        return myView;
    }



    public void sendrequest(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.URL_GET_HEADER, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    plantodays = response.getString("totalplan");
                    mint.setText(plantodays);

//                    int d=Integer.parseInt(pspine.getText().toString());
//                    int e=Integer.parseInt(aspine.getText().toString());
//                    int f=d-e;
//                    String zste = String.valueOf(f);
//                    balspine.setText(zste);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }

        , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(jsonObjectRequest);
    }
    public void sendrequest1(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.URL_GET_DASHBOARD , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            unresolved = response.getString("totalaging");
                            totaltt = response.getString("totaltt");
                            ttmonitoring = response.getString("cabinetmonitor");

                            un.setText(unresolved);
                            tt.setText(totaltt);
                            monitor.setText(ttmonitoring);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(jsonObjectRequest);
    }

    public void sendrequest2(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.URL_GET_MONITORING , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            migrated = response.getString("total");

                            textt.setText(migrated);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(jsonObjectRequest);
    }

    public void sendrequest3(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.URL_GET_HANDOVER , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            handedover = response.getString("handover");

                            handed.setText(handedover);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(jsonObjectRequest);
    }

    public void sendrequest4(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.URL_FAIZ , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            plansubb = response.getString("plansubb");
                            planfiber = response.getString("planfiber");
                            planspine = response.getString("plancolo");

                            actualsubb = response.getString("subb");
                            actualfiber = response.getString("fiber");
                            actualspine = response.getString("colo");


                            psubb.setText(plansubb);
                            pfiber.setText(planfiber);
                            pspine.setText(planspine);

                            asubb.setText(actualsubb);
                            afiber.setText(actualfiber);
                            aspine.setText(actualspine);



                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(jsonObjectRequest);
    }
    public void sendrequest5(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.SUBB , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            balancesubb = response.getString("countsubb");
                            balsubb.setText(balancesubb);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(jsonObjectRequest);
    }
    public void sendrequest6(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.COLO , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
//                            balancesubb = response.getString("countsubb");
//                            balancefiber = response.getString("fiber");
                            balancecolo = response.getString("countcolo");

//                            balsubb.setText(balancesubb);
//                            balfiber.setText(balancefiber);
                            balspine.setText(balancecolo);


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(jsonObjectRequest);
    }
    public void sendrequest7(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.FIBER , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            balancefiber = response.getString("countfiber");
                            balfiber.setText(balancefiber);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(jsonObjectRequest);
    }
    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == textview6) {
            //finish();
            startActivity(new Intent(getActivity(), CabinetMonitoring.class));
        }if(view == tst) {
            //finish();
            startActivity(new Intent(getActivity(), CabinetMonitoring.class));
        }if(view == textt) {
            //finish();
            startActivity(new Intent(getActivity(), TotalMigCab.class));
        }if(view == textView22) {
            //finish();
            startActivity(new Intent(getActivity(), TotalMigCab.class));
        }if(view == mitos) {
            //finish();
            startActivity(new Intent(getActivity(), PlanMigrateToday.class));
        }if(view == mint) {
            //finish();
            startActivity(new Intent(getActivity(), PlanMigrateToday.class));
        }if(view == plansubbb) {
            //finish();
            startActivity(new Intent(getActivity(), PlanSubb.class));
        }else if (view == plansubbb){

            Toast toast = Toast.makeText(getActivity() , "No Data Found !",   Toast.LENGTH_SHORT);
            toast.show();
        }
        if(view == plansubbbb) {
            //finish();
            startActivity(new Intent(getActivity(), PlanSubb.class));
        }else if(view == plansubbb){


            Toast toast = Toast.makeText(getActivity() , "No Data Found !",   Toast.LENGTH_SHORT);
            toast.show();


        }if(view == actualsub) {
            //finish();
            startActivity(new Intent(getActivity(), ActualSubb.class));
        }else if(view == actualsub){

            Toast toast = Toast.makeText(getActivity() , "No Data Found !",   Toast.LENGTH_SHORT);
            toast.show();
        }
        if(view == actualsubbb) {
            //finish();
            startActivity(new Intent(getActivity(), ActualSubb.class));
        }else if (view == actualsubbb){

            Toast toast = Toast.makeText(getActivity() , "No Data Found !",   Toast.LENGTH_SHORT);
            toast.show();
        }
        if(view == planfiberz) {
            //finish();
            startActivity(new Intent(getActivity(), PlanFiber.class));
        }else if (view ==planfiberz){

            Toast toast = Toast.makeText(getActivity() , "No Data Found !",   Toast.LENGTH_SHORT);
            toast.show();
        }
        if(view == planfibers) {
            //finish();
            startActivity(new Intent(getActivity(), PlanFiber.class));
        }
        if(view == actualfiberr) {
            //finish();
            startActivity(new Intent(getActivity(), ActualFiber.class));
        }if(view == afiber) {
            //finish();
            startActivity(new Intent(getActivity(), ActualFiber.class));
        }
        if(view == plancolo) {
            //finish();
            startActivity(new Intent(getActivity(), PlanColo.class));
        }if(view == planspines) {
            //finish();
            startActivity(new Intent(getActivity(), PlanColo.class));
        }
        if(view == actualcolo) {
            //finish();
            startActivity(new Intent(getActivity(), ActualColo.class));
        }if(view == aspine) {
            //finish();
            startActivity(new Intent(getActivity(), ActualColo.class));
        }
        if(view == balsubb) {
            //finish();
            startActivity(new Intent(getActivity(), BalanceSubb.class));
        }
        if(view == b) {
            //finish();
            startActivity(new Intent(getActivity(), BalanceSubb.class));
        }
        if(view == a) {
            //finish();
            startActivity(new Intent(getActivity(), BalanceFiber.class));
        }
        if(view == l) {
            //finish();
            startActivity(new Intent(getActivity(), BalanceColo.class));
        }
        if(view == refreshs) {
            //finish();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(PofileActivity.this).attach(PofileActivity.this).commit();
            //startActivity(new Intent(getActivity(), MainActivity.class));
            Toast.makeText(getActivity(),"REFRESH",
                    Toast.LENGTH_LONG).show();
        }
        if(view == balspine) {
            //finish();
            startActivity(new Intent(getActivity(), BalanceColo.class));
        }
        if(view == balfiber) {
            //finish();
            startActivity(new Intent(getActivity(), BalanceFiber.class));
        }
        if(view == balsubb) {
            //finish();
            startActivity(new Intent(getActivity(), BalanceSubb.class));
        }
    }
}
