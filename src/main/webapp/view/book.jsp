<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html>

<html lang="eng">

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>All Books</title>

<link type="text/css" rel="stylesheet" href="/css/book.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
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
								<form:form action="/bookActions/rate" modelAttribute="rating"
									method="PUT">
									<c:choose>
										<c:when test="${rating.id == 0}">
										Rate this book
										</c:when>
										<c:otherwise>

											<div id="yourRate">Your rate ${rating.rate}</div>
										</c:otherwise>
									</c:choose>

									<form:select id="selectRate" class="rounded" path="rate" onchange="this.form.submit()">
										<form:option value="1" label="1" />
										<form:option value="2" label="2" />
										<form:option value="3" label="3" />
										<form:option value="4" label="4" />
										<form:option value="5" label="5" />
										<form:option value="0" label="Rate" selected="selected" disabled="disabled" hidden="true"/>
									</form:select>
									<form:hidden path="id" />
									<input type="hidden" name="bookId" value="${book.id}">
								</form:form>
							</div>

							<form:form action="/bookActions/saveReaderBook"
								modelAttribute="readerBook" method="POST">
								<c:choose>
									<c:when test="${readerBook.id == 0}">
										<br>Add this book to your books
									</c:when>
									<c:otherwise>
										<br>Current State:${readerBook.shelves}
										<form:hidden path="id" />
										<form:hidden path="dateAdded" />
										<input type="hidden" name="isItUpdateReaderBook" value="true">
									</c:otherwise>
								</c:choose>

								<form:select class="rounded" path="shelves" onchange="this.form.submit()">
									<form:option value="Read" label="Read" />
									<form:option value="Currently Reading"
										label="Currently Reading" />
									<form:option value="Want to Read" label="Want to Read" />
									<form:option value="0" label="Change State" selected="selected" disabled="disabled" hidden="true"/>
								</form:select>

								<input type="hidden" name="bookId" value="${book.id}">
							</form:form>
						</sec:authorize> <sec:authorize access="!isFullyAuthenticated()">
							<a href="/login">Sign In</a> to Rate this book!
							</sec:authorize></td>
					<td id="desc">
						<h3 class="inline">${book.fullTitle}</h3>
						<p id="credit">by ${book.reviewAuthor} (AllBooks Author)</p>
						<table>
							<tr>
								<td id="ratingTd">Rating: ${overallRating}</td>
								<td id="ratingDetails">Rating Details ${rates} Ratings
									${quantity.ratings} Reviews ${quantity.reviews}</td>
							</tr>
						</table>
						<div id="bookDescription">${book.description}</div> <br /> <c:choose>
							<c:when test="${book.description.length() > 540}">
								<a class="moreDesc" href="#" data-value="bookDescription">More...</a>
							</c:when>
						</c:choose>
						<hr> BUY A COPY<br> <a target="blank"
						href="${book.buyBook}">
							<button style="background-color: #ff9900; border-radius: 5px;"
								type="button">Amazon</button>
					</a>
						<hr>
						<div id="details">
							${book.coverType}, ${book.pages} pages<br> Published
							${book.publishDate} by ${book.publishCompany}
						</div>
					</td>
					<td id="aboutAuthor">
						<h4 class="inline">About ${book.author}</h4>
						<hr>
						<div id="authorDescription">${book.aboutAuthor}</div> <br /> <c:choose>
							<c:when test="${book.aboutAuthor.length() > 290}">
								<a class="moreDesc" href="#" data-value="authorDescription">More...</a>
							</c:when>
						</c:choose>
						<div id="quotesFromBook">
							<h4 class="inline">Quotes From ${book.miniTitle}</h4>
							<hr>
							<c:forEach var="tempQuote" items="${quotesSplit}">
								<p>
									<i>${tempQuote}</i>
								</p>
							</c:forEach>
						</div>
					</td>
					<td id="authorImg"><img src="data:image/jpeg;base64,${authorPic}"></td>
				</tr>
			</table>
		</div>

		<div id="communityReviews">
			<h4 class="inline">COMMUNITY REVIEWS</h4>
			<hr>

			<sec:authorize access="isFullyAuthenticated()">
				<c:url var="reviewLink" value="/bookActions/postReviewPage">
					<c:param name="bookId" value="${book.id}" />
				</c:url>
				<a class="blackRef" href="${reviewLink}">Post a Review!</a>
			</sec:authorize>
			<sec:authorize access="!isFullyAuthenticated()">
				<a href="/login">Sign In to post a review!</a>
			</sec:authorize>
			<hr>

			<div id="allReviews">
				<table>
					<c:forEach var="tempReview" items="${bookReviews}">

						<c:url var="reviewLink" value="/bookActions/reviewPage">
							<c:param name="reviewId" value="${tempReview.id}" />
						</c:url>

						<c:url var="profileLink" value="/profile/showProfile">
							<c:param name="readerId" value="${tempReview.reader.id}" />
						</c:url>

						<tr>
							<td>
								<h4 class="inline">
									<a class="blackRef" href="${profileLink}">${tempReview.reader.username}</a>
								</h4> rated it ${tempReview.rating.rate}
							</td>
						</tr>
						<tr>
							<td id="reviewTitleTd">
								${fn:substring(tempReview.text,0,300)}...
							</td>
						</tr>
						<tr>
							<td id="spaceUnder">
							Likes ${tempReview.likes.size()}
							<sec:authorize
									access="isFullyAuthenticated()">
									<form:form action="/bookActions/dropLike" method="POST"
										id="likeForm">
										<input type="hidden" name="reviewId" value="${tempReview.id}" />
										<input type="hidden" name="bookId" value="${book.id}" />
										<input class="like" type="submit" value="Like" />
									</form:form>
								</sec:authorize> 
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>

		</div>

	</div>


	<script src="/js/bookShowMore.js"></script>

</body>

</html>