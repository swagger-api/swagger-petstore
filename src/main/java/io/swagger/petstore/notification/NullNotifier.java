package io.swagger.petstore.notification;

public class NullNotifier implements Notifier {

    @Override
    public void notify(Throwable e) {
        //
    }
}
