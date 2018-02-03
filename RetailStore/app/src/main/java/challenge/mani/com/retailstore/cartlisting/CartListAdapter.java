package challenge.mani.com.retailstore.cartlisting;/**
 * Created by mani on 25/11/17.
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

public class CartListAdapter extends RecyclerView.Adapter<CartListViewHolder> {

  private Context mContext;

  private List<Product> productList;

  private View.OnClickListener onItemClickListener, onDeleteItemListener;

  public CartListAdapter(Context mContext, List<Product> productList,
      View.OnClickListener onItemClickListener, View.OnClickListener onDeleteItemListener) {

    this.mContext = mContext;

    this.productList = productList;

    this.onItemClickListener = onItemClickListener;

    this.onDeleteItemListener = onDeleteItemListener;
  }

  @Override public CartListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    Context context = parent.getContext();

    LayoutInflater inflater = LayoutInflater.from(context);

    View view = inflater.inflate(R.layout.cart_listing_item, parent, false);

    ButterKnife.bind(this, view);

    CartListViewHolder cartListViewHolder = new CartListViewHolder(view);

    return cartListViewHolder;
  }

  @Override public void onBindViewHolder(CartListViewHolder holder, int position) {
    Product item = productList.get(position);

    holder.bind(item, onItemClickListener, onDeleteItemListener);
  }

  @Override public int getItemCount() {
    return productList.size();
  }


  void setItems(Product products) {

    this.productList.add(products);

    notifyDataSetChanged();
  }

  public void clearItems() {

    this.productList.clear();

    notifyDataSetChanged();
  }
}
