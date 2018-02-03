package challenge.mani.com.retailstore.productlisting;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.ViewModelFactory;
import challenge.mani.com.retailstore.cartlisting.CategoryListAdapter;
import challenge.mani.com.retailstore.data.Category;
import challenge.mani.com.retailstore.data.Product;
import challenge.mani.com.retailstore.util.BaseFragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductListFragment extends BaseFragment {

  private ProductListAdapter mProductListAdapter;

  @BindView(R.id.product_listing_recycler_view) RecyclerView mProductListRecyclerView;

  @BindView(R.id.category_spinner) Spinner mCategorySpinner;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.product_list_fragment, container, false);

    ButterKnife.bind(this, rootView);

    setupRecyclerView();

    setupSpinner();

    return rootView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    initializeViewModel();

    listenForProducts();

    listenForDataLoadingEvent();
  }

  private void initializeViewModel() {
    // Use a Factory to inject dependencies into the ViewModel
    ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());

    mProductListingViewModel = ViewModelProviders.of(getActivity(), factory).get(ProductListingViewModel.class);
  }

  private void listenForProducts() {
    mProductListingViewModel.getProductsListLiveData().observe(this, new Observer<List<Product>>() {
      @Override public void onChanged(@Nullable List<Product> products) {

        if (products != null && !products.isEmpty()) {

          mProductListAdapter.addProducts(products);
        }
      }
    });
  }

  private void setupRecyclerView() {
    LinearLayoutManager
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

    mProductListRecyclerView.setLayoutManager(layoutManager);

    mProductListAdapter = new ProductListAdapter(getContext(), new ArrayList<Product>(), itemClickListener);

    mProductListRecyclerView.setAdapter(mProductListAdapter);

    final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        mProductListRecyclerView.getContext(),
        layoutManager.getOrientation());

    mProductListRecyclerView.addItemDecoration(dividerItemDecoration);
  }

  private void setupSpinner() {

    ArrayAdapter<CharSequence> adapter = new CategoryListAdapter(getContext(), android.R.layout.simple_spinner_item,
        android.R.id.text1, Arrays.asList(Category.values()));

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    mCategorySpinner.setAdapter(adapter);

    mCategorySpinner.setOnItemSelectedListener(categorySelectedListener);
  }

  private View.OnClickListener itemClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      Product product = (Product) view.getTag();

      startProductDetailActivity(product);
    }
  };

  private AdapterView.OnItemSelectedListener categorySelectedListener = new AdapterView.OnItemSelectedListener() {
    @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

      mProductListingViewModel.loadProducts(Category.values()[i]);
    }

    @Override public void onNothingSelected(AdapterView<?> adapterView) {

    }
  };

  public ProductListFragment() {
    // Requires empty public constructor
  }

  public static ProductListFragment newInstance() {
    return new ProductListFragment();
  }
}
