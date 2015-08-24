package pmv02.ppr.yuichi10.github.com.joinevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by yuichi on 8/21/15.
 */
public class MakeGroup extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_group);
        Intent intent = getIntent();
    }
}
