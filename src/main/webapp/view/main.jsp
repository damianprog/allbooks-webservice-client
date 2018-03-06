<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html lang="eng">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All Books</title>

<link type="text/css" rel="stylesheet"
	href="/css/style.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">

			<jsp:include page='/view/header.jsp' />
			
		<div style=""></div>

		<hr>

		<div id="meet">
			<img
				src="/css/images/meet.png">
		</div>

		<sec:authorize access="!isFullyAuthenticated()">
				<div id="mainRegister">

					<div id="newHere">
						<p>New here? Create a free account!</p>
					</div>

					<form:form action="saveReader" modelAttribute="reader"
						method="POST">
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
						<img
						src="/css/images/rowling.jpg">
					</td>
						<td id="quoteTd">
						"It is our choices... that show what we truly are, far more than our abilities."
						<p>~ J.K. Rowling</p>
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
				<td id="browse">
					<h3>New Releases in Fantasy</h3> <c:url var="magnusChase"
						value="showBook">
						<c:param name="bookName" value="The Ship of the Dead" />
					</c:url> <c:url var="rulesOfMagic" value="showBook">
						<c:param name="bookName" value="The Rules of Magic" />
					</c:url> <c:url var="bookOfDust" value="showBook">
						<c:param name="bookName" value="La Belle Sauvage" />
					</c:url> <c:url var="crookedSaints" value="showBook">
						<c:param name="bookName" value="All the Crooked Saints " />
					</c:url> <a href="${magnusChase }"><img class="minBrowse"
						src="/css/images/mmagnusChase.jpg" /></a>
					<a href="${rulesOfMagic }"><img class="minBrowse"
						src="/css/images/mrulesOfMagic.jpg" /></a>
					<a href="${bookOfDust }"><img class="minBrowse"
						src="/css/images/mbookOfDust.jpg" /></a>
					<a href="${crookedSaints }"><img class="minBrowse"
						src="/css/images/mcrookedSaints.jpg" /></a>
				</td>
			</tr>
		</table>

		<div id="add">
			<a target="_blank"
				href="https://www.jetbrains.com/rider/?gclid=EAIaIQobChMI_8SygpPQ1wIVyYEZCh22lg5sEAEYASAAEgJ7rvD_BwE">
				<img
				src="/css/images/add.png" />
			</a>
		</div>

	</div>


</body>

</html>