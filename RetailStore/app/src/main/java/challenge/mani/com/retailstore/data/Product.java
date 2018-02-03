package challenge.mani.com.retailstore.data;

import android.os.Parcelable;
import java.io.Serializable;

/**
 * Created by mani on 23/11/17.
 */

public abstract class Product implements Serializable{

  private String id, name, imageUrl,categoryId;
  private float price;

  public Product(String name, String categoryId, String imageUrl, String id, float price) {
    this.name = name;
    this.categoryId = categoryId;
    this.imageUrl = imageUrl;
    this.id = id;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }
}
