package mk.ukim.finki.mpip.aud1.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mk.ukim.finki.mpip.aud1.CardViewActivity;
import mk.ukim.finki.mpip.aud1.R;
import mk.ukim.finki.mpip.aud1.constants.Constants;
import mk.ukim.finki.mpip.aud1.utils.NotificationUtils;

public class FetchAddressIntentService extends IntentService {

    private static final String TAG = "FetchAddressIntentService";

    protected ResultReceiver mReceiver;

    public FetchAddressIntentService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        if (intent == null) {
            return;
        }
        String errorMessage = "";

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(
                Constants.LOCATION_DATA_EXTRA);

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "Invalid lat and lng";
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address found";
            }
            showNotificationWithGeoLocation(Constants.FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            showNotificationWithGeoLocation(Constants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));
        }

    }

    private void showNotificationWithGeoLocation(int resultCode, String message) {
        NotificationUtils.makeNotification(this,
                "my_channel_05",
                "MPiP - Current location",
                message,
                R.drawable.ic_gps_fixed_black_24dp,
                new Intent(this, FetchAddressIntentService.class),
                1235,
                b-> b.setAutoCancel(true));
    }
}
