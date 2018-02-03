package challenge.mani.com.retailstore.productdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.data.Product;
import challenge.mani.com.retailstore.productlisting.ProductListingViewModel;
import challenge.mani.com.retailstore.util.ActivityUtils;
import challenge.mani.com.retailstore.util.BaseActivity;

public class ProductDetailActivity extends BaseActivity {

  public static final String PRODUCT_KEY = "product_key";

  public static final String PRODUCT_CATEGORY = "product_category";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.product_detail_activity);

    Bundle bundle = getIntent().getExtras();

    if (bundle != null && bundle.containsKey(PRODUCT_KEY) && bundle.containsKey(PRODUCT_CATEGORY)) {

      setupViewFragment(bundle);
    } else {
      //TODO: handle error behaviour
    }


  }

  private void setupViewFragment(Bundle bundle) {
    ProductDetailFragment productDetailFragment =
        (ProductDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

    if (productDetailFragment == null) {
      // Create the fragment
      productDetailFragment = ProductDetailFragment.newInstance();

      productDetailFragment.setArguments(bundle);

      ActivityUtils.replaceFragmentInActivity(
          getSupportFragmentManager(), productDetailFragment, R.id.contentFrame);
    }
  }
}
