var ActionTools = (function () {

	var init = function () {
		createObjTool();
		selectionTool();
		zoomTool();
		textTool();
		pickerTool();
		garbageTool();
	};

	const textTool = function() {
		$("#textTool").click(function() {
			lockOptions(true);
		});
	};

	const pickerTool = function() {
		$("#pickerTool").click(function() {
			lockOptions(true);
			//Se implementa cuando las figuras estén sincronizadas con el servidor
		});
	}

	const garbageTool = function() {
		$("#garbageTool").click(function() {
			lockOptions(true);
			var canvas = MockerController.getCanvas();
			console.log("Borra elementos seleccionados");
			canvas.remove(canvas.getActiveObject());
		});
	};

	const zoomTool = function() {
		$("#zoomTool").click(function() {
			lockOptions(true);
			var canvas = MockerController.getCanvas();
			canvas.forEachObject(function(o){
				o.lockScalingX = o.lockScalingY = false;
			});
			canvas.deactivateAll().renderAll();
		});
	};

	const selectionTool = function () {
		$("#selectionTool").click(function() {
			lockOptions(false);
			o.lockScalingX = o.lockScalingY = true;
		});
	};

	const lockOptions = function (val) {
		console.log("Boquea rotacion y movimiento");
		var canvas = MockerController.getCanvas();
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
            canvas.deactivateAll().renderAll();
            selectionTool();
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
            canvas.deactivateAll().renderAll();
            selectionTool();
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
            canvas.deactivateAll().renderAll();
            selectionTool();
		});
	};

	return {
		init : init
	};
})();