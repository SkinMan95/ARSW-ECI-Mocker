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
    
    /* ==== GLOBAL VARIABLES ==== */
    const canvaces = {
    	main: undefined
    };

    var sessionState = {
	polygons: undefined,
	users: undefined
    };

    var myPolygons = undefined;

    var selectedTool = undefined;

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
    	}
    };

    var test = function () {
	testCanvaces();
    };
    
    var initCanvas = function () {
	// main canvas
	var width = $(`#${CANVAS_MAIN_NAME}`).css('width');
	var height = $(`#${CANVAS_MAIN_NAME}`).css('height');

	$(`#${CANVAS_MAIN_NAME}`)
	    .attr('width', width)
	    .attr('height', height);
	
    	canvaces.main  = new fabric.Canvas(CANVAS_MAIN_NAME, {
    	    backgroundColor: CANVAS_BACKGROUND_COLOR,
    	    selectionLineWidth: 2
    	});
    };

    var initTools = function () {
	// TODO agregar cada boton con su funcionalidad
	// $("#toolsSection").html('<h3>Caja de herramientas (Generado con Javascript)</h3>');

	$(".toolBtns").click(function () {
	    $(".toolBtns").css("pointer-events", "");
	    $(this).css("pointer-events", "none");

	    selectedTool = $(this).prop('id');
	    console.info("Selecciona herramienta: " + $(this).prop('id'));
	});

	// By default start with selection tool
	$("#selectionTool").click();
    };

    // TODO mejorar presentacion del error
    var showErrorMessage = function(msg = undefined) { 
	msg = "ERROR" + (msg !== undefined ? msg : "");
	console.error(msg);
	alert(msg);
    };
    
    /* ================ PUBLIC ================ */
    
    const init = function() {
	initCanvas();
	initTools();
	
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
