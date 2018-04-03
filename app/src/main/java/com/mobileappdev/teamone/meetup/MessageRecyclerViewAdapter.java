package com.mobileappdev.teamone.meetup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;


import java.util.List;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> {

    private final List<MessageItem> mValues;
    private final MessageFragment.OnFragmentInteractionListener mListener;

    public MessageRecyclerViewAdapter(MessageFragment.OnFragmentInteractionListener listener) {
        mValues = MessageContent.getItems();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = android.view.LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mMessage.setText(holder.mItem.getMessageText());
        holder.mUser.setText(holder.mItem.getMessageUser());
        holder.mDate.setText(holder.mItem.getMessageTimeString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public MessageItem mItem;
        public final View mView;
        public final TextView mMessage;
        public final TextView mUser;
        public final TextView mDate;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mMessage = itemView.findViewById(R.id.message_text);
            mUser = itemView.findViewById(R.id.message_user);
            mDate = itemView.findViewById(R.id.message_time);
        }
    }

}
