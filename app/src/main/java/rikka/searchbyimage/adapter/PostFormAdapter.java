package rikka.searchbyimage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import rikka.searchbyimage.R;
import rikka.searchbyimage.staticdata.SearchEngine;

/**
 * Created by Rikka on 2016/1/26.
 */
public class PostFormAdapter extends RecyclerView.Adapter<PostFormAdapter.ViewHolder> {
    private SearchEngine mData;
    private boolean mEnabled;
    private PostFormAdapter mAdapter;
    private int mCount;
    private OnFocusChangeListener mOnFocusChangeListener;

    public PostFormAdapter(SearchEngine data, boolean enabled) {
        mData = data;
        mEnabled = enabled;
        mAdapter = this;
        mCount = mData.post_text_key.size() + 1;
    }

    public PostFormAdapter() {
        mData = new SearchEngine();
        mEnabled = true;
        mAdapter = this;
        mCount = 1;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener mOnFocusChangeListener) {
        this.mOnFocusChangeListener = mOnFocusChangeListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == (getItemCount() - 1) ? 1 : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(viewType == 1 ? R.layout.list_item_post_form_add : R.layout.list_item_post_form, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == (getItemCount() - 1)) {
            if (mEnabled) {
                holder.vView.setOnClickListener(v -> {
                    mAdapter.notifyItemInserted(mCount - 1);
                    mCount++;
                });
            } else {
                holder.vView.setVisibility(View.GONE);
            }

        } else {
            if (position < mData.post_text_key.size()) {
                holder.vKey.setText(mData.post_text_key.get(position));
                holder.vValue.setText(mData.post_text_value.get(position));

                if (mData.post_text_type.get(position) == -1) {
                    holder.vValue.setText(R.string.upload_form_built_in_selector);
                    holder.vValue.setEnabled(false);
                }
            }

            holder.vKey.setOnFocusChangeListener((v, hasFocus) -> {
                if (mOnFocusChangeListener != null) {
                    mOnFocusChangeListener.onFocusChange(v, hasFocus);
                }
            });
            holder.vValue.setOnFocusChangeListener((v, hasFocus) -> {
                if (mOnFocusChangeListener != null) {
                    mOnFocusChangeListener.onFocusChange(v, hasFocus);
                }
            });

            if (!mEnabled) {
                holder.vKey.setEnabled(false);
                holder.vValue.setEnabled(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public void setItemCount(int count) {
        mCount = count;
    }

    public interface OnFocusChangeListener {
        void onFocusChange(View view, boolean hasFocus);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout vView;
        EditText vKey;
        EditText vValue;

        ViewHolder(View itemView) {
            super(itemView);

            vView = itemView.findViewById(R.id.linearLayout);
            vKey = itemView.findViewById(R.id.editText_key);
            vValue = itemView.findViewById(R.id.editText_value);
        }
    }
}
