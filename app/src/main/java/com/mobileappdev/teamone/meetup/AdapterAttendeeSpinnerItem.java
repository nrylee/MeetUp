package com.mobileappdev.teamone.meetup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class AdapterAttendeeSpinnerItem extends ArrayAdapter<AttendeeListSimpleSearch>
{
    public static String oneSpace =" ";
    public static int tikMark =0X2714;
    public static int crossMark =0X2715;
    public static int tikMarkAroundBox =0X2611;
    public static int crossMarkAroundBox =0X274E;
    public static String dash ="-";

    private LayoutInflater mInflater;
    private List<AttendeeListSimpleSearch> listState;
    public Spinner mySpinner = null;

    public AdapterAttendeeSpinnerItem(Context context, int resource, List<AttendeeListSimpleSearch> objects, Spinner mySpinner)
    {
        super(context, resource, objects);
        this.listState = objects;
        this.mySpinner = mySpinner;
        mInflater = LayoutInflater.from(context);
    }

    public List<AttendeeListSimpleSearch> getListState() {
        return listState;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent)
    {
        String text = "";
        final ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.attendee_spinner_item, null, false);
            holder.mTextView = convertView.findViewById(R.id.attendee_spinner_item_username);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         * check position , if position is zero we put space on top of list of spinner
         */
        if ((position == 0))
            text = oneSpace;
        /**
         * check position , if position is one we put cross mark before text to show that position used to be for clear all selected items on spinner
         */
        else if ((position == 1))
            text = "  " + String.valueOf((char) crossMarkAroundBox) + " " + listState.get(position).getTagText();
        /**
         * check position , if position is two we put check mark before text to show that position used to be for select all items on spinner
         */
        else if ((position == 2))
            text = "  " + String.valueOf((char) tikMarkAroundBox) + " " + listState.get(position).getTagText();
        /**
         * check position , if position is bigger than two we have to check that position is selected before or not and put check mark or dash before text
         */
        else
        {
            if (listState.get(position).isSelected())
            {
                text = "  " + String.valueOf((char) tikMark) + " " + listState.get(position).getTagText();
            }
            else
            {
                text = "  " + String.valueOf(dash) + " " + listState.get(position).getTagText();
            }
        }
        holder.mTextView.setText(text);
        holder.mTextView.setTag(position);
        holder.mTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /**
                 * if you want open spinner after click on text for first time we have to open spinner programmatically
                 */
                mySpinner.performClick();
                int getPosition = (Integer) v.getTag();
                listState.get(getPosition).setSelected(!listState.get(getPosition).isSelected());
                notifyDataSetChanged();

                /**
                 * if clicked position is one
                 * that means you want clear all select item in list
                 */
                if (getPosition == 1)
                {
                    clearList();
                }
                /**
                 * if clicked position is two
                 * that means you want select all item in list
                 */
                else if (getPosition == 2)
                {
                    fillList();
                }
            }
        });
        return convertView;
    }


    /**
     * clear all items in list
     */
    public void clearList()
    {
        for (AttendeeListSimpleSearch items : listState)
        {
            items.setSelected(false);
        }
        notifyDataSetChanged();
    }

    /**
     * select all items in list
     */
    public void fillList()
    {
        for (AttendeeListSimpleSearch items : listState)
        {
            items.setSelected(true);
        }
        notifyDataSetChanged();
    }

    /**
     * view holder
     */
    private class ViewHolder
    {
        private TextView mTextView;
    }
}