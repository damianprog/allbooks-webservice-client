<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<title>Sign In</title>
<link type="text/css" rel="stylesheet" href="/css/account/changePassword.css" />
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

				<c:when test="${isAllowedToChange == false}">
					<c:choose>
						<c:when test="${information == 'INVALID_TOKEN'}">
						<h3>The token is invalid.</h3>
						<p><a href="/reader/main">Allbooks home</a></p>
					</c:when>
						<c:when test="${information == 'EXPIRED_TOKEN'}">
						<h3>The token validation has expired.</h3>
						<p><a href="/reader/main">Allbooks home</a></p>
					</c:when>
					</c:choose>
					
					<div id="sponsored">
					sponsored books
					<table>
						<tr>
							<td>
								<img id="sBook1" src="/css/images/idParadox.jpg">
							</td>
							<td id="sBook1Desc">
								<h4>The Id Paradox</h4>
								How do we manage the primal beast within us? Is it an inevitable 
								part of us we must accommodate or a vicious monster we must suppress?
							</td>
							<td>
								<img id="sBook1" src="/css/images/wizardOfNod.jpg">
							</td>
							<td id="sBook1Desc">
								<h4>The Wizard of Nod</h4>
								"Those who enjoy Dan Brown or even (dare I say) Cassandra Clare should walk away 
								satisfied. I am eagerly awaiting the third installment"
							</td>
						</tr>
					</table>
				</div>
					
				</c:when>

				<c:otherwise>
					<h3>Change your Allbooks password</h3>
			Now you can change your Allbooks Account password
			
			<div id="loginTable">

						<form:form method="POST" action="/readerAccount/changePassword">

							<table>
								<tr>
									<td>Allbooks Password</td>
								</tr>
								<tr>
									<td><input name="password" type="password"
										placeholder="Password" class="joinInputBox"
										required="required" /> <input name="readerId" type="hidden"
										value="${readerId}" /></td>

								</tr>

								<tr>
									<td id="joinSubmitTd"><input class="signIn" type="submit"
										value="Change" /></td>
								</tr>
							</table>
							<input type="hidden" name="readerId" value="${reader.id}">
						</form:form>
					</div>
				</c:otherwise>

			</c:choose>
		</div>

	</div>
</body>

</html>