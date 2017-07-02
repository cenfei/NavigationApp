package com.yj.navigation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.util.ImageLoaderUtil;
import com.yj.navigation.util.ReadFile;

import org.camera.encode.VideoEncoderFromSurface;

import java.util.List;

/**
 * Created by Sam on 2015/7/27.
 */
public class AdapterUpVideoItemView extends BaseAdapter {

    private static Context context;
    public List<String> mPersonal;
    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;


    public AdapterUpVideoItemView(Context context, List<String> personalList) {
        super();
        this.context = context;
        this.mPersonal = personalList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();

    }

    public void addList(List<String> addList) {
        if (mPersonal == null || mPersonal.size() == 0) {
            mPersonal = addList;
        } else {
            mPersonal.addAll(addList);
        }

    }


    @Override
    public int getCount() {
        return this.mPersonal != null ? this.mPersonal.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mPersonal.get(i);
    }

//    @Override
//    public String getItem(int position) {
//        return this.mPersonal.get(position);
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String personalRanking = mPersonal.get(position);

        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.video_item, null);
            convertView.setTag(viewholder);
            viewholder.video_img_id = (ImageView) convertView.findViewById(R.id.video_img_id);
            viewholder.video_title_id = (TextView) convertView.findViewById(R.id.video_title_id);

        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }


        viewholder.video_title_id.setText(personalRanking);
       String mp4Num=personalRanking.substring(0,personalRanking.indexOf(".mp4"));
        String imgUrl= VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+mp4Num+"/"+mp4Num+".jpg";
        Log.e("video:imgurl",imgUrl);

        viewholder.video_img_id.setImageBitmap(ReadFile.getDiskBitmap(imgUrl));


        return convertView;
    }

    private static class ViewHolder {
        ImageView video_img_id;

        TextView video_title_id;

    }
}
