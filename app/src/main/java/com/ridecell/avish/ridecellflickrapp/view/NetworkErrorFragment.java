package com.ridecell.avish.ridecellflickrapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ridecell.avish.ridecellflickrapp.R;


/**
 * A simple {@link Fragment} subclass.
 *
 * Use the {@link NetworkErrorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkErrorFragment extends Fragment {

    public NetworkErrorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment NetworkErrorFragment.
     */
    public static NetworkErrorFragment newInstance() {
        return new NetworkErrorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_network_error, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
