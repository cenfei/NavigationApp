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
import com.yj.navigation.object.BankCardInfoJson;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;

import java.util.List;


public class MyBankcardAllListAdapter extends BaseAdapter {

    private Context context;
    private List<BankCardInfoJson> mAppList;
private String token=null;
    public MyBankcardAllListAdapter(Context context, List<BankCardInfoJson> mAppList, String usertoken) {
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
        return mAppList.get(position).bankname;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        final BankCardInfoJson deviceJson = mAppList.get(position);
        positionIndex=position;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.bankcard_item, null);
            holder.cardid_name = (TextView) convertView.findViewById(R.id.cardid_name);
            holder.cardid_value = (TextView) convertView.findViewById(R.id.cardid_value);

            holder.unbind_id = (TextView) convertView.findViewById(R.id.unbind_id);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cardid_name.setText("" + deviceJson.bankname);
        holder.cardid_value.setText("" + deviceJson.bankcardno);

        holder.unbind_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unBindDeviceFromServerForMsg(deviceJson.id);
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
        TextView cardid_name;
        TextView cardid_value;

        TextView unbind_id;

    }
    FoxProgressbarInterface foxProgressbarInterface;
    public void unBindDeviceFromServerForMsg(Integer deviceId) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(context, "加载中...");


        ProtocolUtil.unbindCardFunction(context, new
                UnBindDeviceHandler(), token, deviceId);


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
