var pointsArray = [];

		var socket = io.connect("http://localhost:3000");

		socket.on('connect', function(){

        	console.log('connected');
        	$("#search").show();        
   		 });  
   		

    	
    
		function startStormPage(){
	           
			socket.emit('startStormPage');

    }
		
		function startStormUser(){
			           
			socket.emit('startStormUser');

    }
    
	socket.on('disconnect', function(){
	
	console.log('disconected');

});