package dk.appdictive.feedbackdialog.rate_dialog.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import dk.appdictive.feedbackdialog.rate_dialog.model.Email;
import dk.appdictive.rateapp.R;

public class RateDialogHelper {

    public static void openEmailProgramAndStartEmail(Context context, Email email) {
        email.setMessage(email.getMessage() + "\n");
        String intentChooserTitle = context.getString(R.string.rate_email_intent_chooser_title);
        Intent emailIntent = EmailHelper.getEmailIntent(email.getToEmailAddr(), email.getSubject(), email.getMessage(), intentChooserTitle);
        context.startActivity(emailIntent);
    }

    public static void openGooglePlayOnApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}