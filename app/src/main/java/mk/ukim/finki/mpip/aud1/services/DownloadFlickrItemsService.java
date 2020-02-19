package mk.ukim.finki.mpip.aud1.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.List;

import mk.ukim.finki.mpip.aud1.CardViewActivity;
import mk.ukim.finki.mpip.aud1.R;
import mk.ukim.finki.mpip.aud1.clients.FlickrApiClient;
import mk.ukim.finki.mpip.aud1.models.FlickrItem;
import mk.ukim.finki.mpip.aud1.models.FlickrResponse;
import mk.ukim.finki.mpip.aud1.persistence.repository.FlickItemRepository;
import mk.ukim.finki.mpip.aud1.repository.FlickrApiInterface;
import mk.ukim.finki.mpip.aud1.utils.NotificationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DownloadFlickrItemsService extends IntentService {

    public static final String DATABASE_UPDATED = "mk.ukim.finki.mpip.DownloadAndSaveCountriesService.DATABASE_UPDATED";
    private FlickrApiInterface flickrApiInterface;

    private FlickItemRepository flickItemRepository;

    public DownloadFlickrItemsService() {
        super("Download and save FlickrItems");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = FlickrApiClient.getRetrofit();
        flickrApiInterface = retrofit.create(FlickrApiInterface.class);

        flickItemRepository = new FlickItemRepository(DownloadFlickrItemsService.this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationUtils.makeNotification(this,
                "my_channel_05",
                "Downloading",
                "Downloading Items from API",
                R.drawable.ic_search_black_24dp,
                new Intent(this, CardViewActivity.class),
                1234,
                b-> b.setAutoCancel(true));

        flickrApiInterface.getPublicPhotos(intent.getStringExtra(getString(R.string.download_flickr_tag)))
                .enqueue(new Callback<FlickrResponse>() {
                    @Override
                    public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
                        if (response.isSuccessful()) {
                            saveResultsInDb(response.body().getItems());

                        }
                    }

                    @Override
                    public void onFailure(Call<FlickrResponse> call, Throwable t) {

                    }
                });

    }

    private void saveResultsInDb(List<FlickrItem> items) {
        flickItemRepository.deleteAll();
        for (FlickrItem item: items) {
            flickItemRepository.insertItem(item);
        }
//        sendDatabaseUpdatedBroadcast();
    }

    private void sendDatabaseUpdatedBroadcast() {
        sendBroadcast(new Intent(DATABASE_UPDATED));
    }

}
