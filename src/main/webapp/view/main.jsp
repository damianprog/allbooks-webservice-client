<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html lang="eng">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All Books</title>

<link type="text/css" rel="stylesheet" href="/css/style.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">

</head>

<body>



	<div id="container">

		<jsp:include page='/view/header.jsp' />


		<hr>

		<div id="meet">
			<img src="/css/images/meet.png">
		</div>

		<sec:authorize access="!isFullyAuthenticated()">
			<div id="mainRegister">

				<div id="newHere">
					<p>New here? Create a free account!</p>
				</div>

				<form:form action="/reader/saveReader" modelAttribute="reader" method="POST">
					<table>
						<tr>
							<td><form:input path="username" placeholder="Name"
									id="inputRegister" required="required" /></td>
						</tr>
						<tr>
							<td><form:password path="password" placeholder="Password"
									id="inputRegister" required="required" /></td>
						</tr>
						<tr>
							<td><form:input path="email" placeholder="Email Address"
									id="inputRegister" required="required" /></td>
						</tr>
						<tr>
							<td><input class="submit" type="submit" value="Sign Up" />
							</td>
						</tr>
					</table>
				</form:form>
			</div>
		</sec:authorize>
		<sec:authorize access="isFullyAuthenticated()">
			<div id="quote">
				<table>
					<tr>
						<td>
							<div class="mySlides">
								<div style="float: left">
									<img src="/css/images/rowling.jpg">
								</div>
								<div id="wrappedQuotes">
									"It is our choices... that show what we truly are, far more
									than our abilities."
									<p>~ J.K. Rowling</p>
								</div>
							</div>

							<div class="mySlides">
								<div style="float: left">
									<img src="/css/images/wilde.jpg">
								</div>
								<div id="wrappedQuotes">
									"Be yourself everyone else is already taken"
									<p>~ Oscar Wilde</p>
								</div>
							</div>

							<div class="mySlides">
								<div style="float: left">
									<img src="/css/images/camus.jpg">
								</div>
								<div id="wrappedQuotes">
									"Don't walk in front of me... I may not follow Don't walk
									behind me... I may not lead Walk beside me... just be my
									friend"
									<p>~ Albert Camus</p>
								</div>
							</div>

							<div class="mySlides">
								<div style="float: left">
									<img src="/css/images/zappa.jpg">
								</div>
								<div id="wrappedQuotes">
									"So many books, so little time."
									<p>~ Frank Zappa</p>
								</div>
							</div>

							<div class="mySlides">
								<div style="float: left">
									<img src="/css/images/west.jpg">
								</div>
								<div id="wrappedQuotes">
									"You only live once, but if you do it right, once is enough."
									<p>~ Mae West</p>
								</div>
							</div>

							<div style="clear: both"></div>
						</td>
					</tr>
				</table>
			</div>
		</sec:authorize>
		<hr>

		<div style=""></div>

		<table id="descs">

			<tr>
				<td class="smallDesc">
					<h3>Deciding what to read next?</h3> You're in the right place.
					Tell us what titles or genres you've enjoyed in the past, and we'll
					give you surprisingly insightful recommendations.
				</td>

				<td class="smallDesc">
					<h3>What are your friends reading?</h3> Chances are your friends
					are discussing their favorite (and least favorite) books on
					Allbooks.
				</td>
			</tr>

			<tr>
				<td>

					<h3>Search and browse books</h3>

					<div id="searchDiv">
						<form action="/visitor/searchBooks">
							<input id="searchBar" name="phrase" placeholder="Search books">
							<input id="searchButton" type="submit" value="Search">

						</form>
					</div>

					<div id="categoriesBox" style="">
						<div class="categoriesPart">
							<c:url var="art" value="showCategory">
								<c:param name="categoryName" value="art"></c:param>
							</c:url>
							<a class="blueLink" href="${art}">Art</a><br>
							<c:url var="biography" value="showCategory">
								<c:param name="categoryName" value="biography"></c:param>
							</c:url>
							<a class="blueLink" href="${biography}">Biography</a><br>
							<c:url var="business" value="showCategory">
								<c:param name="categoryName" value="business"></c:param>
							</c:url>
							<a class="blueLink" href="${business}">Business</a><br>
							<c:url var="children" value="showCategory">
								<c:param name="categoryName" value="children"></c:param>
							</c:url>
							<a class="blueLink" href="${children}">Children's</a><br>
							<c:url var="christian" value="showCategory">
								<c:param name="categoryName" value="christian"></c:param>
							</c:url>
							<a class="blueLink" href="${christian}">Christian</a><br>
							<c:url var="classics" value="showCategory">
								<c:param name="categoryName" value="classics"></c:param>
							</c:url>
							<a class="blueLink" href="${classics}">Classics</a><br>
							<c:url var="comics" value="showCategory">
								<c:param name="categoryName" value="comics"></c:param>
							</c:url>
							<a class="blueLink" href="${comics}">Comics</a><br>
							<c:url var="cookbooks" value="showCategory">
								<c:param name="categoryName" value="cookbooks"></c:param>
							</c:url>
							<a class="blueLink" href="${cookbooks}">Cookbooks</a><br>
						</div>
						<div class="categoriesPart">
							<c:url var="ebooks" value="showCategory">
								<c:param name="categoryName" value="ebooks"></c:param>
							</c:url>
							<a class="blueLink" href="${ebooks}">Ebooks</a><br>
							<c:url var="fantasy" value="showCategory">
								<c:param name="categoryName" value="fantasy"></c:param>
							</c:url>
							<a class="blueLink" href="${fantasy}">Fantasy</a><br>
							<c:url var="fiction" value="showCategory">
								<c:param name="categoryName" value="fiction"></c:param>
							</c:url>
							<a class="blueLink" href="${fiction}">Fiction</a><br>
							<c:url var="graphicNovels" value="showCategory">
								<c:param name="categoryName" value="graphicNovels"></c:param>
							</c:url>
							<a class="blueLink" href="${graphicNovels}">Graphic Novels</a><br>
							<c:url var="historicalFiction" value="showCategory">
								<c:param name="categoryName" value="historicalFiction"></c:param>
							</c:url>
							<a class="blueLink" href="${historicalFiction}">Historical
								Fiction</a><br>
							<c:url var="history" value="showCategory">
								<c:param name="categoryName" value="history"></c:param>
							</c:url>
							<a class="blueLink" href="${history}">History</a><br>
							<c:url var="horror" value="showCategory">
								<c:param name="categoryName" value="horror"></c:param>
							</c:url>
							<a class="blueLink" href="${horror}">Horror</a><br>
							<c:url var="memoir" value="showCategory">
								<c:param name="categoryName" value="memoir"></c:param>
							</c:url>
							<a class="blueLink" href="${memoir}">Memoir</a><br>
						</div>
						<div id="categoriesBox" style="">
							<div class="categoriesPart">
								<c:url var="art" value="showCategory">
									<c:param name="categoryName" value="art"></c:param>
								</c:url>
								<a class="blueLink" href="${art}">Art</a><br>
								<c:url var="biography" value="showCategory">
									<c:param name="categoryName" value="biography"></c:param>
								</c:url>
								<a class="blueLink" href="${biography}">Biography</a><br>
								<c:url var="business" value="showCategory">
									<c:param name="categoryName" value="business"></c:param>
								</c:url>
								<a class="blueLink" href="${business}">Business</a><br>
								<c:url var="children" value="showCategory">
									<c:param name="categoryName" value="children"></c:param>
								</c:url>
								<a class="blueLink" href="${children}">Children's</a><br>
								<c:url var="christian" value="showCategory">
									<c:param name="categoryName" value="christian"></c:param>
								</c:url>
								<a class="blueLink" href="${christian}">Christian</a><br>
								<c:url var="classics" value="showCategory">
									<c:param name="categoryName" value="classics"></c:param>
								</c:url>
								<a class="blueLink" href="${classics}">Classics</a><br>
								<c:url var="comics" value="showCategory">
									<c:param name="categoryName" value="comics"></c:param>
								</c:url>
								<a class="blueLink" href="${comics}">Comics</a><br>
								<c:url var="cookbooks" value="showCategory">
									<c:param name="categoryName" value="cookbooks"></c:param>
								</c:url>
								<a class="blueLink" href="${cookbooks}">Cookbooks</a><br>
							</div>
							<div class="categoriesPart">
								<c:url var="ebooks" value="showCategory">
									<c:param name="categoryName" value="ebooks"></c:param>
								</c:url>
								<a class="blueLink" href="${ebooks}">Ebooks</a><br>
								<c:url var="fantasy" value="showCategory">
									<c:param name="categoryName" value="fantasy"></c:param>
								</c:url>
								<a class="blueLink" href="${fantasy}">Fantasy</a><br>
								<c:url var="fiction" value="showCategory">
									<c:param name="categoryName" value="fiction"></c:param>
								</c:url>
								<a class="blueLink" href="${fiction}">Fiction</a><br>
								<c:url var="graphicNovels" value="showCategory">
									<c:param name="categoryName" value="graphicNovels"></c:param>
								</c:url>
								<a class="blueLink" href="${graphicNovels}">Graphic Novels</a><br>
								<c:url var="historicalFiction" value="showCategory">
									<c:param name="categoryName" value="historicalFiction"></c:param>
								</c:url>
								<a class="blueLink" href="${historicalFiction}">Historical
									Fiction</a><br>
								<c:url var="history" value="showCategory">
									<c:param name="categoryName" value="history"></c:param>
								</c:url>
								<a class="blueLink" href="${history}">History</a><br>
							</div>
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<td id="browse">
					<h3>New Releases in Fantasy</h3> <c:url var="magnusChase"
						value="/visitor/showBook">
						<c:param name="bookId" value="5" />
					</c:url> <c:url var="rulesOfMagic" value="/visitor/showBook">
						<c:param name="bookId" value="6" />
					</c:url> <c:url var="bookOfDust" value="/visitor/showBook">
						<c:param name="bookId" value="7" />
					</c:url> <c:url var="crookedSaints" value="/visitor/showBook">
						<c:param name="bookId" value="8" />
					</c:url> <a href="${magnusChase }"><img class="minBrowse"
						src="/css/images/mmagnusChase.jpg" /></a> <a href="${rulesOfMagic }"><img
						class="minBrowse" src="/css/images/mrulesOfMagic.jpg" /></a> <a
					href="${bookOfDust }"><img class="minBrowse"
						src="/css/images/mbookOfDust.jpg" /></a> <a href="${crookedSaints }"><img
						class="minBrowse" src="/css/images/mcrookedSaints.jpg" /></a>
				</td>
			</tr>
		</table>

		<div id="add">
			<a target="_blank"
				href="https://www.jetbrains.com/rider/?gclid=EAIaIQobChMI_8SygpPQ1wIVyYEZCh22lg5sEAEYASAAEgJ7rvD_BwE">
				<img src="/css/images/add.png" />
			</a>
		</div>

	</div>

	<script src="/js/slider.js"></script>

</body>

</html>