package es.minsait.mobility.mapsviewer;

import android.content.Context;
import android.view.View;

import es.minsait.mobility.mapsviewer.exceptions.MapViewNotSupported;
import es.minsait.mobility.mapsviewer.wrappers.GoogleMapsMap;
import es.minsait.mobility.mapsviewer.wrappers.MapBoxMap;


/**
 * Created by davidoliverosescribano on 20/1/17.
 */

public class MapCreator {

    /**
     * Método que genera el wrapper correspondiente en función del SDK al que corresponde el MapView utilizado.
     * @param context
     * @param view MapView
     * @return
     * @throws MapViewNotSupported
     */
    public static BaseMapView getMapView(Context context, View view, String apikey) throws MapViewNotSupported {
        BaseMapView baseMapView = null;

        if(view instanceof com.mapbox.mapboxsdk.maps.MapView){
            baseMapView = new MapBoxMap();
        } else if(view instanceof com.google.android.gms.maps.MapView){
            baseMapView = new GoogleMapsMap();
        } else{
            throw new MapViewNotSupported();
        }

        baseMapView.init(context, view, apikey);

        return baseMapView;

    }
}
