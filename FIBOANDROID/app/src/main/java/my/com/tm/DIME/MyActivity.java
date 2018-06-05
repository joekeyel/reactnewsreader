package my.com.tm.DIME;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.maps.android.PolyUtil;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MyActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnInfoWindowClickListener,GoogleMap.OnMarkerDragListener,GoogleMap.OnMarkerClickListener {


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    String  uuid;

    public static MyActivity myactivitymain;


    public static String USERNAME1 = "";
    public static String UUIDLATLNG="";
    public static String token="";
    TextView resulttv;

    //marker of current position

    MarkerOptions tagmylocation;
    Marker deletemarker;


    //for loading bar progress
    AlertDialog.Builder alertDialogprogress;
    AlertDialog alertprogress;


    String cabinetidstrnameimage = "";
    String pushid = "";

    remarkadaptor rmarkadptor;
    ListView remarklistview;
    String remarkstr = "";
    EditText remarkinput;
    String markernamestr = "";

    static final int CAM_REQUEST = 1;
    Button btntagwithimage = null;
    EditText cabinetnameet = null;
    Spinner spinnerelement = null;
    ImageView imagecaptured = null;
    String elementstr = "Cabinet";
    Spinner elementtype;
    Spinner pttspinner;
    String cabinetidstr;
    MarkerOptions markercabinet;
    Marker marker;
    AlertDialog alert;
    boolean not_first_time_showing_info_window;
    boolean clearmap = true;


    //for googlemapview

    private GoogleMap mMap;

    //to change menu when click
    private Menu menuall;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    //free drawing maps
    Projection projection;
    Boolean Is_MAP_Moveable = false;
    Double latitude;
    Double longitude;
    Polyline polygon;
    Polyline poliline;
    PolylineOptions rectOptions;
    ArrayList<LatLng> val = new ArrayList<LatLng>();
    Float distance = (float) 0.0;;
    Object val2 = new ArrayList<String>();


    //for searching marker in page
    final ArrayList<markermodel> listallmarker = new ArrayList<>();
    ArrayList<MarkerOptions> listallmarker2 = new ArrayList<>();

    GoogleCloudMessaging gcm;
    public static final String REG_ID = "regId";
    Context context;
    static final String TAG = "Register Activity";

    AsyncTask<Void, Void, String> shareRegidTask, updatelatlng;

//geocoding for getting address

    Geocoder geocoder;
    List<Address> addresses;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


//        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        uuid = telephonyManager.getDeviceId();
//



        myactivitymain = this;


        resulttv = (TextView) findViewById(R.id.resultTv);


        //for googlemap api view generate map on fragment id map and allow the location

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //start update location every 5 minutes

       // startService(new Intent(MyActivity.this, locationupdateservice.class));
       // to get userid for given uuid of the phone from server




    }




    public static MyActivity getInstance() {

        return myactivitymain;
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub


        super.onDestroy();


    }






    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {


            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(final Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {

            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        //mCurrLocationMarker = mMap.addMarker(markerOptions);


        tagmylocation = markerOptions;


        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));





//        Float accuracy =  location.getAccuracy();
//
//        Toast.makeText(MyActivity.this, "Accuracy is "+String.valueOf(accuracy), Toast.LENGTH_LONG)
//                .show();
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        //put marker of other mobile on the map when map is ready





        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {



            public void onMapLongClick(LatLng latLng) {

                opentagcabinet(latLng);


            }

        });
