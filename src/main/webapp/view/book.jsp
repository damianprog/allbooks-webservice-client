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
											<input type="hidden" name="bookName" value="${book.title}">
											<input type="submit" value="Submit" />
										</form:form>
									</div>
								</c:when>
								<c:when test="${userRated == true }">
							
									Your rate ${readerRating}
						</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${update == true}">
									<br>Current State:${readerBook.shelves}
										</c:when>
								<c:otherwise>
									<br>Add this book to your books
									</c:otherwise>
							</c:choose>
							<form:form action="readstate" modelAttribute="readerBook"
								method="GET">
								<form:select path="shelves">
									<form:option value="Read" label="Read" />
									<form:option value="Currently Reading"
										label="Currently Reading" />
									<form:option value="Want to Read" label="Want to Read" />
								</form:select>
								<input type="hidden" name="bookName" value="${book.title}">
								<input type="hidden" name="update" value="${update}">
								<input type="submit" value="Submit" />
							</form:form>
						</sec:authorize> <sec:authorize access="!isFullyAuthenticated()">
							<a href="/reader/loginPage">Sign In</a> to Rate this book!
							</sec:authorize></td>
					<td id="desc">
						<h3 id="h3">${book.fullTitle}</h3>
						<p id="credit">by ${book.reviewAuthor} (AllBooks Author)</p>
						<table>
							<tr>
								<td id="pInstead">Rating: ${overallRating}</td>
								<td id="ratingDetails">Rating Details ${rates} Ratings
									${reviews} Reviews</td>
							</tr>
						</table>${book.description}
						<hr> BUY A COPY<br> <a target="blank"
						href="${book.buyBook}">
							<button type="button">Amazon</button>
					</a>
						<hr>
						<div id="details">
							${book.coverType}, ${book.pages} pages<br> Published ${book.publishDate} by
							${book.publishCompany}
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
				<form:form action="submitReview" modelAttribute="review"
					method="GET">
					Title of Review<br>
					<form:input id="reviewTitle" path="title" required="required" />
					<br>
					Content<br>
					<form:textarea id="reviewBox" path="text" required="required" />
					<br>
					<input type="hidden" name="bookName" value="${book.title}" />
					<input type="submit" value="Submit">
				</form:form>
			</sec:authorize>
			<sec:authorize access="!isFullyAuthenticated()">
				<a href="/reader/loginPage">Sign In to post a review!</a>
			</sec:authorize>
			<hr>

			<div id="allReviews">
				<table>
					<c:forEach var="tempReview" items="${bookReviews}">

						<c:url var="reviewLink" value="/reader/reviewPage">
							<c:param name="reviewId" value="${tempReview.id}" />
							<c:param name="readerLogin" value="${tempReview.readerLogin}" />
							<c:param name="bookId" value="${tempReview.bookId}" />
							<c:param name="readerRating" value="${tempReview.readerRating}" />
							<c:param name="fullBookName"
								value="${book.fullTitle}" />
							<c:param name="authorName" value="${book.author}" />
						</c:url>

						<c:url var="profileLink" value="/profile/showProfile">
							<c:param name="readerId" value="${tempReview.readerIdentity}" />
							<c:param name="guest" value="true" />
						</c:url>

						<tr>
							<td>
								<h4 id="h4">
									<a class="blackRef" href="${profileLink}">${tempReview.readerLogin}</a>
								</h4> rated it ${tempReview.readerRating}
							</td>
						</tr>
						<tr>
							<td id="reviewTitleTable"><a href="${reviewLink}">${tempReview.title}</a>
							</td>
						</tr>
						<tr>
							<td id="spaceUnder">Likes: ${tempReview.likes} 
									<sec:authorize access="isFullyAuthenticated()">
										<form:form action="/reader/dropLike" method="GET"
											id="likeForm">
											<input type="hidden" name="reviewId" value="${tempReview.id}" />
											<input type="hidden" name="bookName" value="${book.title}" />
											<input type="submit" value="Like" />
										</form:form>
									</sec:authorize>
									<sec:authorize access="!isFullyAuthenticated()">
										<form:form action="/reader/loginPage" method="GET"
											id="likeForm">
											<input type="submit" value="Like" />
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