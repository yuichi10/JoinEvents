package pmv02.ppr.yuichi10.github.com.joinevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by yuichi on 8/20/15.
 */
public class Home extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Log.d("Intent", "success");
        Intent intent = getIntent();

        TextView tv = (TextView)findViewById(R.id.homeText);
    }
}
