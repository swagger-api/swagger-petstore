package io.swagger.petstore.notification;

public interface Notifier {

    void notify(Throwable e);
}
