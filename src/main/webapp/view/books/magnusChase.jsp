<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html lang="eng">

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>All Books</title>

<link type="text/css" rel="stylesheet"
	href="/css/book.css" />
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
					<td id="img"><img
						src="/css/images/magnusChasePage.jpg">
						<br> <c:choose>
							<c:when
								test="${(sessionScope.logged == true) and (userRated == false)}">
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
										<input type="hidden" name="bookName" value="magnusChase">
										<input type="submit" value="Submit" />
									</form:form>
								</div>
							</c:when>
							<c:when
								test="${sessionScope.logged == true and (userRated == true)}">
									Your rate ${readerRating}
									</c:when>
						</c:choose> <c:choose>

							<c:when test="${sessionScope.logged == true }">
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
									<input type="hidden" name="bookName" value="magnusChase">
									<input type="hidden" name="update" value="${update}">
									<input type="submit" value="Submit" />
								</form:form>
							</c:when>

							<c:when test="${sessionScope.logged == false }">
								<a href="/reader/loginPage">Sign In</a> to Rate this book!
							</c:when>
						</c:choose></td>
					<td id="desc">
						<h3 id="h3">The Ship of the Dead (Magnus Chase and the Gods
							of Asgard #3)</h3>
						<p id="credit">by Damian Andersen (AllBooks Author)</p>
						<table>
							<tr>
								<td id="pInstead">Rating: ${overallRating}</td>
								<td id="ratingDetails">Rating Details ${rates} Ratings
									${reviews} Reviews</td>
							</tr>
						</table> Magnus Chase, a once-homeless teen, is a resident of the Hotel
						Valhalla and one of Odin's chosen warriors. As the son of Frey,
						the god of summer, fertility, and health, Magnus isn't naturally
						inclined to fighting. But he has strong and steadfast friends,
						including Hearthstone the elf, Blitzen the dwarf, and Samirah the
						Valkyrie, and together they have achieved brave deeds, such as
						defeating Fenris Wolf and battling giants for Thor's hammer,
						Mjolnir. Now Magnus and his crew must sail to the farthest borders
						of Jotunheim and Niflheim in pursuit of Asgard's greatest threat.
						Will they succeed in their perilous journey, or is Ragnarok
						lurking on the horizon?
						<hr> BUY A COPY<br> <a target="blank"
						href="https://www.amazon.com/gp/product/1423160932/ref=x_gr_w_bb?ie=UTF8&tag=x_gr_w_bb-20&linkCode=as2&camp=1789&creative=9325&creativeASIN=1423160932&SubscriptionId=1MGPYB6YW3HWK55XCGG2">
							<button type="button">Amazon</button>
					</a>
						<hr>
						<div id="details">
							Hardcover, 423 pages<br> Published October 3rd 2017 by
							Disney-Hyperion
						</div>
					</td>
					<td id="aboutAuthor">
						<h4 id="h3">ABOUT RICK RIORDAN</h4>
						<hr>
						<div>Rick Riordan is the #1 New York Times bestselling
							author of many books, including the Percy Jackson & the Olympians
							series. Please follow him on Twitter and via his official blog.</div>
						<div id="quotesFromBook">
							<h4 id="h3">QUOTES FROM THE SHIP OF THE DEAD</h4>
							<hr>
							<p>
								<i>"You're a strange person." "I prefer the term fabulously
									weird."</i>
							</p>
							<p>
								<i>"I figured something out. You can't hold onto hate
									forever. It won't do a thing to the person you hate, but it'll
									poison you, sure enough."</i>
							</p>
						</div>
					</td>
					<td id="img2"><img
						src="/css/images/RickRiordan.jpg">
					</td>
				</tr>
			</table>
		</div>

		<div id="communityReviews">
			<h4 id="h3">COMMUNITY REVIEWS</h4>
			<hr>

			<c:choose>
				<c:when test="${sessionScope.logged == true }">
					<form:form action="submitReview" modelAttribute="review"
						method="GET">
					Title of Review<br>
						<form:input id="reviewTitle" path="title" required="requierd" />
						<br>
					Content<br>
						<form:textarea id="reviewBox" path="text" required="required" />
						<br>
						<input type="hidden" name="bookName" value="magnusChase" />
						<input type="submit" value="Submit">
					</form:form>
				</c:when>
				<c:when test="${sessionScope.logged == false }">
					<a href="/reader/loginPage">Sign In to post a
						review!</a>
				</c:when>
			</c:choose>
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
								value="The Ship of the Dead (Magnus Chase and the Gods of Asgard)" />
							<c:param name="authorName" value="Rick Riordan" />
						</c:url>

						<c:url var="profileLink" value="/profile/showProfile">
							<c:param name="readerId" value="${tempReview.readerId}" />
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
							<td id="spaceUnder">Likes: ${tempReview.likes} <c:choose>
									<c:when test="${sessionScope.logged == true}">
										<form:form action="/reader/dropLike"
											method="GET" id="likeForm">
											<input type="hidden" name="reviewId" value="${tempReview.id}" />
											<input type="hidden" name="bookName" value="magnusChase" />
											<input type="submit" value="Like" />
										</form:form>
									</c:when>
									<c:when test="${sessionScope.logged == false}">
										<form:form action="/reader/loginPage"
											method="GET" id="likeForm">
											<input type="submit" value="Like" />
										</form:form>
									</c:when>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>

		</div>

	</div>
</body>

</html>