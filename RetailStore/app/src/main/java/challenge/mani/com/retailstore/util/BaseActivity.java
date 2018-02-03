package challenge.mani.com.retailstore.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import challenge.mani.com.retailstore.R;
import challenge.mani.com.retailstore.cartlisting.CartListActivity;

/**
 * Created by mani on 25/11/17.
 */

public class BaseActivity extends AppCompatActivity {


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    MenuInflater inflater = getMenuInflater();

    inflater.inflate(R.menu.menu_item, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.action_cart:

        Intent cartActivityIntent = new Intent(getApplicationContext(), CartListActivity.class);

        cartActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        startActivity(cartActivityIntent);

        break;
    }
    return true;
  }
}
