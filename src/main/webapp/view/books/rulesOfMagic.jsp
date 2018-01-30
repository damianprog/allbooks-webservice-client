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
						src="/css/images/rulesOfMagicPage.jpg">
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
										<input type="hidden" name="bookName" value="rulesOfMagic">
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
									<input type="hidden" name="bookName" value="rulesOfMagic">
									<input type="hidden" name="update" value="${update}">
									<input type="submit" value="Submit" />
								</form:form>
							</c:when>

							<c:when test="${sessionScope.logged == false }">
								<a href="/reader/loginPage">Sign In</a> to Rate this book!
							</c:when>
						</c:choose></td>
					<td id="desc">
						<h3 id="h3">The Rules of Magic (Practical Magic #2)</h3>
						<p id="credit">by Damian Andersen (AllBooks Author)</p>
						<table>
							<tr>
								<td id="pInstead">Rating: ${overallRating}</td>
								<td id="ratingDetails">Rating Details ${rates} Ratings
									${reviews} Reviews</td>
							</tr>
						</table> For the Owens family, love is a curse that began in 1620, when
						Maria Owens was charged with witchery for loving the wrong man.

						Hundreds of years later, in New York City at the cusp of the
						sixties, when the whole world is about to change, Susanna Owens
						knows that her three children are dangerously unique. Difficult
						Franny, with skin as pale as milk and blood red hair, shy and
						beautiful Jet, who can read other people's thoughts, and
						charismatic Vincent, who began looking for trouble on the day he
						could walk. From the start Susanna sets down rules for her
						children: No walking in the moonlight, no red shoes, no wearing
						black, no cats, no crows, no candles, no books about magic. And
						most importantly, never, ever, fall in love. But when her children
						visit their Aunt Isabelle, in the small Massachusetts town where
						the Owens family has been blamed for everything that has ever gone
						wrong, they uncover family secrets and begin to understand the
						truth of who they are. Back in New York City each begins a risky
						journey as they try to escape the family curse. The Owens children
						cannot escape love even if they try, just as they cannot escape
						the pains of the human heart. The two beautiful sisters will grow
						up to be the revered, and sometimes feared, aunts in Practical
						Magic, while Vincent, their beloved brother, will leave an
						unexpected legacy.
						<hr> GET A COPY<br> <a target="blank"
						href="https://www.amazon.com/gp/product/B071Y37P87/ref=x_gr_w_bb?ie=UTF8&tag=x_gr_w_bb-20&linkCode=as2&camp=1789&creative=9325&creativeASIN=B071Y37P87&SubscriptionId=1MGPYB6YW3HWK55XCGG2">
							<button type="button">Amazon</button>
					</a>
						<hr>
						<div id="details">
							Kindle Edition, 369 pages<br> Published October 10th 2017 by
							Simon & Schuster
						</div>
					</td>
					<td id="aboutAuthor">
						<h4 id="h3">ABOUT Alice Hoffman</h4>
						<hr>
						<div>Alice Hoffman is the author of more than thirty works
							of fiction, including The Rules of Magic, The Marriage of
							Opposites, Practical Magic, The Red Garden, the Oprah's Book Club
							selection Here on Earth, The Museum of Extraordinary Things, and
							The Dovekeepers. She lives near Boston.</div>
						<div id="quotesFromBook">
							<h4 id="h3">QUOTES FROM The Rules of Magic</h4>
							<hr>
							<p>
								<i>"Other people's judgments were meaningless unless you
									allowed them to mean something."</i>
							</p>
							<p>
								<i>"Life is a mystery, and it should be so, for the sorrow
									that accompanies being human and the choices one will have to
									make are a burden, too heavy for most to know before their time
									comes."</i>
							</p>
						</div>
					</td>
					<td id="img2"><img
						src="/css/images/AliceHoffman.jpg">
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
						<input type="hidden" name="bookName" value="rulesOfMagic" />
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
								value="The Rules of Magic (Practical Magic #2)" />
							<c:param name="authorName" value=" Alice Hoffman" />
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
											<input type="hidden" name="bookName" value="rulesOfMagic" />
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