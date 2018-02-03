package challenge.mani.com.retailstore.cartlisting;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import challenge.mani.com.retailstore.data.Cart;
import challenge.mani.com.retailstore.data.source.local.cart.CartsLocalDataSource;
import java.util.List;

/**
 * Created by mani on 24/11/17.
 */

public class CartListingViewModel extends AndroidViewModel {

  private CartsLocalDataSource mCartLocalDataSource;

  public CartListingViewModel(Application application,CartsLocalDataSource cartsLocalDataSource) {
    super(application);

    this.mCartLocalDataSource = cartsLocalDataSource;
  }

  public LiveData<List<Cart>> loadProductIds() {

    return mCartLocalDataSource.getProductIds();
  }

  public void addProductId(Cart productId) {
    mCartLocalDataSource.addProductId(productId);
  }

  public void deleteProductId(Cart productId) {

    mCartLocalDataSource.deleteProductId(productId);
  }
}
