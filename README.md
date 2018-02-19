App Feedback Dialog
====================

A simple dialog for user feedback making it possible to redirect the users depending on their rating.

![RateDialog example](https://github.com/appdictive/AppFeedbackDialog/blob/master/RateDialog.gif?raw=tru)

If a user gives a poor rating they will be directed to an E-mail intent to let them help improve the app by writing to the developers.
If instead the user gives a good rating they will be redirected to Google Play to add the rating there.

Usage
====================

To use the RateDialog you first need to add the module to your app dependencies in the apps build.gradle file

```java
dependencies {
    compile 'dk.appdictive.feedbackdialog:AppFeedbackDialog:v0.1.8'
}
```
then add the following to your repositories
```java
repositories {
    maven { url "http://dl.bintray.com/appdictive/maven/" }
}
```

To initiate a RateDialog you need to provide a RateDialogStrategy, a RateDialogTitle and an optional color.

The RateDialogStrategy provides the callbacks used by the RateDialog.
The title is divided into two segments for emphasis as seen in the above image.
Finally an optional color which customizes the dialog background and button.

```java
private final RateDialogStrategy rateDialogStrategy = new RateDialogStrategy() {
    @Override
    public void onNegativeFeedback() {
        Email email = new Email("test@test.com", "subject", "message");
        RateDialogHelper.openEmailProgramAndStartEmail(Context, email);
    }

	@Override
    public void onPositiveFeedback() {
        RateDialogHelper.openGooglePlayOnApp(Context);
    }

	@Override
    public boolean isRatingGood(int seekPosition) {
		return seekPosition > (RateDialog.SEEK_MAX / 2);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Toast.makeText(Context, "Dialog closed", Toast.LENGTH_SHORT).show();
    }
};

private final RateDialogTitle rateDialogTitle = new RateDialogTitle("Title part one", "Title part two");
private final int color = getResources().getColor(android.R.color.holo_blue_bright);
```

Afterwards you can use the RateDialog by creating a new instance and showing it.

```java
new RateDialog(Context, rateDialogTitle, rateDialogStrategy, color).show();
```

Or use the builder for more customizability
```java
final RateDialog rateDialog = new RateDialog.
    RateDialogBuilder(DemoRateBuilderActivity.this).
    rateDialogTitle(rateDialogTitle).
    strategy(rateDialogStrategy).
    backgroundColor(color).
    positiveTextResource(R.string.resource_positive_text).
    negativeTextResource(R.string.resource_negative_text).
    build();

rateDialog.show();
```

In the project is also included a helper class. The RateDialogHelper provides to methods, one for starting an E-mail intent to send feedback via. E-mail and another for opening the app on Google Play.

```java
Email email = new Email("test@test.com", "subject", "message");
RateDialogHelper.openEmailProgramAndStartEmail(Context, email);

RateDialogHelper.openGooglePlayOnApp(Context);
```

Note on Releases
====================

Moving from version 0.0.5 to 0.1.5+ is breaking.

License
====================

MIT License

Copyright (c) 2016 Appdictive ApS

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
