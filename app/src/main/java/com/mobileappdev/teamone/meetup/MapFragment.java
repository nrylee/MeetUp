package com.mobileappdev.teamone.meetup;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mobileappdev.teamone.meetup.MapModels.MapContent;
import com.mobileappdev.teamone.meetup.MapModels.MapEventAttendee;
import com.mobileappdev.teamone.meetup.MapModels.MapEventItem;

import java.io.Console;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GoogleMap mMap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initializeMap();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    private void initializeMap() {
        if (mMap == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.google_map_fragment);
            mapFrag.getMapAsync(this);
        }
    }

    private void populateMap(List<MapEventItem> eventItems) {
        if(mMap != null) {
            for (MapEventItem eventItem : eventItems) {
                CircleOptions eventCircleOptions = new CircleOptions();
                eventCircleOptions.strokeColor(0xFF000000);
                eventCircleOptions.center(eventItem.getCenter());
                eventCircleOptions.radius(eventItem.getRadius());
                eventCircleOptions.fillColor(eventItem.getColor());
                eventCircleOptions.visible(true);
                mMap.addCircle(eventCircleOptions);

                for (final MapEventAttendee attendee : eventItem.getEventAttendeeList()) {
                    MarkerOptions personMarker = new MarkerOptions();
                    personMarker.position(attendee.getCenter());
                    personMarker.visible(true);
                    BitmapDescriptor bitmapDescriptor = getBitmapDescriptor(R.drawable.ic_dot_green_12dp);
                    personMarker.icon(bitmapDescriptor);
                    Marker marker = mMap.addMarker(personMarker);
                    marker.setTitle(attendee.getName());

                    mMap.setOnMarkerClickListener(
                        new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker m) {
                                m.showInfoWindow();
                                return true;
                            }
                        }
                    );

                    mMap.setOnInfoWindowClickListener(
                        new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker m) {
                                //android.support.v7.app.AlertDialog.Builder alertDialog;
                                //(alertDialog = android.support.v7.app.AlertDialog.Builder) instanceof  ? (() (alertDialog = android.support.v7.app.AlertDialog.Builder)) : null;;
                            }
                        }
                    );
                }
            }
        }
    }

    private com.google.android.gms.maps.model.BitmapDescriptor getBitmapDescriptor(int id) {
        Drawable vectorDrawable = this.getContext().getDrawable(id);
        int h = vectorDrawable.getIntrinsicHeight();
        int w = vectorDrawable.getIntrinsicWidth();
        vectorDrawable.setBounds(0, 0, w, h);
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bm);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        populateMap(MapContent.getMapEvents());

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(
                        MapContent.LAT_START,
                        MapContent.LNG_START
                ),
                8.0f
        ));
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
