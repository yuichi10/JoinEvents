package pmv02.ppr.yuichi10.github.com.joinevents;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

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

    public interface GetPlaceCallBack{
        public void isGetPlaceCB();
    }
    private GetPlaceCallBack _mGetPlaceCallBack;

    public void setCallbacks(GetPlaceCallBack getPlaceCallBack){
        _mGetPlaceCallBack = getPlaceCallBack;
    }

    public Gps(Context context){
        this.mContext = context;
        mCriteria = new Criteria();
    }

    //setProvider
    public void setProvider(){
        mCriteria.setPowerRequirement(Criteria.POWER_LOW);
        mCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
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
        Log.d("aaa","start");
    }

    //stop to get place
    public void stopGps(){
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLongitude = location.getLongitude();
        mLatitude  = location.getLatitude();
        Log.d("aaa","changed");
        _mGetPlaceCallBack.isGetPlaceCB();
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
