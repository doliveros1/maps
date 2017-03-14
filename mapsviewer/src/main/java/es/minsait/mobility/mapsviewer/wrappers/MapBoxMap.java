package es.minsait.mobility.mapsviewer.wrappers;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import es.minsait.mobility.mapsviewer.BaseMapView;
import es.minsait.mobility.mapsviewer.IMapEvents;
import es.minsait.mobility.mapsviewer.model.PointViewModel;


/**
 * Wrapper para mapas del SDK de MapBox.
 */
public class MapBoxMap implements BaseMapView, MapboxMap.OnMapClickListener,OnMapReadyCallback, MapboxMap.OnMarkerClickListener {

    private MapView mapView;
    private MapboxMap map;
    private IMapEvents mapEvents;
    private Context context;

    private List<PointViewModel> pois = new ArrayList<>();

    private LatLngBounds.Builder builder = new LatLngBounds.Builder();

    @Override
    public void init(Context context, View mapView, String apikey) {
        this.mapView = (MapView) mapView;
        this.context = context;
        MapboxAccountManager.start(mapView.getContext(), apikey);
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
        location.setMarker(marker);
        builder.include(marker.getPosition());
        this.pois.add(location);

    }

    @Override
    public void add(List<PointViewModel> pois) {
        builder = new LatLngBounds.Builder();
        for (PointViewModel location : pois) {
            add(location);
        }
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 20));
   }

    @Override
    public void remove(List<PointViewModel> locations) {

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
        for(PointViewModel point : pois){
            ((Marker)point.getMarker()).setIcon(getIcon(point.getImage()));
        }
    }

    @Override
    public void update(PointViewModel point) {
        ((Marker)point.getMarker()).setIcon(getIcon(point.getImage()));

        for(PointViewModel pointViewModel : this.pois){
            if(pointViewModel.getMarker() == (point.getMarker())){
                pointViewModel = point;
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
            public void onMapReady(MapboxMap mapboxMap) {
                MapBoxMap.this.map = mapboxMap;
                MapBoxMap.this.map.setOnMarkerClickListener(MapBoxMap.this);
                MapBoxMap.this.map.setOnMapClickListener(MapBoxMap.this);
                MapBoxMap.this.mapEvents.onMapLoaded();
            }
        });
    }

    public void setCenter(double latitude, double longitude, int zoomLevel) {
        this.map.setCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(zoomLevel)
                .build());
    }

    @Override
    public void onResume() {
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        mapView.onSaveInstanceState(savedInstanceState);
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
        mapView.setStyleUrl(Style.SATELLITE);
    }

    @Override
    public void showMap() {
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
    }

    @Override
    public void setZoom(int zoom) {
        map.animateCamera( CameraUpdateFactory.zoomTo( zoom) );
    }

    @Override
    public void showPopUpWindow(PointViewModel point) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        for(PointViewModel point : pois){
            if(point.getMarker() == marker){

                this.clearSelection();
                final CameraPosition cameraPosition = new CameraPosition.Builder()
                        .zoom(map.getCameraPosition().zoom)
                        .target(marker.getPosition()).build();
                ((Marker)point.getMarker()).setIcon(getIcon(point.getImageSelected()));

                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                map.animateCamera(cameraUpdate);
                mapEvents.onPoiSelected(point);
            }
        }
        return false;
    }

    @Override
    public void setMapBounds(int paddingBounds) {

        LatLngBounds bounds = builder.build();
        Resources res = context.getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingBounds, res.getDisplayMetrics());
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, (int) padding);
        map.moveCamera(cu);
        map.animateCamera(cu);
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        if(mapEvents != null){
            mapEvents.onMapClick(point.getLatitude(), point.getLongitude());
        }
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        //map = mapboxMap;
        //if(mapEvents != null){
        //    mapEvents.onMapLoaded();
        //}
    }

    private Icon getIcon(int drawable){
        IconFactory iconFactory = IconFactory.getInstance(context);
        Drawable iconDrawable = ContextCompat.getDrawable(context, drawable);
        Icon icon = iconFactory.fromDrawable(iconDrawable);
        return  icon;
    }
}
