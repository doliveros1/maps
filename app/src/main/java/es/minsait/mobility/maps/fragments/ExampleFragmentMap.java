package es.minsait.mobility.maps.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.minsait.mobility.maps.R;
import es.minsait.mobility.maps.model.GasolineraViewModel;
import es.minsait.mobility.maps.model.RestaurantesViewModel;
import es.minsait.mobility.mapsviewer.IMapEvents;
import es.minsait.mobility.mapsviewer.app.MapFragment;
import es.minsait.mobility.mapsviewer.model.PointViewModel;

/**
 * Created by davidoliverosescribano on 10/3/17.
 */

public class ExampleFragmentMap extends MapFragment implements IMapEvents {

    public static ExampleFragmentMap newInstance() {
        return new ExampleFragmentMap();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseMapView().initMap(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.map_layout;
    }

    @Override
    public int getMapViewId() {
        return R.id.mapView;
    }

    @Override
    public String getToken() {
        return getString(R.string.api_key);
    }

    @Override
    public void onMapLoaded() {
        //Pintar en mapa cuando el mapa esté cargado
        setMockPOIS();
        addRuta();
    }

    @Override
    public void onPoiSelected(PointViewModel point) {
        if(point instanceof RestaurantesViewModel){
            Toast.makeText(this.getActivity(), ((RestaurantesViewModel) point).nombreRestaurante, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapClick(double latitude, double longitude) {
        getBaseMapView().clearSelection();
    }

    @Override
    public void onMapLongClick(double latitude, double longitude) {

    }

    private void setMockPOIS() {
        //Cuando el mapa esta cargado se puede "pintar" sobre el mapa
        List<PointViewModel> list = new ArrayList<>();

        GasolineraViewModel gasolineraViewModel = new GasolineraViewModel();
        gasolineraViewModel.setLatitude(39);
        gasolineraViewModel.setTitle("Gasolinera 1");

        gasolineraViewModel.setLongitude(-3.90);
        gasolineraViewModel.setImageDrawable(R.drawable.ic_gasolinera);
        gasolineraViewModel.setImageDrawableSelected(R.drawable.ic_gasolinera_selected);

        list.add(gasolineraViewModel);

        gasolineraViewModel = new GasolineraViewModel();
        gasolineraViewModel.setLatitude(41);
        gasolineraViewModel.setTitle("Gasolinera 2");
        gasolineraViewModel.setLongitude(-2);
        gasolineraViewModel.setImageDrawable(R.drawable.ic_gasolinera);
        gasolineraViewModel.setImageDrawableSelected(R.drawable.ic_gasolinera_selected);

        list.add(gasolineraViewModel);


        RestaurantesViewModel restaurantesViewModel = new RestaurantesViewModel();
        restaurantesViewModel.setLatitude(38);
        restaurantesViewModel.setLongitude(-2);
        restaurantesViewModel.setTitle("Restaurante 1");
        restaurantesViewModel.nombreRestaurante = "Restaurante Murciano";
        restaurantesViewModel.setImageDrawable(R.drawable.ic_restaurante);
        restaurantesViewModel.setImageDrawableSelected(R.drawable.ic_restaurante_seleccionado);

        list.add(restaurantesViewModel);

        restaurantesViewModel = new RestaurantesViewModel();
        restaurantesViewModel.setLatitude(37);
        restaurantesViewModel.setLongitude(-3);
        restaurantesViewModel.setTitle("Restaurante 1");
        restaurantesViewModel.nombreRestaurante = "Restaurante Murciano";

        list.add(restaurantesViewModel);

        getBaseMapView().add(list);

    }

    private void addRuta() {
        List<PointViewModel> list = new ArrayList<>();

        PointViewModel pointViewModel = new PointViewModel();
        pointViewModel.setLatitude(43.059527);
        pointViewModel.setLongitude(-5.829931);
        list.add(pointViewModel);

        pointViewModel = new PointViewModel();
        pointViewModel.setLatitude(42.361511);
        pointViewModel.setLongitude(-4.176837);
        list.add(pointViewModel);

        pointViewModel = new PointViewModel();
        pointViewModel.setLatitude(40.804379);
        pointViewModel.setLongitude(-0.930383);
        list.add(pointViewModel);

        pointViewModel = new PointViewModel();
        pointViewModel.setLatitude(40.050363);
        pointViewModel.setLongitude(-5.270469);
        list.add(pointViewModel);

        getBaseMapView().addPolyline(list, R.color.black, 6);
    }
}
