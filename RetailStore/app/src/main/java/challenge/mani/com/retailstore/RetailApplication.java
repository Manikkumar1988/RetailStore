package challenge.mani.com.retailstore;

import android.app.Application;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by mani on 27/11/17.
 */

public class RetailApplication extends Application {
  private static GoogleAnalytics sAnalytics;
  private static Tracker sTracker;

  @Override
  public void onCreate() {
    super.onCreate();

    sAnalytics = GoogleAnalytics.getInstance(this);
  }

  /**
   * Gets the default {@link Tracker} for this {@link Application}.
   * @return tracker
   */
  synchronized public Tracker getDefaultTracker() {
    if (sTracker == null) {
      sTracker = sAnalytics.newTracker(R.xml.global_tracker);
    }

    return sTracker;
  }
}
