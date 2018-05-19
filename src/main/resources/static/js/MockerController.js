var MockerController = (function () {
	    
    /*const objectModel = { // NO USAR ESTE OBJECTO
	objId: 0, // concateniacion del numero del usuario que
	// lo creo con el numero de objetos que ha creado
	selected: false,
	userId: undefined,
	type: "rect", // new fabric.Rect({...attributes...})
	attributes: { // DINAMICO: VARIA SEGUN EL 'TYPE'
	    pos: {x, y},
	    rot: 0,
	    scale: 1.0,
	    color: "red",
	    width: 15,
	    height: 20
	}
    };*/
    
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

    class CObject {
	constructor(id) {
	    this.id = id;
	    this.selected = true; // por defecto esta seleccionado al crearse
	    this.userId = 0; // TODO cambiar por el id del usuario
	    this.type = "rect"; // por defecto
	    this.attributes = {};
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
            console.log(rect);
    	    canvaces.main.add(rect);
            console.log(rect);
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
    	canvaces.main.selection = false; //Selección multiple lo desactiva
    	
    	var clickHandler = function(e) {
    		console.log("captura evento");
    		console.log(e.target);
    	};

    	canvaces.main.on({
    		'mouse:down' : clickHandler
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

	//Selected Style
	$('.toolBtns').on('click', function(){
            $('.toolBtns').removeClass('btnSelected');
            $(this).addClass('btnSelected');
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
        StateSessionControllerModule.init();
		initCanvas();
		initTools();
		ActionTools.init();

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

		//test();
    };

    const getCanvas = function() {
    	//console.log("Pasa!");
    	return canvaces.main;
    };

    const getSelectedTool = function() {
    	return selectedTool;
    };

    const setSelectedTool = function(nt) {
    	selectedTool = nt;
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
	selectObject : selectObject,
	getCanvas : getCanvas,
	getSelectedTool : getSelectedTool
    };
})();

console.assert = function(cond, text){ // ensure to stop execution
    if( cond ) return;
    if( console.assert.useDebugger ) debugger;
    throw new Error(text || "Assertion failed!");
};
