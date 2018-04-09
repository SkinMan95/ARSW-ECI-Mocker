var MockerController = (function () {

    /* ================ PRIVATE ================ */
    const assert = function(condition, msg = null) {
	if (!condition) {
	    var assertMsg = 'Assertion Error';
	    if (msg != null) {
		assertMsg += ': ' + msg;
	    }
	    throw new Error(assertMsg);
	}
    };
    
    var mainCanvas = undefined;
    const TESTING = true;

    var test = function() {
	if (TESTING) {
	    assert(mainCanvas !== undefined, 'canvas is undefined');
	    var rect = new fabric.Rect({
		left: 100,
		top: 100,
		fill: 'red',
		width: 20,
		height: 20
	    });

	    mainCanvas.add(rect);
	}
    };

    /* ================ PUBLIC ================ */

    const CANVAS_NAME = "drawingCanvas";
    const CANVAS_BACKGROUND_COLOR = 'rgb(255, 255, 255)';
    const CANVAS_WIDTH = 800;
    const CANVAS_HEIGHT = 800;
    
    var init = function() {
	$('#' + CANVAS_NAME)
	    .attr('width', CANVAS_WIDTH)
	    .attr('height', CANVAS_HEIGHT);
	
	mainCanvas  = new fabric.Canvas(CANVAS_NAME, {
	    backgroundColor: CANVAS_BACKGROUND_COLOR,
	    selectionColor: 'blue',
	    selectionLineWidth: 2
	});

	test();
    };

    var connectToServer = function() {
	// TODO
    };

    var addObject = function () {
	// TODO
    };
    
    return {
	init: init,
	connectToServer: connectToServer,
	addObject : addObject
    };

})();
