package pmv02.ppr.yuichi10.github.com.joinevents;

import android.content.Entity;
import android.net.Uri;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by yuichi on 8/25/15.
 */
public class HttpCommunication {
    Uri.Builder mBuilder;
    String mUri;
    //Post parameters
    List<NameValuePair> postParams = new ArrayList<NameValuePair>();

    public interface HttpCommunicationCallBack {
        public void getResponse(String str);
    }

    private HttpCommunicationCallBack _httpCommunicationCallBack;

    public void setCallbacks(HttpCommunicationCallBack myClassCallbacks){
        _httpCommunicationCallBack = myClassCallbacks;
    }


    public HttpCommunication(String serverName, String path){
        //Uri for get
        mBuilder = new Uri.Builder();
        setServerName(serverName);

        if(path != "") {
            mBuilder.path("/" + path);
        }
        //Uri for post
        mUri = "http://" + serverName + DataManage.port + "/" + path;
    }

    public HttpCommunication(String serverName){
        //Uri for get
        mBuilder = new Uri.Builder();
        setServerName(serverName);
        //Uri for post
        mUri = "http://" + serverName + DataManage.port;
    }

    private void setServerName(String str){
        //Uri for get
        mBuilder.scheme("http");
        mBuilder.encodedAuthority(str);
    }

    public void setURL(String serverName, String path){
        setServerName(serverName);
        mBuilder.path("/" + path);
    }

    //set parameter for get
    public void setGetParameter(String key1, String key2) {
        mBuilder.appendQueryParameter(key1, key2);
    }

    //set parameter for post
    public void setPostParameter(String key1, String key2){
        postParams.add(new BasicNameValuePair(key1, key2));
    }

    //try to do get
    public void Get(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String url = mBuilder.build().toString();
                HttpGet request = new HttpGet(url);
                try{
                    String result = httpClient.execute(request, new ResponseHandler<String>() {
                        @Override
                        public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                            switch (httpResponse.getStatusLine().getStatusCode()){
                                case HttpStatus.SC_OK:
                                    return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                                case HttpStatus.SC_NOT_FOUND:
                                    throw new RuntimeException("There are no data");
                                default:
                                    throw new RuntimeException("something wrong");
                            }
                        }
                    });
                    Log.d("test", result);
                }catch (ClientProtocolException e){
                    throw new RuntimeException(e);
                }catch (IOException e){
                    throw new RuntimeException(e);
                }finally {
                    httpClient.getConnectionManager().shutdown();
                }

            }
        }).start();
    }

    public String Post() {
        Log.d("aaa", mUri);
        Post post = new Post(mUri,postParams);
        String res = "";
        try{
            Thread thPost = new Thread(post);
            thPost.start();
            thPost.join();
            res = post.getResponse();

        }catch (Exception e){

        }
        Log.d("aaa",res);
        return res;
    }
}
