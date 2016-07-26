package dk.appdictive.feedbackdialog.rate_dialog.model;

public class Email {
    private final String mToEmailAddress;
    private final String mSubject;
    private String mMessage;

    public Email(String toEmailAddress, String subject, String message) {
        mToEmailAddress = toEmailAddress;
        mSubject = subject;
        mMessage = message;
    }

    public String getToEmailAddr() {
        return mToEmailAddress;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}