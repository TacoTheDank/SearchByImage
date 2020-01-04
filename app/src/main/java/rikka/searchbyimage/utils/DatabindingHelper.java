package rikka.searchbyimage.utils;

import android.widget.ImageView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

/**
 * Created by Yulan on 2016/1/30.
 */
public class DatabindingHelper {

    @BindingAdapter("imageUrl")
    public static void setimageUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @BindingAdapter("OnCheckedChangeListener")
    public static void setOnCheckedChangeListener(SwitchCompat switchCompat, SwitchCompat.OnCheckedChangeListener onCheckedChangeListener) {
        switchCompat.setOnCheckedChangeListener(onCheckedChangeListener);
    }

}
