package pmv02.ppr.yuichi10.github.com.joinevents;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignIn extends ActionBarActivity implements View.OnClickListener{
    //edit text for id and password
    EditText idSignIn;
    EditText passwordSignIn;
    //the path of this application
    static String packageName = "pmv02.ppr.yuichi10.github.com.joinevents";

    //data for json analyze
    static String ISERR = "is_err";
    static String errPasskey = "pass_err";
    static String errIdkey   = "id_err";
    static String errDescription = "err_description";
    static String sessionKey = "SessionId";

    //data responses
    String errPassword = "";
    String errId       = "";
    String mSessionID   = "";
    boolean mIsErr;
    String mErrDescription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //get the edit text for sign in
        idSignIn = (EditText)findViewById(R.id.idSignIn);
        passwordSignIn = (EditText)findViewById(R.id.passwordSignIn);
        //button for sign in
        Button doSignIn = (Button)findViewById(R.id.doSignIn);
        doSignIn.setOnClickListener(this);
        //button for sign up
        Button doSignUp = (Button)findViewById(R.id.signInSignUp);
        doSignUp.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()){
            //when sing in button was pushed
            case R.id.doSignIn:
                //get server inf
                String server = DataManage.server;
                String path   = "login";
                //get ID and password
                String strID = idSignIn.getText().toString();
                String strPass = passwordSignIn.getText().toString();
                HttpCommunication hc = new HttpCommunication(server, path);
                hc.setPostParameter("Id", strID);
                hc.setPostParameter("Password", strPass);
                String response = hc.Post();

                analyseJson(response);
                Log.d("bbb", " try sign in " + response);
                if(mIsErr || response == "" || response == null){
                    return;
                }
                //if the password and ID was collect, go to Home activity
                intent.setClassName(packageName, packageName + ".Home");
                startActivity(intent);
                break;
            //when user try to sign up
            case R.id.signInSignUp:
                //go to the page for sign up
                intent.setClassName(packageName, packageName + ".SignUP");
                startActivity(intent);
                break;
        }
    }

    public void analyseJson(String jStr){
        try {
            JSONObject json = new JSONObject(jStr);
            this.mIsErr     = json.getBoolean(ISERR);
            Log.d("bbb", "check err:" + mIsErr);
            Log.d("bbb", "json :"  + jStr);
            if(mIsErr) {
                this.errPassword = json.getString(errPasskey);
                this.errId = json.getString(errIdkey);
                this.mErrDescription = json.getString(errDescription);
                //this.mSessionID = json.getString(sessionKey);
                Log.d("bbb", errPassword);
                Log.d("bbb", errId);
                Log.d("bbb", mSessionID);
                Log.d("bbb", mErrDescription);
            }
        }catch (Exception e){

        }

    }
}
