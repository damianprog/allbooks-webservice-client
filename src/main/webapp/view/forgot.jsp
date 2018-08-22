<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<title>Sign In</title>
<link type="text/css" rel="stylesheet" href="/css/forgot.css" />
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
					<h3>You forgot your Allbooks password</h3>
			Enter your email so we can send you link to page where you can change
			your password
			<c:choose>
				<c:when test="${information == 'EMAIL_ERROR'}">
					<br>
					<span style="color: red">There is no reader with email like
						this</span>
				</c:when>

				<c:when test="${information == 'TOKEN_SENT'}">
					<br>
				The email has been sent to the given email address.
			</c:when>

				<c:when test="${information == 'ALREADY_SENT'}">
					<br>
					<span style="color: red">The token has been already sent to
						this email!</span>
				</c:when>
				
			</c:choose>
			<div id="loginTable">

						<form:form method="POST" action="/readerAccount/forgotPassword">

							<table>
								<tr>
									<td>Allbooks Email</td>
								</tr>
								<tr>
									<td><input name="email" type="text"
										placeholder="your@yours.com" class="joinInputBox"
										required="required" /></td>
								</tr>

								<tr>
									<td id="joinSubmitTd"><input class="signIn" type="submit"
										value="Send email" /></td>
								</tr>
							</table>
						</form:form>
			</div>
		</div>

	</div>
</body>

</html>