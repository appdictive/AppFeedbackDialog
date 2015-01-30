package dk.appdictive.feedbackdialog.rate_dialog.model;

public class Email {
    private final String mToEmailAddr;
    private final String mSubject;
    private String mMessage;

    public Email(String toEmailAddr, String subject, String message) {
        mToEmailAddr = toEmailAddr;
        mSubject = subject;
        mMessage = message;
    }

    public String getToEmailAddr() {
        return mToEmailAddr;
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
