package com.yj.navigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yj.navigation.R;
import com.yj.navigation.object.DeviceJson;

import java.util.List;


public class MyDeviceAllListAdapter extends BaseAdapter {

    private Context context;
    private List<DeviceJson> mAppList;

    public MyDeviceAllListAdapter(Context context, List<DeviceJson> mAppList) {
        this.context = context;
        this.mAppList = mAppList;
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public String getItem(int position) {
        return mAppList.get(position).devNo;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        DeviceJson deviceJson = mAppList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.device_item, null);
            holder.device_content_id = (TextView) convertView.findViewById(R.id.device_content_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.device_content_id.setText("设备号" + deviceJson.devNo);
//        ApplicationInfo item = getItem(position);
//        holder.iv_icon.setImageDrawable(item.loadIcon(packageManager));
//        holder.tv_name.setText(item.loadLabel(packageManager));
        return convertView;
    }

    class ViewHolder {
        //  ImageView iv_icon;
        TextView device_content_id;


    }
}
