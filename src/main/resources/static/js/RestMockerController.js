var RestMockerController = (function () {

    /* ================ PRIVATE ================ */

    /* ==== CONSTANTS ==== */
    const SERVER_URL = 'http://localhost:8080'; // TODO

    /* ==== VARIABLES ==== */
    var stompClient = undefined;
    var stompIsConnected = undefined;
    var subscribedTopics = undefined;

    /* ================ PUBLIC ================ */
    const TESTING = true;

    var init = function (callback) {
        // TODO
        subscribedTopics = [];
        stompIsConnected = false;
	registerToServer(callback);
    };

    var registerToServer = function (callback) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log(`Connected: ${frame}`);
            stompIsConnected = true;
	    callback.onSuccess();
        });

        // TODO: axios realiza registro en el servidor
    };

    var disconnectFromServer = function () {
        // TODO: axios informa al servidor para desconectarse

        console.assert(stompClient !== undefined);
        stompClient.disconnect();
        console.log('Disconnected');
    };

    var subscribeToTopic = function (topic, topicCallback) {
        console.assert(typeof topic === "string");
	console.log('subscribing to topic:', topic);

        console.assert(stompClient !== undefined);
        console.assert(stompIsConnected);

        stompClient.subscribe(topic, function (eventbody) {
            var payload = JSON.parse(eventbody.body);
            topicCallback(payload);
        });

        subscribedTopics.push(topic);
    };

    var publishInTopic = function (topic, payload) {
        console.assert(topic instanceof String);
        console.assert(subscribedTopics.includes(topic));
        console.assert(stompIsConnected);

        if (subscribedTopics.includes(topic) && stompIsConnected) {
            stompClient.send(topic, {}, JSON.stringify(payload));
        }
    };

    var getConnectedUsers = function (session, callback) {
        axios.get(SERVER_URL + "/mocker/users/" + session)
            .then(callback.onSuccess)
            .catch(callback.onFailed);
    };

    var dissociateUser = function (token, callback) {
        axios.delete(SERVER_URL + "/mocker/users/" + token)
            .then(callback.onSuccess)
            .catch(callback.onFailed);
    };
    
    var addObject = function (session, token, obj, callback) {
        axios.post(SERVER_URL + "/mocker/sessions/" + session + "/newobject?token=" + token, obj)
            .then(callback.onSuccess)
            .catch(callback.onFailed);
    };

    return {
        testing: TESTING,
        init: init,
        registerToServer: registerToServer,
        disconnectFromServer: disconnectFromServer,
        publishInTopic: publishInTopic,
        subscribeToTopic: subscribeToTopic,
        getConnectedUsers: getConnectedUsers,
        addObject : addObject,
        dissociateUser: dissociateUser
    };

})();
