<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<title>Sign In</title>
<link type="text/css" rel="stylesheet" href="/css/saved.css" />
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
			<c:choose>
				<c:when test="${empty changing}">
					<h3>You forgot your Allbooks password</h3>
			Enter your email so we can send you link to page where you can change
			your password
			</c:when>
				<c:otherwise>
					<h3>Change your Allbooks password</h3>
			Now you can change your Allbooks Account password
			</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${!empty error}">
					<br>
					<span style="color: red">There is no reader with email like
						this</span>
				</c:when>

				<c:when test="${!empty success}">
					<br>
				The email has been sent to the given email address.
			</c:when>

				<c:when test="${!empty invalidToken}">
					<br>
					<span style="color: red">The token is invalid</span>
				</c:when>

				<c:when test="${!empty tokenSent}">
					<br>
					<span style="color: red">The token has been already sent to
						this email!</span>
				</c:when>
				
			</c:choose>
			<div id="loginTable">

				<c:choose>
					<c:when test="${!empty changing}">
						<form:form method="POST" action="/readerAccount/changePassword">

							<table>
								<tr>
									<td>Allbooks Password</td>
								</tr>
								<tr>
									<td><input name="password" type="password"
										placeholder="Password" id="joinInputBox" required="required" />
										<input name="readerId" type="hidden" value="${readerId}" /></td>

								</tr>

								<tr>
									<td id="joinSubmitTd"><input class="signIn" type="submit"
										value="Change" /></td>
								</tr>
							</table>
							<input type="hidden" name="readerId" value="${reader.id}">
						</form:form>
					</c:when>
					<c:otherwise>

						<form:form method="POST" action="/readerAccount/forgotPassword">

							<table>
								<tr>
									<td>Allbooks Email</td>
								</tr>
								<tr>
									<td><input name="email" type="text"
										placeholder="your@yours.com" id="joinInputBox"
										required="required" /></td>
								</tr>

								<tr>
									<td id="joinSubmitTd"><input class="signIn" type="submit"
										value="Send email" /></td>
								</tr>
							</table>
						</form:form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

	</div>
</body>

</html>