//
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//
//
//            public void onMapClick(LatLng latLng) {
//
//                if(val.size()>0) {
//                   boolean result = isPointInPolygon(latLng, val);
//
//                    if(result == true)
//                    Toast.makeText(MyActivity.this, "Inside", Toast.LENGTH_SHORT)
//                            .show();
//
//                }
//
//            }
//
//        });


      mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
          @Override
          public boolean onMarkerClick(Marker marker) {

              deletemarker = marker;



              return false;
          }
      });


        FrameLayout fram_map = (FrameLayout) findViewById(R.id.fram_map);


        fram_map.setBackgroundColor(20);



        fram_map.setOnTouchListener(new View.OnTouchListener() {     @Override
        public boolean onTouch(View v, MotionEvent event) {

            ArrayList<LatLng> val2 = new ArrayList<>();
            if (Is_MAP_Moveable == true) {
                float x = event.getX();
                float y = event.getY();

                int x_co = Math.round(x);
                int y_co = Math.round(y);

                projection = mMap.getProjection();
                Point x_y_points = new Point(x_co, y_co);

                LatLng latLng = mMap.getProjection().fromScreenLocation(x_y_points);
                latitude = latLng.latitude;

                longitude = latLng.longitude;




                int eventaction = event.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        // finger touches the screen
                        val.add(new LatLng(latitude, longitude));



                        //  Log.e("draw", String.valueOf(latLng));

                    case MotionEvent.ACTION_MOVE:
                        // finger moves on the screen
                        val.add(new LatLng(latitude, longitude));
                        //Log.e("draw", String.valueOf(latLng));

                      case MotionEvent.ACTION_UP:
//                         finger leaves the scree
//
                       Draw_Map(val);


                       //checkifmarkerInsidethePoly(val);

                        break;
                }

            //    Draw_Map(val2);

                return true;

            } else {
                return false;
            }
        }
        });





        mMap.setInfoWindowAdapter(new infowindowsadaptor());

        loadfirebase();

        //for action when click on infowindows

        mMap.setOnInfoWindowClickListener(this);

        mMap.setOnMarkerDragListener(this);


    }

    //permission for phone to use location

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    public void Draw_Map(ArrayList<LatLng> vallat) {



        rectOptions = new PolylineOptions();
        rectOptions.addAll(vallat);
       rectOptions.color(Color.BLUE);
//        rectOptions.strokeWidth(7);
//        rectOptions.fillColor(Color.CYAN);
        polygon = mMap.addPolyline(rectOptions);





    }

    public void checkifmarkerInsidethePoly(ArrayList<LatLng> vallat){

        //this part to check if there is marker inside the drawing polyline

        if(listallmarker2.size()>0){

            ArrayList<MarkerOptions> listmarkerinpolyline = new ArrayList<>();
            for(int i =0;i <=listallmarker2.size()-1;i++) {

                LatLng markerposition = listallmarker2.get(i).getPosition();

                //if found manhole inside the drawing
                if( PolyUtil.containsLocation(markerposition, vallat, false) == true){

                    listmarkerinpolyline.add(listallmarker2.get(i));

                }





            }

            if(listmarkerinpolyline != null && listmarkerinpolyline.size()>0){

                listallmarker2.clear();
                listallmarker2 = listmarkerinpolyline;
                serachdialog();


            }
        }

    }

    public void Draw_map2(ArrayList<LatLng> vallat, String polylinename,String createdby){

        PolylineOptions polyoption = new PolylineOptions();
        polyoption.addAll(vallat);
        polyoption.color(Color.BLUE);

        poliline = mMap.addPolyline(polyoption);


        //add marker to tag the drawing name
        MarkerOptions markerdrawing = new MarkerOptions();
        markerdrawing.title(polylinename);
        markerdrawing.snippet(createdby);
        markerdrawing.position(vallat.get(vallat.size()-1));
        markerdrawing.icon(BitmapDescriptorFactory.fromResource(R.drawable.flagpole));

        mMap.addMarker(markerdrawing);


        //save to arraymarker object for searching of marker
        markermodel markerobject = new markermodel();
        markerobject.setCoordinate(vallat.get(vallat.size()-1));
        markerobject.setTitle(polylinename);

       // listallmarker.add(markerobject);
       // listallmarker2.add(markerdrawing);

       vallat.clear();


    }






    //function to togglemaps type
    Boolean type2 = true;

    public void togglemaptype(){

        if(type2 == true ) {

           mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            type2 = !type2;
        }
        else{

            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            type2 = !type2;

        }

    }




    //function to put marker on maps



 //function to save the polyline
 public void savepolyline( ArrayList<LatLng> arraylat) {

     ArrayList<LatLng> arraylatlng = new ArrayList<>();

     arraylatlng = arraylat;



     final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

     alert = alertDialog.create();

     alert.setTitle("Save Sketch");


     LayoutInflater inflater = getLayoutInflater();

     // inflate the custom popup layout
     final View convertView = inflater.inflate(R.layout.polylinesave_layout, null);



     alert.setView(convertView);

     alert.show();





      Button btnsavesktch = (Button)convertView.findViewById(R.id.btnsavesketch);



     final ArrayList<LatLng> finalArraylatlng = arraylatlng;
     final ArrayList<LatLng> finalArraylatlng1 = arraylatlng;


     btnsavesktch.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {


             for(Integer i =0;i < finalArraylatlng.size()-1;){

                 Location L1 = new Location("Start");
                 Location L2 = new Location("End");

                 L1.setLatitude(finalArraylatlng.get(i).latitude);
                 L1.setLongitude(finalArraylatlng.get(i).longitude);

                 L2.setLatitude(finalArraylatlng.get(i+1).latitude);
                 L2.setLongitude(finalArraylatlng.get(i+1).longitude);



                 distance = distance + L1.distanceTo(L2);
                 i++;



             }
             Log.e("Distance", String.valueOf(distance));


             // Perform action on click
             EditText sketchname = (EditText)convertView.findViewById(R.id.sketchname);

             String sketchnamestr = sketchname.getText().toString();



             if(sketchnamestr.equals("") || sketchname.equals(null)){

                 Toast.makeText(MyActivity.this, "Pls insert your drawing Name", Toast.LENGTH_LONG)
                         .show();
             }
             else{

                 //save to realtime database
                 FirebaseDatabase database = FirebaseDatabase.getInstance();
                 DatabaseReference myRef = database.getReference();

                 String distancestr = distance.toString().replace(".","_");

                 FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                 if (user != null) {
                     // User is signed in

                     //this will make the current sketch only on....new sketch will be added

                     myRef.child("currentsketch").removeValue();

                     for (int i = 0; i < finalArraylatlng.size(); i++) {
                         //System.out.println(val.get(i));
                         myRef.child("currentsketch/"+user.getUid()).child(sketchnamestr+":Distance"+distancestr+"m").child("Lat").child(String.valueOf(i)).setValue(finalArraylatlng.get(i).latitude+","+finalArraylatlng.get(i).longitude);
                         //  myRef.child("sketch/"+user.getUid()).child(sketchnamestr).child("Lng").child(String.valueOf(i)).setValue(finalArraylatlng.get(i).longitude);

                     }

                     //this save  will be used to loaded initial sketch...will load all sketches from firebase

                     for (int i = 0; i < finalArraylatlng.size(); i++) {
                         //System.out.println(val.get(i));
                         myRef.child("sketch/"+user.getUid()).child(sketchnamestr+":Distance:"+distancestr+"m").child("Lat").child(String.valueOf(i)).setValue(finalArraylatlng.get(i).latitude+","+finalArraylatlng.get(i).longitude);
                       //  myRef.child("sketch/"+user.getUid()).child(sketchnamestr).child("Lng").child(String.valueOf(i)).setValue(finalArraylatlng.get(i).longitude);

                     }

                     myRef.child("sketch/"+user.getUid()).child(sketchnamestr+":Distance:"+distancestr+"m").child("createdby").setValue(user.getEmail());


                     finalArraylatlng1.clear();
                     distance = (float)0.0;

                 } else {
                     // No user is signed in
                 }
                 alert.dismiss();



             }

             loadfirebase();

                 MenuView.ItemView saveitem = (MenuView.ItemView) findViewById(R.id.draw);
                 saveitem.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.drawicon));


         }
     });



     Button btncancel = (Button)convertView.findViewById(R.id.cancelsavesketch);

     btncancel.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {

             val.clear();
             loadfirebase();
            // setupdraw();
             alert.dismiss();
         }


     });


 }

 public void loadfirebase(){

   // make arraylist object marker to save all marker for searching function

   listallmarker.clear();
     listallmarker2.clear();


  mMap.clear();
   //first initial load

     FirebaseDatabase databasefirebase2 = FirebaseDatabase.getInstance();
     final DatabaseReference myRefdatabase2 = databasefirebase2.getReference("sketch");


     myRefdatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {


             ArrayList<String> listpolylat2 = new ArrayList<>();
             String polylinename = "";
             String createdby = "";

             LatLng lastcoordinates = new LatLng(0, 0);

             for (DataSnapshot child: dataSnapshot.getChildren()) {

             for (DataSnapshot child2 : child.getChildren()) {

                 polylinename = child2.getKey();
                 createdby = (String) child2.child("createdby").getValue();


                 listpolylat2 = (ArrayList<String>) child2.child("Lat").getValue();


                 ArrayList<LatLng> vallatlng = new ArrayList<>();

                 for (int i = 0; i < listpolylat2.size(); i++) {

                     String lat = String.valueOf(listpolylat2.get(i));
                     String[] separated = lat.split(",");


                     Double latdbl = Double.valueOf(separated[0]);
                     Double lngdbl = Double.valueOf(separated[1]);

                     LatLng coords = new LatLng(latdbl, lngdbl);
                     lastcoordinates = coords;

                     vallatlng.add(coords);
                 }

                 Draw_map2(vallatlng, polylinename, createdby);


             }

         }



         }

         @Override
         public void onCancelled(DatabaseError databaseError) {
             // Getting Post failed, log a message
             Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
             // ...
         }
     });



  //initial load once when loading

     FirebaseDatabase databasefirebaseinitial = FirebaseDatabase.getInstance();
     final DatabaseReference myRefdatabaseinitial = databasefirebaseinitial.getReference("photomarkeridraw");

     myRefdatabaseinitial.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {

             Double latphoto = Double.valueOf(0);
             Double lngphoto = Double.valueOf(0);


             String markername = "";
             String createdby = "";

               for (DataSnapshot child: dataSnapshot.getChildren()) {

             for (DataSnapshot child2 : child.getChildren()) {

                 markername = child2.getKey().toString();

                 for (DataSnapshot child3 : child2.getChildren()) {

                     if (child3.getKey().toString().equals("lat")) {
//                     Log.i("MARKERPHOTOLat", String.valueOf(markerlist));
//                     Log.i("MARKERPHOTOLng", String.valueOf(dataSnapshot.child("lng").getValue()));
                         Log.i("MARKERPHOTOname", markername);

                         latphoto = (Double) child3.getValue();


                     }

                     if (child3.getKey().toString().equals("lng")) {
//                     Log.i("MARKERPHOTOLat", String.valueOf(markerlist));
//                     Log.i("MARKERPHOTOLng", String.valueOf(dataSnapshot.child("lng").getValue()));
                         Log.i("MARKERPHOTOname", markername);

                         lngphoto = (Double) child3.getValue();


                     }

                     if (child3.getKey().toString().equals("createdby")) {
//                     Log.i("MARKERPHOTOLat", String.valueOf(markerlist));
//                     Log.i("MARKERPHOTOLng", String.valueOf(dataSnapshot.child("lng").getValue()));
                         Log.i("MARKERPHOTOname", createdby);

                         createdby = (String) child3.getValue();


                     }


                 }


                 if (!latphoto.equals(null)) {

                     LatLng coords = new LatLng(latphoto, lngphoto);


                     MarkerOptions markerphotooption = new MarkerOptions();
                     markerphotooption.position(coords);
                     markerphotooption.snippet(createdby);
                     markerphotooption.draggable(true);

                     // commnet for DIME apps...want to see manhole
//               if (markername.contains("Cabinet_")) {
//                   markerphotooption.title(markername);
//                   markerphotooption.icon(BitmapDescriptorFactory.fromResource(R.drawable.cabinet));
//                   //saving the marker for searching purpose
//                   markermodel markerobject = new markermodel();
//                   markerobject.setTitle(markername);
//                   markerobject.setCoordinate(coords);
//
//
//                   listallmarker.add(markerobject);
//                   listallmarker2.add(markerphotooption);
//
//
//                   mMap.addMarker(markerphotooption);
//               }
                     if (markername.contains("ManHole_")) {
                         markerphotooption.title(markername);
                         markerphotooption.icon(BitmapDescriptorFactory.fromResource(R.drawable.mainholeicon));
                         //saving the marker for searching purpose
                         markermodel markerobject = new markermodel();
                         markerobject.setTitle(markername);
                         markerobject.setCoordinate(coords);

                         listallmarker.add(markerobject);

                         listallmarker2.add(markerphotooption);


                         mMap.addMarker(markerphotooption);
                     }
                     // commnet for DIME apps...want to see manhole
//               if (markername.contains("DP_")) {
//                   markerphotooption.title(markername);
//                   markerphotooption.icon(BitmapDescriptorFactory.fromResource(R.drawable.dppole));
//                   //saving the marker for searching purpose
//                   markermodel markerobject = new markermodel();
//                   markerobject.setTitle(markername);
//                   markerobject.setCoordinate(coords);
//
//                   listallmarker.add(markerobject);
//                   listallmarker2.add(markerphotooption);
//
//
//                   mMap.addMarker(markerphotooption);
//                  }

                 }
             }
         }

             // ...
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {
             // Getting Post failed, log a message
             Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
             // ...
         }
     });





 }

