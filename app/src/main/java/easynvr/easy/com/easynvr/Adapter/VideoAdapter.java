package easynvr.easy.com.easynvr.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import easynvr.easy.com.easynvr.BR;
import easynvr.easy.com.easynvr.Model.VideoModel;
import easynvr.easy.com.easynvr.R;

public class VideoAdapter extends BaseBindRecyclerViewAdapter<VideoModel> {
    public VideoAdapter(Context context, List<VideoModel> mList) {
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
        binding.setVariable(BR.item, mList.get(pos));
        binding.executePendingBindings();// 数据改变时立即刷新数据
    }
}
