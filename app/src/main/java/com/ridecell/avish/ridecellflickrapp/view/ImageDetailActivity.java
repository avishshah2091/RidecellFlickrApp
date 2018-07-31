package com.ridecell.avish.ridecellflickrapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ridecell.avish.ridecellflickrapp.R;
import com.ridecell.avish.ridecellflickrapp.model.ImageMetadata;

/**
 * A simple {@link AppCompatActivity} subclass.
 *
 * Usage: Contains the Frame to load the image details
 */
public class ImageDetailActivity extends AppCompatActivity {

    public static final String TAG = "ImageDetailActivity";
    public static final String DETAIL_KEY = "image-detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        ImageMetadata imageMetadata = getIntent().getParcelableExtra(DETAIL_KEY);

        // check error cases
        if (imageMetadata == null) loadErrorFragment();

        loadFragment(ImageDetailFragment.newInstance(imageMetadata));
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        // create a FragmentTransaction to begin the transaction and replace the Fragment
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    private void loadErrorFragment() {
        loadFragment(NetworkErrorFragment.newInstance());
    }
}
