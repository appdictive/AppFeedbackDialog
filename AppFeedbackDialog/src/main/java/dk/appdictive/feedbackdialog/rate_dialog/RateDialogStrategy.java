package dk.appdictive.feedbackdialog.rate_dialog;

import android.content.DialogInterface;

/**
 * Created by tobalr on 21-01-2015.
 */
public interface RateDialogStrategy extends DialogInterface.OnDismissListener {
    void sendEmailFeedback();

    void startGooglePlayForRating();
}
