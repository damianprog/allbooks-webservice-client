$(document).ready(function() {

	$('.moreDesc').click(function(e) {
		descMoreLess($(this).text(), $(this).data("value"));
	});

	var descMoreLess = function(method, value) {

		var currentTag = '#' + value + '';
		var currentButton = 'a[data-value=\'' + value + '\']';
		if (method == 'More...') {
			
			$(currentTag).css({
				'height' : 'auto'
			})
			$(currentButton).text('Less...');
		}

		else if (value == 'bookDescription' && method == 'Less...') {
			$('#bookDescription').css({
				'height' : '180px'
			})
			$(currentButton).text('More...');
		}

		else if (value == 'authorDescription' && method == 'Less...') {
			$('#authorDescription').css({
				'height' : '198px'
			})
			$(currentButton).text('More...');
		}

		else if (value == 'profileAbout' && method == 'Less...') {
			$('#profileAbout').css({
				'height' : '118px'
			})
			$(currentButton).text('More...');
		}

	}

});
