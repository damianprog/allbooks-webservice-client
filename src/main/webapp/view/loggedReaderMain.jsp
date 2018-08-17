<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/loggedReaderMain.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>

		<div id="leftSideContainer">

			<div id="searchBooks">

				<h3>Search books</h3>

				<div id="searchBookImage">
					<img src="/css/images/books-stack-of-three.png">
				</div>

				<div id="searchBookDesc">Search any book</div>

				<div style="clear: both"></div>

				<form action="/reader/searchBooks">
					<input class="mainInputBox" name="phrase"
						placeholder="Search books" required="required"> <input
						id="search" type="submit" value="Search">

				</form>

			</div>

			<hr class="leftSideHr">

			<div id="readingChallange">

				<h3>2018 Reading Challange</h3>
				<div id="readingChallangeDesc">Challenge yourself to read more
					this year!</div>

				<div id="readingChallangeImage">
					<img src="/css/images/books.png">
				</div>

				<div id="readingChallangeActions">

					<c:choose>
						<c:when test="${empty readingChallange}">
							<form action="/loggedReader/readingChallange">
								<span>I want to read</span><br> <input name="numberOfBooks"
									id="numberOfBooks" type="number" min="0"><br> <span>books
									in 2018</span><br> <input id="startChallange" type="submit"
									value="Start Challange">
							</form>
						</c:when>
						<c:otherwise>

							<c:url var="readingChallangeRef"
								value="/loggedReader/showReadingChallange">
								<c:param name="readerId" value="${loggedReaderId}" />
							</c:url>
							<div style="font-size: 28px;">${currentNumberOfBooks}</div>
							books completed<br>
							<div style="margin-top: 10px; font-size: 14px; color: #767676;">You're
								on track!</div>
							<div style="margin-top: 5px;">${currentNumberOfBooks}/${readingChallange.numberOfBooks}
								(${readingProgressPercentage} &#37;)</div>
							<div>
								<a class="blueLink" href="${readingChallangeRef}">view
									challange</a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<div style="clear: both"></div>

			<hr class="leftSideHr">

			<div id="wantToRead">

				<h3>Want to read</h3>

				<c:choose>
					<c:when test="${empty wantToReadBooks}">
						<div id="wantToReadImage">
							<img src="/css/images/living-room-books-group.png">
						</div>

						<div id="wantToReadDesc">What do you want to read next?</div>
					</c:when>
					<c:otherwise>
						<c:forEach begin="0" end="3" var="readerBook"
							items="${wantToReadBooks}">
							<c:url var="bookPageRef" value="/reader/showBook">
								<c:param name="bookId" value="${readerBook.book.id}" />
							</c:url>
							<a href="${bookPageRef}"><img
								src="data:image/jpeg;base64,${readerBook.book.encodedBookPhoto}"></a>
						</c:forEach>
					</c:otherwise>
				</c:choose>

				<c:url var="myBooksPage" value="/myBooks/showMyBooks">
					<c:param name="shelves" value="Want To Read" />
					<c:param name="readerId" value="${loggedReaderId}" />
				</c:url>
				<br> <a class="blueLink" href="${myBooksPage}">View all
					books...</a>
			</div>

			<div style="clear: both"></div>

			<hr class="leftSideHr">

			<c:url var="showRead" value="/myBooks/showMyBooks">
				<c:param name="shelves" value="Read" />
				<c:param name="readerId" value="${loggedReaderId}" />
			</c:url>

			<c:url var="showCurrentlyReading" value="/myBooks/showMyBooks">
				<c:param name="shelves" value="Currently Reading" />
				<c:param name="readerId" value="${loggedReaderId}" />
			</c:url>

			<c:url var="showWantToRead" value="/myBooks/showMyBooks">
				<c:param name="shelves" value="Want To Read" />
				<c:param name="readerId" value="${loggedReaderId}" />
			</c:url>

			<div id="bookShelves">
				<h3>Bookshelves</h3>
				<a class="blueLink" href="${showRead}">${read}&nbsp;Read</a><br>
				<a class="blueLink" href="${showCurrentlyReading}">
					${currentlyReading}&nbsp;Currently Reading</a><br> <a
					class="blueLink" href="${showWantToRead}">
					${wantToRead}&nbsp;Want to Read</a><br>
			</div>

		</div>

		<div id="middleContainer">

			<c:forEach var="post" items="${latestReaderbooks}" varStatus="status">

				<div class="whiteContainer">

					<div class="postHeader">

						<c:url var="postingReaderProfileRef" value="/profile/showProfile">
							<c:param name="readerId" value="${post.reader.id}" />
						</c:url>

						<a class="blackRef" href="${postingReaderProfileRef}"><span
							style="font-weight: bold">${post.reader.username}</span></a>&nbsp;has
						added new bookshelf
					</div>

					<c:url var="bookSite" value="/reader/showBook">
						<c:param name="bookId" value="${post.book.id}" />
					</c:url>

					<div class="postBookPhoto">
						<a href="${bookSite}"><img
							src="data:image/jpeg;base64,${post.book.encodedBookPhoto}"></a>
					</div>

					<div class="currentBookDetails">

						<div class="topDesc">
							<a class="blackRef" href="${bookSite}">${fn:substring(post.book.fullTitle,0,38)}
								<c:if test="${post.book.fullTitle.length() > 38}">...</c:if>
							</a>
						</div>
						by ${post.book.author}<br> bookshelves:
						${post.shelvesStates.shelveState()}

						<div class="currentBookDescription">
							${fn:substring(post.book.description,0,300)}...</div>
					</div>
					<div style="clear: both"></div>
				</div>

			</c:forEach>

		</div>

		<div id="rightSideContainer">
			<div id="recommendationDiv">

				<c:choose>
					<c:when test="${empty recommendedBook}">
					Help us to tune recommendations for you by choosing yours favorite genres!
					<div style="margin-top:15px;">
					<a class="blackRef" href="/loggedReader/showFavoriteGenres">Choose
							your favorite genres</a>
					</div>
					</c:when>
					<c:otherwise>
						<c:url var="recommendedBookPageRef" value="/reader/showBook">
							<c:param name="bookId" value="${recommendedBook.id}" />
						</c:url>

						<h3>RECOMMENDATIONS</h3>
						<div id="recommendedBookHeader">

							<div id="recommendedBookPhoto">
								<a href="${recommendedBookPageRef}"> <img
									src="data:image/jpeg;base64,${recommendedBook.encodedBookPhoto}">
								</a>
							</div>

							<div id="recommendedBookHeaderDesc">

								<h4 style="display: inline">
									<a class="blackRef" href="${recommendedBookPageRef}">${recommendedBook.miniTitle}</a>
								</h4>
								<br>
								<div style="margin-top: 10px">
									by ${recommendedBook.author}<br> <span
										style="color: #767676;">${recommendedBookRating}</span>
								</div>
							</div>
						</div>

						<div style="clear: both"></div>

						<div id="recommendedBookDesc">
							${fn:substring(recommendedBook.description,0,300)}... <a
								class="blueLink" href="${recommendedBookPageRef}">Continue
								reading</a>
						</div>

					</c:otherwise>

				</c:choose>



			</div>


		</div>


	</div>
</body>

</html>