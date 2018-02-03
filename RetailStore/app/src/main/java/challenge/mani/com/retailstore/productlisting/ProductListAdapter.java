package challenge.mani.com.retailstore.productlisting;/**
 * Created by mani on 24/11/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.data.Product;
import java.util.List;

import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> {

  private static final String TAG = ProductListAdapter.class.getSimpleName();

  private Context context;

  private List<Product> productList;

  private View.OnClickListener onItemClickListener;

  public ProductListAdapter(Context context, List<Product> productList,
      View.OnClickListener onItemClickListener) {

    this.context = context;

    this.productList = productList;

    this.onItemClickListener = onItemClickListener;
  }

  @Override public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    Context context = parent.getContext();

    LayoutInflater inflater = LayoutInflater.from(context);

    View view = inflater.inflate(R.layout.product_listing_item, parent, false);

    ButterKnife.bind(this, view);

    ProductViewHolder productViewHolder = new ProductViewHolder(view);

    return productViewHolder;
  }

  @Override public void onBindViewHolder(ProductViewHolder holder, int position) {

    Product item = productList.get(position);

    holder.bind(item, onItemClickListener);
  }

  @Override public int getItemCount() {
    return productList.size();
  }

  void addProducts(List<Product> products) {

    this.productList = products;

    notifyDataSetChanged();
  }
}
