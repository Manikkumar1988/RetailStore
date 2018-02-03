package challenge.mani.com.retailstore.productdetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.ViewModelFactory;
import challenge.mani.com.retailstore.cartlisting.CartListingViewModel;
import challenge.mani.com.retailstore.data.Product;
import challenge.mani.com.retailstore.data.Cart;
import challenge.mani.com.retailstore.productlisting.ProductListingViewModel;
import challenge.mani.com.retailstore.util.BaseFragment;

public class ProductDetailFragment extends BaseFragment implements View.OnClickListener {

  private CartListingViewModel mCartListViewModel;

  @BindView(R.id.product_image) ImageView productImage;

  @BindView(R.id.product_name) TextView productText;

  @BindView(R.id.product_price) TextView productPrice;

  @BindView(R.id.add_to_cart) Button addToCart;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootFragment = inflater.inflate(R.layout.product_detail_fragment, container, false);

    ButterKnife.bind(this, rootFragment);

    setupView();

    return rootFragment;
  }

  private void setupView() {

    addToCart.setOnClickListener(this);
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    initializeViewModel();

    listenForProductData();

    listenForDataLoadingEvent();

    initiateRequestToProductData();
  }

  private void initiateRequestToProductData() {

    String productId = null;

    if (getArguments() != null && getArguments().containsKey(ProductDetailActivity.PRODUCT_KEY)) {

      productId = getArguments().getString(ProductDetailActivity.PRODUCT_KEY);
    }

    if (!TextUtils.isEmpty(productId)) {

      mProductListingViewModel.loadProductDetail(productId);
    } else {
      //TODO: handle error behaviour
    }
  }

  private void listenForProductData() {

    mProductListingViewModel.getProductDetailLiveData().observe(this, new Observer<Product>() {
      @Override public void onChanged(@Nullable Product product) {

        if (product != null) {

          productImage.setImageResource(R.drawable.dummy);

          productText.setText(product.getName());

          productPrice.setText('$' + String.valueOf(product.getPrice()));

          addToCart.setVisibility(View.VISIBLE);

          addToCart.setTag(product);
        }
      }
    });
  }

  private void initializeViewModel() {
    // Use a Factory to inject dependencies into the ViewModel
    ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());

    mProductListingViewModel =
        ViewModelProviders.of(getActivity(), factory).get(ProductListingViewModel.class);

    mCartListViewModel =
        ViewModelProviders.of(getActivity(), factory).get(CartListingViewModel.class);
  }

  @OnClick(R.id.add_to_cart) public void onAddToCartClick(final View view) {

    new Thread() {
      @Override public void run() { //TODO: Reuse threads or move as Async operation
        super.run();

        Product product = (Product) view.getTag();

        mCartListViewModel.addProductId(new Cart(product.getId()));
      }
    }.start();

    Toast.makeText(getContext(), getString(R.string.add_to_cart_success), Toast.LENGTH_SHORT)
        .show();
  }

  public ProductDetailFragment() {

  }

  public static ProductDetailFragment newInstance() {
    return new ProductDetailFragment();
  }

  @Override public void onClick(View view) {
    onAddToCartClick(view);
  }
}
