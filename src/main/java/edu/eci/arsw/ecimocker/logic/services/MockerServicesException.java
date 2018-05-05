package edu.eci.arsw.ecimocker.logic.services;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public class MockerServicesException extends Exception {

    public MockerServicesException() {
    }

    public MockerServicesException(String message) {
        super(message);
    }

    public MockerServicesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MockerServicesException(Throwable cause) {
        super(cause);
    }
}
