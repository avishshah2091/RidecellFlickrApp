package com.ridecell.avish.ridecellflickrapp.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.ridecell.avish.ridecellflickrapp.model.ImageMetadata;

import java.util.ArrayList;

public class MetadataViewModel extends ViewModel {

    private ArrayList<ImageMetadata> mImageMetadataList;

    public ArrayList<ImageMetadata> getImageMetadataList() {
        return mImageMetadataList;
    }

    public void setImageMetadataList(ArrayList<ImageMetadata> mImageMetadataList) {
        this.mImageMetadataList = mImageMetadataList;
    }
}
