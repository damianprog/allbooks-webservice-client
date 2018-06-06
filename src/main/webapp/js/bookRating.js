$(document).ready(function(){
	
	var ratingJson = JSON.parse($("#ratingJson").val());
	
	$.ajax({
		dataType: 'jsonp',
        url: "http://localhost:9000/readers/" + ratingJson.readerId + "/books/" + ratingJson.bookId + "/ratings"
    }).then(function(data) {
    	if(data.rate!=null)
    		$('#yourRate').append(' ' + data.rate);
    	
    });
	
	$('#selectRate').change(function() {
		var rate = $(this).val();
		ratingJson.rate = rate;
		
		console.log(JSON.stringify(ratingJson));
		
		if($('input[name="updateRating"]').val()){
			$.ajax({
				type: "PUT",
				contentType : "application/json",
		        url: "http://localhost:9000/ratings",
		        data : JSON.stringify(ratingJson),
		        dataType : 'json',
		        success: function(){
	                $('#yourRate').html( data.rate );
	            },
			error: function(errorThrown ){
	            console.log( errorThrown );
	        }
		    });
		}
		
	})
	});
