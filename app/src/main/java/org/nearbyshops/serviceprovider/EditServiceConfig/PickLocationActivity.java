package org.nearbyshops.serviceprovider.EditServiceConfig;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.mapbox.mapboxsdk.annotations.MarkerView;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;


import org.nearbyshops.serviceprovider.ModelServiceConfig.ServiceConfigurationLocal;
import org.nearbyshops.serviceprovider.Preferences.PrefServiceConfiguration;
import org.nearbyshops.serviceprovider.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class PickLocationActivity extends FragmentActivity implements SeekBar.OnSeekBarChangeListener {

//    private GoogleMap mMap;
//    Marker currentMarker;



    @BindView(R.id.mapview) MapView mapView;

    MarkerView markerView;

    MapboxMap mapboxMapInstance;



    @BindView(R.id.seekbar)
    SeekBar seekbar;

    @BindView(R.id.delivery_range_header)
    TextView deliveryRangeHeader;
    private double deliveryRange;


    public static final String INTENT_KEY_CURRENT_LAT = "current_lat";
    public static final String INTENT_KEY_CURRENT_LON = "current_lon";
    public static final String INTENT_KEY_DELIVERY_RANGE = "delivery_range";


//    float current_latitude;
//    float current_longitude;


//    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_pick_location_new);

        ButterKnife.bind(this);


//        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

//        ServiceConfigurationLocal configuration = PrefServiceConfiguration.getServiceConfig(this);
        mapView.setStyleUrl("abcd");

        seekbar.setOnSeekBarChangeListener(this);


        final double current_lat = getIntent().getDoubleExtra(INTENT_KEY_CURRENT_LAT,-1000);
        final double current_lon = getIntent().getDoubleExtra(INTENT_KEY_CURRENT_LON,-1000);
        final double delivery_range = getIntent().getDoubleExtra(INTENT_KEY_DELIVERY_RANGE,-1);



        // Add a MapboxMap
        mapView.getMapAsync(new com.mapbox.mapboxsdk.maps.OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.



//                VectorSource vectorSource = new VectorSource("source-id", "mapbox://mapbox.2opop9hr");
//                mapboxMap.addSource(vectorSource);


//                mapboxMap.setMyLocationEnabled(true);
                mapboxMap.getUiSettings().setAllGesturesEnabled(true);
                mapboxMap.getUiSettings().setLogoEnabled(false);
                mapboxMap.getUiSettings().setAttributionEnabled(false);
//                mapboxMap.getUiSettings().setAttributionGravity(GravityCompat.END| Gravity.BOTTOM);


//                LatLng pointOne = new LatLng(16.4700,78.4520);
//                LatLng pointTwo = new LatLng(17.4500,78.4420);
//
//                LatLngBounds latLngBounds = new LatLngBounds.Builder()
//                        .include(pointOne)
//                        .include(pointTwo)
//                        .build();
//
//

                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_lat,current_lon),getZoomLevel(delivery_range)),5000);
//
//

                mapboxMapInstance = mapboxMap;




//                circleLayer = new CircleLayer("oif","afew");
//
//
//                circleLayer.setProperties(
//                        PropertyFactory.visibility(Property.VISIBLE),
//                        PropertyFactory.circleRadius(8f),
//                        PropertyFactory.circleColor(Color.argb(1, 55, 148, 179))
//                );
//
//
//                mapboxMapInstance.addLayer(circleLayer);



                mapboxMapInstance.addOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng point) {

                        if(markerView!=null)
                        {
                            mapboxMapInstance.removeMarker(markerView);
                        }


                        markerView = mapboxMapInstance.addMarker(
                                new MarkerViewOptions()
                                        .position(point)
                                        .title("Current")

                        );

                    }
                });


            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }




    @OnClick(R.id.confirm_selected_location_button)
    void confirmButtonClick()
    {





        if(markerView==null)
        {
            Toast.makeText(this, R.string.toast_location_not_selected, Toast.LENGTH_SHORT).show();

            return;
        }



        Intent data = new Intent();
        data.putExtra("latitude",markerView.getPosition().getLatitude());
        data.putExtra("longitude",markerView.getPosition().getLongitude());
        data.putExtra("delivery_range_kms",(deliveryRange/ 1000));
        setResult(RESULT_OK,data);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


//    CircleLayer circleLayer;


//    Circle circle = null;

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


    }



    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {


//
//
//        if(markerView==null)
//        {
//            return;
//        }
//

//        if(circleLayer!=null)
//        {
//            mapboxMapInstance.removeLayer(circleLayer);
//        }
//
//
//        circleLayer = new CircleLayer(1);
//
//        circleLayer.setProperties(
//                PropertyFactory.visibility(Property.VISIBLE),
//                PropertyFactory.circleRadius(8f),
//                PropertyFactory.circleColor(Color.argb(1, 55, 148, 179))
//        );
//
//
//
//
//        mapboxMapInstance.addLayer(circleLayer);

//        deliveryRange = ((float)seekBar.getProgress() * 10 );
//        deliveryRangeHeader.setText("Delivery Range : " + String.format( "%.2f",(deliveryRange/ 1000)) + " Km : " + String.valueOf((seekBar.getProgress()*10)) + " Meters");


//        mapboxMapInstance.animateCamera(CameraUpdateFactory.newLatLngZoom(markerView.getPosition(),17));

    }



    public int getZoomLevel(double radius)
    {
        int zoomLevel = 11;

        double scale = radius / 500;
        zoomLevel = (int) Math.floor((16 - Math.log(scale) / Math.log(2)));

        return zoomLevel ;
    }




}
