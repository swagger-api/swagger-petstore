package io.swagger.petstore.exception;

public class InvalidIdException extends javax.ws.rs.BadRequestException {
    private int code;

    public InvalidIdException(String msg) {
        super(msg);
    }
}
