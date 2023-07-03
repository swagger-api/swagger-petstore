package io.swagger.petstore.exception;

public class NotFoundException extends javax.ws.rs.NotFoundException {
    private int code;

    public NotFoundException(String msg) {
        super(msg);
        this.code = code;
    }
}
