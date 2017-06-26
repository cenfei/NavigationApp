package com.yj.navigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.object.BankInfoJson;
import com.yj.navigation.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Sam on 2015/7/27.
 */
public class AdapterBankItemView extends BaseAdapter {

    private static Context context;
    public List<BankInfoJson> mPersonal;
    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;


    public AdapterBankItemView(Context context, List<BankInfoJson> personalList) {
        super();
        this.context = context;
        this.mPersonal = personalList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();

    }

    public void addList(List<BankInfoJson> addList) {
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
        BankInfoJson personalRanking = mPersonal.get(position);

        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.bank_item, null);
            convertView.setTag(viewholder);
            viewholder.bankname = (TextView) convertView.findViewById(R.id.bankname_id);

        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }


        viewholder.bankname.setText(personalRanking.bankname+"");


        return convertView;
    }

    private static class ViewHolder {
        TextView bankname;


    }
}
