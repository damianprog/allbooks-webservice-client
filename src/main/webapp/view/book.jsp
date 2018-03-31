<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html lang="eng">

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>All Books</title>

<link type="text/css" rel="stylesheet" href="/css/book.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<hr>
		<div>
			<table>
				<tr>
					<td id="img"><img src="data:image/jpeg;base64,${bookPic}">
						<br> <sec:authorize access="isFullyAuthenticated()">

							<div id="rating">
								<form:form action="rate" modelAttribute="rating" method="GET">
									<c:choose>
										<c:when test="${rating.id == 0}">
										Rate this book
										<input type="hidden" name="updateRating" value="false">
										</c:when>
										<c:otherwise>
							
									Your rate ${rating.rate}
									<input type="hidden" name="updateRating" value="true">
										</c:otherwise>
									</c:choose>

									<form:select class="rounded" path="rate">
										<form:option value="1" label="1" />
										<form:option value="2" label="2" />
										<form:option value="3" label="3" />
										<form:option value="4" label="4" />
										<form:option value="5" label="5" selected="selected" />
									</form:select>
									<form:hidden path="id" />
									<form:hidden path="bookId" />
									<form:hidden path="readerIdentity" />
									<input type="hidden" name="bookName" value="${book.miniTitle}">
									<input class="submit" type="submit" value="Submit" />
								</form:form>
							</div>

							<form:form action="readState" modelAttribute="readerBook"
								method="GET">
								<c:choose>
									<c:when test="${readerBook.id == 0}">
										<br>Add this book to your books
									<input type="hidden" name="update" value="false">
									</c:when>
									<c:otherwise>
										<br>Current State:${readerBook.shelves}
										<form:hidden path="id" />
										<form:hidden path="dateAdded" />
										<input type="hidden" name="update" value="true">
									</c:otherwise>
								</c:choose>

								<form:select class="rounded" path="shelves">
									<form:option value="Read" label="Read" />
									<form:option value="Currently Reading"
										label="Currently Reading" />
									<form:option value="Want to Read" label="Want to Read" />
								</form:select>

								<input type="hidden" name="bookName" value="${book.miniTitle}">
								<input class="submit" type="submit" value="Submit" />
							</form:form>
						</sec:authorize> <sec:authorize access="!isFullyAuthenticated()">
							<a href="/login">Sign In</a> to Rate this book!
							</sec:authorize></td>
					<td id="desc">
						<h3 id="h3">${book.fullTitle}</h3>
						<p id="credit">by ${book.reviewAuthor} (AllBooks Author)</p>
						<table>
							<tr>
								<td id="pInstead">Rating: ${overallRating}</td>
								<td id="ratingDetails">Rating Details ${rates} Ratings
									${quantity.ratings} Reviews ${quantity.reviews}</td>
							</tr>
						</table>${book.description}
						<hr> BUY A COPY<br> <a target="blank"
						href="${book.buyBook}">
							<button style="background-color:#ff9900;border-radius: 5px;	" type="button">Amazon</button>
					</a>
						<hr>
						<div id="details">
							${book.coverType}, ${book.pages} pages<br> Published
							${book.publishDate} by ${book.publishCompany}
						</div>
					</td>
					<td id="aboutAuthor">
						<h4 id="h3">About ${book.author}</h4>
						<hr>
						<div>${book.aboutAuthor}</div>
						<div id="quotesFromBook">
							<h4 id="h3">Quotes From ${book.miniTitle}</h4>
							<hr>
							<c:forEach var="tempQuote" items="${quotesSplit}">
								<p>
									<i>${tempQuote}</i>
								</p>
							</c:forEach>
						</div>
					</td>
					<td id="img2"><img src="data:image/jpeg;base64,${authorPic}"></td>
				</tr>
			</table>
		</div>

		<div id="communityReviews">
			<h4 id="h3">COMMUNITY REVIEWS</h4>
			<hr>

			<sec:authorize access="isFullyAuthenticated()">
				<c:url var="reviewLink" value="/reader/postReviewPage">
					<c:param name="bookId" value="${book.id}" />
				</c:url>
				<a href="${reviewLink}">Post a Review!</a>
			</sec:authorize>
			<sec:authorize access="!isFullyAuthenticated()">
				<a href="/login">Sign In to post a review!</a>
			</sec:authorize>
			<hr>

			<div id="allReviews">
				<table>
					<c:forEach var="tempReview" items="${bookReviews}">

						<c:url var="reviewLink" value="/reader/reviewPage">
							<c:param name="reviewId" value="${tempReview.id}" />
						</c:url>

						<c:url var="profileLink" value="/profile/showProfile">
							<c:param name="readerId" value="${tempReview.readerIdentity}" />
						</c:url>

						<tr>
							<td>
								<h4 id="h4">
									<a class="blackRef" href="${profileLink}">${tempReview.readerLogin}</a>
								</h4> rated it ${tempReview.rating.rate}
							</td>
						</tr>
						<tr>
							<td id="reviewTitleTable"><a href="${reviewLink}">${tempReview.title}</a>
							</td>
						</tr>
						<tr>
							<td id="spaceUnder">Likes: ${tempReview.likes} <sec:authorize
									access="isFullyAuthenticated()">
									<form:form action="/reader/dropLike" method="GET" id="likeForm">
										<input type="hidden" name="reviewId" value="${tempReview.id}" />
										<input type="hidden" name="bookName" value="${book.miniTitle}" />
										<input id="like" type="submit" value="Like" />
									</form:form>
								</sec:authorize> <sec:authorize access="!isFullyAuthenticated()">
									<form:form action="/login" method="GET" id="likeForm">
										<input  type="submit" value="Like" />
									</form:form>
								</sec:authorize>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>

		</div>

	</div>
</body>

</html>