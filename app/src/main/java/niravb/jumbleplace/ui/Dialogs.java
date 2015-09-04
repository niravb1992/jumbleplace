package niravb.jumbleplace.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Dialogs {

    public static void showConfirmDialog(Context context,
                                         String dialogTitle,
                                         String dialogMessage,
                                         String yesButtonText,
                                         DialogInterface.OnClickListener yesButtonListener,
                                         String noButtonText,
                                         DialogInterface.OnClickListener noButtonListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(dialogMessage).
                setTitle(dialogTitle);

        builder.setPositiveButton(yesButtonText, yesButtonListener);

        builder.setNegativeButton(noButtonText, noButtonListener);

        builder.create().show();

    }

}
