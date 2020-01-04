package rikka.searchbyimage.utils;

import android.widget.ImageView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * Created by Yulan on 2016/1/30.
 */
public class DatabindingHelper {

    @BindingAdapter("imageUrl")
    public static void setimageUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .crossFade()
                .into(imageView);
    }

    @BindingAdapter("OnCheckedChangeListener")
    public static void setOnCheckedChangeListener(SwitchCompat switchCompat, SwitchCompat.OnCheckedChangeListener onCheckedChangeListener) {
        switchCompat.setOnCheckedChangeListener(onCheckedChangeListener);
    }

}
