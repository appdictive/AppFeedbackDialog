package dk.appdictive.feedbackdialog.rate_dialog;

public class RateDialogTitle {
    private final String mFirstPartOfTitle;
    private final String mSecondPartOfTitle;

    public RateDialogTitle(String firstPartOfTitle, String secondPartOfTitle) {
        mFirstPartOfTitle = firstPartOfTitle;
        mSecondPartOfTitle = secondPartOfTitle;
    }

    public String getFirstPartOfTitle() {
        return mFirstPartOfTitle;
    }

    public String getSecondPartOfTitle() {
        return mSecondPartOfTitle;
    }
}