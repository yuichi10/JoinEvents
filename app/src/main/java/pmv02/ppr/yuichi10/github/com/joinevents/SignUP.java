package pmv02.ppr.yuichi10.github.com.joinevents;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
    EditText age;
    RadioGroup gender;
    TextView idErrText, pass1ErrText, pass2ErrText, nameErrText, genderErrText,ageErrText;
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
        gender = (RadioGroup)findViewById(R.id.signUpGender);
        age = (EditText)findViewById(R.id.signUpAge);
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

    public boolean showErr(String jsonStr){
        try {
            JSONObject json = new JSONObject(jsonStr);
            if(json.getBoolean(DataManage.errCheck)){
                if(json.getString(DataManage.errId) != ""){
                    idErrText = (TextView)findViewById(R.id.signUpEmail_t_err);
                    idErrText.setText(json.getString(DataManage.errId));
                }
                if(json.getString(DataManage.errPass1) != ""){
                    pass1ErrText = (TextView)findViewById(R.id.signUpPassword_t_err);
                    pass1ErrText.setText(json.getString(DataManage.errPass1));
                }
                if(json.getString(DataManage.errPass2) != ""){
                    pass2ErrText = (TextView)findViewById(R.id.signUpPassword2_t_err);
                    pass2ErrText.setText(json.getString(DataManage.errPass2));
                }
                if(json.getString(DataManage.errName) != ""){
                    nameErrText = (TextView)findViewById(R.id.signUpName_t_err);
                    nameErrText.setText(json.getString(DataManage.errName));
                }
                if(json.getString(DataManage.errGender) != ""){
                    genderErrText = (TextView)findViewById(R.id.signUpGender_t_err);
                    genderErrText.setText(json.getString(DataManage.errGender));
                }
                if(json.getString(DataManage.errAge) != ""){
                    ageErrText = (TextView)findViewById(R.id.signUpAge_t_err);
                    ageErrText.setText(json.getString(DataManage.errAge));
                }
                return true;
            }else{
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
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
                //get server info
                String server = DataManage.server;
                String path   = "signup";
                //get editor info
                String sEmail = email.getText().toString();
                String sPassword = password1.getText().toString();
                String sPassword2 = password2.getText().toString();
                String sName     = name.getText().toString();
                int iGender = gender.getCheckedRadioButtonId();
                String sGender = "NOT";
                if(iGender != -1){
                    sGender = ((RadioButton)findViewById(iGender)).getText().toString();
                    switch (iGender){
                        case R.id.signUpMan:
                            iGender = 0;
                            break;
                        case R.id.signUpFemale:
                            iGender = 1;
                            break;
                    }
                }
                String sAge = age.getText().toString();
                HttpCommunication hc = new HttpCommunication(server,path);
                hc.setPostParameter(DataManage.httpID, sEmail);
                hc.setPostParameter(DataManage.httpPassword, sPassword);
                hc.setPostParameter(DataManage.httpPassword2, sPassword2);
                hc.setPostParameter(DataManage.httpName, sName);
                hc.setPostParameter(DataManage.httpGender, iGender + "");
                hc.setPostParameter(DataManage.httpAge, sAge);
                String res = hc.Post();
                boolean iserr = showErr(res);

                Log.d("POST", res);
                break;
        }
    }
}
