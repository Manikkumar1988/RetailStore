package challenge.mani.com.retailstore.data.source;

import android.arch.lifecycle.LiveData;
import challenge.mani.com.retailstore.data.Cart;
import java.util.List;

/**
 * Created by mani on 25/11/17.
 */

public interface CartDataSource {

  void addProductId(Cart cart);

  void deleteProductId(Cart cart);

  LiveData<List<Cart>> getProductIds();
}
