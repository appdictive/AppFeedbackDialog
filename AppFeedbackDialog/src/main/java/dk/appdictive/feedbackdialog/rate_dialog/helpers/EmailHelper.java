package dk.appdictive.feedbackdialog.rate_dialog.helpers;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class EmailHelper {

    public static Intent getEmailIntent(String toEmailAdr, String subject, String message, String intentChooserTitle) {
        return getEmailIntent(toEmailAdr, subject, message, null, intentChooserTitle);
    }

    public static Intent getEmailIntent(String toEmailAdr, String subject, String message, File attachmentFile, String intentChooserTitle) {
        String uriText =
                "mailto:" + toEmailAdr +
                        "?subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(message);
        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        if (attachmentFile != null) sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(attachmentFile));
        return Intent.createChooser(sendIntent, intentChooserTitle);
    }
}