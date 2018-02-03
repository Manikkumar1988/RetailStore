package challenge.mani.com.retailstore.productlisting;

import android.app.Application;
import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.content.res.Resources;
import challenge.mani.com.retailstore.data.Category;
import challenge.mani.com.retailstore.data.ElectronicsProduct;
import challenge.mani.com.retailstore.data.Product;
import challenge.mani.com.retailstore.data.source.ProductsDatasource;
import challenge.mani.com.retailstore.data.source.remote.ProductsRemoteDataSource;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by mani on 23/11/17.
 */
public class ProductListingViewModelTest {

  private static final Category CATEGORY = Category.ELECTRONICS;

  private static List<Product> PRODUCTS = new ArrayList<>();

  @Mock
  private ProductsRemoteDataSource mProductsRemoteDataSource;

  @Mock
  private Application mContext;

  @Captor
  private ArgumentCaptor<ProductsDatasource.LoadProductsCallback> mLoadTasksCallbackCaptor;

  @Rule
  public TestRule rule = new InstantTaskExecutorRule();

  private ProductListingViewModel mProductListingViewModel;


  @Before
  public void setupProductsViewModel() throws Exception {
    // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
    // inject the mocks in the test the initMocks method needs to be called.
    MockitoAnnotations.initMocks(this);

    setupContext();

    // Get a reference to the class under test
    mProductListingViewModel = new ProductListingViewModel(mContext, mProductsRemoteDataSource);

    PRODUCTS.add(new ElectronicsProduct("Title1", "Description1",null,null,1.0f));
    PRODUCTS.add(new ElectronicsProduct("Title2", "Description2",null,null,1.0f));
  }

  private void setupContext() {
    when(mContext.getApplicationContext()).thenReturn(mContext);

    when(mContext.getResources()).thenReturn(mock(Resources.class));
  }

  @Test
  public void loadAllTasksFromRepository_dataLoaded() {
    // When loading of Tasks is requested
    mProductListingViewModel.loadProducts(CATEGORY);

    // Callback is captured and invoked with stubbed tasks
    verify(mProductsRemoteDataSource).getProducts(any(Category.class), mLoadTasksCallbackCaptor.capture());


    // Then progress indicator is shown
    assertTrue(mProductListingViewModel.dataLoading.getValue());
    mLoadTasksCallbackCaptor.getValue().onProductsLoaded(PRODUCTS);

    // Then progress indicator is hidden
    assertFalse(mProductListingViewModel.dataLoading.getValue());

    // And data loaded
    assertFalse(mProductListingViewModel.productsListLiveData.getValue().isEmpty());

    assertTrue(mProductListingViewModel.productsListLiveData.getValue().size() == 2);
  }

  @Test
  public void loadAllTasksFromRepository_dataNotAvailable() {
    // When loading of Tasks is requested
    mProductListingViewModel.loadProducts(CATEGORY);

    // Callback is captured and invoked with stubbed tasks
    verify(mProductsRemoteDataSource).getProducts(any(Category.class), mLoadTasksCallbackCaptor.capture());


    // Then progress indicator is shown
    assertTrue(mProductListingViewModel.dataLoading.getValue());
    mLoadTasksCallbackCaptor.getValue().onDataNotAvailable();

    // Then progress indicator is hidden
    assertFalse(mProductListingViewModel.dataLoading.getValue());

    // And data not loaded
    assertNull(mProductListingViewModel.productsListLiveData.getValue());
  }
}