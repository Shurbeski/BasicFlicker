package mk.ukim.finki.mpip.aud1.asynctask;

import android.os.AsyncTask;

import java.util.List;

import mk.ukim.finki.mpip.aud1.persistence.dao.FlickrDao;
import mk.ukim.finki.mpip.aud1.models.FlickrItem;

public class GetAllFlickrItemsAsyncTask extends AsyncTask<Void,Void,List<FlickrItem>> {

    private GetAllTaskCallback callback;
    private FlickrDao flickrDao;

    public GetAllFlickrItemsAsyncTask(FlickrDao flickrDao, GetAllTaskCallback callback) {
        this.callback = callback;
        this.flickrDao = flickrDao;
    }

    @Override
    protected List<FlickrItem> doInBackground(Void... voids) {
        return flickrDao.getAllSync();
    }

    @Override
    protected void onPostExecute(List<FlickrItem> flickrItems) {
        super.onPostExecute(flickrItems);
        callback.onLoaded(flickrItems);
    }
}
