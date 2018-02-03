package challenge.mani.com.retailstore.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;
import challenge.mani.com.retailstore.data.Category;
import challenge.mani.com.retailstore.data.ElectronicsProduct;
import challenge.mani.com.retailstore.data.Product;
import challenge.mani.com.retailstore.data.source.ProductsDatasource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mani on 23/11/17.
 */

public class ProductsRemoteDataSource implements ProductsDatasource {

  private static ProductsRemoteDataSource INSTANCE;

  private final static Map<String, Product> PRODUCTS_SERVICE_DATA;

  private static final int SERVICE_LATENCY_IN_MILLIS = 2000;

  static {
    PRODUCTS_SERVICE_DATA = new LinkedHashMap<>(6);

    addProduct("Microwave oven", "Electronics",  "https://i.pinimg.com/736x/fc/ef/57/fcef57440babccb507c7265d0402826f.jpg", "1",1.0f);
    addProduct("Television", "Electronics",  "https://i.pinimg.com/736x/fc/ef/57/fcef57440babccb507c7265d0402826f.jpg", "2",1.0f);
    addProduct("Vacuum Cleaner", "Electronics",  "https://i.pinimg.com/736x/fc/ef/57/fcef57440babccb507c7265d0402826f.jpg", "3",1.0f);
    addProduct("Table", "Furniture",  "https://i.pinimg.com/736x/fc/ef/57/fcef57440babccb507c7265d0402826f.jpg", "4",1.0f);
    addProduct("Chair", "Furniture",  "https://i.pinimg.com/736x/fc/ef/57/fcef57440babccb507c7265d0402826f.jpg", "5",1.0f);
    addProduct("Almirah", "Furniture",  "https://i.pinimg.com/736x/fc/ef/57/fcef57440babccb507c7265d0402826f.jpg", "6",1.0f);
  }


  public static ProductsRemoteDataSource getInstance() {

    if (INSTANCE == null) {

      INSTANCE = new ProductsRemoteDataSource();
    }

    return INSTANCE;
  }

  // Prevent direct instantiation.
  private ProductsRemoteDataSource() {}

  private static void addProduct(String title, String categoryId, String imageUrl, String id, float price) {

    Product newProduct = new ElectronicsProduct(title, categoryId, imageUrl, id, price);

    PRODUCTS_SERVICE_DATA.put(newProduct.getId(), newProduct);
  }

  @Override public void getProducts(@NonNull final Category category, final @NonNull LoadProductsCallback callback) {
    // Simulate network by delaying the execution.
    Handler handler = new Handler();


    handler.postDelayed(new Runnable() {
      @Override
      public void run() {

        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product:PRODUCTS_SERVICE_DATA.values()) {

          if(product.getCategoryId().toLowerCase().equals(category.name().toLowerCase())) {

            productArrayList.add(product);
          }
        }
        callback.onProductsLoaded(productArrayList);

      }
    }, SERVICE_LATENCY_IN_MILLIS);
  }

  @Override public void getProduct(@NonNull String productId, @NonNull final GetProductCallback callback) {

    final Product product = PRODUCTS_SERVICE_DATA.get(productId);

    // Simulate network by delaying the execution.
    Handler handler = new Handler();

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {

        callback.onProductLoaded(product);
      }
    }, SERVICE_LATENCY_IN_MILLIS);
  }
}
