<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Allbooks Review</title>
<link type="text/css" rel="stylesheet" href="/css/review.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script src="/js/jquery-3.3.1.min.js"></script>
</head>

<body>

	<sec:authentication var="principal" property="principal" />

	<c:set var="isItLoggedReaderReview" value="false"></c:set>

	<sec:authorize access="isFullyAuthenticated()">
		<sec:authentication property="principal.username" var="username" />

		<c:if test="${username == review.reader.username}">

			<c:set var="isItLoggedReaderReview" value="true"></c:set>

		</c:if>
	</sec:authorize>

	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<c:url var="bookPageRef" value="/reader/showBook">
			<c:param name="bookId" value="${review.book.id}" />
		</c:url>

		<div id="reviewDiv">
			<div id="header">
				<table>
					<tr>
						<td>
							<h3>${review.reader.username}'sReviews>
								${review.book.fullTitle}</h3>
						</td>
					</tr>
				</table>
			</div>
			<div id="imgReview">
				<a class="blackRef" href="${bookPageRef}"><img
					src="data:image/jpeg;base64,${bookPic}"></a> <br>
				<sec:authorize access="isFullyAuthenticated()">
					<c:choose>
						<c:when test="${loggedReaderRating.id == 0}">
							<div id="rating">
								Rate this book
								<form:form action="rate" modelAttribute="loggedReaderRating"
									method="PUT">
									<form:select path="rate">
										<form:option value="1" label="1" />
										<form:option value="2" label="2" />
										<form:option value="3" label="3" />
										<form:option value="4" label="4" />
										<form:option value="5" label="5" />
										<form:option value="0" label="Rate" selected="selected"
											disabled="disabled" hidden="true" />
									</form:select>
									<input type="hidden" name="bookId"
										value="${loggedReaderRating.book.id}">
									<input class="submit" type="submit" value="Submit" />

								</form:form>
							</div>
						</c:when>
						<c:otherwise>
								Your rate ${loggedReaderRating.rate}
							</c:otherwise>
					</c:choose>
				</sec:authorize>
				<sec:authorize access="!isFullyAuthenticated()">
					<a href="/login">Sign In</a> to Rate this book!
							</sec:authorize>

			</div>
			<div id="reviewDesc">

				<h4 style="margin-top: -5px; margin-bottom: -2px;">
					<a class="blackRef" href="${bookPageRef}">${review.book.fullTitle}</a>
				</h4>
				by ${authorName}<br> ${review.reader.username}'s Review<br>
				<div id="reviewText">
					<p>${review.text }</p>
				</div>
				<div id="editReviewBox">

					<form action="/bookActions/editReview" method="POST">
						<textarea id="editReviewTextArea" name="reviewText"
							required="required">${review.text}</textarea>
						<br> <input type="hidden" name="reviewId"
							value="${review.id}" /> <input class="submit" type="submit"
							value="Submit">
					</form>

				</div>

				<div id="reviewLikes">
					Likes: ${review.likes.size()}

					<sec:authorize access="isFullyAuthenticated()">
						<form:form action="/bookActions/dropLikeReview" method="POST"
							id="likeForm">
							<input type="hidden" name="reviewId" value="${review.id}" />
							<input type="hidden" name="bookId" value="${review.book.id}" />
							<input id="like" type="submit" value="Like" />
						</form:form>
					</sec:authorize>

				</div>

				<c:if test="${isItLoggedReaderReview}">

					<c:url var="editReviewRef" value="/bookActions/editReview">
						<c:param name="reviewId" value="${review.id}" />
					</c:url>

					<div id="editReview">
						<div id="editReviewButton">Edit</div>
					</div>

				</c:if>
			</div>

		</div>

		<div style="clear: both">

			<div id="commentLabel">
				<h3>Comments</h3>
				<hr>
			</div>

			<div id="allComments">
				<table>
					<c:forEach var="tempComment" items="${reviewComments}"
						varStatus="status">

						<tr>
							<td>
								<h4 style="display: inline;">${tempComment.reader.username}</h4>
								<c:choose>
									<c:when test="${tempComment.rating != null}">
								 rated it
								${tempComment.rating.rate}
							</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td style="padding-bottom: 15px;">
								<div class="commentText${status.count}">
									${tempComment.text}</div>
								<div class="editCommentBox${status.count}">

									<form action="/bookActions/editComment" method="POST">
										<textarea style="height: 100px; width: 400px;"
											class="editCommentTextArea${status.count}" name="commentText"
											required="required">${tempComment.text}</textarea>
										<br> <input type="hidden" name="commentId"
											value="${tempComment.id}" /> <input type="hidden"
											name="reviewId" value="${review.id}" /> <input
											class="submit" type="submit" value="Submit">
									</form>

								</div>

							</td>
						</tr>
						<c:if test="${username == tempComment.reader.username}">
							<tr>

								<td style="padding-bottom: 15px;">
									<div class="editCommentButton${status.count}"
										style="cursor: pointer;"
										onclick="showEditCommentBox(${status.count})">Edit</div>
								</td>

							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>

			<sec:authorize access="isFullyAuthenticated()">
				<form:form action="/bookActions/postComment"
					modelAttribute="comment" method="POST">
					<input type="hidden" name="reviewId" value="${review.id}" />
					<input type="hidden" name="bookId" value="${review.book.id}" />
					Comment<br>
					<form:textarea id="reviewBox" path="text" required="required" />
					<br>
					<input type="hidden" name="bookName" value="magnusChase" />
					<input class="submit" type="submit" value="Submit">
				</form:form>
			</sec:authorize>
			<sec:authorize access="!isFullyAuthenticated()">
				<a href="/login">Sign In to post a comment!</a>
			</sec:authorize>

		</div>
	</div>
</body>

<script type="text/javascript">

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
		
		var commentsListSize = ${reviewComments.size()};
		
		for(i=1;i<=commentsListSize;i++)
			$('.editCommentBox' + i).hide();
		

	});
</script>

</html>