//function to search marker list

    public void serachdialog(){



        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alert = alertDialog.create();

        alert.setTitle("Search Network Element");


        LayoutInflater inflater = getLayoutInflater();

        // inflate the custom popup layout
        final View convertView = inflater.inflate(R.layout.markerlist, null);
        // find the ListView in the popup layout

        final ListView listviewmarker = (ListView)convertView.findViewById(R.id.markerlv);

        final markerlistadaptor adaptormarker = new markerlistadaptor(getApplicationContext(),R.layout.markerrow,listallmarker2);
        listviewmarker.setAdapter(adaptormarker);

        SearchView sv = (SearchView) convertView.findViewById(R.id.serachmarker);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adaptormarker.getFilter().filter(newText);
                return false;
            }
        });


        alert.setView(convertView);

        alert.show();


        listviewmarker.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        MarkerOptions obj = (MarkerOptions) listviewmarker.getAdapter().getItem(position);
                        String title = obj.getTitle();
                        LatLng coords = obj.getPosition();


                        //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(coords));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
                    alert.dismiss();


                    }

                });





      Button dismiss = (Button)convertView.findViewById(R.id.dismissdialog);

      dismiss.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              // Perform action on click

              val.clear();
              loadfirebase();
              alert.dismiss();
              //setupdraw();

          }
      });

    }


