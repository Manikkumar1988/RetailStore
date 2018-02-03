package challenge.mani.com.retailstore.data.source;

import android.support.annotation.NonNull;
import challenge.mani.com.retailstore.data.Category;
import challenge.mani.com.retailstore.data.Product;
import java.util.List;

/**
 * Created by mani on 23/11/17.
 */

public interface ProductsDatasource {

  interface LoadProductsCallback {

    void onProductsLoaded(List<Product> products);

    void onDataNotAvailable();
  }

  interface GetProductCallback {

    void onProductLoaded(Product product);

    void onDataNotAvailable();
  }

  void getProducts(@NonNull Category categoryId, @NonNull LoadProductsCallback callback);

  void getProduct(@NonNull String productId, @NonNull GetProductCallback callback);
}
