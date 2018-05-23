var LoginRestController = (function(){
    
    const DEFAULT_ADDRESS = ''; //'http://localhost:8080';
    
    var newSession = function(sessionName, callback){
        console.log(sessionName);
        axios.post(DEFAULT_ADDRESS + "/mocker/sessions/newsession", sessionName, {headers: {"Content-Type": "text/plain"}})
                .then(callback.onSuccess)
                .catch(callback.onFailed);
        
    };
    
    var getSessions = function(callback){
        axios.get(DEFAULT_ADDRESS + "/mocker/sessions")
                .then(callback.onSuccess)
                .catch(callback.onFailed);
    };
    
    var newUser = function(user, callback){
        axios.post(DEFAULT_ADDRESS + "/mocker/users/newuser", user)
                .then(callback.onSuccess)
                .catch(callback.onFailed);
    };
    
    var tokenUser = function(sessionID, user, callback){
        console.log("Usuario:" , user);
        console.log("Sesion:" , sessionID);
        axios.post(DEFAULT_ADDRESS + "/mocker/tokens/" + sessionID ,user)
                .then(callback.onSuccess)
                .catch(callback.onFailed);
    };
    
    return {
        newSession: newSession,
        getSessions: getSessions,
        newUser: newUser,
        tokenUser: tokenUser
    };
    
})();

window.onload = function () {
    LoginControllerModule.init();
};
