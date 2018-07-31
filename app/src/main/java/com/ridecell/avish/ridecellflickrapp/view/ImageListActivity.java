package com.ridecell.avish.ridecellflickrapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonObject;
import com.ridecell.avish.ridecellflickrapp.R;
import com.ridecell.avish.ridecellflickrapp.model.ImageMetadata;
import com.ridecell.avish.ridecellflickrapp.model.RetrofitInterface;
import com.ridecell.avish.ridecellflickrapp.viewmodel.MetadataViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ridecell.avish.ridecellflickrapp.AndroidApplication.LOG_ENABLED;


public class ImageListActivity extends AppCompatActivity implements
            ImageListFragment.OnListFragmentInteractionListener {

    private static final String SEARCH_VALUE = "SEARCH_STRING";
    public static final String TAG = "ImageListActivity";

    private MetadataViewModel mMmetadataViewModel;
    private String mSearchStr = "recent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if (intent != null) {
            mSearchStr = intent.getStringExtra(SEARCH_VALUE);
        }
        initializeViewWithData();
    }

    private void initializeViewWithData() {
        // The ViewModelStore provides a new ViewModel or one previously created.
        mMmetadataViewModel = ViewModelProviders.of(this).get(MetadataViewModel.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(searchActivity);
            }
        });
        ArrayList<ImageMetadata> imageMetadataArrayList = mMmetadataViewModel.getImageMetadataList();

        if (imageMetadataArrayList != null) {
            loadImageListFragment(imageMetadataArrayList);
        } else {
            prepareData();
        }
    }

    @Override
    public void onListFragmentInteraction(ImageMetadata item) {
        Intent detailActivityIntent = new Intent(this, ImageDetailActivity.class);
        detailActivityIntent.putExtra(ImageDetailActivity.DETAIL_KEY, item);
        startActivity(detailActivityIntent);
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        // create a FragmentTransaction to begin the transaction and replace the Fragment
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);

        // replace the FrameLayout with new Fragment
        fragmentTransaction.commit(); // save the changes
    }

    private void prepareData() {
        String apiKey = "6bf318919bbbc455f3573d18798a58e3";
        // Server end-point call to fetch Image Metadata List
        Call<ResponseBody> call = getFetchImageInterface().searchPhotos(apiKey, mSearchStr, 20);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    Log.d(TAG, "onResponse: ");
                    JSONObject object = new JSONObject(str);
                    String stat = object.getString("stat");
                    ArrayList<String> ids;
                    if (stat.equals("ok")) {
                        ids = new ArrayList<>();
                        JSONArray photos = object.getJSONObject("photos").getJSONArray("photo");
                        for (int i = 0; i < photos.length(); i++) {
                            ids.add(photos.getJSONObject(i).getString("id"));
                        }

                        Log.d(TAG, "printing ids:");
                        for (String id: ids) {
                            Log.d(TAG, "id: " + id);
                        }

                        parseIdList(ids);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());

            }
        });
    }

    private void parseIdList(ArrayList<String> ids) {
        String apiKey = "6bf318919bbbc455f3573d18798a58e3";
        final String secret = "177e9b4d7f3766dc";
        RetrofitInterface retrofitInterface = getFetchImageInterface();

        final ArrayList<ImageMetadata> imageMetadataList = new ArrayList<>();

        for (String id : ids) {
            Call<ResponseBody> call = retrofitInterface.getInfo(apiKey, id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String str = null;
                    try {
                        str = response.body().string();
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("stat").equals("ok")) {
                            JSONObject inside = jsonObject.getJSONObject("photo");

                            //url
                            int farm = inside.getInt("farm");
                            String server = inside.getString("server");
                            String id = inside.getString("id");
                            String url = "https://farm" + farm + ".staticflickr.com/" +
                                    server + "/" + id + "_" + secret + ".jpg";

                            //title
                            String title = inside.getJSONObject("title").getString("_content");

                            ImageMetadata imageMetadata = new ImageMetadata(title, "", url);
                            imageMetadataList.add(imageMetadata);
                            // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg

                            loadImageListFragment(imageMetadataList);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onResponse: " + str);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

    }

    private RetrofitInterface getFetchImageInterface() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RetrofitInterface.class);
    }

    Callback<List<ImageMetadata>> imageMetadataCallback = new Callback<List<ImageMetadata>>() {
        @Override
        public void onResponse(Call<List<ImageMetadata>> call, Response<List<ImageMetadata>> response) {

            List<ImageMetadata> imageMetadataList = response.body();
            // checking null cases
            if (imageMetadataList == null) {
                Log.w(TAG, "onResponse: response body is null!");
                loadErrorFragment();
                return;
            }

            if (LOG_ENABLED) {
                Log.d(TAG, "onResponse: " + response.body().toString());
            }

            ArrayList<ImageMetadata> imageMetadataArrayList = new ArrayList<>(imageMetadataList);
            mMmetadataViewModel.setImageMetadataList(imageMetadataArrayList);
            loadImageListFragment(imageMetadataArrayList);
        }

        @Override
        public void onFailure(Call<List<ImageMetadata>> call, Throwable t) {
            Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            loadErrorFragment();
        }
    };


    private void loadImageListFragment(ArrayList<ImageMetadata> imageMetadataArrayList) {
        loadFragment(ImageListFragment.newInstance(imageMetadataArrayList));
    }

    private void loadErrorFragment() {
        loadFragment(NetworkErrorFragment.newInstance());
    }
}
