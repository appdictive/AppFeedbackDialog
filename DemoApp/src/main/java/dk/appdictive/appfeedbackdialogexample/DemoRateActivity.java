package dk.appdictive.appfeedbackdialogexample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dk.appdictive.feedbackdialog.rate_dialog.RateDialog;
import dk.appdictive.feedbackdialog.rate_dialog.RateDialogStrategy;
import dk.appdictive.feedbackdialog.rate_dialog.RateDialogTitle;
import dk.appdictive.feedbackdialog.rate_dialog.helpers.RateDialogHelper;
import dk.appdictive.feedbackdialog.rate_dialog.model.Email;

public class DemoRateActivity extends AppCompatActivity {

    private final RateDialogStrategy rateDialogStrategy = new RateDialogStrategy() {
        @Override
        public void onNegativeFeedback() {
            Email email = new Email("test@test.com", "subject", "message");
            RateDialogHelper.openEmailProgramAndStartEmail(DemoRateActivity.this, email);
        }

        @Override
        public void onPositiveFeedback() {
            RateDialogHelper.openGooglePlayOnApp(DemoRateActivity.this);
        }

        @Override
        public boolean isRatingGood(int seekPosition) {
            return seekPosition > (RateDialog.SEEK_MAX / 2);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            Toast.makeText(DemoRateActivity.this, "Dialog closed", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openDialog = (Button) findViewById(R.id.open_dialog);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RateDialogTitle rateDialogTitle = new RateDialogTitle("Do you love", "RateDialog?");
                final int color = getResources().getColor(android.R.color.holo_orange_dark);
                new RateDialog(DemoRateActivity.this, rateDialogTitle, rateDialogStrategy, color).show();
            }
        });
    }
}