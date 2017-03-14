package es.minsait.mobility.mapsviewer.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.minsait.mobility.mapsviewer.BaseMapView;
import es.minsait.mobility.mapsviewer.MapCreator;
import es.minsait.mobility.mapsviewer.exceptions.MapViewNotSupported;

/**
 * Created by davidoliverosescribano on 9/3/17.
 */

public abstract class MapFragment extends Fragment {

    BaseMapView baseMapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView;
        int layoutRes = getFragmentLayout();
        if (layoutRes == 0) {
            throw new IllegalArgumentException(
                    "getLayoutRes() returned 0, which is not allowed. "
                            + "If you don't want to use getLayoutRes() but implement your own view for this "
                            + "fragment manually, then you have to override onCreateView();");
        } else {

            fragmentView = inflater.inflate(layoutRes, container, false);
            return fragmentView;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //ButterKnife in parent class
        super.onViewCreated(view, savedInstanceState);
        setUpMap(savedInstanceState);
    }

    private void setUpMap(Bundle savedInstanceState) {
        try {
            baseMapView = MapCreator.getMapView(this.getActivity(), getView().findViewById(getMapViewId()), getToken());
            baseMapView.onCreate(savedInstanceState);
        } catch (MapViewNotSupported mapViewNotSupported) {
            mapViewNotSupported.printStackTrace();
        }
    }

    protected abstract int getFragmentLayout();

    public abstract int getMapViewId();

    public abstract String getToken();

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
