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
<script src="/js/editCommentBox.js"></script>
</head>

<body>

	<sec:authentication var="principal" property="principal" />

	<c:set var="isItLoggedReaderReview" value="false"></c:set>

	<sec:authorize access="isFullyAuthenticated()">
		<sec:authentication property="principal.username" var="username" />

		<c:if test="${username == review.postingReader.username}">

			<c:set var="isItLoggedReaderReview" value="true"></c:set>

		</c:if>
	</sec:authorize>

	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<c:url var="bookPageRef" value="/visitor/showBook">
			<c:param name="bookId" value="${review.book.id}" />
		</c:url>

		<div id="reviewDiv">
			<div id="header">
				<table>
					<tr>
						<td>
							<h3>${review.postingReader.username}'sReviews>
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

				<div id="reviewHeader">
					<h4 style="margin-top: -5px; margin-bottom: -2px;">
						<a class="blackRef" href="${bookPageRef}">${review.book.fullTitle}</a>
					</h4>
					by ${authorName}<br> ${review.postingReader.username}'s Review
				</div>

				<div class="adminActions">
					<sec:authorize access="hasAuthority('ADMIN')">

						<form action="/adminAction/action">

							<select class="rounded" name="adminAction"
								onchange="this.form.submit()">
								<option value="deleteReview" label="Delete review" />
								<option value="sendNotice" label="Send notice" />
								<option value="0" label="Admin Actions" selected="selected"
									disabled="disabled" hidden="true" />
							</select> <input name="reviewId" type="hidden" value="${review.id}" /> <input
								name="readerId" type="hidden" value="${review.postingReader.id}" />

						</form>
					</sec:authorize>
				</div>

				<div style="clear: both"></div>

				<br>
				<div id="reviewText">
					<p>${review.text }</p>
				</div>
				<div id="editReviewBox">

					<form action="/readerPost/editReview" method="POST">
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
						<form:form action="/bookActions/dropLike" method="POST"
							id="likeForm">
							<input type="hidden" name="reviewId" value="${review.id}" />
							<input type="hidden" name="bookId" value="${review.book.id}" />
							<input type="hidden" name="pageName" value="review" />
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

					<div id="deleteReview">
						<c:url var="deleteReview" value="/readerPost/deleteReview">
							<c:param name="reviewId" value="${tempReview.id}" />
						</c:url>

						<a
							onclick="return confirm('Are you sure you want to delete this review?');"
							class="blackRefNon" href="${deleteReview}">Delete review</a>
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
				<c:forEach var="tempComment" items="${reviewComments}"
					varStatus="status">

					<div class="commentAuthorPhoto">
						<img
							src="data:image/jpeg;base64,${tempComment.postingReader.encodedProfilePhoto}">
					</div>


					<div class="comment">
						<div class="commentHeader">
							<h4 style="display: inline;">${tempComment.postingReader.username}</h4>
							<c:if test="${tempComment.rating != null}">
								 rated it
								${tempComment.rating.rate}
							</c:if>
						</div>
					

					<sec:authorize access="hasAuthority('ADMIN')">
						<div class="adminActions">

							<form action="/adminAction/action">

								<select class="rounded" name="adminAction"
									onchange="this.form.submit()">
									<option value="deleteComment" label="Delete comment" />
									<option value="sendNotice" label="Send notice" />
									<option value="0" label="Admin Actions" selected="selected"
										disabled="disabled" hidden="true" />
								</select> <input name="commentId" type="hidden" value="${tempComment.id}" />
								<input type="hidden" name="commentType" value="REVIEW_COMMENT" />
								<input name="readerId" type="hidden"
									value="${tempComment.postingReader.id}" />

							</form>
						</div>
					</sec:authorize>

					
					<div style="clear:both"></div>

					<div class="commentText">
						<div class="commentText${status.count}">${tempComment.text}</div>
					</div>
					<div class="editCommentBox">
						<div class="editCommentBox${status.count}">

							<form action="/readerPost/editComment" method="POST">
								<textarea style="height: 100px; width: 400px;"
									class="editCommentTextArea${status.count}" name="commentText"
									required="required">${tempComment.text}</textarea>
								<br> <input type="hidden" name="commentId"
									value="${tempComment.id}" /> <input type="hidden"
									name="reviewId" value="${review.id}" /> <input class="submit"
									type="submit" value="Submit">
							</form>

						</div>
					</div>



					<c:if test="${username == tempComment.postingReader.username}">

						<div class="editCommentButton${status.count}"
							style="cursor: pointer; float: left;"
							onclick="showEditCommentBox(${status.count})">Edit</div>
						<c:url var="removeCommentRef" value="/readerPost/deleteComment">
							<c:param name="commentId" value="${tempComment.id}" />

						</c:url>

						<div id="deleteComment">
							<a class="blackRefNon"
								onclick="return confirm('Are you sure you want to delete this comment?');"
								href="${removeCommentRef}">Delete</a>
						</div>

					</c:if>
					</div>
					<div style="clear: both;"></div>
				</c:forEach>
			</div>

			<sec:authorize access="isFullyAuthenticated()">
				<form:form action="/readerPost/postComment" modelAttribute="comment"
					method="POST">
					<input type="hidden" name="reviewId" value="${review.id}" />
					<input type="hidden" name="bookId" value="${review.book.id}" />
					Comment<br>
					<form:textarea id="reviewBox" path="text" required="required" />
					<br>
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
var commentsListSize = ${reviewComments.size()};

for(i=1;i<=commentsListSize;i++)
	$('.editCommentBox' + i).hide();
</script>


</html>