package challenge.mani.com.retailstore;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.VisibleForTesting;
import challenge.mani.com.retailstore.cartlisting.CartListingViewModel;
import challenge.mani.com.retailstore.data.source.local.cart.CartsLocalDataSource;
import challenge.mani.com.retailstore.data.source.local.cart.UserDatabase;
import challenge.mani.com.retailstore.data.source.remote.ProductsRemoteDataSource;
import challenge.mani.com.retailstore.productlisting.ProductListingViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak") private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final ProductsRemoteDataSource mProductsDatasource;

    private final UserDatabase mUserDatabase;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application, Injection.provideProductsRemoteDataSource(), Injection.provideUserDatabase(application));
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application, ProductsRemoteDataSource repository, UserDatabase userDatabase) {

        mApplication = application;

        mProductsDatasource = repository;

        mUserDatabase = userDatabase;
    }

    @Override public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(ProductListingViewModel.class)) {

            //noinspection unchecked
            return (T) new ProductListingViewModel(mApplication, mProductsDatasource);

        } else if (modelClass.isAssignableFrom(CartListingViewModel.class)) {

            //noinspection unchecked
            return (T) new CartListingViewModel(mApplication,  new CartsLocalDataSource(
                mUserDatabase));
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}