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
    	    	fill: 'red',
    	    	width: 20,
    	    	height: 20
    	    });

    	    var canvaces = document.getElementById("drawingCanvas");
            console.log(rect);
    	    canvaces.main.add(rect);
            console.log(rect);
            modal.style.display = "none";
		});
	};

	return {
		init : init
	};
})();