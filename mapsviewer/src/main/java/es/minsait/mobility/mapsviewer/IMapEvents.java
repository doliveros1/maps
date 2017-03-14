package es.minsait.mobility.mapsviewer;

import es.minsait.mobility.mapsviewer.model.PointViewModel;

/**
 * Created by davidoliverosescribano on 23/1/17.
 */

public interface IMapEvents {
    void onMapLoaded();
    void onPoiSelected(PointViewModel point);
    void onMapClick(double latitude, double longitude);
    void onMapLongClick(double latitude, double longitude);


}
