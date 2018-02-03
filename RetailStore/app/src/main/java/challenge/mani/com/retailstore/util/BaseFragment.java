package challenge.mani.com.retailstore.util;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.data.Product;
import challenge.mani.com.retailstore.productdetail.ProductDetailActivity;
import challenge.mani.com.retailstore.productlisting.ProductListingViewModel;

/**
 * Created by mani on 26/11/17.
 */

public abstract class BaseFragment extends LifecycleFragment {

  protected ProductListingViewModel mProductListingViewModel;

  protected @BindView(R.id.data_loading_ui) View mDataLoadingProgressBar;

  protected void startProductDetailActivity(Product product) {

    Intent detailActivityIntent = new Intent(getContext(), ProductDetailActivity.class);

    detailActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

    detailActivityIntent.putExtra(ProductDetailActivity.PRODUCT_KEY,product.getId());

    detailActivityIntent.putExtra(ProductDetailActivity.PRODUCT_CATEGORY,product.getId());

    startActivity(detailActivityIntent);
  }

  protected void listenForDataLoadingEvent() {
    mProductListingViewModel.getDataLoading().observe(this, new Observer<Boolean>() {
      @Override public void  onChanged(@Nullable Boolean isDataLoading) {

        if (isDataLoading != null && isDataLoading) {

          mDataLoadingProgressBar.setVisibility(View.VISIBLE);
        } else {

          mDataLoadingProgressBar.setVisibility(View.INVISIBLE);
        }
      }
    });
  }
}
