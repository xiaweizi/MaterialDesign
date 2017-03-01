package com.xiaweizi.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MeizhiAdapter extends RecyclerView.Adapter<MeizhiAdapter.ViewHolder>{

    private static final String TAG = "MeizhiAdapter";

    private Context mContext;

    private List<Meizhi> mMeizhiList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

    public MeizhiAdapter(List<Meizhi> meizhiList) {
        mMeizhiList = meizhiList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Meizhi meizhi = mMeizhiList.get(position);
                Intent intent = new Intent(mContext, MeizhiActivity.class);
                intent.putExtra(MeizhiActivity.FRUIT_NAME, meizhi.getName());
                intent.putExtra(MeizhiActivity.FRUIT_IMAGE_ID, meizhi.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meizhi meizhi = mMeizhiList.get(position);
        holder.fruitName.setText(meizhi.getName());
        Glide.with(mContext).load(meizhi.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mMeizhiList.size();
    }

}
