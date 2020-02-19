package mk.ukim.finki.mpip.aud1.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import mk.ukim.finki.mpip.aud1.models.FlickrItem;
import mk.ukim.finki.mpip.aud1.persistence.repository.FlickItemRepository;

public class FlickrItemsViewModel extends AndroidViewModel {

    private LiveData<List<FlickrItem>> items;

    private FlickItemRepository flickItemRepository = null;

    public FlickrItemsViewModel(@NonNull Application application) {
        super(application);
        flickItemRepository = new FlickItemRepository(FlickrItemsViewModel.this.getApplication());
        items = flickItemRepository.listAllFlickrItems();
    }

    public LiveData<List<FlickrItem>> getFlickrItems() {
        return items;
    }

    public void updateData(List<FlickrItem> items) {
        if (items==null) {
            return;
        }
        flickItemRepository.deleteAll();
        for (FlickrItem item:items) {
            flickItemRepository.insertItem(item);
        }
    }

}
