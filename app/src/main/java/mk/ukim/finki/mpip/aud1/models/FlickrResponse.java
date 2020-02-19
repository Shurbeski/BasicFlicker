package mk.ukim.finki.mpip.aud1.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlickrResponse {

    private List<FlickrItem> items;

    public List<FlickrItem> getItems() {
        return items;
    }

    public void setItems(List<FlickrItem> items) {
        this.items = items;
    }
}


