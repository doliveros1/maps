package es.minsait.mobility.mapsviewer.wrappers;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import es.minsait.mobility.mapsviewer.BaseMapView;
import es.minsait.mobility.mapsviewer.IMapEvents;
import es.minsait.mobility.mapsviewer.model.PointViewModel;


/**
 * Created by davidoliverosescribano on 20/1/17.
 */

public class GoogleMapsMap implements BaseMapView, OnMapReadyCallback,GoogleMap.OnMapClickListener,GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private GoogleMap map;
    private IMapEvents mapEvents;
    private Context context;

    private List<Marker> pois = new ArrayList<>();

    private LatLngBounds.Builder builder = new LatLngBounds.Builder();

    @Override
    public void init(Context context, View mapView, String apikey) {
        this.context = context;
        this.mapView = (MapView) mapView;
        ((MapView) mapView).getMapAsync(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
    }


    /**
     * Método que añade un POI
     * @param location Localización en proyección WGS84 (Web Mercator)
     */
    @Override
    public void add(PointViewModel location) {

        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .icon(getIcon(location.getImage())));
        marker.setTitle(location.getTitle());
        marker.setTag(location);
        pois.add(marker);
        builder.include(marker.getPosition());
    }

    @Override
    public void add(List<PointViewModel> pois) {
        builder = new LatLngBounds.Builder();
        for (int i = 0; i<pois.size(); i++) {
            add(pois.get(i));
        }
        animateCamera();

    }

    private void animateCamera(){
        try {
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 20));
        } catch (IllegalStateException ise) {

            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                @Override
                public void onMapLoaded() {
                    map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 20));
                }
            });
        }
    }

    @Override
    public void remove(List<PointViewModel> points) {

    }

    /**
     * Limpia todos los POIs
     */
    @Override
    public void clearMap() {
        map.clear();
    }

    @Override
    public void select(List<PointViewModel> location) {

    }

    @Override
    public void clearSelection() {
        PointViewModel pointVM;
        for(Marker point : pois){
            pointVM = (PointViewModel) point.getTag();
            point.setIcon(getIcon(pointVM.getImage()));
        }
    }

    @Override
    public void update(PointViewModel point) {
        for(Marker marker : pois){
            if(point.getLatitude() == marker.getPosition().latitude && point.getLongitude() == marker.getPosition().longitude){
                marker.setIcon(getIcon(point.getImage()));
            }
        }
    }

    @Override
    public void addPolyline(List<PointViewModel> polyline, int color, int size) {

        List<LatLng> latLngsList = new ArrayList<>();

        for(PointViewModel pointViewModel : polyline){
            latLngsList.add(new LatLng(pointViewModel.getLatitude(), pointViewModel.getLongitude()));
        }

        PolylineOptions polylineOptions = new PolylineOptions()
                .color(ContextCompat.getColor(context, color))
                .addAll(latLngsList)
                .width(size);
        map.addPolyline(polylineOptions);
    }

    /**
     * Método que inicializa el mapa base.
     */
    @Override
    public void initMap(IMapEvents mapEvents) {

        this.mapEvents = mapEvents;

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GoogleMapsMap.this.map = googleMap;
                GoogleMapsMap.this.map.setOnMapClickListener(GoogleMapsMap.this);
                GoogleMapsMap.this.map.setOnMarkerClickListener(GoogleMapsMap.this);
                GoogleMapsMap.this.mapEvents.onMapLoaded();

            }
        });
    }

    @Override
    public void setCenter(double latitude, double longitude, int zoomLevel) {
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(latitude,
                        longitude));
        CameraUpdate zoom= CameraUpdateFactory.zoomTo(zoomLevel);

        this.map.moveCamera(center);
        this.map.animateCamera(zoom);
    }

    @Override
    public void setMapBounds(int paddingBounds){

        LatLngBounds bounds = builder.build();
        Resources res = context.getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingBounds, res.getDisplayMetrics());
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, (int) padding);
        map.moveCamera(cu);
        map.animateCamera(cu);
    }

    @Override
    public void onResume() {
        mapView.onResume();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
    }

    @Override
    public void showEarth() {
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    @Override
    public void showMap() {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    @Override
    public void setZoom(int zoom) {
        map.animateCamera( CameraUpdateFactory.zoomTo( zoom) );
    }

    @Override
    public void showPopUpWindow(PointViewModel point) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //this.map = googleMap;
        //mapEvents.onMapLoaded();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        PointViewModel point = (PointViewModel) marker.getTag();

                this.clearSelection();
                final CameraPosition cameraPosition = new CameraPosition.Builder()
                        .zoom(16)
                        .target(marker.getPosition()).build();
                marker.setIcon(getIcon(point.getImageSelected()));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                map.animateCamera(cameraUpdate);
                mapEvents.onPoiSelected(point);


        return false;
    }

    private BitmapDescriptor getIcon(int imageSelected) {
        return BitmapDescriptorFactory.fromResource(imageSelected);
    }

    @Override
    public void onMapClick(LatLng point) {
        if(mapEvents != null){
            mapEvents.onMapClick(point.latitude, point.longitude);
        }
    }
}
