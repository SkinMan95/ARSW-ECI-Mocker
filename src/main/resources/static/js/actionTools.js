var ActionTools = (function () {

	var init = function () {
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

			console.log("Llama la funcion del controler");
    	    var canvaces = MockerController.getCanvas();
            console.log(rect);
    	    canvaces.add(rect);
            console.log(rect);
            document.getElementById('myModal').style.display = "none";
		});

		$('#circle').click(function () {
			var circ = new fabric.Circle({ 
				radius: 30, 
				fill: document.getElementById("colorPicker").value, 
				top: 100, 
				left: 100 
			});

			var canvaces = MockerController.getCanvas();
    	    canvaces.add(circ);
            console.log(circ);
            document.getElementById('myModal').style.display = "none";
		});

		$('#triangle').click(function () {
			var triang = new fabric.Triangle({ 
				top: 300, 
				left: 210, 
				width: 100, 
				height: 100, 
				fill: document.getElementById("colorPicker").value 
			});

			var canvaces = MockerController.getCanvas();
    	    canvaces.add(triang);
            console.log(triang);
            document.getElementById('myModal').style.display = "none";
		});
	};

	return {
		init : init
	};
})();