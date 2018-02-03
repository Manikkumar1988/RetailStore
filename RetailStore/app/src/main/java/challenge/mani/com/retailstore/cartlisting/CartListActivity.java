package challenge.mani.com.retailstore.cartlisting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.productdetail.ProductDetailFragment;
import challenge.mani.com.retailstore.util.ActivityUtils;
import challenge.mani.com.retailstore.util.BaseActivity;

public class CartListActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.cart_list_activity);

    setupViewFragment();

    getSupportActionBar().setTitle(getString(R.string.cart_title));

  }

  private void setupViewFragment() {
    CartListFragment tasksFragment =
        (CartListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

    if (tasksFragment == null) {
      // Create the fragment
      tasksFragment = CartListFragment.newInstance();

      ActivityUtils.replaceFragmentInActivity(
          getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
    }
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {

    menu.setGroupVisible(0,false);
    return true;
  }

}
