package com.yj.navigation.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.object.JobImageJson;
import com.yj.navigation.object.JobJson;
import com.yj.navigation.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Sam on 2015/7/27.
 */
public class NewAdapterUpVideoListView extends BaseAdapter {

    private static Context context;
    public List<JobJson> mPersonal;
    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;


    public NewAdapterUpVideoListView(Context context, List<JobJson> personalList) {
        super();
        this.context = context;
        this.mPersonal = personalList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();

    }

    public void addList(List<JobJson> addList) {
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
        JobJson personalRanking = mPersonal.get(position);

        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.new_up_video_item, null);
            convertView.setTag(viewholder);
            viewholder.upvideo_img_id = (ImageView) convertView.findViewById(R.id.upvideo_img_id);
            viewholder.upvideo_title_id = (TextView) convertView.findViewById(R.id.upvideo_title_id);

            viewholder.upvideo_staus_id = (TextView) convertView.findViewById(R.id.upvideo_staus_id);

            viewholder.upvideo_time_id = (TextView) convertView.findViewById(R.id.upvideo_time_id);

        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        if(!TextUtils.isEmpty(personalRanking.address)){
            viewholder.upvideo_title_id.setText(personalRanking.address);

        }else{
            viewholder.upvideo_title_id.setText("无具体位置");

        }
        if(!TextUtils.isEmpty(personalRanking.takedt)){
            viewholder.upvideo_time_id.setText(personalRanking.takedt+"");

        }
        if(!TextUtils.isEmpty(personalRanking.state)){

            if(personalRanking.state.equals("1")) {

                viewholder.upvideo_staus_id.setText("等待筛选");
                viewholder.upvideo_staus_id.setTextColor(ContextCompat.getColor(context, R.color.new_login_text_color));

                viewholder.upvideo_staus_id.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rounded_sx));

            }else{
                viewholder.upvideo_staus_id.setText("已筛选");
                viewholder.upvideo_staus_id.setTextColor(ContextCompat.getColor(context,R.color.font_titie2));

                viewholder.upvideo_staus_id.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rounded_sx_ok));


            }

        }


        List<JobImageJson> jobImageJsonList=personalRanking.images;
        JobImageJson jobImageJsonOk=null;
        if(jobImageJsonList!=null){
            for(JobImageJson jobImageJson :jobImageJsonList){
                jobImageJsonOk=jobImageJson;
                break;

            }

            imageLoader.displayImage(personalRanking.remoteBaseUrl+jobImageJsonOk.bigPicUrl, viewholder.upvideo_img_id, options);

        }












//        if(personalRanking.state.equals("1")){
//            viewholder.job_state_id.setText("一审");
//
//        }else{
//            viewholder.job_state_id.setText("二审");
//
//        }
//
//
//        List<JobImageJson> jobImageJsonList=personalRanking.images;
//        JobImageJson jobImageJsonOk=null;
//        if(jobImageJsonList!=null){
//        for(JobImageJson jobImageJson :jobImageJsonList){
//            jobImageJsonOk=jobImageJson;
//            break;
//
//        }
//
//        imageLoader.displayImage(personalRanking.remoteBaseUrl+jobImageJsonOk.minPicUrl, viewholder.job_avar_id, options);
//
//        }
//        viewholder.job_address_id.setText(personalRanking.address);
//
//        viewholder.job_address_pro_id.setText(personalRanking.province+personalRanking.area);
//
//        viewholder.job_take_time_id.setText(personalRanking.takedt+"");


        return convertView;
    }

    private static class ViewHolder {
        ImageView upvideo_img_id;
        TextView upvideo_title_id;
        TextView upvideo_staus_id;
        TextView upvideo_time_id;

    }
}
