package io.swagger.petstore.notification;

import com.bugsnag.Bugsnag;

public class BugSnagNotifier implements Notifier {

    protected  Bugsnag bugsnag;

    public void init() {
        String bugsnagApiKey = System.getenv("BUGSNAG_API_KEY");
        if (bugsnagApiKey != null) {
            bugsnag = new Bugsnag(bugsnagApiKey);
        } else {
            System.err.println("BUGSNAG_API_KEY environment variable is not set");
        }
    }

    @Override
    public void notify(Throwable e) {
        if (bugsnag != null) {
            bugsnag.notify(e);
        }
    }
}
