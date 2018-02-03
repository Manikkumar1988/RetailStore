package challenge.mani.com.retailstore.data.source.local.cart;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import challenge.mani.com.retailstore.data.Cart;

@Database(entities = { Cart.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
 
   public abstract CartDao cartDao();
 
}