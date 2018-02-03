package challenge.mani.com.retailstore.cartlisting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;

public class CategoryListAdapter extends ArrayAdapter {

  public CategoryListAdapter(Context context, int resource, int textViewResourceId, List objects) {
    super(context, resource, textViewResourceId, objects);
  }

  @Override
  public int getCount() {
    return super.getCount();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return super.getView(position, convertView, parent);
  }
}