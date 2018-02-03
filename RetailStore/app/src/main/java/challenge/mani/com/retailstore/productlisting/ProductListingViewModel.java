package challenge.mani.com.retailstore.productlisting;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import challenge.mani.com.retailstore.SingleLiveEvent;
import challenge.mani.com.retailstore.data.Category;
import challenge.mani.com.retailstore.data.Product;
import challenge.mani.com.retailstore.data.source.ProductsDatasource;
import challenge.mani.com.retailstore.data.source.remote.ProductsRemoteDataSource;
import java.util.List;

/**
 * Created by mani on 23/11/17.
 */

public class ProductListingViewModel extends AndroidViewModel {

  final MutableLiveData<List<Product>> productsListLiveData = new MutableLiveData<>();

  private final MutableLiveData<Product> productDetailLiveData = new MutableLiveData<>();

  final MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();

  private final MutableLiveData mIsDataLoadingError = new MutableLiveData();

  private final SingleLiveEvent<String> mShowProductDetailEvent = new SingleLiveEvent<>();

  private final SingleLiveEvent<String> mShowCartEvent = new SingleLiveEvent<>();

  private ProductsRemoteDataSource mProductsRemoteDataSource;

  private final Context mContext; // To avoid leaks, this must be an Application Context.

  public ProductListingViewModel(Application application,ProductsRemoteDataSource productsRemoteDataSource) {
    super(application);

    this.mProductsRemoteDataSource = productsRemoteDataSource;

    mContext = application.getApplicationContext();
  }

  public SingleLiveEvent<String> getmShowProductDetailEvent() {
    return mShowProductDetailEvent;
  }

  public SingleLiveEvent<String> getmShowCartEvent() {
    return mShowCartEvent;
  }

  public void loadProducts(Category categoryId) {

    dataLoading.setValue(true);

    mProductsRemoteDataSource.getProducts(categoryId, new ProductsDatasource.LoadProductsCallback() {
      @Override public void onProductsLoaded(List<Product> products) {

        productsListLiveData.setValue(products);

        dataLoading.setValue(false);

        mIsDataLoadingError.setValue(false);
      }

      @Override public void onDataNotAvailable() {

        mIsDataLoadingError.setValue(true);

        dataLoading.setValue(false);
      }
    });
  }

  public void loadProductDetail(String productId) {

    dataLoading.setValue(true);

    mProductsRemoteDataSource.getProduct(productId, new ProductsDatasource.GetProductCallback() {
      @Override public void onProductLoaded(Product product) {

        productDetailLiveData.setValue(product);

        dataLoading.setValue(false);

        mIsDataLoadingError.setValue(false);
      }

      @Override public void onDataNotAvailable() {

        mIsDataLoadingError.setValue(true);

        dataLoading.setValue(false);
      }
    });
  }

  public LiveData<List<Product>> getProductsListLiveData() {
    return productsListLiveData;
  }

  public LiveData<Boolean> getDataLoading() {
    return dataLoading;
  }

  public MutableLiveData<Product> getProductDetailLiveData() {
    return productDetailLiveData;
  }
}
