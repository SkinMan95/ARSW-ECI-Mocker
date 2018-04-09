var MockerController = (function () {

    /* ================ PRIVATE ================ */

    /* ==== CLASSES ==== */
    class Point {
	constructor(x, y) {
	    this.x = x;
	    this.y = y;
	}
    }
    
    class Polygon {
	constructor(id, points) {
	    this.id = id;
	    this.points = points;
	}
    }

    /* ==== CONSTANTS ==== */
    const TESTING = RestMockerController.testing;
    const CANVAS_BACKGROUND_COLOR = 'rgb(255, 255, 255)';
    const CANVAS_MAIN_NAME = "drawingCanvas";
    
    const CANVAS_TOOLS_NAME = "toolsCanvas";

    const CANVAS_USERS_NAME = "usersCanvas";
    
    /* ==== GLOBAL VARIABLES ==== */
    const canvaces = {
    	main: undefined,
	tools: undefined,
	users: undefined
    };

    var sessionState = {
	polygons: undefined,
	users: undefined
    };

    var myPolygons = undefined;

    /* ==== FUNCTIONS ==== */
    
    /* Function for testing only !!! */
    var testCanvaces = function() {
    	if (TESTING) {
    	    console.assert(canvaces.main !== undefined, 'canvas is undefined');
    
    	    var rect = new fabric.Rect({
    		left: 100,
    		top: 100,
    		fill: 'red',
    		width: 20,
    		height: 20
    	    });
    
    	    canvaces.main.add(rect);

	    // ++++++++++++++++
	    var circle = new fabric.Circle({
		radius: 20, fill: 'green'
	    });

	    canvaces.tools.add(circle);

	    // ++++++++++++++++
	    var circle2 = new fabric.Circle({
		radius: 20, fill: 'blue'
	    });
	    canvaces.users.add(circle2);
    	}
    };

    var test = function () {
	testCanvaces();
    };
    
    var initCanvaces = function () {
	// main canvas
    	canvaces.main  = new fabric.Canvas(CANVAS_MAIN_NAME, {
    	    backgroundColor: CANVAS_BACKGROUND_COLOR,
    	    selectionLineWidth: 2
    	});

	// tools canvas
	canvaces.tools = new fabric.Canvas(CANVAS_TOOLS_NAME, {
	    backgroundColor: CANVAS_BACKGROUND_COLOR,
    	    selectionLineWidth: 2
	});

	// users canvas
	canvaces.users = new fabric.StaticCanvas(CANVAS_USERS_NAME, { // non-interactive
	    backgroundColor: CANVAS_BACKGROUND_COLOR,
    	    selectionLineWidth: 2
	});
    };

    var showErrorMessage = function(msg = undefined) {
	msg = "ERROR" + (msg !== undefined ? msg : "");
	alert(msg);
    };
    
    /* ================ PUBLIC ================ */
    
    const init = function() {
	initCanvaces();
	console.assert(RestMockerController !== undefined);
	RestMockerController.init({
	    onSuccess: function () {
		connectToServer();
	    },
	    onFailed: function () {
		showErrorMessage("Rest controller intialization failed");
	    }
	});

	myPolygons = [];

	test();
    };

    const connectToServer = function() {
	RestMockerController.registerToServer();
    };

    const addObject = function (obj) {
	console.assert(obj instanceof Polygon);
	// TODO
    };

    const removeObject = function (objId) {
	console.assert(objId instanceof Number);
	// TODO
    };

    const selectObject = function (objId) {
	console.assert(objId instanceof Number);
	// TODO
    };
    
    return {
	init: init,
	connectToServer: connectToServer,
	addObject : addObject,
	removeObject : removeObject,
	selectObject : selectObject
    };
})();

console.assert = function(cond, text){ // ensure to stop execution
    if( cond ) return;
    if( console.assert.useDebugger ) debugger;
    throw new Error(text || "Assertion failed!");
};
