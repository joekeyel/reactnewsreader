package my.com.tm.DIME;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class mainholewall  extends AppCompatActivity implements DialogInterface.OnDismissListener {

    LatLng objLatLng;
    String Markername;
    String createby;
    ductviewmodel nesductid = new ductviewmodel();


    ArrayList<String> ductliststring = new ArrayList<>();
    ArrayList<ductviewmodel> nestductmodelsarray = new ArrayList();
    ArrayList<ductviewmodel> nestductmodelsarraydel = new ArrayList();

    AlertDialog alert;
    AlertDialog alertoccupancy;

    String occupancystr = "AVAILABLE";
    Spinner occupancyspinner;


    ImageView wallimage;
    ImageView wallimage2;
    ImageView wallimage3;
    ImageView wallimage4;

    String wallselected;
    String wallselected2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainholewall);



        objLatLng=getIntent().getExtras().getParcelable("markerlatlng");
        Markername = getIntent().getStringExtra("markertitle");

       createby = getIntent().getStringExtra("markercreateby");


       TextView mainholeid = (TextView)findViewById(R.id.manholename);
       mainholeid.setText(Markername);

       wallimage = (ImageView) findViewById(R.id.wall1imageview);
        wallimage2 = (ImageView) findViewById(R.id.wall1imageview2);
        wallimage3 = (ImageView) findViewById(R.id.wall1imageview3);
        wallimage4 = (ImageView) findViewById(R.id.wall1imageview4);

       loadfirebasewallduct();
       loadimagewall(wallimage,"wall1");
        loadimagewall(wallimage2,"wall2");
        loadimagewall(wallimage3,"wall3");
        loadimagewall(wallimage4,"wall4");



    }


    public void clickduct(View view){

     String idresource = view.getResources().getResourceName(view.getId());
     String ids = idresource.replace("my.com.tm.DIME:id/","");

        wallselected2 = ids.substring(0,2);
        ductviewmodel nestductobject = new ductviewmodel();


        TextView texttouch = (TextView)findViewById(view.getId());



        int intID = getBackgroundColor(texttouch);

        //if vacant

             if(intID == Color.parseColor("#3f51b5")){

             texttouch.setBackgroundColor(Color.parseColor("#ffffff"));


             nestductobject.setWallduct(ids);
             nestductobject.setWallductview(view.getId());
             nestductmodelsarray.add(nestductobject);
                 ductliststring.add(ids.substring(2));





             }
            if(intID == Color.parseColor("#ffffff")){

                texttouch.setBackgroundColor(Color.parseColor("#3f51b5"));
                Integer n = nestductmodelsarray.size();



                if(n>0) {


                    for (int j = n-1; j >= 0; j--) {
                        String wallduct = nestductmodelsarray.get(j).getWallduct();
                        String duct = ductliststring.get(j);

                        if (wallduct.equals(ids)) {
                            nestductmodelsarray.remove(j);
                        }

                        if (duct.equals(ids.substring(2))) {
                            ductliststring.remove(j);
                        }
                    }


                }

            }

//if taken then user touch it...this plan to delete

        if(intID == Color.GREEN || intID == Color.YELLOW || intID == Color.BLACK || intID ==Color.RED){

            texttouch.setPaintFlags(texttouch.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);



           nesductid.setWallduct(ids);
           nesductid.setWallductview(view.getId());
           nesductid.setNesductid(texttouch.getTag().toString());

           show_alert_occupancy();



            nestductobject.setWallduct(ids);
            nestductobject.setWallductview(view.getId());
            nestductobject.setNesductid(texttouch.getTag().toString());

            nestductmodelsarraydel.add(nestductobject);

        }


    }

    public void clickductbutton(View view){
        Integer n = nestductmodelsarray.size();

        if(n>0) {
            show_dialog_nesduct_name(view);

            switch (view.getId()) {
                case R.id.buttonW1:
                    // do something
                    wallselected2 = "W1";
                    break;
                case R.id.buttonW2:
                    // do something else
                    wallselected2 = "W2";
                    break;
                case R.id.buttonW3:
                    // i'm lazy, do nothing

                    wallselected2 = "W3";
                    break;

                case R.id.buttonW4:
                    // i'm lazy, do nothing

                    wallselected2 = "W4";
                    break;
            }

        }


    }


    public void clickductresetbutton(View view){

        loadfirebasewallduct();
    }

    public void clickductdeletebutton(View view){

        String idresource = view.getResources().getResourceName(view.getId());
        String ids = idresource.replace("my.com.tm.idraw:id/","");
        String wall = ids.substring(6);


        Integer d = nestductmodelsarraydel.size();

        //for delete selected database

        if(d>0) {


            for (int l = 0; l < d; l++) {

                String wallduct = nestductmodelsarraydel.get(l).getWallduct();
                Integer viewid = nestductmodelsarraydel.get(l).getWallductview();
                String nesductid = nestductmodelsarraydel.get(l).getNesductid();


                String walls = wallduct.substring(0, 2);

                if (walls.equals(wall)) {



                    deletefirebase(wallduct,viewid,nesductid);
                }


            }


        }


    }

    public void clickductdeletebutton2(View view){


        String wall = wallselected2;


        Integer d = nestductmodelsarraydel.size();

        //for delete selected database

        if(d>0) {


            for (int l = 0; l < d; l++) {

                String wallduct = nestductmodelsarraydel.get(l).getWallduct();
                Integer viewid = nestductmodelsarraydel.get(l).getWallductview();
                String nesductid = nestductmodelsarraydel.get(l).getNesductid();


                String walls = wallduct.substring(0, 2);

                if (walls.equals(wall)) {



                    deletefirebase(wallduct,viewid,nesductid);
                }


            }


        }


    }

    public void preparemarkduct(View view,String nestid){

        String idresource = view.getResources().getResourceName(view.getId());
        String ids = idresource.replace("my.com.tm.DIME:id/","");
        String wall = ids.substring(6);

        Integer n = nestductmodelsarray.size();
        Integer d = nestductmodelsarraydel.size();




        if(n>0) {



            for (int l = 0; l < n; l++) {

                String wallduct = nestductmodelsarray.get(l).getWallduct();
                Integer viewid = nestductmodelsarray.get(l).getWallductview();


                String walls = wallduct.substring(0, 2);
                String duct = wallduct.substring(2);

                if (walls.equals(wall)) {


                    ductliststring.add(duct);
                    //updatefirebase(wallduct,viewid,nestid);
                }


            }







        }



    }


    public void show_alert_occupancy(){

        final String ductname = nesductid.getWallduct();
        final Integer viewid = nesductid.getWallductview();
        final String nesductidstr = nesductid.getNesductid();


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertoccupancy = alertDialog.create();

        alertoccupancy.setTitle("Please Select Occupancy For "+ ductname);



// get the utilization from firebase

        LayoutInflater inflater = getLayoutInflater();

        // inflate the custom popup layout
        final View convertView = inflater.inflate(R.layout.alert_occupancy,null);


        FirebaseDatabase databasefirebaseinitial = FirebaseDatabase.getInstance();
        final DatabaseReference myRefdatabaseinitial = databasefirebaseinitial.getReference("Nesductidutilization").child(Markername).child(nesductidstr).child(ductname);


        String occupancy;
        myRefdatabaseinitial.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("occupancyselector").exists()){

                    if(dataSnapshot.child("occupancyselector").getValue().toString().equals("03")){

                        ToggleButton radioButton = (ToggleButton)convertView.findViewById(R.id.imagetoggle03);
                        radioButton.setChecked(true);

                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("0/3");

                    }

                    if(dataSnapshot.child("occupancyselector").getValue().toString().equals("13")){

                        ToggleButton radioButton = (ToggleButton)convertView.findViewById(R.id.imagetoggle13);
                        radioButton.setChecked(true);

                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("1/3");
                    }

                    if(dataSnapshot.child("occupancyselector").getValue().toString().equals("23")){

                        ToggleButton radioButton = (ToggleButton)convertView.findViewById(R.id.imagetoggle23);
                        radioButton.setChecked(true);

                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("2/3");
                    }

                    if(dataSnapshot.child("occupancyselector").getValue().toString().equals("33")){

                        ToggleButton radioButton = (ToggleButton)convertView.findViewById(R.id.imagetoggle33);
                        radioButton.setChecked(true);

                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("3/3");
                    }

                    if(dataSnapshot.child("occupancyselector").getValue().toString().equals("01")){

                        ToggleButton radioButton = (ToggleButton)convertView.findViewById(R.id.imagetoggle01);
                        radioButton.setChecked(true);

                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("0/1");
                    }

                    if(dataSnapshot.child("occupancyselector").getValue().toString().equals("11")){

                        ToggleButton radioButton = (ToggleButton)convertView.findViewById(R.id.imagetoggle11);
                        radioButton.setChecked(true);

                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("1/1");
                    }





                }

                if(dataSnapshot.child("cablecode").exists()) {
                    EditText cablecodeetext = (EditText) convertView.findViewById(R.id.cablecodeedittext);
                    cablecodeetext.setText(dataSnapshot.child("cablecode").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        //this to set the slider bar inserting utilization

//         SeekBar utilizationseekbar = (SeekBar)convertView.findViewById(R.id.utilizationsb);
//         final TextView utizaltiontv = (TextView)convertView.findViewById(R.id.utilizationet);
//
//         utilizationseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//           int progressChangedValue = 0;
//
//           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//               progressChangedValue = progress;
//
//               utizaltiontv.setText(String.valueOf(progressChangedValue));
//           }
//
//           public void onStartTrackingTouch(SeekBar seekBar) {
//               // TODO Auto-generated method stub
//           }
//
//           public void onStopTrackingTouch(SeekBar seekBar) {
//
//
//
//               utizaltiontv.setText(String.valueOf(progressChangedValue));
//               updateutilizationfirebase(progressChangedValue,ductname,nesductidstr);
//
//           }
//       });


//        occupancyspinner = (Spinner) convertView.findViewById(R.id.spinneroccupancy);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.occupancyvalues, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        occupancyspinner.setAdapter(adapter);
//
//        occupancyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//
//                occupancystr = parent.getItemAtPosition(pos).toString();
//
//
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });



        final Button updateoccupancy = (Button)convertView.findViewById(R.id.updateoccupancy);
        updateoccupancy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//                EditText ductcode = (EditText)convertView.findViewById(R.id.cablecodeduct);
