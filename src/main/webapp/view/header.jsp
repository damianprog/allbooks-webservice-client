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

<link href="https://fonts.googleapis.com/css?family=Lato:700" rel="stylesheet"> 
</head>

<body>

	<div id="headerContainer">

		<table id="titleMain">
			<tr>
				<td id="titleImg"><img src="/css/images/bookshelf.png"></td>
				<td id="allbooksName"><a id="allBooks" href="/reader/main">All
						Books</a></td>
				
				<td id="homeRef">
					<a class="blackRef" href="/reader/main">Home</a>
				</td>
				
				<td class="pageRef"><sec:authorize
						access="isFullyAuthenticated()">

						<a class="blackRef" href="/reader/showMyBooks">My Books</a>
					</sec:authorize></td>

				<sec:authorize access="hasAuthority('ADMIN')">
					<td class="pageRef"><c:url var="addBook"
							value="/admin/addBookPage">
						</c:url> <a class="blackRef" href="${addBook}">Add Book</a></td>
				</sec:authorize>
				<sec:authorize access="isFullyAuthenticated()">
					<td id="loginPart"><c:url var="showProfile"
							value="/profile/showProfile">
						</c:url> Reader:<a class="login" href="${showProfile}"><sec:authentication
								property="principal.username" /></a></td>
					<td><form:form method="POST" action="/logout">
							<input class="logoutHeader" type="submit" value="Logout" />
						</form:form></td>
				</sec:authorize>

				<sec:authorize access="!isFullyAuthenticated()">

					<form:form method="POST" action="/login">
					<td>
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
								<td id="cbMainForgot"><a href="/reader/login">Forgot
										it?</a></td>
							</tr>
						</table>
					</td>
					</form:form>
				</sec:authorize>
			</tr>
		</table>


	</div>
</body>

</html>