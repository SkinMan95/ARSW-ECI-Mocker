var RestMockerController = (function() {

    /* ================ PRIVATE ================ */

    /* ==== CONSTANTS ==== */
    const SERVER_URL = ''; // TODO

    /* ==== VARIABLES ==== */
    var stompClient = undefined;
    
    /* ================ PUBLIC ================ */
    const TESTING = true;

    var init = function (callback) {
	// TODO
    };

    var registerToServer = function() {
	// TODO
    };

    var disconnectFromServer = function () {
	// TODO
    };

    var subscribeToTopic = function(topic) {
	console.assert(topic instanceof String);
	// TODO
    };

    var publishInTopic = function (topic) {
	console.assert(topic instanceof String);
	// TODO
    };

    return {
	testing: TESTING,
	init: init,
	registerToServer : registerToServer,
	disconnectFromServer : disconnectFromServer,
	publishInTopic : publishInTopic,
	subscribeToTopic : subscribeToTopic
    };
    
})();