//                final String ductcodestr = ductcode.getText().toString();
//
//
//                   updateoccupancyfirebase(occupancystr,ductname,nesductidstr);
//
//
//                    if(!ductcodestr.isEmpty()){
//
//                        updatecablecodefirebase(ductcodestr,ductname);
//                    }

                EditText cablecodeetext = (EditText)convertView.findViewById(R.id.cablecodeedittext);
                String cablecodestr = cablecodeetext.getText().toString();

                updatecablecodefirebase(cablecodestr,ductname,nesductidstr);
                loadfirebasewallduct();
                alertoccupancy.dismiss();

            }
        });



        Button deleteduct = (Button)convertView.findViewById(R.id.deleteduct);
        deleteduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click


                // deletefirebase(ductname,viewid,nesductidstr);
                deletefirebaseselector(ductname,nesductidstr);
                final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                selector.setText("Unset");


                loadfirebasewallduct();

                alertoccupancy.dismiss();

            }
        });

        final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
                for (int j = 0; j < radioGroup.getChildCount(); j++) {
                    final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                    view.setChecked(view.getId() == i);


                    if(i == R.id.imagetoggle01){
                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("0/1");

                    }

                    if(i == R.id.imagetoggle11){
                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("1/1");

                    }

                    if(i == R.id.imagetoggle03){
                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("0/3");

                    }

                    if(i == R.id.imagetoggle13){
                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("1/3");

                    }

                    if(i == R.id.imagetoggle33){
                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("3/3");

                    }

                    if(i == R.id.imagetoggle23){
                        final TextView selector = (TextView)convertView.findViewById(R.id.ductnameid);
                        selector.setText("2/3");

                    }

                }
            }
        };

        ((RadioGroup) convertView.findViewById(R.id.toggleGroup)).setOnCheckedChangeListener(ToggleListener);


        alertoccupancy.setView(convertView);

        alertoccupancy.show();

    }

    public void onToggle(View view) {


        ((RadioGroup)view.getParent()).check(view.getId());
        // app specific stuff ..


        final String ductname = nesductid.getWallduct();
        final Integer viewid = nesductid.getWallductview();
        final String nesductidstr = nesductid.getNesductid();





        if(view.getId() == R.id.imagetoggle03){

            Toast.makeText(mainholewall.this, "0", Toast.LENGTH_SHORT).show();
            updateutilizationfirebase(0,ductname,nesductidstr);
            updateoccupancyfirebase("AVAILABLE",ductname,nesductidstr);
            updateoccupancyfirebaseselector("03",ductname,nesductidstr);



        }

        if(view.getId() == R.id.imagetoggle13){


            Toast.makeText(mainholewall.this, "33", Toast.LENGTH_SHORT).show();
            updateutilizationfirebase(33,ductname,nesductidstr);
            updateoccupancyfirebase("PARTIALLY UTILIZED",ductname,nesductidstr);
            updateoccupancyfirebaseselector("13",ductname,nesductidstr);



        }

        if(view.getId() == R.id.imagetoggle23){


            Toast.makeText(mainholewall.this, "66", Toast.LENGTH_SHORT).show();
            updateutilizationfirebase(67,ductname,nesductidstr);
            updateoccupancyfirebase("PARTIALLY UTILIZED",ductname,nesductidstr);
            updateoccupancyfirebaseselector("23",ductname,nesductidstr);


        }

        if(view.getId() == R.id.imagetoggle33){
            ;
            Toast.makeText(mainholewall.this, "100", Toast.LENGTH_SHORT).show();

            updateutilizationfirebase(100,ductname,nesductidstr);
            updateoccupancyfirebase("FULLY UTILIZED",ductname,nesductidstr);
            updateoccupancyfirebaseselector("33",ductname,nesductidstr);


        }

        if(view.getId() == R.id.imagetoggle11){


            Toast.makeText(mainholewall.this, "100", Toast.LENGTH_SHORT).show();
            updateutilizationfirebase(100,ductname,nesductidstr);
            updateoccupancyfirebase("FULLY UTILIZED",ductname,nesductidstr);
            updateoccupancyfirebaseselector("11",ductname,nesductidstr);




        }


        if(view.getId() == R.id.imagetoggle01){


            Toast.makeText(mainholewall.this, "0", Toast.LENGTH_SHORT).show();
            updateutilizationfirebase(100,ductname,nesductidstr);
            updateoccupancyfirebase("ABANDONED",ductname,nesductidstr);
            updateoccupancyfirebaseselector("01",ductname,nesductidstr);


        }
    }

    public void show_dialog_nesduct_name(final View view) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alert = alertDialog.create();

        alert.setTitle("Select NestDuct Name");


        LayoutInflater inflater = getLayoutInflater();

        // inflate the custom popup layout
        final View convertView = inflater.inflate(R.layout.insert_nestduct_name, null);
        // find the ListView in the popup layout


        //show ductlist selectec

        Collections.sort(ductliststring);



        TextView ductlisttv = (TextView)convertView.findViewById(R.id.ductlist);
        TextView ductlisttv2 = (TextView)convertView.findViewById(R.id.ductlist2);
        String ductstr = "";



        String ductstrwidth = "";


        for(int i=0;i<ductliststring.size();i++){

            //ductlisttv.setText(ductlisttv.getText()+ductliststring.get(i)+"");

            String col1 = ductliststring.get(0).substring(1);

            if(ductliststring.get(i).substring(1).equals(col1)) {
                if (!ductstr.contains(ductliststring.get(i).substring(0, 1)))
                    ductstr = ductstr + ductliststring.get(i).substring(0, 1);
            }

            String row1 = ductliststring.get(0).substring(0,1);
            if(ductliststring.get(i).substring(0,1).equals(row1))
                if(!ductstrwidth.contains(ductliststring.get(i).substring(1)))
                    ductstrwidth = ductstrwidth+ductliststring.get(i).substring(1);


        }
        // removeDuplicates(ductstr.toCharArray());
        //remove all digits
        //ductstr.replaceAll("[0-9]", "");
        ductlisttv.setText(ductstr);
        ductlisttv2.setText(ductstrwidth);
        //test the height is in alpabatically order



        String str = ductstr;
        char[] newStr = str.toCharArray();
        final int height = newStr.length;
        char previous = '\u0000';

        String str2 = ductstrwidth;
        char[] newStr2 = str2.toCharArray();
        final int width = newStr2.length;


        if(isInOrder(previous,newStr) && isInOrder(previous,newStr2)){
            // Toast.makeText(mainholewall.this, "Height & Width is in order, Height = "+height + " and Width = " + width, Toast.LENGTH_SHORT).show();

            String dimension = "Height = "+height+" Width = "+width;
            final String startduct = "Start Duct = "+ductliststring.get(0).toString();

            ductlisttv.setText(dimension);
            ductlisttv2.setText(startduct);


            final ArrayList<String> finallist = nestductcombination(ductliststring.get(0).toString().substring(1),width,ductstr,wallselected2);

            Button updatenesductaction = (Button)convertView.findViewById(R.id.updatenestduct);



            updatenesductaction.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    TextView nestductetname = (TextView)convertView.findViewById(R.id.nestductnameet);
                    final String nestductnamestr = nestductetname.getText().toString();

                    if(nestductnamestr.isEmpty()){


                        Toast.makeText(mainholewall.this, "Pls Insert NestDuct Name", Toast.LENGTH_SHORT).show();
                        alert.dismiss();

                    }else {
                        // preparemarkduct(view,nestductnamestr);

                        updatenewduct(finallist,nestductnamestr,height,width,ductliststring.get(0).toString());
                        loadfirebasewallduct();

                        alert.dismiss();
                    }
                }
            });
        }else{

            Toast.makeText(mainholewall.this, "Height not in order", Toast.LENGTH_SHORT).show();
        }






