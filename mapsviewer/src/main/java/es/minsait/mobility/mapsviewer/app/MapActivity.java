package es.minsait.mobility.mapsviewer.app;

import android.app.Activity;
import android.os.Bundle;

import es.minsait.mobility.mapsviewer.BaseMapView;
import es.minsait.mobility.mapsviewer.MapCreator;
import es.minsait.mobility.mapsviewer.exceptions.MapViewNotSupported;

/**
 * Created by davidoliverosescribano on 9/3/17.
 */

public abstract class MapActivity extends Activity{


    BaseMapView baseMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        setUpMap(savedInstanceState);
    }

    private void setUpMap(Bundle savedInstanceState) {
        try {
            baseMapView = MapCreator.getMapView(this.getApplicationContext(), findViewById(getMapViewId()), getToken());
            baseMapView.onCreate(savedInstanceState);
        } catch (MapViewNotSupported mapViewNotSupported) {
            mapViewNotSupported.printStackTrace();
        }

    }

    public abstract int getMapViewId();

    public abstract String getToken();

    public abstract int getLayout();

    @Override
    protected void onPause() {
        super.onPause();
        baseMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        baseMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        baseMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        baseMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        baseMapView.onLowMemory();
    }


    public BaseMapView getBaseMapView() {
        return baseMapView;
    }

}
