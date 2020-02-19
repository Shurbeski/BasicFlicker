package mk.ukim.finki.mpip.aud1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;


public class MapMarkerViewModel extends ViewModel {

    MutableLiveData<Location> location;

    public LiveData<Location> getLatLng() {
        if (location==null) {
            location = new MutableLiveData<>();
        }
        return location;
    }

    public void setLatLng(Location ll) {
        location.postValue(ll);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
