<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.allbooks.org/tags" prefix="ei"%>

<!DOCTYPE html>

<html lang="eng">

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>All Books</title>

<link type="text/css" rel="stylesheet" href="/css/book/book.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<hr>
		<div>
		
		<div id="bookActionsAndPhoto">
					<img src="<ei:image image='${book.bookPhoto}' width='150' height='228'/>"/>
						<br> <sec:authorize access="isFullyAuthenticated()">

							<div id="rating">
								<form:form action="/bookActions/rate" modelAttribute="rating"
									method="PUT">
									<c:choose>
										<c:when test="${rating.id == 0}">
										Rate this book
										</c:when>
										<c:otherwise>

											Your rate ${rating.rate}
										</c:otherwise>
									</c:choose>
									<br>
									<form:select id="selectRate" class="rounded" path="rate"
										onchange="this.form.submit()">
										<form:option value="1" label="1" />
										<form:option value="2" label="2" />
										<form:option value="3" label="3" />
										<form:option value="4" label="4" />
										<form:option value="5" label="5" />
										<form:option value="0" label="Rate" selected="selected"
											disabled="disabled" hidden="true" />
									</form:select>
									<form:hidden path="id" />
									<input type="hidden" name="bookId" value="${book.id}">
								</form:form>
							</div>

							<form action="/myBooks/saveReaderBook" method="POST">
								<c:choose>
									<c:when test="${readerBook.id == 0}">
										<br>Add this book to your books
										<input type="hidden" name="isItUpdateReaderBook" value="false">
									</c:when>
									<c:otherwise>
										<br>Current State:${readerBook.shelvesStates.shelveState()}
										<input type="hidden" name="readerBookId" value="${readerBook.id}"/>
										<input type="hidden" name="isItUpdateReaderBook" value="true"/>
									</c:otherwise>
								</c:choose>
								<br>
								<select class="rounded" name="shelves"
									onchange="this.form.submit()">
									<option value="Read" label="Read" />
									<option value="Currently Reading" label="Currently Reading" />
									<option value="Want To Read" label="Want to Read" />
									<option value="0" label="Change State" selected="selected"
										disabled="disabled" hidden="true" />
								</select>
								<input type="hidden" name="pageName" value="book">
								<input type="hidden" name="bookId" value="${book.id}">
							</form>
						</sec:authorize> <sec:authorize access="!isFullyAuthenticated()">
							<a href="/login">Sign In</a> to Rate this book!
							</sec:authorize>
							</div>
							
						<div id="middle">	
							
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
						
						</div>
						
						<div id="right">
						<div id="authorDescription">
						<h4 class="inline">About ${book.author}</h4>
						<hr>
						${book.aboutAuthor}</div>
						<div style="clear:both"></div>
						 <br /> <c:choose>
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
					</div>
					<div id="authorPhoto">
							<img src="<ei:image image="${book.authorPhoto}" width='50' height='66'/>"/>
						</div>
					
					
		</div>

		<div id="communityReviews">
			<h4 class="inline">COMMUNITY REVIEWS</h4>
			<hr>

			<sec:authorize access="isFullyAuthenticated()">
				<c:url var="reviewLink" value="/bookActions/showPostReview">
					<c:param name="bookId" value="${book.id}" />
				</c:url>
				<a class="blackRef" href="${reviewLink}">Post a Review!</a>
			</sec:authorize>
			<sec:authorize access="!isFullyAuthenticated()">
				<a href="/login">Sign In to post a review!</a>
			</sec:authorize>
			<hr>

			<div id="allReviews">
				<c:forEach var="tempReview" items="${bookReviews}">

					<div class="reviewAuthorPhoto">
						<img
							src="<ei:image image='${tempReview.postingReader.profilePhoto}' width='80' height='80'/>"/>
					</div>

					<table class="reviewTable">

						<c:url var="reviewLink" value="/bookActions/showReview">
							<c:param name="reviewId" value="${tempReview.id}" />
						</c:url>

						<c:url var="profileLink" value="/profile/showProfile">
							<c:param name="readerId" value="${tempReview.postingReader.id}" />
						</c:url>

						<tr>
							<td>
								<div style="float: left; width: 300px;">
									<h4 class="inline">
										<a class="blackRef" href="${profileLink}">${tempReview.postingReader.username}</a>
									</h4>
									rated it ${tempReview.rating.rate}
								</div> <sec:authorize access="hasAuthority('ADMIN')">

									<form action="/adminAction/action">

										<select class="dropDown" name="adminAction"
											onchange="this.form.submit()">
											<option value="deleteReview" label="Delete review" />
											<option value="sendNotice" label="Send notice" />
											<option value="0" label="Admin Actions" selected="selected"
												disabled="disabled" hidden="true" />
										</select> <input name="reviewId" type="hidden" value="${tempReview.id}" />
										<input name="readerId" type="hidden"
											value="${tempReview.postingReader.id}" />

									</form>
								</sec:authorize>
							</td>

						</tr>
						<tr>
							<td id="reviewTitleTd">
								<div class="reviewText">${fn:substring(tempReview.text,0,500)} <c:if
									test="${fn:length(tempReview.text) > 500}">
									<a class="moreDesc" href="${reviewLink}">...see review</a>
									
								</c:if>
								</div>
							</td>
						</tr>
						<tr>
							<td id="spaceUnder">
								<div style="float: left;">
									Likes ${tempReview.likes.size()}
									<sec:authorize access="isFullyAuthenticated()">
										<form:form action="/bookActions/dropLike" method="POST"
											id="likeForm">
											<input type="hidden" name="reviewId" value="${tempReview.id}" />
											<input type="hidden" name="bookId" value="${book.id}" />
											<input type="hidden" name="pageName" value="book" />
											<input class="like" type="submit" value="Like" />
										</form:form>
									</sec:authorize>
								</div>
								<div style="float: left; margin-left: 10px;">
									<a class="blackRef" href="${reviewLink}">see review</a>
								</div>

								<div class="readerActions">

									<sec:authorize access="isFullyAuthenticated()">
										<sec:authentication property="principal.username"
											var="username" />

										<c:if test="${username == tempReview.postingReader.username}">

											<c:url var="deleteReview" value="/readerPost/deleteReview">
												<c:param name="reviewId" value="${tempReview.id}" />
											</c:url>

											<a
												onclick="return confirm('Are you sure you want to delete this review?');"
												class="blackRef" href="${deleteReview}">Delete review</a>

										</c:if>

									</sec:authorize>

								</div>

								<div style="clear: both;"></div>
							</td>
						</tr>

					</table>
					
				</c:forEach>
			</div>

		</div>

	</div>


	<script src="/js/bookShowMore.js"></script>

</body>

</html>