//function to search marker list

    public void showmarkerdetail(final Marker marker){

        final String[] createby = {null};

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alert = alertDialog.create();

        alert.setTitle("Network Element Details");


        LayoutInflater inflater = getLayoutInflater();

        // inflate the custom popup layout
        final View convertView = inflater.inflate(R.layout.markerdetails, null);
        // find the ListView in the popup layout
        TextView title = (TextView)convertView.findViewById(R.id.markernametv);
        title.setText(marker.getTitle());

        final ImageView image = (ImageView)convertView.findViewById(R.id.imageViewmarker);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();


        storageRef.child("remote_camera" + File.separator +marker.getSnippet()+File.separator+ marker.getTitle() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // TODO: handle uri

                Context context = image.getContext();

                image.invalidate();

                Picasso.with(context).load(uri).networkPolicy(NetworkPolicy.NO_CACHE).into(image);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        final ListView listviewmarker = (ListView)convertView.findViewById(R.id.remarklv);

        final List<remarkmodel> listremark = new ArrayList<>();



        final remarkadaptor adaptormarker = new remarkadaptor(getApplicationContext(),R.layout.remarkrow,listremark);
        listviewmarker.setAdapter(adaptormarker);

        FirebaseDatabase databasefirebase2 = FirebaseDatabase.getInstance();
        final DatabaseReference myRefdatabase2 = databasefirebase2.getReference("remarkelement").child("").child(marker.getTitle());


        remarklistview = listviewmarker;
        rmarkadptor = adaptormarker;
// listerner initial

       myRefdatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    listremark.clear();

                    for (DataSnapshot child2 : dataSnapshot.getChildren()) {

                        remarkmodel remarkobject = new remarkmodel();
                        remarkobject.setCreatedby((String) child2.child("createdby").getValue());
                        remarkobject.setRemark((String) child2.child("remark").getValue());
                        remarkobject.setDatetime((String) child2.child("datetime").getValue());
                        remarkobject.setImageid((String) child2.child("imageid").getValue());

                        listremark.add(remarkobject);

                    }

                }else{



                }

                // adaptormarker.notifyDataSetChanged();

                scrollMyListViewToBottom(listviewmarker,adaptormarker);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        FirebaseDatabase databasefirebase3 = FirebaseDatabase.getInstance();
        final DatabaseReference myRefdatabase3 = databasefirebase3.getReference("remarkistyping").child(marker.getTitle());


        myRefdatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    TextView istypingtv = (TextView)convertView.findViewById(R.id.istyping);
                    istypingtv.setText(dataSnapshot.getValue().toString());

                }else{

                    TextView istypingtv = (TextView)convertView.findViewById(R.id.istyping);
                    istypingtv.setText("");

                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        alert.setView(convertView);

        alert.show();



        Button send = (Button)convertView.findViewById(R.id.sendremark);


        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                EditText insertremark = (EditText)convertView.findViewById(R.id.insertremark);
                String remarkstr = insertremark.getText().toString();


                if(!remarkstr.isEmpty()) {
                    FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = databasefirebase.getReference();

//                if(marker.getSnippet().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String formattedDate = df.format(currentTime.getTime());

                    HashMap<String, String> names = new HashMap();
                    names.put("remark", remarkstr);
                    names.put("createdby", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    names.put("datetime", formattedDate);


                    myRef.child("remarkelement/" + marker.getTitle().toString()).push().setValue(names);

                    insertremark.setText("");
                    myRef.child("remarkistyping/" + marker.getTitle().toString()).removeValue();

                    scrollMyListViewToBottom(listviewmarker,adaptormarker);

//                }

                }



            }
        });

        Button sendimage = (Button)convertView.findViewById(R.id.sendimage);


        sendimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = databasefirebase.getReference();

                pushid = myRef.push().getKey();

                EditText insertremark = (EditText)convertView.findViewById(R.id.insertremark);
                remarkinput = insertremark;
                String remarkstr2 = insertremark.getText().toString();
                remarkstr =remarkstr2;
                markernamestr = marker.getTitle().toString();


                // Perform action on click
                Intent camera_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                File file = getFile2(pushid);

                Uri apkURI = FileProvider.getUriForFile(
                        MyActivity.this,
                        MyActivity.this.getApplicationContext()
                                .getPackageName() + ".provider", file);


                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,

                        //photoURI
                        apkURI

                );

                startActivityForResult(camera_intent,3);




//                if(marker.getSnippet().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {



            }




        });


        Button sendcamera = (Button)convertView.findViewById(R.id.sendcamera);


        sendcamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = databasefirebase.getReference();

                pushid = myRef.push().getKey();

                EditText insertremark = (EditText)convertView.findViewById(R.id.insertremark);
                remarkinput = insertremark;
                String remarkstr2 = insertremark.getText().toString();
                remarkstr =remarkstr2;
                markernamestr = marker.getTitle().toString();



                // Perform action on click
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File file = getFile2(pushid);

                Uri apkURI = FileProvider.getUriForFile(
                        MyActivity.this,
                        MyActivity.this.getApplicationContext()
                                .getPackageName() + ".provider", file);


                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,

                        //photoURI
                        apkURI

                );

                camera_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivityForResult(camera_intent,4);




