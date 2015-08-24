package pmv02.ppr.yuichi10.github.com.joinevents;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by yuichi on 8/24/15.
 */
public class Gps implements LocationListener {

    //location manager
    private LocationManager mLocationManager;
    //Criteria
    private Criteria mCriteria;
    //provider
    private String mProvider;
    //Context
    Context mContext;
    //Longitude Latitude
    private double mLongitude = 0;
    private double mLatitude = 0;
    //get the place or not
    boolean isGetPlace = false;

    //set interface for call back
    public interface GetPlaceCallBack{
        public void isGetPlaceCB(double lon, double lat);
    }
    private GetPlaceCallBack _mGetPlaceCallBack;

    //set call backs
    public void setCallbacks(GetPlaceCallBack getPlaceCallBack){
        _mGetPlaceCallBack = getPlaceCallBack;
    }

    //Constructor
    public Gps(Context context){
        //set context
        this.mContext = context;
        //make criteria
        mCriteria = new Criteria();
    }

    //setProvider
    public void setProvider(){
        //set how power they use
        mCriteria.setPowerRequirement(Criteria.POWER_LOW);
        //set how much accuracy
        mCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
        //get provider name from conditions
        mProvider = mLocationManager.getBestProvider(mCriteria, true);
    }

    public void setProvider(String accuracy, String power){
        //later
    }

    //start gps
    public void startGps(){
        mLocationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        setProvider();
        mLocationManager.requestLocationUpdates(mProvider, 0, 0, this);
    }

    //stop to get place
    public void stopGps(){
        mLocationManager.removeUpdates(this);
    }

    //give the value of longitude
    public double getLongitude(){
        return mLongitude;
    }

    //give the value of latitude
    public double getLatitude(){
        return mLatitude;
    }

    @Override
    public void onLocationChanged(Location location) {
        //set coordinate
        mLongitude = location.getLongitude();
        mLatitude  = location.getLatitude();
        //call callback function
        _mGetPlaceCallBack.isGetPlaceCB(mLongitude, mLatitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
