package com.ridecell.avish.ridecellflickrapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ridecell.avish.ridecellflickrapp.R;
import com.ridecell.avish.ridecellflickrapp.model.ImageMetadata;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ImageListFragment extends Fragment {

    public static final String TAG = "ImageListFragment";

    private static final String METADATA_LIST_KEY = "image-metadata-list";

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ImageListFragment() {
    }

    public static ImageListFragment newInstance(ArrayList<ImageMetadata> imageMetadataList) {
        ImageListFragment fragment = new ImageListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(METADATA_LIST_KEY, imageMetadataList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (getArguments() == null) {
            Log.w(TAG, "onCreateView: received arguments null!");
            return view;
        }

        // Adding Toolbar to Main screen
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        ArrayList<ImageMetadata> imageMetadataArrayList =
                getArguments().getParcelableArrayList(METADATA_LIST_KEY);
        RecyclerView recyclerView = view.findViewById(R.id.list);

        // Set the adapter
        setImageListAdapter(imageMetadataArrayList, recyclerView);

        return view;
    }

    private void setImageListAdapter(ArrayList<ImageMetadata> imageMetadataArrayList,
                                     RecyclerView recyclerView) {
        MetadataRecyclerViewAdapter adapter =
                new MetadataRecyclerViewAdapter(imageMetadataArrayList, mListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ImageMetadata item);
    }
}
