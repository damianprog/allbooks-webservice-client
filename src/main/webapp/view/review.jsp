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
<link type="text/css" rel="stylesheet" href="/css/book.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>
	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<div>
			<table>
				<tr>
					<td>
						<h3>${readerLogin}'s Reviews > ${book.fullTitle}</h3>
					</td>
				</tr>
				<tr>

					<td id="img">
						<div id="imgRateReview">
							<img src="data:image/jpeg;base64,${bookPic}"> <br>
							<sec:authorize access="isFullyAuthenticated()">
								<c:choose>
									<c:when test="${userRated == false}">
										<div id="rating">
											Rate this book
											<form:form action="rate" modelAttribute="rating" method="GET">
												<form:select path="rate">
													<form:option value="1" label="1" />
													<form:option value="2" label="2" />
													<form:option value="3" label="3" />
													<form:option value="4" label="4" />
													<form:option value="5" label="5" selected="selected" />
												</form:select>
												<input type="hidden" name="bookName" value="${bookName}">
												<input type="submit" value="Submit" />

											</form:form>
										</div>
									</c:when>
									<c:when test="${userRated == true}">
								Your rate ${rating.rate}
							</c:when>
								</c:choose>
							</sec:authorize>
							<sec:authorize access="!isFullyAuthenticated()">
								<a href="/login">Sign In</a> to Rate this book!
							</sec:authorize>

						</div>
						<div id="reviewDesc">
							<h4 id="h3">${book.fullTitle}</h4>
							by ${authorName}<br> ${readerLogin}'s
							Review<br>
							<p>${review.text }</p>

							<div id="reviewLikes">
								Likes: ${review.likes}

								<sec:authorize access="isFullyAuthenticated()">
										<form:form action="/reader/dropLikeReview" method="GET"
											id="likeForm">
											<input type="hidden" name="reviewId" value="${review.id}" />
											<input type="hidden" name="readerLogin"
												value="${readerLogin}" />
											<input type="hidden" name="bookId" value="${book.id}" />
											<input type="submit" value="Like" />
										</form:form>
									</sec:authorize>
									<sec:authorize access="!isFullyAuthenticated()">
										<form:form action="/login" method="GET"
											id="likeForm">
											<input type="submit" value="Like" />
										</form:form>
									</sec:authorize>
					
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="communityReviews">
			<h4 id="h3">Comments</h4>
			<hr>

			<sec:authorize access="isFullyAuthenticated()">
					<form:form action="submitComment" modelAttribute="comment"
						method="GET">
						<input type="hidden" name="reviewId" value="${review.id}" />
						<input type="hidden" name="readerLogin" value="${readerLogin}" />
						<input type="hidden" name="bookId" value="${bookId}" />
						<input type="hidden" name="bookName" value="${book.miniTitle}" />
						<input type="hidden" name="authorName" value="${authorName}" />									
					Comment<br>
						<form:textarea id="reviewBox" path="text" required="required" />
						<br>
						<input type="hidden" name="bookName" value="magnusChase" />
						<input type="submit" value="Submit">
					</form:form>
				</sec:authorize>
				<sec:authorize access="!isFullyAuthenticated()">
					<a href="/login">Sign In to post a comment!</a>
				</sec:authorize>
			<hr>
		</div>

		<div id="allReviews">
			<table>
				<c:forEach var="tempComment" items="${reviewComments}">

					<tr>
						<td>
							<h4 id="h4">${tempComment.readerLogin}</h4> rated it
							${tempComment.rating.rate}
						</td>
					</tr>
					<tr>
						<td id="reviewTitleTable">${tempComment.text}</td>
					</tr>
					<tr>
						<td id="spaceUnder"></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>

</html>