package mk.ukim.finki.mpip.aud1.persistence.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import mk.ukim.finki.mpip.aud1.models.FlickrItem;
import mk.ukim.finki.mpip.aud1.persistence.FlickrItemDatabase;

public class FlickItemRepository {

    private String DB_NAME = "flickr_items";

    private static FlickrItemDatabase flickrItemDatabase = null;

    private Context context;

    public FlickItemRepository(Context context) {
        if (flickrItemDatabase==null) {
            flickrItemDatabase = Room.databaseBuilder(context, FlickrItemDatabase.class, DB_NAME).build();
        }
    }

    public void insertItem(final FlickrItem item) {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                flickrItemDatabase.flickrDao().insert(item);
                return null;
            }

        }.execute();
    }

    public LiveData<List<FlickrItem>> listAllFlickrItems() {
        return flickrItemDatabase.flickrDao().getAll();
    }

    public void deleteAll() {
       new AsyncTask<Void,Void,Void>() {

           @Override
           protected Void doInBackground(Void... voids) {
               flickrItemDatabase.flickrDao().deleteAll();
               return null;
           }
       }.execute();
    }

}
