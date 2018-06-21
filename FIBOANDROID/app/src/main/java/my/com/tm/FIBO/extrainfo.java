package my.com.tm.FIBO;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class extrainfo extends AppCompatActivity {


    String manholename;
    String ownerstr;
    String tresspasingstr;
    String olnosstr;


    String createdby;
    LatLng manholeposition;



    TextView manholenametv;

    RadioGroup ownergroup;
    RadioGroup trespassinggroup;


    RadioButton ownerdeveloper;
    RadioButton ownertm;
    RadioButton ownerunknown;
    RadioButton trespassingyes;
    RadioButton trespassingno;


    CheckBox olnosunknown;
    CheckBox olnosdigi;
    CheckBox olnosumobile;
    CheckBox olnostime;
    CheckBox olnosmaxis;
    CheckBox olnoscelcom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra_info);

         manholename = getIntent().getStringExtra("markertitle");
         manholenametv = (TextView)findViewById(R.id.tvmanholenameinfo);
         manholenametv.setText(manholename);


        manholeposition=getIntent().getExtras().getParcelable("markerlatlng");
        createdby = getIntent().getStringExtra("markercreateby");

        ownerdeveloper = (RadioButton)findViewById(R.id.radiodeveloperowner);
        ownertm = (RadioButton)findViewById(R.id.radioTMowner);
        ownerunknown = (RadioButton)findViewById(R.id.radiounknownowner);





        //set the radio group
        ownergroup = (RadioGroup)findViewById(R.id.ownergroup);
        trespassinggroup = (RadioGroup)findViewById(R.id.trespassinggroup);



        //toggle olnos base on trespassing status



        olnoscelcom = (CheckBox)findViewById(R.id.olnoscelcom);
        olnosdigi = (CheckBox)findViewById(R.id.olnosdigi);
        olnosunknown = (CheckBox)findViewById(R.id.olnosunknown);
        olnosmaxis = (CheckBox)findViewById(R.id.olnosmaxis);
        olnostime = (CheckBox)findViewById(R.id.olnostime);
        olnoscelcom = (CheckBox)findViewById(R.id.olnoscelcom);
        olnosumobile = (CheckBox)findViewById(R.id.olnosumobile);

        olnoscelcom.setVisibility(View.INVISIBLE);
        olnosdigi.setVisibility(View.INVISIBLE);
        olnosunknown.setVisibility(View.INVISIBLE);
        olnosmaxis.setVisibility(View.INVISIBLE);
        olnostime.setVisibility(View.INVISIBLE);
        olnoscelcom.setVisibility(View.INVISIBLE);
        olnosumobile.setVisibility(View.INVISIBLE);


        loadmanholeinfo();

        trespassinggroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.trespassingyes) {
//                    Toast.makeText(getApplicationContext(), "choice: Yes",
//                            Toast.LENGTH_SHORT).show();

                    olnoscelcom.setVisibility(View.VISIBLE);
                    olnosdigi.setVisibility(View.VISIBLE);
                    olnosunknown.setVisibility(View.VISIBLE);
                    olnosmaxis.setVisibility(View.VISIBLE);
                    olnostime.setVisibility(View.VISIBLE);
                    olnoscelcom.setVisibility(View.VISIBLE);
                    olnosumobile.setVisibility(View.VISIBLE);

                    updatetrespassingfirebase("Yes");


                } else {
//                    Toast.makeText(getApplicationContext(), "choice: No",
//                            Toast.LENGTH_SHORT).show();

                    olnoscelcom.setVisibility(View.INVISIBLE);
                    olnosdigi.setVisibility(View.INVISIBLE);
                    olnosunknown.setVisibility(View.INVISIBLE);
                    olnosmaxis.setVisibility(View.INVISIBLE);
                    olnostime.setVisibility(View.INVISIBLE);
                    olnoscelcom.setVisibility(View.INVISIBLE);
                    olnosumobile.setVisibility(View.INVISIBLE);

                    updatetrespassingfirebase("No");
                    updateolnosfirebase("UNKNOWN");
                }
            }

        });


        ownergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radioTMowner) {
//                    Toast.makeText(getApplicationContext(), "choice: Yes",
//                            Toast.LENGTH_SHORT).show();
                    // olnosgroup.setVisibility(View.VISIBLE);

                    updateownerfirebase("TM");


                } else if(checkedId == R.id.radiodeveloperowner) {
//                    Toast.makeText(getApplicationContext(), "choice: No",
//                            Toast.LENGTH_SHORT).show();
                    updateownerfirebase("DEVELOPER");
                }

                else{

                    updateownerfirebase("UNKNOWN");
                }
            }

        });


        olnosumobile.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(olnosumobile.isChecked()) {
                    checkolnosavailable("Umobile");

                }
                else {

                    deleteolnosfirebase("Umobile");

                }


            }
        });


        olnoscelcom.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(olnoscelcom.isChecked()) {
                    checkolnosavailable("Celcom");

                }
                else{

                    deleteolnosfirebase("Celcom");

                }

            }
        });


        olnosdigi.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(olnosdigi.isChecked()) {
                    checkolnosavailable("Digi");


                }else{

                    deleteolnosfirebase("Digi");

                }

            }
        });


        olnosmaxis.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(olnosmaxis.isChecked()) {
                    checkolnosavailable("Maxis");

                }else{

                    deleteolnosfirebase("Maxis");

                }

            }
        });

        olnostime.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(olnostime.isChecked()) {
                    checkolnosavailable("Time");

                }else{

                    deleteolnosfirebase("Time");

                }

            }
        });

        olnosumobile.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(olnosumobile.isChecked()) {
                    checkolnosavailable("Umobile");

                }else{

                    deleteolnosfirebase("Umobile");

            }

            }
        });

        olnosunknown.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(olnosunknown.isChecked()) {
                    checkolnosavailable("UNKNOWN");

                }else{

                    deleteolnosfirebase("UNKNOWN");

                }

            }
        });




        //load initial value of extrainfo


    }



    public void updateownerfirebase(String owner){

        if (createdby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference();
            myRef.child("manholeinfo/"  + manholename).child("owner").setValue(owner);


         //   updatemysql
            updateextrainfo updateinfo = new updateextrainfo(getApplicationContext());
            updateinfo.execute(manholename,owner,"owner",FirebaseAuth.getInstance().getCurrentUser().getEmail());

            loadmanholeinfo();
        }


    }


    public void updatetrespassingfirebase(String trespass){

        if (createdby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference();
            myRef.child("manholeinfo/"  + manholename).child("trespass").setValue(trespass);


            //updatemysql
            updateextrainfo updateinfo = new updateextrainfo(getApplicationContext());
            updateinfo.execute(manholename,trespass,"trespass",FirebaseAuth.getInstance().getCurrentUser().getEmail());

            loadmanholeinfo();
        }


    }


    public void updateolnosfirebase(String olnos){

        if (createdby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference();
            myRef.child("manholeinfo/"  + manholename).child("olnos").push().setValue(olnos);


            //updatemysql
            updateextrainfo updateinfo = new updateextrainfo(getApplicationContext());
            updateinfo.execute(manholename,olnos,"olnos",FirebaseAuth.getInstance().getCurrentUser().getEmail());

           // loadmanholeinfo();
        }


    }

    public void deleteolnosfirebase(final String olnos){

        if (createdby.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference("manholeinfo").child(manholename);

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.child("olnos").exists()) {

                        for (DataSnapshot child: dataSnapshot.child("olnos").getChildren()) {

                            String olnosfirebase = child.getValue().toString();
                            String key = child.getKey();
//                            System.out.println(olnosfirebase);
//                            System.out.println(key);


                            if (olnosfirebase != null) {

                                if (olnos.equals(olnosfirebase)) {

                                    FirebaseDatabase databasefirebase2 = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef2 = databasefirebase2.getReference();

                                    myRef2.child("manholeinfo/"  + manholename).child("olnos").child(key).removeValue();



                                    //delete olnos in mysql
                                    deleteolnos deleteolnosmysql = new deleteolnos(getApplicationContext());
                                    deleteolnosmysql.execute(manholename,olnos,"olnos",FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                }

                            }


                    }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





            //updatemysql
//            updateextrainfo updateinfo = new updateextrainfo(getApplicationContext());
//            updateinfo.execute(manholename,olnos,"olnos",FirebaseAuth.getInstance().getCurrentUser().getEmail());


        }


    }



    public void checkolnosavailable(final String olnos){



            FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = databasefirebase.getReference("manholeinfo").child(manholename);

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.child("olnos").exists()) {

                        String abahkau = " ";

                        for (DataSnapshot child: dataSnapshot.child("olnos").getChildren()) {

                            String olnosfirebase = child.getValue().toString();
                            String key = child.getKey();
//                            System.out.println(olnosfirebase);
//                            System.out.println(key);


                            abahkau = abahkau+olnosfirebase;



                        }

                        if(!abahkau.contains(olnos)){

                            updateolnosfirebase(olnos);
                        }
                    }

                    else {

                        updateolnosfirebase(olnos);
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });







            //updatemysql
