package com.example.muzamil.navigationtestingsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    PlaceAutocompleteFragment sourceFragment;
    PlaceAutocompleteFragment destinationFragment;
    Place sourcePlace;
    Place destinationPlace;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpComponents();
    }

    private void setUpComponents() {
        initializeComponents();
        setUpListeners();
    }

    private void initializeComponents() {

        button = (Button) findViewById(R.id.button);
        sourceFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.sourceFragment);
        destinationFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.destinationFragment);


    }

    private void setUpListeners() {

        sourceFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                sourcePlace = place;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
                sourcePlace = null;
            }
        });

        destinationFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                destinationPlace = place;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
                sourcePlace = null;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GeoApiContext context = new GeoApiContext().setApiKey(getResources().getString(R.string.google_map_api_key));
                DirectionsResult result = null;

                try {
//                    result = DirectionsApi.getDirections(context, "Sydney, AU",
//                            "Melbourne, AU").await();
//                    result = DirectionsApi.getDirections(context,
//                            sourcePlace.getLatLng().toString() ,
//                            destinationPlace.getLatLng().toString()).await();

                    double lat = sourcePlace.getLatLng().latitude;
                    double lng = sourcePlace.getLatLng().longitude;
                    LatLng sourceLatlng = new LatLng(lat, lng);

                     lat = destinationPlace.getLatLng().latitude;
                     lng = destinationPlace.getLatLng().longitude;
                    LatLng destLatlng = new LatLng(lat, lng);


                    result = DirectionsApi.newRequest(context)
                            .origin(sourceLatlng)
                            .destination(destLatlng)
                            .await();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result != null)
                    Log.d(TAG, result.toString());

            }
        });

    }

}
