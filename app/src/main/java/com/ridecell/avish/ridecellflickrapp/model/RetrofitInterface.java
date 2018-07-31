package com.ridecell.avish.ridecellflickrapp.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitInterface {
    public static final String BASE_URL = "https://api.flickr.com";

    @GET("/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    Call<ResponseBody> searchPhotos(@Query("api_key") String apiKey,
                                    @Query("tags") String keyWord,
                                    @Query("per_page") int perPage
                                    );

    @GET("/services/rest/?method=flickr.photos.getInfo&format=json&nojsoncallback=1")
    Call<ResponseBody> getInfo(@Query("api_key") String apiKey,
                               @Query("photo_id") String photoId);

}
