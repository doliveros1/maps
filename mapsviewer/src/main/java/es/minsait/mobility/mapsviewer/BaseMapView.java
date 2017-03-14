package es.minsait.mobility.mapsviewer;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import es.minsait.mobility.mapsviewer.model.PointViewModel;

/**
 * Created by davidoliverosescribano on 20/1/17.
 */

public interface BaseMapView {

    /**
     * Inicializa el componente
     * @param context
     * @param mapView
     * @param apikey
     */
    void init(Context context, View mapView, String apikey);

    /**
     * Eventos para gestionar los ciclos de vida de Android.
     * @param savedInstanceState
     */
    void onCreate(Bundle savedInstanceState);
    void onResume();
    void onPause();
    void onSaveInstanceState(Bundle savedInstanceState);
    void onLowMemory();
    void onDestroy();


    /**
     * Inicializa mapa base y eventos sobre mapa
     */
    void initMap(IMapEvents mapEvents);

    /**
     * Establece el centro del mapa
     * @param latitude
     * @param longitude
     * @param zoomLevel
     */
    void setCenter(double latitude, double longitude, int zoomLevel);

    /**
     * Establece el Map Bounds
     * @param paddingBounds
     */
    void setMapBounds(int paddingBounds);

    /**
     * Muestra como fondo Ortofotos e imágenes de satélite.
     */
    void showEarth();

    /**
     * Muestra como fondo un mapa base.
     */
    void showMap();

    /**
     * Se añade un POI.
     * @param point
     */
    void add(PointViewModel point);

    /**
     * Se añaden varios POIS.
     * @param points
     */
    void add(List<PointViewModel> points);

    /**
     * Se eliminan los POIS seleccionados
     * @param points
     */
    void remove(List<PointViewModel> points);

    /**
     * Se borran todos los gráficos del mapa.
     */
    void clearMap();

    /**
     * Se marcan como seleccionados los puntos especificados.
     * @param location
     */
    void select(List<PointViewModel> location);

    /**
     * Limpia la selección.
     */
    void clearSelection();

    /**
     * Limpia la selección.
     */
    void update(PointViewModel point);

    /**
     * Añade una polilínea
     * @param polyline
     * @param color
     * @param size
     */
    void addPolyline(List<PointViewModel> polyline, int color, int size);

    /**
     * Establece un zoom al mapa.
     * @param zoom
     */
    void setZoom(int zoom);

    /**
     * Muestra un PopUpWindow en el punto especificado.
     * @param point
     */
    void showPopUpWindow(PointViewModel point);


}
