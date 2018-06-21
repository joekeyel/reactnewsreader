package my.com.tm.FIBO;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

public class streetview extends FragmentActivity
        implements OnStreetViewPanoramaReadyCallback {

    LatLng objLatLng;
    String Markername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetview);


         objLatLng=getIntent().getExtras().getParcelable("markerlatlng");
         Markername = getIntent().getStringExtra("markertitle");

        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);

        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

        streetViewPanorama.setPosition(objLatLng);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
