package rikka.searchbyimage.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rikka on 2017/1/22.
 */

public class ListBottomSheetItemViewHolder extends RecyclerView.ViewHolder {

    public TextView text;

    public ListBottomSheetItemViewHolder(View itemView) {
        super(itemView);

        text = itemView.findViewById(android.R.id.text1);
    }
}