//            updateextrainfo updateinfo = new updateextrainfo(getApplicationContext());
//            updateinfo.execute(manholename,olnos,"olnos",FirebaseAuth.getInstance().getCurrentUser().getEmail());





    }

    public  void loadmanholeinfo(){

        FirebaseDatabase databasefirebaseinitial = FirebaseDatabase.getInstance();
        final DatabaseReference myRefdatabaseinitial = databasefirebaseinitial.getReference("manholeinfo").child(manholename);

        myRefdatabaseinitial.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ownerstr = "";
                tresspasingstr = "";
                olnosstr = "";


                if(dataSnapshot.child("trespass").exists()) {
                    String trespassfirebase = dataSnapshot.child("trespass").getValue().toString();

                    if (trespassfirebase != null) {

                        tresspasingstr = trespassfirebase;

                        if (tresspasingstr.equals("Yes")) {

                            trespassingyes = (RadioButton) findViewById(R.id.trespassingyes);
                            trespassingyes.setChecked(true);

                        } else if (tresspasingstr.equals("No")) {

                            trespassingno = (RadioButton) findViewById(R.id.tresspasingno);

                            trespassingno.setChecked(true);
                        }

                    }
                }

                if(dataSnapshot.child("owner").exists()) {
                    String ownerfirebase = dataSnapshot.child("owner").getValue().toString();

                    if (ownerfirebase != null) {

                        ownerstr = ownerfirebase;

                        if (ownerstr.equals("TM")) {

                            ownertm = (RadioButton) findViewById(R.id.radioTMowner);
                            ownertm.setChecked(true);

                        } else if (ownerstr.equals("DEVELOPER")) {

                            ownerdeveloper = (RadioButton) findViewById(R.id.radiodeveloperowner);

                            ownerdeveloper.setChecked(true);
                        } else {

                            ownerunknown = (RadioButton) findViewById(R.id.radiounknownowner);

                            ownerunknown.setChecked(true);

                        }

                    }
                }


                if(dataSnapshot.child("olnos").exists()) {

                    for (DataSnapshot child: dataSnapshot.child("olnos").getChildren()) {

                        String olnosfirebase = child.getValue().toString();

                        if (olnosfirebase != null) {

                            olnosstr = olnosfirebase;

                            if (olnosstr.equals("Celcom")) {

                                olnoscelcom = (CheckBox) findViewById(R.id.olnoscelcom);
                                olnoscelcom.setChecked(true);

                            } else if (olnosstr.equals("Digi")) {

                                olnosdigi = (CheckBox) findViewById(R.id.olnosdigi);

                                olnosdigi.setChecked(true);

                            } else if (olnosstr.equals("Umobile")) {

                                olnosumobile = (CheckBox) findViewById(R.id.olnosumobile);

                                olnosumobile.setChecked(true);
                            } else if (olnosstr.equals("Maxis")) {

                                olnosmaxis = (CheckBox) findViewById(R.id.olnosmaxis);

                                olnosmaxis.setChecked(true);
                            } else if (olnosstr.equals("Time")) {

                                olnostime = (CheckBox) findViewById(R.id.olnostime);

                                olnostime.setChecked(true);
                            } else {

                                olnosunknown = (CheckBox) findViewById(R.id.olnosunknown);

                                olnosunknown.setChecked(true);
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
    }

}
