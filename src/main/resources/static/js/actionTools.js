var ActionTools = (function () {

	var init = function () {
		createObjTool();
		selectionTool();
		pencilTool();
		zoomTool();
		textTool();
		garbageTool();
		imageTool();
	};

	

	const textTool = function() {
		$("#textTool").click(function() {
			lockOptions(true);
			var canvas = MockerController.getCanvas();
			var text = new fabric.Textbox('Texto', {
				left: 100,
				top: 150,
				fill: '#000000',
				strokeWidth: 2,
				stroke: "#000000",
			});
			canvas.add(text);
			$("#selectionTool").click();
		});
	};

	const garbageTool = function() {
		$("#garbageTool").click(function() {
			lockOptions(true);
			var canvas = MockerController.getCanvas();
			console.log("Borra elementos seleccionados");
			canvas.remove(canvas.getActiveObject());
			$("#selectionTool").click();
		});
	};

	const zoomTool = function() {
		$("#zoomTool").click(function() {
			lockOptions(true);
			var canvas = MockerController.getCanvas();
			canvas.forEachObject(function(o){
				o.lockScalingX = o.lockScalingY = false;
			});
			//canvas.deactivateAll().renderAll();
		});
	};

	const selectionTool = function () {
		$("#selectionTool").click(function() {
			lockOptions(false);
			var canvas = MockerController.getCanvas();
			canvas.isDrawingMode = false;
			canvas.forEachObject(function(o){
				o.lockScalingX = o.lockScalingY = true;
			});
		});
	};

	const lockOptions = function (val) {
		console.log("Boquea rotacion y movimiento");
		var canvas = MockerController.getCanvas();
		canvas.isDrawingMode = !val;
		canvas.forEachObject(function(o){ 
			o.lockScalingX = o.lockScalingY = val;
			o.lockRotation = val;
			o.lockMovementY = o.lockMovementX = val;
		});
	};
 
	const createObjTool = function () {
		$('#objectTool').click(function() {
			// Get the modal
			var modal = document.getElementById('myModal');

			// Get the button that opens the modal
			var btn = document.getElementById("objectTool");

			// Get the <span> element that closes the modal
			var span = document.getElementsByClassName("close")[0];

			// When the user clicks the button, open the modal 
			btn.onclick = function() {
			    modal.style.display = "block";
			}

			// When the user clicks on <span> (x), close the modal
			span.onclick = function() {
			    modal.style.display = "none";
			}

			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
			    if (event.target == modal) {
			        modal.style.display = "none";
			    }
			}
		});

		$('#square').click(function () {
			var rect = new fabric.Rect({
    	    	left: 100,
    	    	top: 100,
    	    	fill: document.getElementById("colorPicker").value,
    	    	width: 20,
    	    	height: 20
    	    });

			var canvas = MockerController.getCanvas();
			console.log("Llama la funcion del controler");
            console.log(rect);
            rect.lockScalingX = rect.lockScalingY = true;
    	    canvas.add(rect);
    	    lockOptions(true);
            console.log(rect);
            document.getElementById('myModal').style.display = "none";
            //canvas.deactivateAll().renderAll();
            $("#selectionTool").click();
		});

		$('#circle').click(function () {
			var circ = new fabric.Circle({ 
				radius: 30, 
				fill: document.getElementById("colorPicker").value, 
				top: 100, 
				left: 100 
			});

			var canvas = MockerController.getCanvas();
			circ.lockScalingX = circ.lockScalingY = true;
    	    canvas.add(circ);
    	    lockOptions(true);
            console.log(circ);
            document.getElementById('myModal').style.display = "none";
            //canvas.deactivateAll().renderAll();
            $("#selectionTool").click();
		});

		$('#triangle').click(function () {
			var triang = new fabric.Triangle({ 
				top: 300, 
				left: 210, 
				width: 100, 
				height: 100, 
				fill: document.getElementById("colorPicker").value 
			});

			var canvas = MockerController.getCanvas();
			triang.lockScalingX = triang.lockScalingY = true;
    	    canvas.add(triang);
    	    lockOptions(true);
            console.log(triang);
            document.getElementById('myModal').style.display = "none";
            //canvas.deactivateAll().renderAll();
            $("#selectionTool").click();
		});
	};

	const pencilTool = function () {
		$('#pencilTool').click(function() {
			lockOptions(true);
			var canvas = MockerController.getCanvas();
			canvas.isDrawingMode = true;
			if (fabric.PatternBrush){
				var vLinePatternBrush = new fabric.PatternBrush(canvas);
				vLinePatternBrush.getPatternSrc = function() {

					var patternCanvas = fabric.document.createElement('canvas');
					patternCanvas.width = patternCanvas.height = 10;
					var ctx = patternCanvas.getContext('2d');

					ctx.strokeStyle = this.color;
					ctx.lineWidth = 5;
					ctx.beginPath();
					ctx.moveTo(0, 5);
					ctx.lineTo(10, 5);
					ctx.closePath();
					ctx.stroke();

					return patternCanvas;
			    };
			};
			if (canvas.freeDrawingBrush) {
				canvas.freeDrawingBrush.width = 1;
				canvas.freeDrawingBrush.shadow = new fabric.Shadow({
					blur: 0,
					offsetX: 0,
					offsetY: 0,
					affectStroke: true,
					color: black,
				});
		    }
			canvas.freeDrawingBrush = vLinePatternBrush;
		});
	};

	const imageTool = function () {
		$('#imageTool').click(function() {
			// Get the modal
			var modal = document.getElementById('myModal2');

			// Get the button that opens the modal
			var btn = document.getElementById("imageTool");

			// Get the <span> element that closes the modal
			var span = document.getElementsByClassName("close")[0];

			// When the user clicks the button, open the modal 
			btn.onclick = function() {
			    modal.style.display = "block";
			}

			// When the user clicks on <span> (x), close the modal
			span.onclick = function() {
			    modal.style.display = "none";
			}

			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
			    if (event.target == modal) {
			        modal.style.display = "none";
			    }
			}
		});

		$('#square').click(function () {
			var rect = new fabric.Rect({
    	    	left: 100,
    	    	top: 100,
    	    	fill: document.getElementById("colorPicker").value,
    	    	width: 20,
    	    	height: 20
    	    });

			var canvas = MockerController.getCanvas();
			console.log("Llama la funcion del controler");
            console.log(rect);
            rect.lockScalingX = rect.lockScalingY = true;
    	    canvas.add(rect);
    	    lockOptions(true);
            console.log(rect);
            document.getElementById('myModal').style.display = "none";
            //canvas.deactivateAll().renderAll();
            $("#selectionTool").click();
		});

		$('#circle').click(function () {
			var circ = new fabric.Circle({ 
				radius: 30, 
				fill: document.getElementById("colorPicker").value, 
				top: 100, 
				left: 100 
			});

			var canvas = MockerController.getCanvas();
			circ.lockScalingX = circ.lockScalingY = true;
    	    canvas.add(circ);
    	    lockOptions(true);
            console.log(circ);
            document.getElementById('myModal').style.display = "none";
            //canvas.deactivateAll().renderAll();
            $("#selectionTool").click();
		});

		$('#triangle').click(function () {
			var triang = new fabric.Triangle({ 
				top: 300, 
				left: 210, 
				width: 100, 
				height: 100, 
				fill: document.getElementById("colorPicker").value 
			});

			var canvas = MockerController.getCanvas();
			triang.lockScalingX = triang.lockScalingY = true;
    	    canvas.add(triang);
    	    lockOptions(true);
            console.log(triang);
            document.getElementById('myModal').style.display = "none";
            //canvas.deactivateAll().renderAll();
            $("#selectionTool").click();
		});
	};	

	return {
		init : init
	};
})();