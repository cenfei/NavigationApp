package com.yj.navigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.object.AccountDetailJson;
import com.yj.navigation.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Sam on 2015/7/27.
 */
public class AdapterMineAccountListView extends BaseAdapter {

    private static Context context;
    public List<AccountDetailJson> mPersonal;
    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;


    public AdapterMineAccountListView(Context context, List<AccountDetailJson> personalList) {
        super();
        this.context = context;
        this.mPersonal = personalList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();

    }

    public void addList(List<AccountDetailJson> addList) {
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
    public Object getItem(int position) {
        return this.mPersonal.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AccountDetailJson personalRanking = mPersonal.get(position);

        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
           if(position==0){
               convertView = mInflater.inflate(R.layout.myaccount_first_item, null);

//                           viewholder.design_room_title = (TextView) convertView.findViewById(R.id.design_room_content);





               convertView.setTag(viewholder);

            }
            else if(position==mPersonal.size()-1){
                convertView = mInflater.inflate(R.layout.myaccount_end_item, null);
                convertView.setTag(viewholder);
            }

           else {
               convertView = mInflater.inflate(R.layout.myaccount_common_item, null);
               convertView.setTag(viewholder);
            }




//            viewholder.design_room_title = (TextView) convertView.findViewById(R.id.design_room_content);
//            viewholder.design_room_img = (ImageView) convertView.findViewById(R.id.design_room_img);
//
//            viewholder.design_like_num = (TextView) convertView.findViewById(R.id.design_like_num);
//
//            viewholder.design_look_num = (TextView) convertView.findViewById(R.id.design_look_num);
//
//            viewholder.design_room_designer = (TextView) convertView.findViewById(R.id.design_room_designer);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

//        viewholder.design_room_title.setText(personalRanking.name);
//        imageLoader.displayImage(personalRanking.pic, viewholder.design_room_img, options);
//        viewholder.design_room_designer.setText(personalRanking.designer);
//
//        viewholder.design_look_num.setText(personalRanking.views+"");
//
//        viewholder.design_like_num.setText(personalRanking.likes+"");


        return convertView;
    }

    private static class ViewHolder {
        TextView design_room_title;
        TextView design_room_designer;
        TextView design_look_num;
        TextView design_like_num;

        ImageView design_room_img;
    }
}
