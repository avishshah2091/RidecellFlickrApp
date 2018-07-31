package com.ridecell.avish.ridecellflickrapp.view;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.ridecell.avish.ridecellflickrapp.R;
import com.ridecell.avish.ridecellflickrapp.model.ImageMetadata;
import com.ridecell.avish.ridecellflickrapp.model.RetrofitInterface;

import java.util.List;

public class MetadataRecyclerViewAdapter extends
        RecyclerView.Adapter<MetadataRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";

    private List<ImageMetadata> mGalleryList;
    private final ImageListFragment.OnListFragmentInteractionListener mListener;

    MetadataRecyclerViewAdapter(List<ImageMetadata> items,
                                ImageListFragment.OnListFragmentInteractionListener listener) {
        mGalleryList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.w(TAG, "onBindViewHolder: position = " + position);
        viewHolder.title.setText(mGalleryList.get(position).getTitle());

        String url = mGalleryList.get(position).getUrl();
        fillImage(viewHolder, url);

        viewHolder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(
                            mGalleryList.get(viewHolder.getAdapterPosition()));
                }
            }
        });
    }

    private void fillImage(ViewHolder viewHolder, String url) {
        Uri uri = Uri.parse(url);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(130, 130))
                .setCacheChoice(ImageRequest.CacheChoice.SMALL)
                .build();

        viewHolder.draweeView.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(viewHolder.draweeView.getController())
                        .setImageRequest(request)
                        .build());
    }

    @Override
    public int getItemCount() {
        return mGalleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView itemCardView;
        TextView title;
        SimpleDraweeView draweeView;

        ViewHolder(View view) {
            super(view);
            itemCardView = view.findViewById(R.id.itemCardView);
            title = view.findViewById(R.id.title);
            draweeView = view.findViewById(R.id.my_image_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + title.getText() + "'";
        }
    }
}
