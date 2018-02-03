package challenge.mani.com.retailstore;


import android.arch.persistence.room.Room;
import android.content.Context;
import challenge.mani.com.retailstore.data.source.local.cart.UserDatabase;
import challenge.mani.com.retailstore.data.source.remote.ProductsRemoteDataSource;


/**
 * Created by mani on 23/11/17.
 */

public class Injection {

  public static ProductsRemoteDataSource provideProductsRemoteDataSource() {

    return ProductsRemoteDataSource.getInstance();
  }

  public static UserDatabase provideUserDatabase(Context context) {

    return Room.databaseBuilder(context, UserDatabase.class, "user_db").build();
  }
}