//                if(marker.getSnippet().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {



            }




        });


        final long delay = 1000; // 1 seconds after user stops typing
        final long[] last_text_edit = {0};
        final Handler handler = new Handler();

        final Runnable input_finish_checker = new Runnable() {
            public void run() {
                if (System.currentTimeMillis() > (last_text_edit[0] + delay - 500)) {
                    // TODO: do what you need here
                    // ............
                    // ............
                    FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = databasefirebase.getReference();

                    myRef.child("remarkistyping/" + marker.getTitle().toString()).setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail()+" is typing..........");

                }
            }
        };


        // event when user start typing
        EditText insertremark = (EditText)convertView.findViewById(R.id.insertremark);
        insertremark.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged (CharSequence s,int start, int count,
                                                                               int after){
                                                }
                                                @Override
                                                public void onTextChanged ( final CharSequence s, int start, int before,
                                                                            int count){
                                                    //You need to remove this to run only once
                                                    handler.removeCallbacks(input_finish_checker);

                                                }
                                                @Override
                                                public void afterTextChanged ( final Editable s){
                                                    //avoid triggering event when text is empty
                                                    if (s.length() > 0) {

                                                        last_text_edit[0] = System.currentTimeMillis();
                                                        handler.postDelayed(input_finish_checker, delay);

                                                        FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                                                        final DatabaseReference myRef = databasefirebase.getReference();

                                                        myRef.child("remarkistyping/" + marker.getTitle().toString()).removeValue();


                                                    } else {

                                                    }
                                                }
                                            }

        );


       image.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               // Perform action on click
               Intent i = new Intent(getApplicationContext(), streetview.class);

               i.putExtra("markerlatlng",marker.getPosition());
               i.putExtra("markertitle",marker.getTitle());
               startActivity(i);

           }
       });

        TextView textremark = (TextView) convertView.findViewById(R.id.createdbytv);
        textremark.setText("Created By:"+marker.getSnippet());

        Button delete = (Button)convertView.findViewById(R.id.deletemarker);

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = databasefirebase.getReference();

               if(marker.getSnippet().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

                   myRef.child("photomarkeridraw/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + marker.getTitle().toString()).removeValue();

                   myRef.child("sketch/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + marker.getTitle().toString()).removeValue();

                   myRef.child("Nesductidutilization/"  + marker.getTitle().toString()).removeValue();


                   //delete from mysql gnecc server

                   deletemanhole delete = new deletemanhole(getApplicationContext());
                   delete.execute(marker.getTitle().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail());

               }
                loadfirebase();
                alert.dismiss();

            }
        });






        //new DIME requirement only manhole will go to wall view


        Button gotowallbtn = (Button)convertView.findViewById(R.id.gotowall);
        gotowallbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                if(marker.getTitle().toString().contains("ManHole_")) {
                    Intent i = new Intent(getApplicationContext(), mainholewall.class);

                    i.putExtra("markerlatlng", marker.getPosition());
                    i.putExtra("markertitle", marker.getTitle());
                    i.putExtra("markercreateby", marker.getSnippet());
                    startActivity(i);

                    alert.dismiss();
                }


            }
        });


        Button gotodetails = (Button)convertView.findViewById(R.id.extrainfobtn);
        gotodetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                if(marker.getTitle().toString().contains("ManHole_")) {
                    Intent i = new Intent(getApplicationContext(), extrainfo.class);

                    i.putExtra("markerlatlng", marker.getPosition());
                    i.putExtra("markertitle", marker.getTitle());
                    i.putExtra("markercreateby", marker.getSnippet());
                    startActivity(i);

                    alert.dismiss();
                }


            }
        });


    }



