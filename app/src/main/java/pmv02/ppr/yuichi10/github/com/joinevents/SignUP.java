package pmv02.ppr.yuichi10.github.com.joinevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by yuichi on 8/20/15.
 */
public class SignUP extends Activity implements View.OnClickListener{
    static int mRequestCode = 1001;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        Log.d("Intent", "success");
        Intent intent = getIntent();

        Button thumbnail = (Button)findViewById(R.id.signUpGetImage);
        thumbnail.setOnClickListener(this);
    }

    public void onActivityResult( int requestCode, int resultCode, Intent intent ){
        if(requestCode == this.mRequestCode){
            if(resultCode == Activity.RESULT_OK){
                // 返却されてきたintentから値を取り出す
                String str = intent.getStringExtra( "key" );
                Log.d("aa",str);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpGetImage:
                Intent intent = new Intent();
                intent.setClassName(SignIn.packageName, SignIn.packageName + ".ThumbnailImage");
                startActivityForResult(intent,this.mRequestCode);
                break;
        }
    }
}
