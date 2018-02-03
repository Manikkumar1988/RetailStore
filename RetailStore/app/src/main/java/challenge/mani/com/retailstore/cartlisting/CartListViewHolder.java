package challenge.mani.com.retailstore.cartlisting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.data.Product;

public class CartListViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.product_name) TextView productName;

  @BindView(R.id.product_price) TextView productPrice;

  @BindView(R.id.product_image) ImageView productImage;

  @BindView(R.id.delete_cart_item) View deleteFromCartButton;

  public CartListViewHolder(View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }

  public void bind(final Product model, final View.OnClickListener listener, final View.OnClickListener deleteItemListener) {

    itemView.setTag(model);

    deleteFromCartButton.setTag(model);

    productName.setText(model.getName());

    productPrice.setText('$'+String.valueOf(model.getPrice()));

    productImage.setImageResource(R.drawable.dummy);

    deleteFromCartButton.setOnClickListener(deleteItemListener);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onClick(itemView);
      }
    });
  }
}