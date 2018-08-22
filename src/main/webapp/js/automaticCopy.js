$(document).ready(function() {
	
	$('.copyLink').click(function(e) {
		 var copyText = document.getElementById("tokenInput");

		  copyText.select();

		  document.execCommand("copy");

		  alert("Link copied to clipboard!");
	});
	
	 
});