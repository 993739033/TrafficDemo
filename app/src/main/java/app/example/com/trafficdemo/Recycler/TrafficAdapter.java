package app.example.com.trafficdemo.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.example.com.trafficdemo.Bean.AppInfoBean;
import app.example.com.trafficdemo.R;

/**
 * Created by mnkj on 2018/2/23.
 */

public class TrafficAdapter extends RecyclerView.Adapter {
    List<AppInfoBean> beans;

    public TrafficAdapter(List<AppInfoBean> beans) {
        this.beans = beans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((VH) holder).bindView(beans.get(Math.abs(position-getItemCount()+1)));
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv_app_name,tv_app_uuid,tv_app_package_name,tv_app_snd,tv_app_rcv, tv_app_total;
        public VH(View itemView) {
            super(itemView);
            tv_app_name = itemView.findViewById(R.id.tv_app_name);
            tv_app_uuid = itemView.findViewById(R.id.tv_app_uuid);
            tv_app_package_name = itemView.findViewById(R.id.tv_app_package_name);
            tv_app_snd = itemView.findViewById(R.id.tv_app_snd);
            tv_app_rcv = itemView.findViewById(R.id.tv_app_rcv);
            tv_app_total = itemView.findViewById(R.id.tv_app_total);
        }

        public void bindView(AppInfoBean bean) {
            tv_app_name.setText(bean.getAppName());
            tv_app_uuid.setText(bean.getAppUuid()+"");
            tv_app_package_name.setText(bean.getAppPackageName());
            tv_app_snd.setText(bean.getApp_Tcp_Snd()+"");
            tv_app_rcv.setText(bean.getApp_Tcp_Rcv() + "");
            tv_app_total.setText(bean.getApp_Tcp_Tot_MB() + "");

        }
    }

 }
