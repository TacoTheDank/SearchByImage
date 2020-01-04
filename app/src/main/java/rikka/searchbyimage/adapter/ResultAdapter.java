package rikka.searchbyimage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import rikka.searchbyimage.R;
import rikka.searchbyimage.utils.IqdbResultCollecter;

/**
 * Created by Rikka on 2015/12/20.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private ArrayList<IqdbResultCollecter.IqdbItem> mData;
    private int mCount = 0;
    private OnItemClickListener mOnItemClickListener;

    public ResultAdapter(ArrayList<IqdbResultCollecter.IqdbItem> mData) {
        this.mData = mData;
        this.mCount = mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_iqdb_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IqdbResultCollecter.IqdbItem item = mData.get(position);
        holder.mTextViewURL.setText(item.imageURL);
        holder.mTextViewSize.setText(item.size);
        holder.mTextViewSimilarity.setText(item.similarity);

        Glide.with(holder.mImageView.getContext())
                .load(item.thumbnailURL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.mImageView);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView, pos, mData.get(pos));
            });

            holder.itemView.setOnLongClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mOnItemClickListener.onItemLongClick(holder.itemView, pos, mData.get(pos));
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, IqdbResultCollecter.IqdbItem item);

        void onItemLongClick(View view, int position, IqdbResultCollecter.IqdbItem item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mView;
        TextView mTextViewURL;
        TextView mTextViewSize;
        TextView mTextViewSimilarity;
        ImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);

            mView = itemView.findViewById(R.id.view);
            mTextViewURL = itemView.findViewById(R.id.item_text_url);
            mTextViewSize = itemView.findViewById(R.id.item_text_size);
            mTextViewSimilarity = itemView.findViewById(R.id.item_text_similarity);
            mImageView = itemView.findViewById(R.id.item_image);
        }
    }
}
