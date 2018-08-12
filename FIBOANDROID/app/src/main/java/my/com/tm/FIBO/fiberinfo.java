package my.com.tm.FIBO;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class fiberinfo extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnInfoWindowClickListener,GoogleMap.OnMarkerDragListener,GoogleMap.OnMarkerClickListener {


    String siteid,updatedby,orderid;

    AlertDialog alertstate;
    AlertDialog alertmap;
    Geocoder geocoder;
    List<Address> addresses = null;
    public static fiberinfo fiberinfo;

    SupportMapFragment mapFragment = null;
    private GoogleMap mMap;

    //to change menu when click
    private Menu menuall;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker ;
    LocationRequest mLocationRequest;
    View convertView;

    MarkerOptions tagmylocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiberinfo);

        fiberinfo = this;
        Intent i = getIntent();

         siteid = i.getStringExtra("siteid");
         orderid = i.getStringExtra("orderid");
         updatedby = i.getStringExtra("updatedby");

//        double latitude = i.getDoubleExtra("latitude",0);
//        double longitude = i.getDoubleExtra("longitude",0);
//
//
//        TextView latitudetv = (TextView)findViewById(R.id.latitudetv);
//        latitudetv.setText(String.valueOf(latitude));
//
//        TextView longitudetv = (TextView)findViewById(R.id.longitudetv);
//        longitudetv.setText(String.valueOf(longitude));
//
//
//
//        geocoder = new Geocoder(this, Locale.getDefault());
//
//        try {
//            addresses = geocoder.getFromLocation(latitude, longitude, 10); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
//        String knownName = addresses.get(0).getFeatureName(); //
//
//        TextView customertv = (TextView)findViewById(R.id.customeradrtv);
//
//        customertv.setText(address);
//
//        popoveradress(addresses);

       getfiberinfotask loadinfo = new getfiberinfotask();
       loadinfo.execute(Config.APP_SERVER_URL27,siteid);

    }

    public static fiberinfo getInstance() {

        return fiberinfo;
    }


    public void loadinfo(fibermodel3 infofiber){

        EditText orderidet = (EditText)findViewById(R.id.orderidet);
        orderidet.setText(infofiber.getOrderid());

        EditText customernameet = (EditText)findViewById(R.id.customernameet);
        customernameet.setText(infofiber.getCustomername());

        EditText siteidet = (EditText)findViewById(R.id.siteidet);
        siteidet.setText(infofiber.getSiteid());

        EditText addresset = (EditText)findViewById(R.id.customeraddresset);
        addresset.setText(infofiber.getCustomeraddr());

        TextView latitudetv = (TextView)findViewById(R.id.latitudetv);
        latitudetv.setText(infofiber.getLatitude());

        TextView longitudetv = (TextView)findViewById(R.id.longitudetv);
        longitudetv.setText(infofiber.getLongitude());

        EditText tmnodeet = (EditText)findViewById(R.id.tmnodeet);
        tmnodeet.setText(infofiber.getTmnode());

        EditText slgtypeet = (EditText)findViewById(R.id.slgtypeet);
        slgtypeet.setText(infofiber.getSlgtype());

        EditText metroemainprotect = (EditText)findViewById(R.id.metroemainprotect);
        metroemainprotect.setText(infofiber.getMetroemainprotect());

        EditText metroepenode = (EditText)findViewById(R.id.metroepenode);
        metroepenode.setText(infofiber.getMetroeepenode());

        EditText metroeiucport = (EditText)findViewById(R.id.iucportet);
        metroeiucport.setText(infofiber.getMetroeiucport());

        EditText metroeepe = (EditText)findViewById(R.id.epeportet);
        metroeepe.setText(infofiber.getMetroeepeport());

        EditText metroesfp = (EditText)findViewById(R.id.sfptypeet);
        metroesfp.setText(infofiber.getMetroesfptype());

        EditText fibermainprotectet = (EditText)findViewById(R.id.fibermainprotectet);
        fibermainprotectet.setText(infofiber.getFibermainprotect());

        EditText fibertypeet = (EditText)findViewById(R.id.fibertypeet);
        fibertypeet.setText(infofiber.getFibertype());

        EditText fibernameet = (EditText)findViewById(R.id.fibernameet);
        fibernameet.setText(infofiber.getFibername());

        EditText fibercoreet = (EditText)findViewById(R.id.fibercoreet);
        fibercoreet.setText(infofiber.getFibercore());

        EditText fiberdistanceet = (EditText)findViewById(R.id.fiberdistanceet);
        fiberdistanceet.setText(infofiber.getFiberdistance());





    }

    public void popoveradress(final List<Address> addresses){



        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertstate = alertDialog.create();

        alertstate.setTitle("Select Address");


        LayoutInflater inflater = getLayoutInflater();

        // inflate the custom popup layout
        final View convertView = inflater.inflate(R.layout.markerlist, null);
        // find the ListView in the popup layout

        final ListView listviewmarker = (ListView)convertView.findViewById(R.id.markerlv);

        final adressadapter adaptormarker = new adressadapter(getApplicationContext(),R.layout.staterow,addresses);
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


        alertstate.setView(convertView);

        alertstate.show();


        listviewmarker.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Address address = (Address) listviewmarker.getAdapter().getItem(position);


                        TextView customertv = (TextView)findViewById(R.id.customeraddresset);

                        customertv.setText(address.getAddressLine(0));


                        alertstate.dismiss();


                    }

                });





        Button dismiss = (Button)convertView.findViewById(R.id.dismissdialog);

        dismiss.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click



                alertstate.dismiss();
                //setupdraw();

            }
        });

    }


    public void popovermap(){



        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertmap = alertDialog.create();

        alertmap.setTitle("Tag Location");


        LayoutInflater inflater = getLayoutInflater();


        if (convertView != null) {

            mMap.getUiSettings().setScrollGesturesEnabled(true);
            ViewGroup parent = (ViewGroup) convertView.getParent();
            if (parent != null)
                parent.removeView(convertView);
        }

        try{

       convertView = inflater.inflate(R.layout.mapview, null);
          if(mMap != null) {
              mMap.getUiSettings().setScrollGesturesEnabled(true);
          }

        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }


       if(mapFragment == null) {

           mapFragment = (SupportMapFragment) getSupportFragmentManager()
                   .findFragmentById(R.id.mappopover);
           mapFragment.getMapAsync(this);

       }

       Button tagbutton = (Button)convertView.findViewById(R.id.tagbutton);

        tagbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tagmylocation != null) {

                    double latitude = tagmylocation.getPosition().latitude;
                    double longitude = tagmylocation.getPosition().longitude;

                    TextView latitudetv = (TextView) findViewById(R.id.latitudetv);
                    latitudetv.setText(String.valueOf(latitude));

                    TextView longitudetv = (TextView) findViewById(R.id.longitudetv);
                    longitudetv.setText(String.valueOf(longitude));

                    alertmap.dismiss();

                            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(latitude, longitude, 10); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName(); //

                            TextView customertv = (TextView)findViewById(R.id.customeraddresset);

                            customertv.setText(address);

                            popoveradress(addresses);

                }
            }
        });

        alertmap.setView(convertView);

        alertmap.show();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu5, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.loadaddress:

             popovermap();

                return true;


            case R.id.loadfiberdetails:

                Intent i = new Intent(getApplicationContext(),fiberdetails.class);
                i.putExtra("orderid",orderid);
                i.putExtra("siteid",siteid);
                i.putExtra("updatedby",updatedby);


                startActivity(i);


                return true;


            case R.id.uploadcamera:

                Intent j = new Intent(getApplicationContext(),image.class);
                j.putExtra("orderid",orderid);
                j.putExtra("siteid",siteid);
                j.putExtra("updatedby",updatedby);


                startActivity(j);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

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


         Double latitude = 0.0;
         Double longitude = 0.0;

        TextView latitudetv = (TextView)findViewById(R.id.latitudetv);
        String latitudestr = latitudetv.getText().toString();

        if(!latitudestr.isEmpty()){
          latitude = Double.valueOf(latitudestr);
        }

        TextView longitudetv = (TextView)findViewById(R.id.longitudetv);
        String longitudestr = longitudetv.getText().toString();

        if(!longitudestr.isEmpty()) {

            longitude = Double.valueOf(longitudestr);
        }

        if(!(latitude == 0.0) && !(longitude == 0.0)){

            mMap.getUiSettings().setScrollGesturesEnabled(true);

            mMap.clear();

            LatLng latlng = new LatLng(latitude,longitude);
            MarkerOptions markeroption = new MarkerOptions();
            markeroption.position(latlng);
            markeroption.draggable(true);

            // markeroption.icon(BitmapDescriptorFactory.fromResource(R.drawable.dppole));

            mMap.addMarker(markeroption);

        }


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {



            public void onMapLongClick(LatLng latLng) {

                mMap.clear();

                MarkerOptions markeroption = new MarkerOptions();
                markeroption.position(latLng);
                markeroption.draggable(true);

               // markeroption.icon(BitmapDescriptorFactory.fromResource(R.drawable.dppole));

                mMap.addMarker(markeroption);

              tagmylocation = markeroption;

                mMap.getUiSettings().setScrollGesturesEnabled(false);

        }

        });



        mMap.setOnMarkerDragListener(this);

    }

    private void buildGoogleApiClient() {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

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
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {


       return true;

    }

    @Override
    public void onMarkerDragStart(Marker marker) {


        mMap.getUiSettings().setScrollGesturesEnabled(false);
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        mMap.getUiSettings().setScrollGesturesEnabled(true);
        tagmylocation.position(marker.getPosition());

    }





    
}
