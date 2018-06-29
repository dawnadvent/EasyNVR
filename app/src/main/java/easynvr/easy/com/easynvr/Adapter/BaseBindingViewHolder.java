package easynvr.easy.com.easynvr.Adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import easynvr.easy.com.easynvr.BR;
import easynvr.easy.com.easynvr.Model.IBaseBindingAdapterItem;

public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public ViewDataBinding getBinding() {
        return binding;
    }

    public BaseBindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindData(IBaseBindingAdapterItem item) {
        binding.setVariable(BR.item, item);
    }
}
