package dk.appdictive.feedbackdialog.rate_dialog;

import android.content.DialogInterface;

public interface RateDialogStrategy extends DialogInterface.OnDismissListener {
    void onNegativeFeedback();

    void onPositiveFeedback();

    boolean isRatingGood(int seekPosition);
}