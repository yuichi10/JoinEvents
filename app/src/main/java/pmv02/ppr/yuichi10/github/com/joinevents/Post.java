package pmv02.ppr.yuichi10.github.com.joinevents;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuichi on 8/30/15.
 */
public class Post implements Runnable {
    String mUrl;
    List<NameValuePair> mParams;
    String responseData = "nothing";
    public Post(String url, List<NameValuePair> params){
        this.mUrl = url;
        this.mParams = params;
    }

    @Override
    public void run() {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(mUrl);
        try {
            post.setEntity(new UrlEncodedFormEntity(mParams, "UTF-8"));// 文字コード変換
            // リクエスト送信
            HttpResponse response = client.execute(post);
            // 取得
            HttpEntity entity = response.getEntity();
            responseData = EntityUtils.toString(entity, "UTF-8");

        } catch(IOException e) {
            e.printStackTrace();
        }
        Log.d("aaa", responseData);
    }

    public String getResponse(){
        return responseData;
    }

}
