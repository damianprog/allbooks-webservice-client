<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.allbooks.org/tags" prefix="ei"%>
	
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/reader/readingChallange.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/editCommentBox.js"></script>
</head>

<body>

	<c:set var="isItLoggedReader" value="false"></c:set>

	<sec:authorize access="isFullyAuthenticated()">
		<sec:authentication property="principal.username" var="username" />

		<c:if test="${username == challangeReader.username}">

			<c:set var="isItLoggedReader" value="true"></c:set>

		</c:if>
	</sec:authorize>

	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<div id="readingChallangeLogo">

			<img id="openBookImg" src="/css/images/open-book.png">
			<div id="whiteLogo">Reading Challange 2018</div>

		</div>

		<div id="readingChallangeContainer">

			<div style="clear: both"></div>

			<div id="challangeInfoHeader">

				<c:url var="readerProfileRef" value="/profile/showProfile">
					<c:param name="readerId" value="${challangeReader.id}" />
				</c:url>

				<div id="readerPhoto">
					<a href="${readerProfileRef}"><img
						src="<ei:image image='${challangeReader.profilePhoto}' width='80' height='80'/>"/></a>
				</div>

				<div id="challangeInfo">
					<c:choose>
						<c:when test="${isItLoggedReader == true}">
				You have read ${readBooks.size()} of
					${readingChallange.numberOfBooks} books in 2018
				</c:when>
						<c:otherwise>
					<a class="blackRef" href="${readerProfileRef}">${challangeReader.username}</a> have read ${readBooks.size()} of
					${readingChallange.numberOfBooks} books in 2018
				</c:otherwise>
					</c:choose>
				</div>

			</div>
			<div style="clear: both"></div>
			<hr class="hrSpace">

			<div id="currentYearBooks">

				<div id="currentYearBooksHeader">
					<c:choose>
						<c:when test="${isItLoggedReader == true}">
			YOUR 2018 BOOKS
			</c:when>
						<c:otherwise>
				${challangeReader.username} 2018 BOOKS
			</c:otherwise>
					</c:choose>
				</div>

				<c:forEach var="readerBook" items="${readBooks}" varStatus="loop">

					<c:url var="bookPage" value="/visitor/showBook">
						<c:param name="bookId" value="${readerBook.book.id}" />
					</c:url>

					<div class="readBookPhoto">
						<a href="${bookPage}"> <img
							src="<ei:image image='${readerBook.book.bookPhoto}' width='100' height='150'/>"/>
						</a>
					</div>

					<c:if test="${(loop.index+1) % 5 == 0}">
						<div style="clear: both"></div>
					</c:if>

				</c:forEach>

			</div>
			<div style="clear: both"></div>

			<h3>Comments</h3>
			<hr>

			<div id="allComments">
				<c:forEach var="tempComment" items="${readingChallangeComments}"
					varStatus="status">

					<c:url var="profileRef" value="/profile/showProfile">
						<c:param name="readerId" value="${tempComment.postingReader.id}"/>
					</c:url>

					<div class="commentAuthorPhoto">
						<a href="${profileRef}"><img
							src="<ei:image image='${tempComment.postingReader.profilePhoto}' width='80' height='80'/>"/></a>
					</div>

					<div class="comment">
					<div class="commentHeader">
						<h4 style="display: inline;"><a class="blackRef" href="${profileRef}">${tempComment.postingReader.username}</a></h4>

					</div>
					
					<sec:authorize access="hasAuthority('ADMIN')">
						<div class="adminActions">

							<form action="/adminAction/action">

								<select class="rounded" name="adminAction"
									onchange="this.form.submit()">
									<option value="deleteComment"
										label="Delete comment" />
									<option value="sendNotice" label="Send notice" />
									<option value="0" label="Admin Actions" selected="selected"
										disabled="disabled" hidden="true" />
								</select> <input name="commentId" type="hidden" value="${tempComment.id}" />
								<input type="hidden" name="commentType" value="CHALLANGE_COMMENT" />
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
	
							<form action="/readerPost/editChallangeComment" method="POST">
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
						<c:url var="removeCommentRef"
							value="/readerPost/deleteReadingChallangeComment">
							<c:param name="commentId" value="${tempComment.id}" />
							<c:param name="challangeReaderId" value="${challangeReader.id}" />
						</c:url>

						<div class="deleteComment">
							<a class="blackRefNon"
								onclick="return confirm('Are you sure you want to delete this comment?');"
								href="${removeCommentRef}">Delete</a>
						</div>
					</c:if>
					</div>
					<div style="clear: both;"></div>
				</c:forEach>
				<form action="/readerPost/postReadingChallangeComment" method="POST">
					<input type="hidden" name="challangeReaderId" value="${challangeReader.id}" />
					Comment<br>
					<textarea name="challangeCommentText" id="commentBox" required="required"></textarea>
					<br> <input class="submit" type="submit" value="Submit">
				</form>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
var commentsListSize = ${readingChallangeComments.size()};

for(i=1;i<=commentsListSize;i++)
	$('.editCommentBox' + i).hide();
</script>

</html>