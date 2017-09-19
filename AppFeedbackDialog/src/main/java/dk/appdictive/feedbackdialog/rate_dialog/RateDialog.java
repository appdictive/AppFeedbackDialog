package dk.appdictive.feedbackdialog.rate_dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import dk.appdictive.rateapp.R;

public class RateDialog extends Dialog {

    public static final int ROTATE_ANIMATION_DURATION_MILLIS = 300;
    public static final int SEEK_CENTER = 50;
    public static final int SEEK_MAX = 100;
    public static final int SEEK_AT_START = 51;
    private static final String TAG = "rate dialog";

    protected final RateDialogStrategy mStrategy;
    public Context mContext;
    protected RateDialogTitle mRateDialogTitle;
    private int mBackgroundColor;
    private int positiveText = R.string.go_to_google_play;
    private int negativeText = R.string.go_to_email;

    private ImageView mIPoo;
    private ImageView mIHeart;
    private int mPreviousSeek;
    private TextView mTHeader;
    private TextView mTAppName;
    private TextView mTButton;
    private int mButtonWidth;
    private AnimatorSet mAnimatorSet;

    public RateDialog(Context context) {
        super(context);

        mStrategy = new RateDialogStrategy() {

            @Override
            public void onNegativeFeedback() {

            }

            @Override
            public void onPositiveFeedback() {

            }

            @Override
            public boolean isRatingGood(int seekPosition) {
                return seekPosition > RateDialog.SEEK_CENTER;
            }

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        };

        mContext = context;
        mRateDialogTitle = new RateDialogTitle("Do you love", "Our app name ?");
    }

    public RateDialog(Context context, RateDialogTitle rateDialogTitle, RateDialogStrategy strategy) {
        super(context);
        mRateDialogTitle = rateDialogTitle;
        mStrategy = strategy;
        mContext = context;
        mBackgroundColor = mContext.getResources().getColor(R.color.rate_light);
    }

    public RateDialog(Context context, RateDialogTitle rateDialogTitle, RateDialogStrategy strategy, int backgroundColor) {
        super(context);
        mRateDialogTitle = rateDialogTitle;
        mStrategy = strategy;
        mContext = context;
        mBackgroundColor = backgroundColor;
    }

    public RateDialog(RateDialogBuilder rateDialogBuilder) {
        super(rateDialogBuilder.mContext);
        mContext = rateDialogBuilder.mContext;
        mRateDialogTitle = rateDialogBuilder.mRateDialogTitle;
        mStrategy = rateDialogBuilder.mStrategy;
        mBackgroundColor = rateDialogBuilder.mBackgroundColor;
        positiveText = rateDialogBuilder.positiveText;
        negativeText = rateDialogBuilder.negativeText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rate_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout mBackground = (LinearLayout) findViewById(R.id.rate_background);
        mBackground.setBackgroundColor(mBackgroundColor);

        mTHeader = (TextView) findViewById(R.id.rate_text_header);
        mTAppName = (TextView) findViewById(R.id.rate_text_app_name);
        mTButton = (TextView) findViewById(R.id.rate_text_button);

        setupTitleText();
        setupSeekBar();
        setCorrectTypeFace();
        setupFeedbackButton();
        setupCloseButton();

        if (mStrategy.isRatingGood(SEEK_AT_START)) {
            mTButton.setText(positiveText);
        } else {
            mTButton.setText(negativeText);
        }

        setOnDismissListener(mStrategy);
    }

    private void setupTitleText() {
        mTHeader.setText(mRateDialogTitle.getFirstPartOfTitle());
        mTAppName.setText(mRateDialogTitle.getSecondPartOfTitle());
    }

