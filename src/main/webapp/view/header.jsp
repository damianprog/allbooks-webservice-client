<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/header.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="headerContainer">

		<table id="title">
			<tr>
				<td id="titleImg"><img src="/css/images/bookshelf.png"></td>
				<td id="allbooksName"><a id="allBooks" href="/reader/main">All
						Books</a></td>

				<td id="myBooks"><sec:authorize access="isFullyAuthenticated()">

						<c:url var="myBooks" value="/reader/showMyBooks">
							<c:param name="myBooks" value="true" />
						</c:url>

						<a id="myBooksRef" href="${myBooks}">My Books</a>
					</sec:authorize></td>

				<sec:authorize access="isFullyAuthenticated()">
					<td id="loginPart"><c:url var="showProfile"
							value="/profile/showProfile">
							<c:param name="guest" value="false" />
						</c:url> Profile:<a class="login" href="${showProfile}"><sec:authentication
								property="principal.username" /></a></td><td> <form:form method="POST"
							action="/logout">
							<input class="logoutHeader" type="submit" value="Logout" />
						</form:form>
						</td>
				</sec:authorize>

				<sec:authorize access="!isFullyAuthenticated()">

					<form:form method="POST" action="/login">
						<table id="mainLogin">
							<tr>
								<td><input type="text" placeholder="Login" id="inputBox"
									required="required" name="username" /></td>
								<td><input type="password" placeholder="Password"
									id="inputBox" required="required" name="password" /></td>
								<td><input class="signInHeader" type="submit"
									value="Sign In" /></td>
							</tr>
							<tr>
								<td id="cbMain"><input type="checkbox" name="cb"
									value="remember" /> Remember me</td>
								<td id="cbMainForgot"><a href="/reader/loginPage">Forgot
										it?</a></td>
							</tr>
						</table>
					</form:form>
				</sec:authorize>
			</tr>
		</table>


	</div>
</body>

</html>