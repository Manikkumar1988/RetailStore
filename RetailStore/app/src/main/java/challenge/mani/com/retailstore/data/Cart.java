package challenge.mani.com.retailstore.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static challenge.mani.com.retailstore.data.Cart.TABLE_NAME;

/**
 * Created by mani on 25/11/17.
 */

@Entity(tableName = TABLE_NAME)
public class Cart {

  public static final String TABLE_NAME = "cart_table";

  @PrimaryKey
  public String productId;

  public int productCount;

  public Cart(String productId) {
    productCount = 1;

    this.productId = productId;
  }
}
