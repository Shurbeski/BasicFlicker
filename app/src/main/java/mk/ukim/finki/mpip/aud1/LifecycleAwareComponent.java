package mk.ukim.finki.mpip.aud1;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LifecycleAwareComponent implements LifecycleObserver {

    Logger logger = Logger.getLogger(LifecycleAwareComponent.class.getName());

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        logger.log(Level.INFO,"onCreate()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        logger.log(Level.INFO,"onDestroy()");
    }
}

