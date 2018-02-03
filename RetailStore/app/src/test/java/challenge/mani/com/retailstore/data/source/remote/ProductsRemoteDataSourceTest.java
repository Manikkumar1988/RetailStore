package challenge.mani.com.retailstore.data.source.remote;

import android.content.Context;
import challenge.mani.com.retailstore.data.Category;
import challenge.mani.com.retailstore.data.ElectronicsProduct;
import challenge.mani.com.retailstore.data.Product;
import challenge.mani.com.retailstore.data.source.ProductsDatasource;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by mani on 23/11/17.
 */
public class ProductsRemoteDataSourceTest {

  private final static String TASK_TITLE = "title";

  private final static Category CATEGORY_ID = Category.ELECTRONICS;

  private static List<Product> PRODUCTS = new ArrayList<>();

  @Mock
  private ProductsRemoteDataSource mProductsRemoteDataSource;

  @Mock
  private ProductsDatasource.GetProductCallback mGetTaskCallback;

  @Mock
  private ProductsDatasource.LoadProductsCallback mLoadTasksCallback;

  @Captor
  private ArgumentCaptor<ProductsDatasource.LoadProductsCallback> mTasksCallbackCaptor;

  @Captor
  private ArgumentCaptor<ProductsDatasource.GetProductCallback> mTaskCallbackCaptor;

  @Before
  public void setupTasksRepository() {
    // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
    // inject the mocks in the test the initMocks method needs to be called.
    MockitoAnnotations.initMocks(this);

    PRODUCTS.add(new ElectronicsProduct("Title1", "Description1",null,null,1.0f));
    PRODUCTS.add(new ElectronicsProduct("Title2", "Description2",null,null,1.0f));
  }

  @Test
  public void getProducts_WithDataSourceAvailable_productsAreRetrievedFromRemote() {
    // When calling getTasks in the repository
    mProductsRemoteDataSource.getProducts(CATEGORY_ID, mLoadTasksCallback);

    // the remote data source has data available
    setTasksAvailable(mProductsRemoteDataSource, PRODUCTS);

    // Verify the tasks from the local data source are returned
    verify(mLoadTasksCallback).onProductsLoaded(PRODUCTS);
  }


  @Test
  public void getProducts_WithDataSourcesUnavailable_firesOnDataUnavailable() {
    // When calling getTasks in the repository
    mProductsRemoteDataSource.getProducts(CATEGORY_ID, mLoadTasksCallback);

    // the remote data source has no data available
    setTasksNotAvailable(mProductsRemoteDataSource);

    // Verify no data is returned
    verify(mLoadTasksCallback).onDataNotAvailable();
  }

  @Test
  public void getProduct_WithDataSourceAvailable_productIsRetrievedFromRemote() {
    // When calling getTasks in the repository
    mProductsRemoteDataSource.getProduct(TASK_TITLE,mGetTaskCallback);

    // the remote data source has data available
    setTaskAvailable(mProductsRemoteDataSource,PRODUCTS.get(0));

    // Verify the tasks from the local data source are returned
    verify(mGetTaskCallback).onProductLoaded(PRODUCTS.get(0));
  }


  @Test
  public void getProduct_WithDataSourcesUnavailable_firesOnDataUnavailable() {
    // When calling getTasks in the repository
    mProductsRemoteDataSource.getProduct(TASK_TITLE,mGetTaskCallback);

    // the remote data source has no data available
    setTaskNotAvailable(mProductsRemoteDataSource,TASK_TITLE);

    // Verify no data is returned
    verify(mGetTaskCallback).onDataNotAvailable();
  }


  /**
   * Convenience method that mocks the tasks repository
   */
  private void setTasksAvailable(ProductsDatasource dataSource, List<Product> products) {
    verify(dataSource).getProducts(any(Category.class), mTasksCallbackCaptor.capture());
    mTasksCallbackCaptor.getValue().onProductsLoaded(products);
  }

  private void setTasksNotAvailable(ProductsDatasource dataSource) {
    verify(dataSource).getProducts(any(Category.class), mTasksCallbackCaptor.capture());
    mTasksCallbackCaptor.getValue().onDataNotAvailable();
  }

  private void setTaskNotAvailable(ProductsDatasource dataSource, String productId) {
    verify(dataSource).getProduct(eq(productId), mTaskCallbackCaptor.capture());
    mTaskCallbackCaptor.getValue().onDataNotAvailable();
  }

  private void setTaskAvailable(ProductsDatasource dataSource, Product productId) {
    verify(dataSource).getProduct(eq(TASK_TITLE), mTaskCallbackCaptor.capture());
    mTaskCallbackCaptor.getValue().onProductLoaded(productId);
  }

}