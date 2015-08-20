package pmv02.ppr.yuichi10.github.com.joinevents;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuichi on 8/20/15.
 */
public class BitmapAdapter extends ArrayAdapter<Bitmap> {

    private int resourceId;

    public BitmapAdapter(Context context, int resource, int textViewResourceId, List<Bitmap> objects) {
        super(context, resource, textViewResourceId, objects);
        this.resourceId = resource;
    }

    public BitmapAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, null);
        }

        ImageView view = (ImageView) convertView;
        view.setImageBitmap(getItem(position));

        return view;
    }
}
