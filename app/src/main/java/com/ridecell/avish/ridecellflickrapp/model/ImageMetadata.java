package com.ridecell.avish.ridecellflickrapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * A simple {@link Parcelable} subclass.
 *
 * Contains: Members that shows Metadata of Image.
 * Metadata includes Title, Description and Filename of the image.
 *
 * Usage: Use the class to parse JSON response, show metadata of
 * the image to different user screens.
 */
public class ImageMetadata implements Parcelable {

    // Members of class
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("url")
    @Expose
    private String url;

    // Parcelable Constructor
    private ImageMetadata(Parcel in) {
        title = in.readString();
        description = in.readString();
        url = in.readString();
    }

    public ImageMetadata(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    // Used to regenerate class object. Parcelables must have a CREATOR
    public static final Creator<ImageMetadata> CREATOR = new Creator<ImageMetadata>() {
        @Override
        public ImageMetadata createFromParcel(Parcel in) {
            return new ImageMetadata(in);
        }

        @Override
        public ImageMetadata[] newArray(int size) {
            return new ImageMetadata[size];
        }
    };

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageMetadata withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageMetadata withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageMetadata withFilename(String filename) {
        this.url = filename;
        return this;
    }

    // Customized toString method helpful for debugging
    @Override
    public String toString() {
        return "(" +
                "title" + title +
                "description" + description +
                "url" + url +
                ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
    }
}
