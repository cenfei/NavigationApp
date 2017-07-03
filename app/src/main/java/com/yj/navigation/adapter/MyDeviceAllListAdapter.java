package com.yj.navigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.navigation.R;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.object.DeviceJson;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;

import java.util.List;


public class MyDeviceAllListAdapter extends BaseAdapter {

    private Context context;
    private List<DeviceJson> mAppList;
private String token=null;
    public MyDeviceAllListAdapter(Context context, List<DeviceJson> mAppList,String usertoken) {
        this.context = context;
        this.token=usertoken;
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
        final DeviceJson deviceJson = mAppList.get(position);
        positionIndex=position;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.device_item, null);
            holder.device_content_id = (TextView) convertView.findViewById(R.id.device_content_id);
            holder.unbind_id = (TextView) convertView.findViewById(R.id.unbind_id);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.device_content_id.setText("设备号" + deviceJson.devNo);
        holder.unbind_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unBindDeviceFromServerForMsg(deviceJson.bindId);
            }
        });
//        ApplicationInfo item = getItem(position);
//        holder.iv_icon.setImageDrawable(item.loadIcon(packageManager));
//        holder.tv_name.setText(item.loadLabel(packageManager));
        return convertView;
    }
int positionIndex=0;
    class ViewHolder {
        //  ImageView iv_icon;
        TextView device_content_id;

        TextView unbind_id;

    }
    FoxProgressbarInterface foxProgressbarInterface;
    public void unBindDeviceFromServerForMsg(Integer deviceId) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(context, "加载中...");


        ProtocolUtil.unbindDeviceFunction(context, new
                UnBindDeviceHandler(),token, deviceId);


    }


    private class UnBindDeviceHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            unBindDeviceHandler(resp);
        }
    }


    public void unBindDeviceHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                Util.Toast(context, "解绑成功",null);
                mAppList.remove(positionIndex);
                notifyDataSetChanged();
                //保存token
//                configPref.userToken().put(baseJson.token);
//                configPref.userMobile().put(mobile);
//                Util.startActivity(LoginActivity.this, MineActivity_.class);
//                finish();
            }

        }
    }
}
