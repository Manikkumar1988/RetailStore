package challenge.mani.com.retailstore.cartlisting;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.ViewModelFactory;
import challenge.mani.com.retailstore.data.Product;
import challenge.mani.com.retailstore.data.Cart;
import challenge.mani.com.retailstore.productlisting.ProductListingViewModel;
import challenge.mani.com.retailstore.util.BaseFragment;
import java.util.ArrayList;
import java.util.List;

public class CartListFragment extends BaseFragment {

  private CartListingViewModel mCartListingViewModel;

  private CartListAdapter mCartListAdapter;

  private float totalPriceValue = 0.0f;

  @BindView(R.id.cart_listing_recycler_view) RecyclerView cartListRecyclerView;

  @BindView(R.id.total_price_value) TextView totalPriceValueText;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.cart_list_fragment, container, false);

    ButterKnife.bind(this, rootView);

    setupRecyclerView(rootView);

    return rootView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    initializeViewModel();

    listenForProducts();

    listenForDataLoadingEvent();

    listenForProductIdFromLocalSource();
  }

  private void listenForProductIdFromLocalSource() {
    mCartListingViewModel.loadProductIds().observe(this, new Observer<List<Cart>>() {
      @Override public void onChanged(@Nullable List<Cart> cartEntities) {

        if (cartEntities != null && !cartEntities.isEmpty()) {
          for (Cart cart : cartEntities) {

            mProductListingViewModel.loadProductDetail(cart.productId);
          }
        } else {

          resetTotalPriceValue();

          mCartListAdapter.clearItems();

          mDataLoadingProgressBar.setVisibility(View.INVISIBLE);
        }

      }
    });
  }

  private void resetTotalPriceValue() {

    totalPriceValue = 0.0f;

    setTotalPriceValue();
  }

  private void listenForProducts() {
    mProductListingViewModel.getProductDetailLiveData().observe(this, new Observer<Product>() {
      @Override public void onChanged(@Nullable Product product) {
        if (product!=null) {

          mCartListAdapter.setItems(product);

          totalPriceValue += product.getPrice();

          setTotalPriceValue();
        }
      }
    });
  }

  private void setTotalPriceValue() {

    totalPriceValueText.setText(String.valueOf(totalPriceValue));
  }

  private void initializeViewModel() {
    // Use a Factory to inject dependencies into the ViewModel
    ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());

    mProductListingViewModel = ViewModelProviders.of(getActivity(), factory).get(ProductListingViewModel.class);

    mCartListingViewModel = ViewModelProviders.of(getActivity(),factory).get(CartListingViewModel.class);
  }


  private void setupRecyclerView(View v) {
    LinearLayoutManager
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

    cartListRecyclerView.setLayoutManager(layoutManager);

    mCartListAdapter = new CartListAdapter(getContext(), new ArrayList<Product>(),
        itemClickListener, deleteCartItemListener);

    cartListRecyclerView.setAdapter(mCartListAdapter);

    final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        cartListRecyclerView.getContext(),
        layoutManager.getOrientation());

    cartListRecyclerView.addItemDecoration(dividerItemDecoration);
  }

  private View.OnClickListener deleteCartItemListener = new View.OnClickListener() {
    @Override public void onClick(final View view) {

      mCartListAdapter.clearItems();

      new Thread() {
        @Override public void run() { //TODO: Reuse threads or move as Async operation
          super.run();

          Product product = (Product) view.getTag();

          mCartListingViewModel.deleteProductId(new Cart(product.getId()));


        }
      }.start();

      resetTotalPriceValue();
    }
  };

  private View.OnClickListener itemClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {

      Product product = (Product) view.getTag();

      startProductDetailActivity(product);
    }
  };

  public CartListFragment() {
    // Requires empty public constructor
  }

  public static CartListFragment newInstance() {
    return new CartListFragment();
  }
}
