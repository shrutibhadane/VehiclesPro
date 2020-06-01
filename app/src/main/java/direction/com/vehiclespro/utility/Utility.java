package direction.com.vehiclespro.utility;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utility {
  public static void errorSnackBar(
      View viewById, String message) {
    Snackbar mySnackbar = Snackbar.make(viewById, message, Snackbar.LENGTH_SHORT);
    mySnackbar.setTextColor(Color.RED);
    mySnackbar.show();
  }

}
