package easynvr.easy.com.easynvr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import easynvr.easy.com.easynvr.Activity.LiveActivity;
import easynvr.easy.com.easynvr.BR;
import easynvr.easy.com.easynvr.Model.Video;
import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.databinding.ItemVideoBinding;

public class VideoAdapter extends BaseBindRecyclerViewAdapter<Video.Channel> {
    private Context mContext;

    public VideoAdapter(Context context, List<Video.Channel> mList) {
        super(context, mList);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_video, parent, false);

        return new BaseBindingViewHolder(binding);
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, int pos) {
        ItemVideoBinding binding = DataBindingUtil.getBinding(holder.itemView);

        Video.Channel channel = mList.get(pos);
        binding.setVariable(BR.item, channel);

        if (channel.getOnline() == 1) {
            binding.onlineIv.setImageResource(R.mipmap.online);
            binding.onlineTv.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
            binding.channelTv.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
            binding.nameTv.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        } else {
            binding.onlineIv.setImageResource(R.mipmap.offline);
            binding.onlineTv.setTextColor(mContext.getResources().getColor(R.color.colorGray));
            binding.channelTv.setTextColor(mContext.getResources().getColor(R.color.colorGray));
            binding.nameTv.setTextColor(mContext.getResources().getColor(R.color.colorGray));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LiveActivity.class);
                // TODO
                mContext.startActivity(intent);
            }
        });

        binding.executePendingBindings();// 数据改变时立即刷新数据
    }
}
