package es.minsait.mobility.maps;

import android.app.Activity;
import android.os.Bundle;
import es.minsait.mobility.maps.model.GasolineraViewModel;
import es.minsait.mobility.mapsviewer.BaseMapView;
import es.minsait.mobility.mapsviewer.IMapEvents;
import es.minsait.mobility.mapsviewer.MapCreator;
import es.minsait.mobility.mapsviewer.exceptions.MapViewNotSupported;
import es.minsait.mobility.mapsviewer.model.PointViewModel;

public class MapsActivity extends Activity implements IMapEvents {

    BaseMapView baseMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        setUpMaps(savedInstanceState);
    }

    private void setUpMaps(Bundle savedInstanceState) {

        try {
            baseMapView = MapCreator.getMapView(this.getApplicationContext(), findViewById(R.id.mapView), getString(R.string.api_key));
        } catch (MapViewNotSupported mapViewNotSupported) {
            //View no encontrado, no se corresponde con ningún SDK de mapas wrappeado.
        }
        // Create a mapView
        baseMapView.onCreate(savedInstanceState);
        baseMapView.initMap(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
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

    @Override
    public void onMapLoaded() {
        baseMapView.setCenter(40.415363, -3.707398, 10);
        //Cuando el mapa esta cargado se puede "pintar" sobre el mapa

        GasolineraViewModel gasolineraViewModel = new GasolineraViewModel();
        gasolineraViewModel.setLatitude(40.415363);
        gasolineraViewModel.setLongitude(-3.707398);
        gasolineraViewModel.setImageDrawable(R.drawable.ic_gasolinera);
        gasolineraViewModel.setImageDrawableSelected(R.drawable.ic_gasolinera_selected);
        baseMapView.add(gasolineraViewModel);
    }

    @Override
    public void onPoiSelected(PointViewModel point) {


    }

    @Override
    public void onMapClick(double latitude, double longitude) {

    }

    @Override
    public void onMapLongClick(double latitude, double longitude) {

    }
}
