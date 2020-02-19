package mk.ukim.finki.mpip.aud1.models;

import com.google.gson.annotations.SerializedName;

public class FlickrMediaItem {
    @SerializedName("m")
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
