package com.mobileappdev.teamone.meetup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ViewHolder>{

    private final List<ChatListItem> mValues;
    private final ChatListFragment.OnFragmentInteractionListener mListener;

    public ChatListRecyclerViewAdapter(ChatListFragment.OnFragmentInteractionListener listener) {
        mValues = ChatListContent.getItems();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = android.view.LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mSubTitle.setText(mValues.get(position).getSubtitle());
        holder.mStatus.setText(mValues.get(position).getStatus());
        holder.mSubStatus.setText(mValues.get(position).getSubStatus());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ChatListItem mItem;
        public final View mView;
        public final TextView mTitle;
        public final TextView mSubTitle;
        public final TextView mStatus;
        public final TextView mSubStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = itemView.findViewById(R.id.textTitle);
            mSubTitle = itemView.findViewById(R.id.textSubTitle);
            mStatus = itemView.findViewById(R.id.textTopRight);
            mSubStatus = itemView.findViewById(R.id.textBottomRight);
        }
    }
}

