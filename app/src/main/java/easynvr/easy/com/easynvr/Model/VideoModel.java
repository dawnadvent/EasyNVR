package easynvr.easy.com.easynvr.Model;

import android.databinding.BaseObservable;

import easynvr.easy.com.easynvr.R;

public class VideoModel extends BaseObservable implements IBaseBindingAdapterItem {

    @Override
    public int getItemViewType() {
        return R.layout.item_video;
    }
}
