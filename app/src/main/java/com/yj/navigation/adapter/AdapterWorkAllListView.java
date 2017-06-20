package com.yj.navigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
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
public class AdapterWorkAllListView extends BaseAdapter {

    private static Context context;
    public List<JobJson> mPersonal;
    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;


    public AdapterWorkAllListView(Context context, List<JobJson> personalList) {
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
            convertView = mInflater.inflate(R.layout.work_all_item, null);
            convertView.setTag(viewholder);
            viewholder.job_avar_id = (ImageView) convertView.findViewById(R.id.job_avar_id);
            viewholder.job_state_id = (TextView) convertView.findViewById(R.id.job_state_id);

            viewholder.job_address_id = (TextView) convertView.findViewById(R.id.job_address_id);

            viewholder.job_address_pro_id = (TextView) convertView.findViewById(R.id.job_address_pro_id);

            viewholder.job_take_time_id = (TextView) convertView.findViewById(R.id.job_take_time_id);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }


        if(personalRanking.state.equals("1")){
            viewholder.job_state_id.setText("一审");

        }else{
            viewholder.job_state_id.setText("二审");

        }


        List<JobImageJson> jobImageJsonList=personalRanking.images;
        JobImageJson jobImageJsonOk=null;
        if(jobImageJsonList!=null){
        for(JobImageJson jobImageJson :jobImageJsonList){
            jobImageJsonOk=jobImageJson;
            break;

        }

        imageLoader.displayImage(personalRanking.remoteBaseUrl+jobImageJsonOk.minPicUrl, viewholder.job_avar_id, options);

        }
        viewholder.job_address_id.setText(personalRanking.address);

        viewholder.job_address_pro_id.setText(personalRanking.province+personalRanking.area);

        viewholder.job_take_time_id.setText(personalRanking.takedt+"");


        return convertView;
    }

    private static class ViewHolder {
        ImageView job_avar_id;
        TextView job_state_id;
        TextView job_address_id;
        TextView job_address_pro_id;
        TextView     job_take_time_id;

    }
}
