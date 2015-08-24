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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yuichi on 8/20/15.
 */
public class ThumbnailImage extends Activity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener{
    //set thumbnails path
    ArrayList<String>imagePaths = new ArrayList<String>();
    //set bitmap image of thumbnail
    ArrayList<Bitmap>imageList = new ArrayList<Bitmap>();
    //
    Cursor cursor;
    ContentResolver resolver;
    BitmapAdapter adapter;
    GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thumbnail_image);

        //get Every Image from external contents at android
        this.resolver = getContentResolver();
        this.cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );
        Log.v("MEDIA", Arrays.toString(cursor.getColumnNames())); // 項目名一覧
        Log.v("MEDIA", "Image files = " + cursor.getCount()); // 取得件数
        //grid view
        this.gridView = (GridView)findViewById(R.id.imageGrid);
        gridView.setOnItemClickListener(this);
        //get images!
        //but now it is too a lot so just get 20 thumbnails
        getThumbnail();
    }

    private void getThumbnail(){
        int i = 0;
        //get current position
        cursor.getPosition();
        //move to first
        if(cursor.moveToFirst()){
            do{
                //get id and path
                long idImage = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String pathImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                //add image path to imagePaths
                imagePaths.add(pathImage);
                Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(resolver,idImage,MediaStore.Images.Thumbnails.MINI_KIND,null);
                Log.v("aa",cursor.getPosition() + "");
                //set thumbnail bitmap to imageList
                imageList.add(bmp);
                //now just show 40 picture
                //coz if set everything the memory will break
                i++;
                if(i == 40){
                    break;
                }
            }while(cursor.moveToNext());
        }
        //set thumbnails to gridview
        this.adapter = new BitmapAdapter(
                getApplicationContext(), R.layout.image_list_item, imageList);
        gridView.setAdapter(adapter);
    }

    //when the thumbnail picture are selected
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        Log.d("aa", imageList.get(position)+"");
        //I do not know but I can not set bmp image
        //thus now I set images url
        intent.putExtra("key", imagePaths.get(position));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
