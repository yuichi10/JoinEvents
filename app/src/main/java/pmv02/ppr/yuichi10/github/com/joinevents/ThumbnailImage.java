package pmv02.ppr.yuichi10.github.com.joinevents;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuichi on 8/20/15.
 */
public class ThumbnailImage extends Activity implements AdapterView.OnItemClickListener{
    ArrayList<String>imageMap = new ArrayList<String>();
    ArrayList<Bitmap>imageList = new ArrayList<Bitmap>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thumbnail_image);

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );
        Log.v("MEDIA", Arrays.toString(cursor.getColumnNames())); // 項目名一覧
        Log.v("MEDIA", "Image files = " + cursor.getCount()); // 取得件数
        int i = 0;
        if(cursor.moveToFirst()){
            do{
                long idImage = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String pathImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                imageMap.add(pathImage);
                Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(resolver,idImage,MediaStore.Images.Thumbnails.MINI_KIND,null);

                imageList.add(bmp);
                ++i;
                if(i == 20){
                    break;
                }
            }while(cursor.moveToNext());
        }
        BitmapAdapter adapter = new BitmapAdapter(
                getApplicationContext(), R.layout.image_list_item, imageList);
        GridView gridView = (GridView)findViewById(R.id.imageGrid);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent();
        Log.d("aa", imageList.get(position)+"");
        intent.putExtra("key", imageMap.get(position));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
