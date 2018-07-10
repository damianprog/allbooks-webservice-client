$(document).ready(function() {
		
		$('#uploadForm').hide();
		    
		$('#photoInput').change(function() {
			
			if(document.getElementById('photoSubmit').disabled == true){
				document.getElementById('photoSubmit').disabled = false;
				 $('#extensionError').text('');
			}
			
			 var name = document.getElementById('photoInput'); 
		      var fileName = name.files.item(0).name;
		      var fileExtension = fileName.substr((fileName.lastIndexOf('.') + 1));
		      
		      if((fileExtension != 'jpg' && fileExtension != 'jpeg')){
		    	  $('#extensionError').text('Wrong file extension.Choose jpg image');
		    	  document.getElementById('photoSubmit').disabled = true;
		      }
		      else if(name.files.item(0).size > 5242880){
		    	  $('#extensionError').text('The File size should not exceed 5MB');
		    	  document.getElementById('photoSubmit').disabled = true;
		      }
		      
		});
		
		$('#uploadPhotoLabel').click(function() {
			if($('#uploadForm').is(':visible'))
				$('#uploadForm').hide();
			else
			$('#uploadForm').show();
		});
		
		
		
	});