// function popup when long click on maps

    public void opentagcabinet(final LatLng latlng){



        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alert = alertDialog.create();

        alert.setTitle("Tag Location");


        LayoutInflater inflater = getLayoutInflater();

        // inflate the custom popup layout
        final View convertView = inflater.inflate(R.layout.tagcabinet_layout, null);




        alert.setView(convertView);

        alert.show();


        final Button btntag = (Button)convertView.findViewById(R.id.btntagcabinetdpmainhole);
        final Button btncancel = (Button)convertView.findViewById(R.id.canceltagcabinetdpmainhole);
        final Button btncaptureimage = (Button)convertView.findViewById(R.id.capturebutton);
        final Button btncaptureimagegalerry = (Button)convertView.findViewById(R.id.selectgalerybutton);
        imagecaptured = (ImageView)convertView.findViewById(R.id.imagecaptureid);
        EditText cabinetidname = (EditText)convertView.findViewById(R.id.cabinetidtaglocation);
        cabinetidstr = String.valueOf(cabinetidname.getText());

        elementtype = (Spinner) convertView.findViewById(R.id.spinnerelementtype);

        btntagwithimage = (Button)convertView.findViewById(R.id.btntagcabinetdpmainhole);
        cabinetnameet = (EditText)convertView.findViewById(R.id.cabinetidtaglocation);

        cabinetidname.setTransformationMethod(new NumericKeyBoardTransformationMethod());

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.elementtype, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        elementtype.setAdapter(adapter);

        elementtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                elementstr = parent.getItemAtPosition(pos).toString();




            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });







        btncancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                if(marker != null){
                marker.remove();
                }
                alert.hide();

            }
        });

        btntag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText cabinetidtotag = (EditText)convertView.findViewById(R.id.cabinetidtaglocation);

                String cabinetstr = elementstr+cabinetidtotag.getText().toString();



                if(cabinetstr.equals("") || cabinetstr.equals(null)){
                    marker.remove();
                    alert.hide();
                }
                else{
                    marker.setTitle(cabinetstr);
                    alert.hide();


                }

            }
        });




        btncaptureimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                EditText cabinetidtotag = (EditText)convertView.findViewById(R.id.cabinetidtaglocation);
                String cabinet_name = cabinetidtotag.getText().toString();
                cabinetidstrnameimage = elementstr+"_"+cabinet_name;





                if(cabinet_name.equals("")) {
//                    marker.remove();
                    alert.hide();

                    Toast.makeText(MyActivity.this, "Pls Insert Text ID", Toast.LENGTH_SHORT)
                            .show();

                }else
                {

                    markercabinet = new MarkerOptions();
                    markercabinet.position(latlng);
                    markercabinet.title(cabinetidstrnameimage);
                    markercabinet.snippet(FirebaseAuth.getInstance().getCurrentUser().getEmail());


                   // marker.setTitle(cabinetidstrnameimage);

                    if(cabinetidstrnameimage.contains("Cabinet")) {
                        markercabinet.icon(BitmapDescriptorFactory.fromResource(R.drawable.cabinet));
                    }

                    if(cabinetidstrnameimage.contains("ManHole")) {
                        markercabinet.icon(BitmapDescriptorFactory.fromResource(R.drawable.mainholeicon));
                    }
                    if(cabinetidstrnameimage.contains("DP")) {
                        markercabinet.icon(BitmapDescriptorFactory.fromResource(R.drawable.dppole));
                    }



                    marker = mMap.addMarker(markercabinet);

                    marker.isDraggable();







                    // Perform action on click
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                    File file = getFile(cabinetidstrnameimage);
//                    Uri photoURI = FileProvider.getUriForFile(MyActivity.this,
                     //       "my.com.tm.moapps.remoteandroid.fileprovider",
                    //        file);

                    Uri apkURI = FileProvider.getUriForFile(
                            MyActivity.this,
                            MyActivity.this.getApplicationContext()
                                    .getPackageName() + ".provider", file);


                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,

                            //photoURI
                          apkURI

                    );


                    camera_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivityForResult(camera_intent, 0);


                    //put marker


                }




            }
        });


        btncaptureimagegalerry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               progressdialogshow();

                EditText cabinetidtotag = (EditText)convertView.findViewById(R.id.cabinetidtaglocation);
                String cabinet_name = cabinetidtotag.getText().toString();
                cabinetidstrnameimage = elementstr+"_"+cabinet_name;




                if(cabinet_name.equals("")) {
                    //marker.remove();
                    alert.hide();

                    Toast.makeText(MyActivity.this, "Pls Insert Text ID", Toast.LENGTH_SHORT)
                            .show();

                }else
                {

                    markercabinet = new MarkerOptions();
                    markercabinet.position(latlng);
                    markercabinet.title(cabinetidstrnameimage);
                    markercabinet.snippet(FirebaseAuth.getInstance().getCurrentUser().getEmail());


                    // marker.setTitle(cabinetidstrnameimage);

                    if(cabinetidstrnameimage.contains("Cabinet")) {
                        markercabinet.icon(BitmapDescriptorFactory.fromResource(R.drawable.cabinet));
                    }

                    if(cabinetidstrnameimage.contains("ManHole")) {
                        markercabinet.icon(BitmapDescriptorFactory.fromResource(R.drawable.mainholeicon));
                    }
                    if(cabinetidstrnameimage.contains("DP")) {
                        markercabinet.icon(BitmapDescriptorFactory.fromResource(R.drawable.dppole));
                    }



                    marker = mMap.addMarker(markercabinet);
                    marker.isDraggable();




                    // Perform action on click
                    Intent camera_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    File file = getFile(cabinetidstrnameimage);

                    Uri apkURI = FileProvider.getUriForFile(
                            MyActivity.this,
                            MyActivity.this.getApplicationContext()
                                    .getPackageName() + ".provider", file);


                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,

                            //photoURI
                            apkURI

                    );

                    startActivityForResult(camera_intent,1);




                    //put marker


                }




            }
        });



    }

    //create a file and folder for the image capture method

    private File getFile(String filename){


        File Folder = new File(Environment.getExternalStorageDirectory() +
                                File.separator +"camera_remote");

        if(!Folder.exists()){

            Folder.mkdir();
        }

        File image_file = new File(Folder,filename+".jpg");

        return image_file;


    }

    private File getFile2(String filename){


        File Folder = new File(Environment.getExternalStorageDirectory() +
                File.separator +"postimage");

        if(!Folder.exists()){

            Folder.mkdir();
        }

        File image_file = new File(Folder,filename+".jpg");

        return image_file;


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //disable the edittext and element selection after uploading the image success


        if(requestCode == 0 && resultCode == RESULT_OK) {

            cabinetnameet.setEnabled(false);
            elementtype.setEnabled(false);

            final String path = Environment.getExternalStorageDirectory() +
                    File.separator + "camera_remote/" + cabinetidstrnameimage + ".jpg";

            Bitmap bmp = BitmapFactory.decodeFile(path);
            Bitmap photo = scaleDown(bmp, 800, true);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "camera_remote/" + cabinetidstrnameimage + ".jpg");
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

            imagecaptured.setImageDrawable(Drawable.createFromPath(path));
        }

        if(requestCode == 1 && resultCode == RESULT_OK){

            cabinetnameet.setEnabled(false);
            elementtype.setEnabled(false);

            if (data != null) {
                Uri contentURI = data.getData();


                final String path = Environment.getExternalStorageDirectory() +
                        File.separator + "camera_remote/" + cabinetidstrnameimage + ".jpg";

                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error image Galery", e.toString());
                }
                Bitmap photo = Bitmap.createScaledBitmap(bmp, 300, 300, true);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

                File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "camera_remote/" + cabinetidstrnameimage + ".jpg");
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

                imagecaptured.setImageDrawable(Drawable.createFromPath(path));
            }
            progressbarhide();
        }




        if(resultCode == RESULT_OK && (requestCode == 0 || requestCode == 1)) {

            cabinetnameet.setEnabled(false);
            elementtype.setEnabled(false);

            btntagwithimage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    // Create a storage reference from our app
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    File file = getFile(cabinetidstrnameimage);
//                    Uri photoURI = FileProvider.getUriForFile(MyActivity.this,
                    //       "my.com.tm.moapps.remoteandroid.fileprovider",
                    //        file);

                    Uri apkURI = FileProvider.getUriForFile(
                            MyActivity.this,
                            MyActivity.this.getApplicationContext()
                                    .getPackageName() + ".provider", file);
                    StorageReference riversRef = storageRef.child("remote_camera" + File.separator + FirebaseAuth.getInstance().getCurrentUser().getEmail() + File.separator + apkURI.getLastPathSegment());
                    UploadTask uploadTask = riversRef.putFile(apkURI);


                    //create database info for marker

                    String useremail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                    FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = databasefirebase.getReference();
                    myRef.child("photomarkeridraw/" + FirebaseAuth
                            .getInstance().getCurrentUser().getUid() + "/" + cabinetidstrnameimage).child("lat").setValue(markercabinet.getPosition().latitude);
                    myRef.child("photomarkeridraw/" + FirebaseAuth
                            .getInstance().getCurrentUser().getUid() + "/" + cabinetidstrnameimage).child("lng").setValue(markercabinet.getPosition().longitude);

                    myRef.child("photomarkeridraw/" + FirebaseAuth
                            .getInstance().getCurrentUser().getUid() + "/" + cabinetidstrnameimage).child("createdby").setValue(useremail);


                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            marker.remove();
                            Toast.makeText(MyActivity.this, "Failed Upload To Server", Toast.LENGTH_SHORT)
                                    .show();

                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();


                            mMap.setInfoWindowAdapter(new infowindowsadaptor());

                            //marker.showInfoWindow();


                            alert.dismiss();
                        }
                    });


                    //update database of mysql

                    if (cabinetidstrnameimage.contains("ManHole_")) {
                        updatemysql(cabinetidstrnameimage, markercabinet.getPosition(), useremail);
                    }
                }


            });


        }

        if(requestCode == 3 && resultCode == RESULT_OK){
            if (data != null) {
                Uri contentURI = data.getData();


                final String path = Environment.getExternalStorageDirectory() +
                        File.separator + "postimage/" + pushid + ".jpg";
                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error image Galery", e.toString());
                }
                Bitmap photo = Bitmap.createScaledBitmap(bmp, 300, 300, true);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

                File f = new File(path);

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

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                File file = getFile2(pushid);
//                    Uri photoURI = FileProvider.getUriForFile(MyActivity.this,
                //       "my.com.tm.moapps.remoteandroid.fileprovider",
                //        file);

                Uri apkURI = FileProvider.getUriForFile(
                        MyActivity.this,
                        MyActivity.this.getApplicationContext()
                                .getPackageName() + ".provider", file);
                StorageReference riversRef = storageRef.child("postimage" + File.separator  + pushid+".jpg");
                UploadTask uploadTask = riversRef.putFile(apkURI);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {


                        Toast.makeText(MyActivity.this, "Failed Upload To Server", Toast.LENGTH_SHORT)
                                .show();

                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.


                        Toast.makeText(MyActivity.this, "Upload To Server", Toast.LENGTH_SHORT)
                                .show();


                        FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef = databasefirebase.getReference();



                        Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String formattedDate = df.format(currentTime.getTime());

                        HashMap<String, String> names = new HashMap();
                        names.put("remark", remarkstr);
                        names.put("createdby", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        names.put("datetime", formattedDate);
                        names.put("imageid", pushid);

                        myRef.child("remarkelement/" + markernamestr).child(pushid).setValue(names);

                        remarkinput.setText("");
                        myRef.child("remarkistyping/" + markernamestr).removeValue();

                        scrollMyListViewToBottom(remarklistview,rmarkadptor);

                        rmarkadptor.notifyDataSetChanged();


                    }
                });


            }
