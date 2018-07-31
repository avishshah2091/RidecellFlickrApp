package com.ridecell.avish.ridecellflickrapp.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ridecell.avish.ridecellflickrapp.R;
import com.ridecell.avish.ridecellflickrapp.model.ImageMetadata;
import com.ridecell.avish.ridecellflickrapp.model.RetrofitInterface;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * Use the {@link ImageDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageDetailFragment extends Fragment {

    private static final String TAG = "ImageDetailFragment";

    private static final String METADATA_KEY = "image-metadata";

    private TextView mTitle;
    private TextView mDescription;
    private SimpleDraweeView mDraweeView;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private BottomSheetDialog mBottomSheetDialog;

    public ImageDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param imageMetadata Object which contains metadata of the image.
     * @return A new instance of fragment ImageDetailFragment.
     */
    public static ImageDetailFragment newInstance(ImageMetadata imageMetadata) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(METADATA_KEY, imageMetadata);
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_image_detail, container, false);

        mTitle = view.findViewById(R.id.title);
        mDescription = view.findViewById(R.id.description);
        mDraweeView = view.findViewById(R.id.full_image_view);
        mCollapsingToolbar = view.findViewById(R.id.collapsing_toolbar);

        if (getArguments() == null) {
            Log.w(TAG, "onCreateView: Arguments null!");
            return view;
        }

        final ImageMetadata imageMetadata = getArguments().getParcelable(METADATA_KEY);

        if (imageMetadata == null) {
            Log.w(TAG, "onCreateView: image metadata from arguments null!");
            return view;
        }

        fillView(imageMetadata);

        return view;
    }

    private void fillView(final ImageMetadata imageMetadata) {
        final Uri uri = Uri.parse(imageMetadata.getUrl());
        fillDetails(imageMetadata, uri);

        mDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(imageMetadata, uri);
            }
        });
    }

    private void showBottomSheetDialog(ImageMetadata imageMetadata, Uri uri) {
        final View bottomSheetLayout =
                getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);

        DraweeView draweeView = bottomSheetLayout.findViewById(R.id.full_image_view);
        draweeView.setImageURI(uri);

        (bottomSheetLayout.findViewById(R.id.button_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        mBottomSheetDialog.setContentView(bottomSheetLayout);
        mBottomSheetDialog.show();
    }

    private void fillDetails(ImageMetadata imageMetadata, Uri uri) {
        mCollapsingToolbar.setTitle(imageMetadata.getTitle());
        mTitle.setText(imageMetadata.getTitle());
        mDescription.setText(imageMetadata.getDescription());
        mDraweeView.setImageURI(uri);
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
