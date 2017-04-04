package es.minsait.mobility.mapsviewer.model;


import es.minsait.mobility.mapsviewer.R;

/**
 * Created by davidoliverosescribano on 9/3/17.
 */

public class PointViewModel<M>{

    private String id;
    private double latitude;
    private double longitude;
    private String title;
    protected int imageDrawable = 0;
    protected int imageDrawableSelected = 0;
    private boolean isSelected;

    protected M marker;

    public void setMarker(M marker) {
        this.marker=marker;
    }

    public M getMarker() {
        return marker;
    }

    public PointViewModel() {

    }

    public PointViewModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getImage(){
        if(imageDrawable == 0){
            return R.drawable.ic_default_poi;
        }
        return imageDrawable;
    }

    public int getImageSelected(){
        if(imageDrawableSelected == 0){
            return getImage();
        }
        return imageDrawableSelected;
    }

    public void setImageDrawable(int drawable){
        this.imageDrawable = drawable;
    }
    public void setImageDrawableSelected(int drawable){
        this.imageDrawableSelected = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