//            progressbarhide();
        }


        if(requestCode == 4 && resultCode == RESULT_OK){



            final String path = Environment.getExternalStorageDirectory() +
                    File.separator + "postimage/" + pushid + ".jpg";

            Bitmap bmp = BitmapFactory.decodeFile(path);

            Bitmap photo = scaleDown(bmp, 600, true);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "postimage/" + pushid + ".jpg");
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

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            File file = getFile2(pushid);
//                    Uri photoURI = FileProvider.getUriForFile(MyActivity.this,
            //       "my.com.tm.moapps.remoteandroid.fileprovider",
            //        file);

            Uri apkURI = FileProvider.getUriForFile(
                    MyActivity.this,
                    MyActivity.this.getApplicationContext()
                            .getPackageName() + ".provider", file);
            StorageReference riversRef = storageRef.child("postimage" + File.separator  + pushid+".jpg");
            UploadTask uploadTask = riversRef.putFile(apkURI);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {


                    Toast.makeText(MyActivity.this, "Failed Upload To Server", Toast.LENGTH_SHORT)
                            .show();

                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                    Toast.makeText(MyActivity.this, "Upload To Server", Toast.LENGTH_SHORT)
                            .show();


                    FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = databasefirebase.getReference();



                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String formattedDate = df.format(currentTime.getTime());

                    HashMap<String, String> names = new HashMap();
                    names.put("remark", remarkstr);
                    names.put("createdby", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    names.put("datetime", formattedDate);
                    names.put("imageid", pushid);

                    myRef.child("remarkelement/" + markernamestr).child(pushid).setValue(names);

                    remarkinput.setText("");
                    myRef.child("remarkistyping/" + markernamestr).removeValue();

                    scrollMyListViewToBottom(remarklistview,rmarkadptor);

                    rmarkadptor.notifyDataSetChanged();


                }
            });


        }


    }


