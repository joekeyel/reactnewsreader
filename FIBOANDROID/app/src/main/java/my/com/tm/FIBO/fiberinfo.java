package my.com.tm.FIBO;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class fiberinfo extends AppCompatActivity {


    String siteid,updatedby;

    AlertDialog alertstate;
    Geocoder geocoder;
    List<Address> addresses = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiberinfo);

        Intent i = getIntent();

        double latitude = i.getDoubleExtra("latitude",0);
        double longitude = i.getDoubleExtra("longitude",0);


        TextView latitudetv = (TextView)findViewById(R.id.latitudetv);
        latitudetv.setText(String.valueOf(latitude));

        TextView longitudetv = (TextView)findViewById(R.id.longitudetv);
        longitudetv.setText(String.valueOf(longitude));



        geocoder = new Geocoder(this, Locale.getDefault());

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

        TextView customertv = (TextView)findViewById(R.id.customeradrtv);

        customertv.setText(address);

        popoveradress(addresses);

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


                        TextView customertv = (TextView)findViewById(R.id.customeradrtv);

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

                popoveradress(addresses);


                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
