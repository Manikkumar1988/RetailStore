package challenge.mani.com.retailstore.data.source.local.cart;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import challenge.mani.com.retailstore.data.Cart;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by mani on 25/11/17.
 */

@Dao
public interface CartDao {

  @Query("SELECT * FROM " + Cart.TABLE_NAME) LiveData<List<Cart>> getProductIds();

  @Insert(onConflict = OnConflictStrategy.REPLACE) void addProduct(Cart cart);

  @Delete void deleteProduct(Cart cart);

  @Update(onConflict = REPLACE) void updateProductId(Cart cart);
}
