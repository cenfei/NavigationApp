package com.yj.navigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.object.JobImageJson;
import com.yj.navigation.object.JobJson;
import com.yj.navigation.object.MessageJson;
import com.yj.navigation.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Sam on 2015/7/27.
 */
public class AdapterMessageListView extends BaseAdapter {

    private static Context context;
    public List<MessageJson> mPersonal;
    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;


    public AdapterMessageListView(Context context, List<MessageJson> personalList) {
        super();
        this.context = context;
        this.mPersonal = personalList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();

    }

    public void addList(List<MessageJson> addList) {
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
        MessageJson personalRanking = mPersonal.get(position);

        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.message_all_item, null);
            convertView.setTag(viewholder);
            viewholder.job_avar_id = (RoundedImageView) convertView.findViewById(R.id.job_avar_id);
            viewholder.job_state_id = (TextView) convertView.findViewById(R.id.job_state_id);

            viewholder.job_address_id = (TextView) convertView.findViewById(R.id.job_address_id);

            viewholder.job_address_pro_id = (TextView) convertView.findViewById(R.id.job_address_pro_id);

            viewholder.job_take_time_id = (TextView) convertView.findViewById(R.id.job_take_time_id);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }


        viewholder.job_address_id.setText(personalRanking.type);

        viewholder.job_address_pro_id.setText(personalRanking.lasttext);

        viewholder.job_take_time_id.setText(personalRanking.lastdt+"");


        return convertView;
    }

    private static class ViewHolder {
        RoundedImageView job_avar_id;
        TextView job_state_id;
        TextView job_address_id;
        TextView job_address_pro_id;
        TextView     job_take_time_id;

    }
}