// radio group of predefine Nest Duct


        RadioGroup Nestduct = (RadioGroup)convertView.findViewById(R.id.nesductgroup);

        Nestduct.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.ND1) {
//                  EditText nestductetname = (EditText)convertView.findViewById(R.id.nestductnameet);
                    TextView nestductetname = (TextView)convertView.findViewById(R.id.nestductnameet);

                    nestductetname.setText("DN1");

                } else if(checkedId == R.id.ND2) {
                    TextView nestductetname = (TextView)convertView.findViewById(R.id.nestductnameet);

                    nestductetname.setText("DN2");

                }else if(checkedId == R.id.ND3){
                    TextView nestductetname = (TextView)convertView.findViewById(R.id.nestductnameet);

                    nestductetname.setText("DN3");

                }else{

                    TextView nestductetname = (TextView)convertView.findViewById(R.id.nestductnameet);

                    nestductetname.setText("DN4");
                }
            }

        });




        //checking for nesduct availability


        FirebaseDatabase databasefirebasesummary = FirebaseDatabase.getInstance();
        final DatabaseReference rootref = databasefirebasesummary.getReference("Nesductidutilization").child(Markername);

        final ArrayList<String> currentndname = new ArrayList<>();

        currentndname.add("DN1");
        currentndname.add("DN2");
        currentndname.add("DN3");
        currentndname.add("DN4");




        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child: dataSnapshot.getChildren()) {

                    if (child.exists()) {

                        for (DataSnapshot child2: child.getChildren()) {

                            String nesduct = child.getKey();
                            String wallduct = child2.getKey();
                            String wall = wallduct.substring(0,2);

                            if(wall.equals(wallselected2)) {

                                if (nesduct.equals("DN1")) {

                                    currentndname.remove("DN1");



                                }
                                if (nesduct.equals("DN2")) {

                                    currentndname.remove("DN2");


                                }
                                if (nesduct.equals("DN3")) {

                                    currentndname.remove("DN3");

                                }
                                if (nesduct.equals("DN4")) {
                                    currentndname.remove("DN4");


                                }


                                System.out.println("test " + nesduct);
                            }
                        }



                    }


                }


                if(currentndname.size()>0){

                    for(int i = 0;i<currentndname.size();i++){

                        System.out.println("test currentndname " + currentndname.get(i));

                        if(currentndname.get(i).equals("DN1")){

                            RadioButton radionestduct1 = (RadioButton) convertView.findViewById(R.id.ND1);

                            radionestduct1.setVisibility(View.VISIBLE);

                        }
                        if(currentndname.get(i).equals("DN2")){

                            RadioButton radionestduct2 = (RadioButton) convertView.findViewById(R.id.ND2);

                            radionestduct2.setVisibility(View.VISIBLE);

                        }

                        if(currentndname.get(i).equals("DN3")){

                            RadioButton radionestduct3 = (RadioButton) convertView.findViewById(R.id.ND3);

                            radionestduct3.setVisibility(View.VISIBLE);

                        }

                        if(currentndname.get(i).equals("DN4")){

                            RadioButton radionestduct4 = (RadioButton) convertView.findViewById(R.id.ND4);

                            radionestduct4.setVisibility(View.VISIBLE);

                        }

                    }

                }

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        alert.setView(convertView);

        alert.show();

    }


    //function to return final array of duct nest base on the dimension given

    public ArrayList<String> nestductcombination(String col, int width,String row,String wall){

        ArrayList<String> finalductliststring = new ArrayList<String>();

        // System.out.println(col);
        int colint = Integer.parseInt(col);

        for (char ch: row.toCharArray()) {

            for (int j = 0; j < width; j++) {
                //System.out.println(wall+ch+String.valueOf(colint));

                finalductliststring.add(wall+String.valueOf(ch).toUpperCase()+String.valueOf(colint));
                colint++;

            }

            colint = Integer.parseInt(col);

        }


        return finalductliststring;
    }
    //function update the list

    public void updatenewduct(ArrayList<String> finalallowlist,String nestid,int height,int width,String startduct){

        for(int i = 0; i <finalallowlist.size(); i++){

            updatefirebase(finalallowlist.get(i),123,nestid,width,height,startduct);
        }


    }


    //function to check the height is in alphabatecillay order


    private static boolean isInOrder(char previous, char[] arr) {
        for (int i = 0; i < arr.length ; i++) {

            String currentstr = String.valueOf(arr[i]);
            int charValue = currentstr.charAt(0);
            String next = String.valueOf( (char) (charValue + 1));

            if(i<arr.length-1) {
                if (!next.equals(String.valueOf(arr[i + 1])))
                    return false;
            }
        }
        return true;
    }



    //update nestduct manhole

    public void updatefirebase(String wallduct,Integer viewid,String nestid,int height,int width,String startduct){

        if (createby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference();
            myRef.child("photomarkeridraw/" + FirebaseAuth
                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallduct).child("textviewid").setValue(viewid);

            myRef.child("photomarkeridraw/" + FirebaseAuth
                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallduct).child("nestductid").setValue(nestid);


            myRef.child("photomarkeridraw/" + FirebaseAuth
                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallduct).child("occupancy").setValue("AVAILABLE");
            //nestduct record

            myRef.child("Nesductid"+ "/"+Markername+"/" + nestid).child(wallduct).setValue("AVAILABLE");
            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("occupancy").setValue("AVAILABLE");
            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("utilization").setValue(0);
            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("height").setValue(height);
            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("width").setValue(width);
            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("startduct").setValue(startduct);





            //updatemysql
            addnewduct addduct = new addnewduct(getApplicationContext());
            addduct.execute(Markername,nestid,wallduct,"AVAILABLE","0",FirebaseAuth.getInstance().getCurrentUser().getEmail(),String.valueOf(height),String.valueOf(width),startduct);

        }


    }

    public void updateoccupancyfirebase(String occupancy,String wallduct,String nestid){

        if (createby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference();
            myRef.child("photomarkeridraw/" + FirebaseAuth
                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallduct).child("occupancy").setValue(occupancy);


            myRef.child("Nesductid"+ "/"+Markername+"/" + nestid).child(wallduct).setValue(occupancy);
            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("occupancy").setValue(occupancy);
            // myRef.child("mainholeutilization"+ "/"+Markername+"/" + nestid).child(wallduct).setValue(occupancy);


            //updatemysql
            updateduct updateduct1 = new updateduct(getApplicationContext());
            updateduct1.execute(Markername,wallduct,occupancy,FirebaseAuth.getInstance().getCurrentUser().getEmail());

        }


    }


    public void updateoccupancyfirebaseselector(String occupancy,String wallduct,String nestid){

        if (createby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference();
            myRef.child("photomarkeridraw/" + FirebaseAuth
                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallduct).child("occupancyselector").setValue(occupancy);



            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("occupancyselector").setValue(occupancy);
            // myRef.child("mainholeutilization"+ "/"+Markername+"/" + nestid).child(wallduct).setValue(occupancy);


//            //updatemysql
            updateductselector updateduct1 = new updateductselector(getApplicationContext());
            updateduct1.execute(Markername,wallduct,occupancy,FirebaseAuth.getInstance().getCurrentUser().getEmail());

        }


    }

    public void updateutilizationfirebase(int utilization,String wallduct,String nestid){

        if (createby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference();
            myRef.child("photomarkeridraw/" + FirebaseAuth
                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallduct).child("utilization").setValue(utilization);


            //myRef.child("Nesductid"+ "/"+Markername+"/" + nestid).child(wallduct).setValue(utilization);
            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("utilization").setValue(utilization);
            // myRef.child("mainholeutilization"+ "/"+Markername+"/" + nestid).child(wallduct).setValue(occupancy);

            updateductutilization updateduct2 = new updateductutilization(getApplicationContext());
            updateduct2.execute(Markername,wallduct,String.valueOf(utilization),FirebaseAuth.getInstance().getCurrentUser().getEmail());

        }


    }


    public void updatecablecodefirebase(String cablecode, String wallduct, String nestid){

        if (createby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference();
            myRef.child("photomarkeridraw/" + FirebaseAuth
                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallduct).child("cablecode").setValue(cablecode);

            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("cablecode").setValue(cablecode);


        }


    }


    public void deletefirebase(final String wallduct, final Integer viewid, final String nesductid){




        FirebaseDatabase databasefirebasesummary = FirebaseDatabase.getInstance();
        final DatabaseReference rootref = databasefirebasesummary.getReference("Nesductidutilization").child(Markername).child(nesductid);


        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child: dataSnapshot.getChildren()) {

                    String wallductall = child.getKey();

                    String wallall = wallductall.substring(0,2);

                    String walltodelete = wallduct.substring(0,2);

                    System.out.println(wallductall);
                    System.out.println(wallall);
                    System.out.println(walltodelete);

                    if (createby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

                        if(walltodelete.equals(wallall)) {

                            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = databasefirebase.getReference();
                            myRef.child("photomarkeridraw/" + FirebaseAuth
                                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallductall).removeValue();

                            TextView strikethru = (TextView) findViewById(viewid);

                            strikethru.setBackgroundColor(Color.parseColor("#3f51b5"));
                            strikethru.setPaintFlags(strikethru.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));


                            myRef.child("Nesductid" + "/" + Markername + "/" + nesductid).child(wallductall).removeValue();
                            myRef.child("Nesductidutilization" + "/" + Markername + "/" + nesductid).child(wallductall).removeValue();

                            //clear the wallduct

                            int resID = getResources().getIdentifier(wallductall, "id", getPackageName());


                            TextView occupyduct2 = (TextView) findViewById(resID);
                            occupyduct2.setBackgroundColor(Color.parseColor("#3f51b5"));

                            alertoccupancy.dismiss();


                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        if (createby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {



            deleteduct delete = new deleteduct(getApplicationContext());
            delete.execute(Markername,wallduct,FirebaseAuth.getInstance().getCurrentUser().getEmail(),nesductid);

        }


    }

    public void deletefirebaseselector(String wallduct,String nestid){

        if (createby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference();
            myRef.child("photomarkeridraw/" + FirebaseAuth
                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallduct).child("occupancyselector").removeValue();

            myRef.child("photomarkeridraw/" + FirebaseAuth
                    .getInstance().getCurrentUser().getUid() + "/" + Markername).child(wallduct).child("occupancy").setValue("AVAILABLE");


            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("occupancy").setValue("AVAILABLE");

            myRef.child("Nesductidutilization"+ "/"+Markername+"/" + nestid).child(wallduct).child("occupancyselector").removeValue();
            // myRef.child("mainholeutilization"+ "/"+Markername+"/" + nestid).child(wallduct).setValue(occupancy);


//            //updatemysql


            updateductselector updateduct1 = new updateductselector(getApplicationContext());
            updateduct1.execute(Markername,wallduct,"",FirebaseAuth.getInstance().getCurrentUser().getEmail());


            updateduct updateduct2 = new updateduct(getApplicationContext());
            updateduct2.execute(Markername,wallduct,"AVAILABLE",FirebaseAuth.getInstance().getCurrentUser().getEmail());



            updateductutilization updateduct3 = new updateductutilization(getApplicationContext());
            updateduct3.execute(Markername,wallduct,String.valueOf(0),FirebaseAuth.getInstance().getCurrentUser().getEmail());

        }



    }

    public static int getBackgroundColor(TextView textView) {
        ColorDrawable drawable = (ColorDrawable) textView.getBackground();
        if (Build.VERSION.SDK_INT >= 11) {
            return drawable.getColor();
        }
        try {
            Field field = drawable.getClass().getDeclaredField("mState");
            field.setAccessible(true);
            Object object = field.get(drawable);
            field = object.getClass().getDeclaredField("mUseColor");
            field.setAccessible(true);
            return field.getInt(object);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }


    public  void loadfirebasewallduct(){

        FirebaseDatabase databasefirebaseinitial = FirebaseDatabase.getInstance();
        final DatabaseReference myRefdatabaseinitial = databasefirebaseinitial.getReference("photomarkeridraw");

        myRefdatabaseinitial.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child: dataSnapshot.getChildren()) {//useridcreated



                    for (DataSnapshot child2 : child.getChildren()) {//mainholename

                      if(child2.getKey().toString().equals(Markername)){



                        for (DataSnapshot child3 : child2.getChildren()) {//wallductname

                            String key = child3.getKey().toString();


                            if(!key.equals("createdby") && !key.equals("lat") && !key.equals("lng")){

                                String viewid = null;

                                String wallduct = key.toString();


                                for (DataSnapshot child4 : child3.getChildren()) {//wallduct itemname
                                    //loop to get teamid

                                    if(child4.getKey().toString().equals("textviewid")  ) {

                                        viewid = child4.getValue().toString();



                                    }
                                }


                                //to change color base on occupancy loop again


                                for (DataSnapshot child4 : child3.getChildren()) {//loop again to change color

                                    int resID = getResources().getIdentifier(wallduct, "id", getPackageName());

                                    TextView occupyduct = (TextView) findViewById(resID);

                                    occupyduct.setPaintFlags(occupyduct.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));




                                    if(child4.getValue().toString().equals("AVAILABLE")  ) {


                                        TextView occupyduct2 = (TextView) findViewById(resID);
                                        occupyduct2.setBackgroundColor(Color.GREEN);


                                    }

                                    if(child4.getValue().toString().equals("PARTIALLY UTILIZED")  ) {

                                        TextView occupyduct2 = (TextView) findViewById(resID);
                                        occupyduct2.setBackgroundColor(Color.YELLOW);


                                    }


                                    if(child4.getValue().toString().equals("FULLY UTILIZED")  ) {

                                        TextView occupyduct2 = (TextView) findViewById(resID);
                                        occupyduct2.setBackgroundColor(Color.RED);


                                    }

                                    if(child4.getValue().toString().equals("ABANDONED")  ) {

                                        TextView occupyduct2 = (TextView) findViewById(resID);
                                        occupyduct2.setBackgroundColor(Color.BLACK);


                                    }


                                    //tag the textview

                                    if(child4.getKey().toString().equals("nestductid")  ) {

                                        String nesductid = child4.getValue().toString();

                                        TextView occupyduct3 = (TextView) findViewById(resID);
                                        occupyduct3.setTag(nesductid);


                                    }
                                }


                               }

                            }


                        }




                    }
                }

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FIREBASELOAD", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


      // loadsummaryfirebase("Nesductid2");
        loadsummaryfirebase("Nesductidutilization");

       resetscreen();

    }



    public void loadsummaryfirebase(String column) {

        FirebaseDatabase databasefirebasesummary = FirebaseDatabase.getInstance();
        final DatabaseReference rootref = databasefirebasesummary.getReference(column).child(Markername);


        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TextView showdata = (TextView) findViewById(R.id.showdata);
                TextView showdata2 = (TextView) findViewById(R.id.showdata2);
                TextView showdata3 = (TextView) findViewById(R.id.showdata3);
                TextView showdata4 = (TextView) findViewById(R.id.showdata4);

                showdata.setText("");
                showdata2.setText("");
                showdata3.setText("");
                showdata4.setText("");

                int countductw1 = 0;
                int countutilizationw1 = 0;

                int countductw2 = 0;
                int countutilizationw2 = 0;

                int countductw3 = 0;
                int countutilizationw3 = 0;

                int countductw4 = 0;
                int countutilizationw4 = 0;

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    String nesduct = child.getKey().toString();





                    for (DataSnapshot child2 : child.getChildren()) {



                        if (child2.getKey().toString().contains("W1")) {

                            countductw1++;
                            countutilizationw1 = countutilizationw1+Integer.valueOf(child2.child("utilization").getValue().toString());


                            if (!showdata.getText().toString().contains(nesduct)) {


                                showdata.setText(showdata.getText() + "NestDuct:" + nesduct + System.getProperty("line.separator"));


                            }
                            showdata.setText(showdata.getText() + child2.getKey().toString() + " " + child2.child("occupancy").getValue() +" ");
                            showdata.setText(showdata.getText()  + " " + child2.child("utilization").getValue() + System.getProperty("line.separator"));


                        }

                        if (child2.getKey().toString().contains("W2")) {
                            countductw2++;
                            countutilizationw2 = countutilizationw2+Integer.valueOf(child2.child("utilization").getValue().toString());


                            if (!showdata2.getText().toString().contains(nesduct)) {
                                showdata2.setText(showdata2.getText() + "NestDuct:" + nesduct + System.getProperty("line.separator"));
                            }
                            showdata2.setText(showdata2.getText() + child2.getKey().toString() + " " + child2.child("occupancy").getValue() +" ");
                            showdata2.setText(showdata2.getText() +  " " + child2.child("utilization").getValue() + System.getProperty("line.separator"));

                        }
                        if (child2.getKey().toString().contains("W3")) {
                            countductw3++;
                            countutilizationw3 = countutilizationw3+Integer.valueOf(child2.child("utilization").getValue().toString());


                            if (!showdata3.getText().toString().contains(nesduct)) {
                                showdata3.setText(showdata3.getText() + "NestDuct:" + nesduct + System.getProperty("line.separator"));
                            }
                            showdata3.setText(showdata3.getText() + child2.getKey().toString() + " " + child2.child("occupancy").getValue() +" ");
                            showdata3.setText(showdata3.getText() +  " " + child2.child("utilization").getValue() + System.getProperty("line.separator"));
                        }



                        if (child2.getKey().toString().contains("W4")) {
                            countductw4++;
                            countutilizationw4 = countutilizationw4+Integer.valueOf(child2.child("utilization").getValue().toString());

                            if (!showdata4.getText().toString().contains(nesduct)) {
                                showdata4.setText(showdata4.getText() + "NestDuct:" + nesduct + System.getProperty("line.separator"));
                            }
                            showdata4.setText(showdata4.getText() + child2.getKey().toString() + " " + child2.child("occupancy").getValue() +" ");
                            showdata4.setText(showdata4.getText() + " " + child2.child("utilization").getValue() + System.getProperty("line.separator"));
                          }



                    }




                }

                int mainholeutilization = 0;
                int mainholecount = 0;

                if(countductw1>0){

                    Integer avg = countutilizationw1/countductw1;
                    showdata.setText(showdata.getText().toString() + avg +" Utilization"+System.getProperty("line.separator"));
                   mainholeutilization = mainholeutilization+avg;
                   mainholecount++;


                    TextView wall1 = (TextView)findViewById(R.id.wall1utilization);
                    wall1.setText(String.valueOf(avg));
                }

                if(countductw2>0){

                    Integer avg = countutilizationw2/countductw2;
                    showdata2.setText(showdata2.getText().toString() + avg +" Utilization"+System.getProperty("line.separator"));

                    mainholeutilization = mainholeutilization+avg;
                    mainholecount++;

                    TextView wall2 = (TextView)findViewById(R.id.wall2utilization);
                    wall2.setText(String.valueOf(avg));
                }
                if(countductw3>0){

                    Integer avg = countutilizationw3/countductw3;
                    showdata3.setText(showdata3.getText().toString() + avg +" Utilization"+System.getProperty("line.separator"));

                    mainholeutilization = mainholeutilization+avg;
                    mainholecount++;

                    TextView wall3 = (TextView)findViewById(R.id.wall3utilization);
                    wall3.setText(String.valueOf(avg));


                }
                if(countductw4>0){

                    Integer avg = countutilizationw4/countductw4;
                    showdata4.setText(showdata4.getText().toString() + avg +" Utilization"+System.getProperty("line.separator"));
                    mainholeutilization = mainholeutilization+avg;
                    mainholecount++;


                    TextView wall4 = (TextView)findViewById(R.id.wall4utilization);
                    wall4.setText(String.valueOf(avg));


                }

                if(mainholecount>0) {

                    int avgmanhole = mainholeutilization / mainholecount;
                    TextView manhole = (TextView) findViewById(R.id.mainholeutilization);
                    manhole.setText(String.valueOf(avgmanhole));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }

    public void resetscreen(){

        for (int l = 0; l < nestductmodelsarray.size(); l++) {


            Integer viewid = nestductmodelsarray.get(l).getWallductview();

            TextView texttoclear = (TextView) findViewById(viewid);
            int intID = getBackgroundColor(texttoclear);

            if(intID == Color.parseColor("#ffffff")) {




                texttoclear.setBackgroundColor(Color.parseColor("#3f51b5"));
                texttoclear.setPaintFlags(texttoclear.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            }



        }



        nestductmodelsarray.clear();
        nestductmodelsarraydel.clear();
        ductliststring.clear();

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        resetscreen();
    }



    public void selectgallerywall1(View view) {

        if (createby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            String idresource = view.getResources().getResourceName(view.getId());
            String wall = idresource.replace("my.com.tm.idraw:id/gallery", "");
            wallselected = wall;

            // Perform action on click
            Intent camera_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            File file = getFile(Markername + "_" + wall);

            Uri apkURI = FileProvider.getUriForFile(
                    mainholewall.this,
                    mainholewall.this.getApplicationContext()
                            .getPackageName() + ".provider", file);


            camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,

                    //photoURI
                    apkURI

            );


            startActivityForResult(camera_intent, 1);
        }

          else{

                Toast.makeText(mainholewall.this, "Only "+createby+" can update this manhole", Toast.LENGTH_SHORT).show();

            }

    }

    private File getFile(String filename){


        File Folder = new File(Environment.getExternalStorageDirectory() +
                File.separator +"DCIM");

        if(!Folder.exists()){

            Folder.mkdir();
        }

        File image_file = new File(Folder,filename+".jpg");

        return image_file;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {




        if(requestCode == 1){
            if (data != null) {
                Uri contentURI = data.getData();


                final String path = Environment.getExternalStorageDirectory() +
                        File.separator + "DCIM/" + Markername+"_"+wallselected + ".jpg";

                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error image Galery", e.toString());
                }
                Bitmap photo = Bitmap.createScaledBitmap(bmp, 1280, 1024, true);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                photo.compress(Bitmap.CompressFormat.JPEG, 80, bytes);

                File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "DCIM/" + Markername+"_"+wallselected + ".jpg");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fo = null;
                try {
                    fo = new FileOutputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if(wallselected.equals("wall1")) {

                    wallimage.setImageDrawable(Drawable.createFromPath(path));
                }
                if(wallselected.equals("wall2")) {

                    wallimage2.setImageDrawable(Drawable.createFromPath(path));
                }
                if(wallselected.equals("wall3")) {

                    wallimage3.setImageDrawable(Drawable.createFromPath(path));
                }
                if(wallselected.equals("wall4")) {

                    wallimage4.setImageDrawable(Drawable.createFromPath(path));
                }

            }



            // Create a storage reference from our app
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            File file = getFile(Markername+"_"+wallselected);
//                    Uri photoURI = FileProvider.getUriForFile(MyActivity.this,
            //       "my.com.tm.moapps.remoteandroid.fileprovider",
            //        file);

            Uri apkURI = FileProvider.getUriForFile(
                    mainholewall.this,
                    mainholewall.this.getApplicationContext()
                            .getPackageName() + ".provider", file);
            StorageReference riversRef = storageRef.child("DCIM" + File.separator + apkURI.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(apkURI);


            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {


                    Toast.makeText(mainholewall.this, "Failed Upload To Server", Toast.LENGTH_SHORT)
                            .show();

                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Toast.makeText(mainholewall.this, "Save to server", Toast.LENGTH_SHORT)
                            .show();

                }
            });


        }
    }

  public void loadimagewall(final ImageView view, String wallnumber){


          FirebaseStorage storage = FirebaseStorage.getInstance();
          StorageReference storageRef = storage.getReference();


          storageRef.child("DCIM" + File.separator + Markername + "_" + wallnumber + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
              @Override
              public void onSuccess(Uri uri) {
                  // TODO: handle uri

                  Context context = view.getContext();

                  view.invalidate();

                  Picasso.with(context).load(uri).networkPolicy(NetworkPolicy.NO_CACHE).into(view);


              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception exception) {
                  // Handle any errors
              }
          });



  }



}
