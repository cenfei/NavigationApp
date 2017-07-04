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
import com.yj.navigation.object.OpesJson;
import com.yj.navigation.util.ImageLoaderUtil;
import com.yj.navigation.util.MyStringUtils;

import java.util.List;

/**
 * Created by Sam on 2015/7/27.
 */
public class AdapterWorkDetailListView extends BaseAdapter {

    private static Context context;
    public List<OpesJson> mPersonal;
    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private String headImg;

    public AdapterWorkDetailListView(Context context, List<OpesJson> personalList, String headImg) {
        super();
        this.context = context;
        this.mPersonal = personalList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.headImg = headImg;
        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();

    }

    private String jobImageUrl=null;
    public void setJobImageUrl(String url){
        this.jobImageUrl=url;
    }

    public void addList(List<OpesJson> addList) {
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
        OpesJson personalRanking = mPersonal.get(position);

        ViewHolder viewholder;
//        if (convertView == null) {
        viewholder = new ViewHolder();
        if (position == 0) {
            convertView = mInflater.inflate(R.layout.mywork_detail_first_item, null);


        } else if (position == mPersonal.size() - 1) {
            convertView = mInflater.inflate(R.layout.mywork_detail_end_item, null);
            TextView end_time_id = (TextView) convertView.findViewById(R.id.end_time_id);

            RoundedImageView end_avar_id = (RoundedImageView) convertView.findViewById(R.id.end_avar_id);
            TextView end_name_id = (TextView) convertView.findViewById(R.id.end_name_id);
            TextView end_content_id = (TextView) convertView.findViewById(R.id.end_content_id);

            end_name_id.setText(personalRanking.opeMan);

            end_content_id.setText(MyStringUtils.getOpesTypeContent(personalRanking.opeType,personalRanking.opeMan,personalRanking.remark));
            imageLoader.displayImage(headImg,end_avar_id,options);

            //YYMMDD HH:MM:SS  2016-12-24 23:54:20
            String monthDate=null;
//            if(mPersonal.size()==2){
                monthDate = personalRanking.opeDt.substring(5, 10);
                monthDate = monthDate.replace("-", "月") + "日";
//            }else{
                monthDate=monthDate+"\n"+personalRanking.opeDt.substring(11,16);
//            }

            end_time_id.setText(monthDate);

        } else {
            convertView = mInflater.inflate(R.layout.mywork_detail_common_item, null);
            TextView common_time_id = (TextView) convertView.findViewById(R.id.common_time_id);

            RoundedImageView common_avar_id = (RoundedImageView) convertView.findViewById(R.id.common_avar_id);
            TextView common_name_id = (TextView) convertView.findViewById(R.id.common_name_id);
            TextView common_content_id = (TextView) convertView.findViewById(R.id.common_content_id);


            common_name_id.setText(personalRanking.opeMan);
            common_content_id.setText(MyStringUtils.getOpesTypeContent(personalRanking.opeType,personalRanking.opeMan,personalRanking.remark));

//            common_content_id.setText(personalRanking.remark);
            imageLoader.displayImage(headImg,common_avar_id,options);

            //YYyyMMDD HH:MM:SS  20161224 23:54:20
            String monthDate = null;

//            if(position==1) {
                monthDate = personalRanking.opeDt.substring(5, 10);
                monthDate = monthDate.replace("-", "月") + "日";
//            }else{
                 monthDate=monthDate+"\n"+personalRanking.opeDt.substring(11,16);

//            }
            common_time_id.setText(monthDate);

        }
//        else {
//            if(jobImageUrl!=null) {
//                convertView = mInflater.inflate(R.layout.mywork_detail_img_item, null);
//
//                TextView img_time_id = (TextView) convertView.findViewById(R.id.img_time_id);
//
//                RoundedImageView img_avar_id = (RoundedImageView) convertView.findViewById(R.id.img_avar_id);
//                TextView img_name_id = (TextView) convertView.findViewById(R.id.img_name_id);
//                ImageView img_src_id = (ImageView) convertView.findViewById(R.id.img_src_id);
//                img_src_id.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Intent intent = new Intent(context, ShowVideoActivity.class);
//                        intent.putExtra("FromVideoSecondFragment", true);
//                        context.startActivity(intent);
//                    }
//                });
//
//
//                img_name_id.setText(personalRanking.opeMan);
//
//                imageLoader.displayImage(headImg, img_avar_id, options);
//                //YYMMDD HH:MM:SS  2016-12-24 23:54:20
//                String monthDate = personalRanking.opeDt.substring(11, 16);
//                imageLoader.displayImage(jobImageUrl, img_src_id, options);
//
//
//                img_time_id.setText(monthDate);
//            }
//
//        }


//            viewholder.design_room_title = (TextView) convertView.findViewById(R.id.design_room_content);
//            viewholder.design_room_img = (ImageView) convertView.findViewById(R.id.design_room_img);
//
//            viewholder.design_like_num = (TextView) convertView.findViewById(R.id.design_like_num);
//
//            viewholder.design_look_num = (TextView) convertView.findViewById(R.id.design_look_num);
//
//            viewholder.design_room_designer = (TextView) convertView.findViewById(R.id.design_room_designer);
//        } else {
//            viewholder = (ViewHolder) convertView.getTag();
//        }

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
