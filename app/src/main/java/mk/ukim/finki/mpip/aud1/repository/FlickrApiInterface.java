package mk.ukim.finki.mpip.aud1.repository;

import mk.ukim.finki.mpip.aud1.models.FlickrResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApiInterface {
    @GET("/services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    Call<FlickrResponse> getPublicPhotos(@Query("tags") String tags);
}
