package challenge.mani.com.retailstore.data.source.local.cart;

import android.arch.lifecycle.LiveData;
import challenge.mani.com.retailstore.data.Cart;
import challenge.mani.com.retailstore.data.source.CartDataSource;
import java.util.List;

/**
 * Created by mani on 24/11/17.
 */

public class CartsLocalDataSource implements CartDataSource {

  private static CartsLocalDataSource INSTANCE;

  UserDatabase userDatabase;

  public CartsLocalDataSource(UserDatabase userDatabase) {
    this.userDatabase = userDatabase;
  }

  @Override
  public void addProductId(Cart cart) {
    userDatabase.cartDao().addProduct(cart);
  }

  @Override
  public void deleteProductId(Cart cart) {
    userDatabase.cartDao().deleteProduct(cart);
  }

  @Override
  public LiveData<List<Cart>> getProductIds() {
    return userDatabase.cartDao().getProductIds();
  }

  public static CartsLocalDataSource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CartsLocalDataSource();
    }
    return INSTANCE;
  }

  // Prevent direct instantiation.
  private CartsLocalDataSource() {}
}
