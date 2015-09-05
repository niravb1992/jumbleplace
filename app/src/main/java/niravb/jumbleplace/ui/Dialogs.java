package niravb.jumbleplace.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import niravb.jumbleplace.R;

public class Dialogs {

    public static void showConfirmDialog(Context context,
                                         String dialogTitle,
                                         String dialogMessage,
                                         DialogInterface.OnClickListener yesButtonListener,
                                         DialogInterface.OnClickListener noButtonListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(dialogMessage).
                setTitle(dialogTitle);

        builder.setPositiveButton(context.getString(R.string.standard_dialog_positive_button_text),
                yesButtonListener);

        builder.setNegativeButton(context.getString(R.string.standard_dialog_negative_button_text),
                noButtonListener);

        builder.create().show();

    }

    public static void showInfoDialog(Context context,
                                      String dialogTitle,
                                      String dialogMessage,
                                      DialogInterface.OnClickListener okButtonListener) {

        AlertDialog.Builder infoDialogBuilder = new AlertDialog.Builder(context);
        infoDialogBuilder
                .setMessage(dialogMessage)
                .setTitle(dialogTitle);

        infoDialogBuilder.setPositiveButton(
                context.getString(R.string.standard_dialog_ok_button_text),
                okButtonListener);

        infoDialogBuilder.create().show();

    }

}
