package com.mobileappdev.teamone.meetup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventDetailFragment.OnEditEventInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnEditEventInteractionListener mListener;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetailFragment newInstance(String param1, String param2) {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        view.findViewById(R.id.editEventButton).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onEditEventInteractionListener((new Random()).nextInt(50));
                }
            }
        );
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditEventInteractionListener) {
            mListener = (OnEditEventInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnEditEventInteractionListener {
        // TODO: Update argument type and name
        void onEditEventInteractionListener(Integer id);
    }

    public void populateEventDetailFragment(String name, Date start, Date end, Boolean linksharing, Boolean requireApproval, Integer numOfAttendees) {

        TextView nameOfEvent = (TextView) getView().findViewById(R.id.NameOfEvent);
        nameOfEvent.setText("Location of Event: " + name);

        TextView startTime = (TextView) getView().findViewById(R.id.Start2);
        startTime.setText("Start: " + start);

        TextView endTime = (TextView) getView().findViewById(R.id.End2);
        endTime.setText("End: " + end);

        TextView linkShare = (TextView) getView().findViewById(R.id.LinkSharing2);
        linkShare.setText("Link Sharing" + linksharing);

        TextView approval = (TextView) getView().findViewById(R.id.Approval2);
        approval.setText("Require Approval: " + requireApproval);

        TextView attendee = (TextView) getView().findViewById(R.id.Attendee2);
        attendee.setText("Number of Attendee: " + numOfAttendees);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //populateEventDetailFragment();
    }
}
