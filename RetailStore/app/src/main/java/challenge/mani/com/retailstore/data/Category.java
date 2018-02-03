package challenge.mani.com.retailstore.data;

/**
 * Created by mani on 26/11/17.
 */

public enum Category {

  ELECTRONICS ("Electronics", 0), FURNITURE ("Furniture",1);

  private final String key;

  private final Integer value;

  Category(String key, Integer value) {

    this.key = key;

    this.value = value;
  }
}
