package com.mobileappdev.teamone.meetup;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileappdev.teamone.meetup.EventModels.EventListContent;
import com.mobileappdev.teamone.meetup.EventModels.EventListItem;
import com.mobileappdev.teamone.meetup.FragmentListeners.OnViewEventDetailListener;
import com.mobileappdev.teamone.meetup.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnViewEventDetailListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEventsListRecyclerViewAdapter extends RecyclerView.Adapter<MyEventsListRecyclerViewAdapter.ViewHolder> {

    private final List<EventListItem> mValues;
    private final OnViewEventDetailListener mListener;

    public MyEventsListRecyclerViewAdapter(/*List<EventListItem> items, */OnViewEventDetailListener listener) {
        mValues = EventListContent.getList();
        mListener = listener;
    }

    public MyEventsListRecyclerViewAdapter(List<EventListItem> items, OnViewEventDetailListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_eventslist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        holder.mEventNameView.setText(mValues.get(position).getEventName());
        holder.mAttendees.setText(mValues.get(position).getAttendee());
        holder.mStatus.setText(mValues.get(position).getStatus());
        holder.mSubStatus.setText(mValues.get(position).getSubStatus());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onViewEventDetailInteraction(holder.mItem.getEventId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
        public final TextView mEventNameView;
        public final TextView mAttendees;
        public final TextView mStatus;
        public final TextView mSubStatus;
        public EventListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.id);
            mEventNameView = (TextView) view.findViewById(R.id.text_view_event_name);
            mAttendees = (TextView) view.findViewById(R.id.textSubTitle);
            mStatus = (TextView) view.findViewById(R.id.textStartTime);
            mSubStatus = (TextView) view.findViewById(R.id.textDistance);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + /*mContentView.getText()*/"not implemented" + "'";
        }
    }
}
