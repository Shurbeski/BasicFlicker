package mk.ukim.finki.mpip.aud1.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import mk.ukim.finki.mpip.aud1.models.FlickrItem;
import mk.ukim.finki.mpip.aud1.persistence.dao.FlickrDao;

@Database(entities = {FlickrItem.class}, version = 1,exportSchema = false)
public abstract class FlickrItemDatabase extends RoomDatabase {

    public abstract FlickrDao flickrDao();

}
