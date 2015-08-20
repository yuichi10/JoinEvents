package pmv02.ppr.yuichi10.github.com.joinevents;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yuichi on 8/20/15.
 */
public class Home extends Activity {
    ArrayAdapter<String> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Log.d("Intent", "success");
        Intent intent = getIntent();
        adapter = new ArrayAdapter<String>(this, R.layout.colmun_home_list);
        getGroups();
        ListView listView = (ListView)this.findViewById(R.id.homeList);
        getListMountHeight(listView);
        listView.setAdapter(adapter);
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
    public void getGroups(){
        for(int i=0; i < 10; ++i){
            adapter.add("aaaaaaa" + i);
        }
    }
}
