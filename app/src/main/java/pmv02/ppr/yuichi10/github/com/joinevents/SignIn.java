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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignIn extends ActionBarActivity implements View.OnClickListener{
    //edit text for id and password
    EditText idSignIn;
    EditText passwordSignIn;
    //the path of this application
    static String packageName = "pmv02.ppr.yuichi10.github.com.joinevents";

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
        Button doSignUp = (Button)findViewById(R.id.doSignUp);
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
                //get ID and password
                String strID = idSignIn.getText().toString();
                String strPass = passwordSignIn.getText().toString();
                //make password complex
                strPass = encodePassdigiest(strPass);
                Log.d("Intent","push button");
                //if the password and ID was collect, go to Home activity
                intent.setClassName(packageName, packageName + ".Home");
                startActivity(intent);
                break;
            //when user try to sign up
            case R.id.doSignUp:
                //go to the page for sign up
                intent.setClassName(packageName, packageName + ".SignUP");
                startActivity(intent);
                break;
        }
    }


    //MD5
    public String encodePassdigiest(String password){
        byte[] enclyptedHash=null;
        // MD5で暗号化したByte型配列を取得する
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            enclyptedHash = md5.digest();

            // 暗号化されたByte型配列を、16進数表記文字列に変換する
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytesToHexString(enclyptedHash);
    }

    private String bytesToHexString(byte[] fromByte) {

        StringBuilder hexStrBuilder = new StringBuilder();
        for (int i = 0; i < fromByte.length; i++) {

            // 16進数表記で1桁数値だった場合、2桁目を0で埋める
            if ((fromByte[i] & 0xff) < 0x10) {
                hexStrBuilder.append("0");
            }
            hexStrBuilder.append(Integer.toHexString(0xff & fromByte[i]));
        }
        return hexStrBuilder.toString();
    }
}