//resize image function

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    //implement from the oninfiwindow click listener


    @Override
    public void onInfoWindowClick(Marker markerdrag) {



     showmarkerdetail(markerdrag);



    }


    @Override
    public void onMarkerDragStart(Marker marker) {

        mMap.getUiSettings().setScrollGesturesEnabled(false);

        //create database info for marker

        FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = databasefirebase.getReference();



        myRef.child("photomarkeridraw/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+marker.getTitle().toString()).child("lat").removeValue();

        myRef.child("photomarkeridraw/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+marker.getTitle().toString()).child("lng").removeValue();





    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(final Marker marker) {

        mMap.getUiSettings().setScrollGesturesEnabled(true);

        FirebaseDatabase databasefirebase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = databasefirebase.getReference();


//add listener on remark update
        FirebaseDatabase databasefirebaseinitial = FirebaseDatabase.getInstance();
        final DatabaseReference myRefdatabaseinitial = databasefirebaseinitial.getReference("photomarkeridraw");

        myRefdatabaseinitial.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child: dataSnapshot.getChildren()) {


                    for (DataSnapshot child2 : child.getChildren()) {

                        if(child2.getKey().toString().equals(marker.getTitle())){

                            for (DataSnapshot child3 : child2.getChildren()) {


                                if(child3.getKey().toString().equals("createdby")){

                                    String usercreated = child3.getValue().toString();
                                    String usernow = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                    if(usercreated.equals(usernow)) {

                                        myRef.child("photomarkeridraw/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + marker.getTitle().toString()).child("lat").setValue(marker.getPosition().latitude);
                                        myRef.child("photomarkeridraw/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + marker.getTitle().toString()).child("lng").setValue(marker.getPosition().longitude);
                                    }
                                    else{

                                        Toast.makeText(MyActivity.this, "U cannot move this marker, Only Creator of the marker can move", Toast.LENGTH_LONG)
                                                .show();
                                    }

                                }

                            }
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });




    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.search:
                serachdialog();
                return true;

            case R.id.taglocation:

                LatLng latlng =tagmylocation.getPosition();
                opentagcabinet(latlng);

                return true;

            case R.id.toggle:
                togglemaptype();
                return true;


            case R.id.reload:
               loadfirebase();
                return true;

            case R.id.draw:
                setupdraw();
                return true;

            case R.id.boundry:
               checkifmarkerInsidethePoly(val);
                return true;

            case R.id.signout:
               FirebaseAuth.getInstance().signOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void setupdraw() {

        // TODO Auto-generated method stub
        if (Is_MAP_Moveable != true && val.size() <= 0) {
            Is_MAP_Moveable = true;

            Toast.makeText(MyActivity.this, "Drawing Is Enable Now, Pls Tap on the Maps to Draw your network", Toast.LENGTH_LONG)
                    .show();

            MenuView.ItemView saveitem = (MenuView.ItemView) findViewById(R.id.draw);
            saveitem.setIcon(ContextCompat.getDrawable(this, R.drawable.drawred));




        } else if (Is_MAP_Moveable != true && val.size() > 0) {


            savepolyline(val);
            Toast.makeText(MyActivity.this, "Drawing Save, Pls Tap Reload to View Marker", Toast.LENGTH_SHORT)
                    .show();

         // checkifmarkerInsidethePoly(val);



        } else if (Is_MAP_Moveable == true ) {
            Is_MAP_Moveable = false;

//                    Button btnsave = (Button)findViewById(R.id.buttonLoad);
//                    btnsave.setText("Save Sketch");

            Toast.makeText(MyActivity.this, "Drawing is Disable Now", Toast.LENGTH_SHORT)
                    .show();

            if(val.size()==0) {
                MenuView.ItemView saveitem = (MenuView.ItemView) findViewById(R.id.draw);
                saveitem.setIcon(ContextCompat.getDrawable(this, R.drawable.drawicon));
            }

            if(val.size()>0) {
                MenuView.ItemView saveitem = (MenuView.ItemView) findViewById(R.id.draw);
                saveitem.setIcon(ContextCompat.getDrawable(this, R.drawable.saveicon));
            }

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // Sign in logic here.

                    Intent i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);
                }
            }
        });


    }


    public void progressdialogshow(){


        alertDialogprogress = new AlertDialog.Builder(MyActivity.this);

        alertprogress = alertDialogprogress.create();

        alertprogress.setTitle("Loading");

        LayoutInflater inflater = getLayoutInflater();

        // inflate the custom popup layout
        final View convertView = inflater.inflate(R.layout.progressdialog, null);
        // find the ListView in the popup layout
        final ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar4);

        // setSimpleList(listView, comment);

        alertprogress.setView(convertView);
        alertprogress.setCanceledOnTouchOutside(false);

        if(alertprogress == null) {
            alertprogress.show();
        }





    }

    public void progressbarhide(){

        alertprogress.dismiss();

    }


    //function when clicking duct from every wall


    public List<Address> getaddress(LatLng location){

        geocoder = new Geocoder(this,Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            return addresses;
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }



    public void updatemysql(String manholeid,LatLng location,String createdby) {


        List<Address> addresses = getaddress(location);

        if (addresses != null) {

            String address = "";
            String city = "";
            String state = "";
            String postalCode = "";
            String knownName = "";
            String premises = "";

            if (addresses.get(0).getAddressLine(0) != null) {
                address = addresses.get(0).getAddressLine(0);
            }

            if (addresses.get(0).getLocality() != null) {
                city = addresses.get(0).getLocality();
            }

            if (addresses.get(0).getLocality() != null) {
                state = addresses.get(0).getAdminArea();
            }

            if (addresses.get(0).getPostalCode() != null) {
                postalCode = addresses.get(0).getPostalCode();
            }
            if (addresses.get(0).getFeatureName() != null) {
                knownName = addresses.get(0).getFeatureName();
            }
            if (addresses.get(0).getPremises() != null) {
                premises = addresses.get(0).getPremises();
            }

            addnewmanhole update = new addnewmanhole(getApplicationContext());

            update.execute(manholeid, state, city, address, String.valueOf(location.latitude), String.valueOf(location.longitude), createdby, postalCode, knownName, premises);

        }
    }




    private boolean isPointInPolygon(LatLng tap, ArrayList<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }


    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

    private void scrollMyListViewToBottom(final ListView myListView, final remarkadaptor myListAdapter ) {
        myListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                myListView.setSelection(myListAdapter.getCount() - 1);
            }
        });
    }
}