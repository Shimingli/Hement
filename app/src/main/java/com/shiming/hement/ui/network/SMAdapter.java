package com.shiming.hement.ui.network;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiming.hement.R;
import com.shiming.hement.data.model.TodayBean;

import java.util.ArrayList;


/**
 * Created by shiming on 2017/4/9.
 */
public class SMAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<TodayBean> mDatas;

    public SMAdapter(Context context, ArrayList<TodayBean> data) {
        mContext=context;
        if (data==null){
            mDatas=new ArrayList<>();
        }else {
            mDatas=data;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new MyViewHolder( LayoutInflater.from(mContext).inflate(R.layout.adapter_layout, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //[{"date":"619年01月20日","day":"1/20","e_id":"886","title":"李密叛后被杀"}
        TodayBean bean = mDatas.get(position);
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        myViewHolder.mTitle.setText(bean.title);
        myViewHolder.mDes.setText("日期"+bean.day);
        myViewHolder.mDate.setText("发生的具体的日期：：："+bean.date);
    }
    @Override
    public int getItemCount() {
        return mDatas!=null?mDatas.size():0;
    }
    public void addData(ArrayList<TodayBean> bean) {
        mDatas.clear();
        mDatas.addAll(bean);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private TextView mDes;
        private TextView mDate;
        public MyViewHolder(View itemView) {
           super(itemView);
            mDes = (TextView) itemView.findViewById(R.id.tv_des);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mDate = (TextView) itemView.findViewById(R.id.tv_data_);
       }
   }
 }
