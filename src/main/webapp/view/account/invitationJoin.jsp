<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.allbooks.org/tags" prefix='ei'%>

<!DOCTYPE html>

<html>

<head>
<title>Join</title>
<link type="text/css" rel="stylesheet" href="/css/account/join.css" />
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

		<div id="invitingReaderPhoto">
			<img src="<ei:image image='${invitingReader.profilePhoto}' width='100' height='100'/>"/>
		</div>

		<div id="rightSide">

		<div id="headerInfo">

			<h3>Sign up for Allbooks</h3>

			Sign up to see ${invitingReader.username}'s ${readerBooksList.size()} books

			<p>Sign up with email</p>
		</div>
			<div id="invitationJoinTable">

				<form:form action="saveReaderInvitation" modelAttribute="reader" method="POST">

					<table>
						<tr>
							<td>Name</td>
						</tr>
						<tr>
							<td><form:errors path="username" cssClass="error" /></td>
						</tr>
						<tr>
							<td><form:input path="username" placeholder="Name"
									class="joinInputBox" required="required" /></td>
						</tr>
						<tr>
							<td>Password</td>
						</tr>
						<tr>
							<td><form:errors path="password" cssClass="error" /></td>
						</tr>
						<tr>
							<td><form:password path="password" placeholder="Password"
									class="joinInputBox" required="required" /></td>
						</tr>
						<tr>
							<td>Email</td>
						</tr>
						<tr>
							<td><form:errors path="email" cssClass="error" /></td>
						</tr>
						<tr>
							<td><form:input path="email" placeholder="you@yours.com"
									class="joinInputBox" required="required" /></td>
						</tr>
						<tr>
							<td id="joinSubmitTd"><input class="signUp" type="submit"
								value="Sign up" /></td>
						</tr>
					</table>
					<input type="hidden" name="invitingReaderId" value="${invitingReader.id}">
				</form:form>
			</div>
</div> <div style="clear:both"></div>
			<div id="smallText">By clicking "Sign up" I agree to the
				Allbooks Terms of Service and confirm that I am at least 13 years of
				age. Read our Privacy Policy.</div>
		</div>
	</div>

</body>

</html>