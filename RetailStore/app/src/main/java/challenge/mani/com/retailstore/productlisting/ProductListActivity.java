package challenge.mani.com.retailstore.productlisting;

import android.os.Bundle;
import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.RetailApplication;
import challenge.mani.com.retailstore.util.ActivityUtils;
import challenge.mani.com.retailstore.util.BaseActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ProductListActivity extends BaseActivity {

  private ProductListingViewModel mProductListingViewModel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_list_activity);

    setupViewFragment();

    appLaunchEvent();
  }

  private void appLaunchEvent() {
    RetailApplication application = (RetailApplication) getApplication();
    Tracker mTracker = application.getDefaultTracker();

    mTracker.setScreenName("Image~" + ProductListActivity.class.getName());
    mTracker.send(new HitBuilders.ScreenViewBuilder().build());
  }

  private void setupViewFragment() {
    ProductListFragment productFragment =
        (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

    if (productFragment == null) {
      // Create the fragment
      productFragment = ProductListFragment.newInstance();

      ActivityUtils.replaceFragmentInActivity(
          getSupportFragmentManager(), productFragment, R.id.contentFrame);
    }
  }
}
