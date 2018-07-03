package easynvr.easy.com.easynvr.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import easynvr.easy.com.easynvr.BR;
import easynvr.easy.com.easynvr.Model.Video;
import easynvr.easy.com.easynvr.R;

public class VideoAdapter extends BaseBindRecyclerViewAdapter<Video.Channel> {

    public VideoAdapter(Context context, List<Video.Channel> mList) {
        super(context, mList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_video, parent, false);

        return new BaseBindingViewHolder(binding);
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, int pos) {
        ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);

        Video.Channel channel = mList.get(pos);
        binding.setVariable(BR.item, channel);

//        android:textColor="@{(item.online == 1) ? 0x58b9fb : 0x999999}

        binding.executePendingBindings();// 数据改变时立即刷新数据
    }
}
