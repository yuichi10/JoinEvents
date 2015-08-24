package pmv02.ppr.yuichi10.github.com.joinevents;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by yuichi on 8/20/15.
 */
public class Home extends Activity implements View.OnClickListener, Gps.GetPlaceCallBack{
    ArrayAdapter<String> adapter;
    //gps
    private Gps mGps;
    //criteria
    private Criteria mCriteria;
    //does get place
    private boolean isGetplace = false;
    //list view to show groups
    ListView mListView;
    //longitude and latitude
    double mLongitude;
    double mLatitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Intent intent = getIntent();
        //get gps
        mGps = new Gps(this.getApplicationContext());
        //set callback
        mGps.setCallbacks(this);
        //get current place
        mGps.startGps();
        //grope name adapter
        //set List view
        this.mListView = (ListView)this.findViewById(R.id.homeList);
        getListMountHeight(mListView);

        //make group button
        Button makeGroupButton = (Button)findViewById(R.id.homeMakeGrope);
        makeGroupButton.setOnClickListener(this);
        //show group list when the place is found
    }

    //show groups list
    public void showList(){
        adapter = new ArrayAdapter<String>(this, R.layout.colmun_home_list);
        //get group list
        getGroups();
        //set groups to list
        this.mListView.setAdapter(adapter);
    }

    public void getListMountHeight(ListView lv){
        ListAdapter la = lv.getAdapter();
        if (la == null)
        {
            return;
        }
        int height = 0;

        for(int i=0; i < la.getCount(); i++){
            View item = la.getView(i, null, lv);
            item.measure(0,0);
            height += item.getHeight();
        }
        ViewGroup.LayoutParams p = lv.getLayoutParams();
        p.height = height + (lv.getDividerHeight() * (la.getCount() - 1));
        lv.setLayoutParams(p);
    }

    //get groups from server
    //actually we get groups from server
    public void getGroups(){
        for(int i=0; i < 20; ++i){
            adapter.add("aaaaaaa" + i);
        }
    }

    //call back when the place was found
    public void isGetPlaceCB(double lon, double lat){
        isGetplace = true;
        mGps.stopGps();
        this.mLongitude = lon;
        this.mLatitude  = lat;
        showList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.homeMakeGrope:
                String className = ".MakeGroup";
                Intent intent = new Intent();
                intent.setClassName(SignIn.packageName, SignIn.packageName + className);
                startActivity(intent);
                break;
        }
    }
}
