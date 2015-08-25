package pmv02.ppr.yuichi10.github.com.joinevents;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by yuichi on 8/20/15.
 */
public class SignUP extends Activity implements View.OnClickListener{
    //code for return image
    static int mRequestCode = 1001;
    //image view
    ImageView mImageView;
    //Edit text
    EditText email;
    EditText password1;
    EditText password2;
    EditText name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        Intent intent = getIntent();

        //the button when user try to choose images
        Button thumbnail = (Button)findViewById(R.id.signUpGetImage);
        thumbnail.setOnClickListener(this);
        //the button for really sign up
        Button signUp = (Button)findViewById(R.id.doSignUp);
        signUp.setOnClickListener(this);

        //set email width. try not to change width
        email = (EditText)findViewById(R.id.signUpEmail_e);
        email.setWidth(email.getWidth());
        //set password width. not to change width
        password1 = (EditText)findViewById(R.id.signUpPassword_e);
        password1.setWidth(password1.getWidth());
        //set password width. not to change width
        password2 = (EditText)findViewById(R.id.signUpPassword2_e);
        password2.setWidth(password2.getWidth());
        //set name width. not to change width
        name = (EditText)findViewById(R.id.signUpName_e);
        name.setWidth(name.getWidth());
    }

    public void onActivityResult( int requestCode, int resultCode, Intent intent ){
        if(requestCode == this.mRequestCode){
            if(resultCode == Activity.RESULT_OK){
                // 返却されてきたintentから値を取り出す
    //            Bundle bundle = intent.getExtras();
                //get image path
                String path = intent.getStringExtra("key");
                BitmapFactory.Options options = new BitmapFactory.Options();
                //just get the biggest of the image
                options.inJustDecodeBounds = true;
                Bitmap bmp = BitmapFactory.decodeFile(path, options);
                //just bitmap image size
                int maxSize = 100;
                int imageScaleWidth = options.outWidth / maxSize;
                int imageScaleHeight = options.outHeight / maxSize;
                int imageScale = (int)Math.floor((imageScaleWidth > imageScaleHeight ? imageScaleHeight : imageScaleWidth));
                for (int i = 2; i <= imageScale; i *= 2) {
                    options.inSampleSize = i;
                }
                //get image
                options.inJustDecodeBounds = false;
                bmp = BitmapFactory.decodeFile(path, options);
                //set bitmap to imageView
                mImageView = (ImageView)findViewById(R.id.singUpShowImage);
                mImageView.setImageBitmap(bmp);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //when select image button was pushed
            case R.id.signUpGetImage:
                //go to ThumbnailImage activity. get answer when next activity is finished
                Intent intent = new Intent();
                intent.setClassName(SignIn.packageName, SignIn.packageName + ".ThumbnailImage");
                startActivityForResult(intent, this.mRequestCode);
                break;
            case R.id.doSignUp:
                String server = "115.65.57.253:8080";
                String path  = "firstTom/servlet/hello";
                //server = "google.co.jp";
                HttpCommunication hc = new HttpCommunication(server,path);
                hc.setGetParameter("message", "success");
                hc.Get();
                break;
        }
    }
}
