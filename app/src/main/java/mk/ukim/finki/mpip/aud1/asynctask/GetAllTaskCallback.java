package mk.ukim.finki.mpip.aud1.asynctask;

import java.util.List;

import mk.ukim.finki.mpip.aud1.models.FlickrItem;

public interface GetAllTaskCallback {
    public void onLoaded(List<FlickrItem> items);
}
