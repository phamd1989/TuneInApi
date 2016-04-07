package com.example.dungpham.tuneintest.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dungpham.tuneintest.R;
import com.example.dungpham.tuneintest.TuneinApplication;
import com.example.dungpham.tuneintest.activity.LinkActivity;
import com.example.dungpham.tuneintest.model.BaseElement;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dungpham on 4/5/16.
 */
public class ElementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<BaseElement> mData;
    Activity mActivity;
    private final int HEADER = 0;
    private final int ITEM = 1;

    public ElementAdapter(Activity activity) {
        mData = new ArrayList<>();
        mActivity = activity;
    }

    public void setData(List<BaseElement> data) {
        mData.clear();
        for (BaseElement element: data) {
            mData.add(element);
            if (element.getChildren() != null && element.getChildren().size() != 0) {
                mData.addAll(element.getChildren());
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case HEADER:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view, parent, false);
                viewHolder = new HeaderViewHolder(v1);
                break;
            case ITEM:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
                viewHolder = new ItemViewHolder(v2);
                break;
            default:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
                viewHolder = new ItemViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BaseElement element = mData.get(position);
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder vh = (ItemViewHolder) holder;
            // set text
            vh.mText.setText(element.getText());
            vh.mText.setTypeface(null, Typeface.BOLD);
            // set subtext
            if (element.getSubtext() != null) {
                vh.mSubtext.setVisibility(View.VISIBLE);
                vh.mSubtext.setText(element.getSubtext());
            } else {
                vh.mSubtext.setVisibility(View.GONE);
            }
            // set click listener
            vh.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (element.getType() != null && !"audio".equals(element.getType())) {
                        String[] parts = element.getURL().split("/");
                        String url = parts[parts.length - 1] + "&render=json";
                        LinkActivity.launchLinkActivity(mActivity, url, element.getText());
                    } else {
                        Toast.makeText(mActivity.getApplicationContext(), "Station playing ...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // set image resource
            if (element.getImageUrl() != null) {
                vh.mImage.setBackground(null);
                vh.mImage.setImageURI(Uri.parse(element.getImageUrl()));
            } else {
                vh.mImage.setBackground(mActivity.getResources().getDrawable(R.mipmap.ic_splash));
                vh.mImage.setImageURI(null);
            }
        } else if (holder instanceof HeaderViewHolder) {
            final HeaderViewHolder vh = (HeaderViewHolder) holder;
            vh.mText.setText(element.getText());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getType() == null) {
            return HEADER;
        } else {
            return ITEM;
        }
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        @Bind(R.id.text)
        public TextView mText;
        @Bind(R.id.subtext)
        public TextView mSubtext;
        @Bind(R.id.image)
        public SimpleDraweeView mImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView = itemView;
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text)
        public TextView mText;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
