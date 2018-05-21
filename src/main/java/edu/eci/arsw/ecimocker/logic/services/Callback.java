package edu.eci.arsw.ecimocker.logic.services;

import java.util.Map;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public interface Callback {
    public void performAction(Map<String, String> attributes);
}
