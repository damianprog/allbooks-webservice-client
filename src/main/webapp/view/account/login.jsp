<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<title>Sign In</title>
<link type="text/css" rel="stylesheet" href="/css/account/login.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">

</head>

<body>
	<div id="container">
		<table id="title">
			<tr>
				<jsp:include page='/view/header.jsp' />
			</tr>
		</table>

		<div class="whiteContainer2">
			<h3>Sign In to Allbooks</h3>
			Sign In to see what your friends are reading, get book
			recommendations, and join the world's largest community of readers.

			<c:if test="${error != null}">
				<br>
				<span style="color: red">Wrong username or password</span>
			</c:if>

			<div id="loginTable">

				<form:form method="POST" action="/login">

					<table>
						<tr>
							<td>Name</td>
						</tr>
						<tr>
							<td><input name="username" type="text" placeholder="Name"
								class="joinInputBox" required="required" /></td>
						</tr>
						<tr>
							<td>Password</td>
						</tr>
						<tr>
							<td><input name="password" type="password"
								placeholder="Password" class="joinInputBox" required="required" /></td>
						</tr>

						<tr>
							<td id="joinSubmitTd"><input class="signIn" type="submit"
								value="Sign In" /></td>
						</tr>
					</table>

				</form:form>
			</div>
			<h3>
				<a class="blackRef" href="/reader/join">New here? Create free account!</a>
			</h3>
		</div>

	</div>
</body>

</html>