function showEditCommentBox(index){
		if ($('.commentText' + index).is(':visible')) {
			$('.commentText'+ index).hide();
			$('.editCommentBox'+ index).show();
			$(".editCommentButton"+ index).text("Cancel edit");
		} else {
			$('.editCommentBox'+ index).hide();
			$('.commentText'+ index).show();
			$(".editCommentButton"+ index).text("Edit");
		}
	}

	$(document).ready(function() {

		$('#editReviewBox').hide();

		$("#editReviewButton").click(function() {

			if ($('#reviewText').is(':visible')) {
				$('#reviewText').hide();
				$('#editReviewBox').show();
				$("#editReviewButton").text("Cancel edit");
			} else {
				$('#editReviewBox').hide();
				$('#reviewText').show();
				$("#editReviewButton").text("Edit");
			}

		});
		

	});