    private void setupFeedbackButton() {
        ImageView circleButton = (ImageView) findViewById(R.id.rate_circle_button);
        final Drawable drawable = circleButton.getBackground();
        drawable.setColorFilter(mBackgroundColor, PorterDuff.Mode.SRC_ATOP);

        RelativeLayout feedbackButton = (RelativeLayout) findViewById(R.id.rate_button_feedback);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStrategy.isRatingGood(mPreviousSeek)) {
                    startGooglePlayForRating();
                } else {
                    sendEmailFeedback();
                }
            }
        });
    }

    private void sendEmailFeedback() {
        mStrategy.onNegativeFeedback();
    }

    private void startGooglePlayForRating() {
        mStrategy.onPositiveFeedback();
    }

    private void setupCloseButton() {
        ImageView iCloseButton = (ImageView) findViewById(R.id.rate_close);
        iCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateDialog.this.dismiss();
            }
        });
    }

    private void setupSeekBar() {
        SeekBar seekBar = (SeekBar) findViewById(R.id.rate_seekBar);
        seekBar.setProgress(SEEK_AT_START);
        mPreviousSeek = SEEK_AT_START;
        mIPoo = (ImageView) findViewById(R.id.rate_poo);
        mIHeart = (ImageView) findViewById(R.id.rate_heart);

        updateImagesAccordingToSeekBar(seekBar.getProgress());

        seekBar.setOnSeekBarChangeListener(getSeekBarChangeListener());
    }

    private SeekBar.OnSeekBarChangeListener getSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateImagesAccordingToSeekBar(i);
                if (mStrategy.isRatingGood(mPreviousSeek) && !mStrategy.isRatingGood(i)) {
                    String emailText = mContext.getString(negativeText);
                    animateButtonToText(emailText);
                } else if (!mStrategy.isRatingGood(mPreviousSeek) && mStrategy.isRatingGood(i)) {
                    String googlePlayText = mContext.getString(positiveText);
                    animateButtonToText(googlePlayText);
                }
                mPreviousSeek = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
    }

    private void animateButtonToText(String text) {
        if (mAnimatorSet != null) {
            mAnimatorSet.end();
        } else {
            mAnimatorSet = new AnimatorSet();
        }

        float xPos = mTButton.getX();
        if (mButtonWidth == 0) {
            mButtonWidth = mTButton.getWidth();
        }

        mAnimatorSet.play(ObjectAnimator.ofFloat(mTButton, View.X, xPos, mButtonWidth * -1)).with(getButtonTextChangeAnimator(text)).before(ObjectAnimator.ofFloat(mTButton, View.X, mButtonWidth * -1, xPos));
        mAnimatorSet.setInterpolator(new OvershootInterpolator());
        mAnimatorSet.setDuration(ROTATE_ANIMATION_DURATION_MILLIS / 2);
        mAnimatorSet.start();
    }

    private ValueAnimator getButtonTextChangeAnimator(final String newText) {
        ValueAnimator changeTextAnimator = ValueAnimator.ofInt(0, 100);
        changeTextAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer value = (Integer) valueAnimator.getAnimatedValue();
                if (value == 100) {
                    mTButton.setText(newText);
                }
            }
        });
        return changeTextAnimator;
    }

    private void updateImagesAccordingToSeekBar(int seekPosition) {
        float scaleHeart = 0.2f + (0.8f * (seekPosition) / 200f);
        float scalePoo = 0.2f + (0.8f * (100 - seekPosition) / 200f);
        mIHeart.setScaleX(scaleHeart);
        mIHeart.setScaleY(scaleHeart);
        mIPoo.setScaleX(scalePoo);
        mIPoo.setScaleY(scalePoo);
    }

    private void setCorrectTypeFace() {
        Typeface typeFaceCondensedBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        Typeface typeFaceCondensedRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        Typeface typeFaceBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Bold.ttf");

        mTHeader.setTypeface(typeFaceCondensedRegular);
        mTAppName.setTypeface(typeFaceCondensedBold);
        mTButton.setTypeface(typeFaceBold);
    }

    public static class RateDialogBuilder {
        public Context mContext;
        protected RateDialogStrategy mStrategy;
        protected RateDialogTitle mRateDialogTitle;
        private int mBackgroundColor;
        private int positiveText;
        private int negativeText;

        public RateDialogBuilder(Context context) {
            mContext = context;
            mStrategy = new RateDialogStrategy() {

                @Override
                public void onNegativeFeedback() {

                }

                @Override
                public void onPositiveFeedback() {

                }

                @Override
                public boolean isRatingGood(int seekPosition) {
                    return seekPosition > RateDialog.SEEK_CENTER;
                }

                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            };
            mRateDialogTitle = new RateDialogTitle("Do you love", "Our app name ?");
            mBackgroundColor = mContext.getResources().getColor(R.color.rate_light);
            positiveText = R.string.go_to_email;
            negativeText = R.string.go_to_google_play;
        }

        public RateDialogBuilder strategy(RateDialogStrategy strategy) {
            this.mStrategy = strategy;
            return this;
        }

        public RateDialogBuilder rateDialogTitle(RateDialogTitle rateDialogTitle) {
            this.mRateDialogTitle = rateDialogTitle;
            return this;
        }

        public RateDialogBuilder positiveTextResource(int positiveTextResource) {
            this.positiveText = positiveTextResource;
            return this;
        }

        public RateDialogBuilder negativeTextResource(int negativeTextResource) {
            this.negativeText = negativeTextResource;
            return this;
        }

        public RateDialogBuilder backgroundColor(int backgroundColor) {
            this.mBackgroundColor = backgroundColor;
            return this;
        }

        public RateDialog build() {
            return new RateDialog(this);
        }
    }
}