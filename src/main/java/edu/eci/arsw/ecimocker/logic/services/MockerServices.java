package edu.eci.arsw.ecimocker.logic.services;

import edu.eci.arsw.ecimocker.entities.CanvasObject;
import edu.eci.arsw.ecimocker.entities.Session;
import edu.eci.arsw.ecimocker.entities.User;
import java.util.List;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public interface MockerServices {

    /**
     * Agrega una accion para realizar en caso de ocurrir un evento
     * @param observer nombre del observado
     * @param call accion a llamar en caso del evento
     * @throws MockerServicesException si no existe el observador
     */
    void addAction(String observer, Callback call) throws MockerServicesException;
    
    /**
     * Agrega una nueva sesion
     *
     * @param sessionName nombre de la sesion
     * @return numero de la sesion asignada
     * @throws MockerServicesException una sesion con el mismo nombre ya existe
     */
    int addNewSession(String sessionName) throws MockerServicesException;

    /**
     * Obtiene lista de sesiones activas
     *
     * @return lista de sesiones actualmente activas
     */
    List<Session> getActiveSessions();

    /**
     * Elimina una sesion
     *
     * @param sessionId numero de la sesion
     * @throws MockerServicesException existen usuarios actualmente registrados
     * en la sesion (no debe tener ninguno)
     */
    void removeSession(int sessionId) throws MockerServicesException;

    /**
     * Registra un nuevo usuario al servicio
     *
     * @param newUser nuevo usuario
     * @throws MockerServicesException el usuario ya existe o existe un usuario
     * con el mismo color
     */
    void registerUser(User newUser) throws MockerServicesException;

    /**
     * Obtiene lista de usuarios dentro de una sesion
     *
     * @param session numero de la sesion
     * @return lista de los usuario en la sesion
     * @throws MockerServicesException la sesion no existe
     */
    List<User> getUsersInSession(int session) throws MockerServicesException;

    /**
     * Obtiene el token para que un usuario se conecte al servidor, si el
     * usuario ya tenia un token antes, el anterior se revoca y se asigna el
     * nuevo
     *
     * @param session numero de sesion
     * @param user usuario que le correspondera el token
     * @return token para conectarse
     * @throws MockerServicesException no existe la sesion O el usuario no esta
     * registrado
     */
    String getNewToken(int session, User user) throws MockerServicesException;

    /**
     * Verifica si un token es valido para una sesion (si pertenece a la sesion)
     *
     * @param session numero de la sesion
     * @param token token del usuario
     * @return pertenece a la sesion
     * @throws MockerServicesException la sesion no existe o el token no existe
     */
    boolean isValidToken(int session, String token) throws MockerServicesException;

    /**
     * Desasocia un usuario al desconectarse
     *
     * @param token del usuario
     * @throws MockerServicesException el token no esta registrado en ninguna
     * sesion
     */
    void dissociateUserFromSession(String token) throws MockerServicesException;

    /**
     * Obtiene todos los objetos dentro de una sesion
     *
     * @param session numero de la sesion
     * @return lista de los objetos presentes en la sesion
     * @throws MockerServicesException la sesion no existe
     */
    List<CanvasObject> getObjectsFromSession(int session) throws MockerServicesException;

    /**
     * Obtiene objeto especifico de una sesion por medio del ID del objeto
     *
     * @param session numero de la sesion
     * @param objId ID del objeto
     * @return objeto pedido
     * @throws MockerServicesException la sesion no existe O el objeto no existe
     * dentro de esa sesion
     */
    CanvasObject getObject(int session, int objId) throws MockerServicesException;

    /**
     * Agrega nuevo objeto a la sesion especificada
     *
     * @param session numero de la sesion
     * @param newObj objeto a agregar
     * @param token token del usuario que lo intenta agregar
     * @throws MockerServicesException existe un objeto con el mismo ID del que
     * se intenta agrega O la sesion no existe O el token es invalido
     */
    void addObject(int session, CanvasObject newObj, String token) throws MockerServicesException;

    /**
     * Elimina un objeto de una sesion
     *
     * @param session numero de la sesion
     * @param objId id del objeto
     * @param token token de quien intenta remover el objeto
     * @throws MockerServicesException el objeto no existe en la sesion O la
     * sesion no existe O el token no es valido O el objeto se encuentra
     * seleccinado por otro usuario
     */
    void removeObject(int session, int objId, String token) throws MockerServicesException;

    /**
     * Actualiza un objeto dentro de una sesion
     *
     * @param session numero de la sesion
     * @param updObj objeto actualizado
     * @param token token de quien intenta actualizar
     * @throws MockerServicesException no existe objeto en la sesion que
     * corresponda al objeto O el token es invalido O no existe la sesion O el
     * objeto se encuentra seleccionado por otro usuario
     */
    void updateObject(int session, CanvasObject updObj, String token) throws MockerServicesException;
}
