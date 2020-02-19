package mk.ukim.finki.mpip.aud1.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mk.ukim.finki.mpip.aud1.models.FlickrItem;

@Dao
public interface FlickrDao  {

    @Deprecated
    @Query("SELECT * FROM flickr_item c ORDER BY c.title")
    List<FlickrItem> getAllSync();

    @Query("SELECT * FROM flickr_item c ORDER BY c.title")
    LiveData<List<FlickrItem>> getAll();

    @Insert
    void insert(FlickrItem item);

    @Update
    void update(FlickrItem item);

    @Delete
    void delete(FlickrItem item);

    @Query("DELETE from flickr_item")
    void deleteAll();

    @Query("SELECT * FROM flickr_item where title=:title")
    LiveData<List<FlickrItem>> getItemsByTitle(String title);
}
