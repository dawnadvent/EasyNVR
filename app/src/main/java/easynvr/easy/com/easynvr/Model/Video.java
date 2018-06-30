package easynvr.easy.com.easynvr.Model;

import android.databinding.BaseObservable;

import easynvr.easy.com.easynvr.BR;
import easynvr.easy.com.easynvr.R;

public class Video extends BaseObservable implements IBaseBindingAdapterItem {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.account);
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_video;
    }
}
