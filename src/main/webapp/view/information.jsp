<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>

<html>

<head>
	<title>
		Information
	</title>
	<link type="text/css"
		  rel="stylesheet"
		  href="/css/information.css"/>
	<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC" rel="stylesheet">
	
</head>

<body>
	
	<div id="container">
	<table id="title">
	<tr>
		<jsp:include page='/view/header.jsp' />
	</tr>
	</table>
	
	<div class="whiteContainer">
		<c:choose>
			<c:when test="${information == 'SUCCESSFULLY_REGISTERED'}">
				<h3>You've been successfully Registered</h3>
				<p>The authentication link has been sent to given email</p>
				<p><a class="blackRef" href="/visitor/main">Allbooks home</a></p>
			</c:when>
			<c:when test="${information == 'LOGIN_TAKEN'}">
				<h3>This login is taken</h3>
				<p><a class="blackRef" href="/reader/join">Go back to Register page</a></p>
				
			</c:when>
			<c:when test="${information == 'PASSWORD_CHANGED'}">
					<br>
					<span style="color: blue">Your Allbooks account password has been changed successfully!</span>
					<p><a href="/login">Go to Login page</a></p>
				</c:when>
			<c:when test="${information == 'ACCESS_DENIED'}">
				<br>
				<span style="color: blue">You don't have access to this page.</span>
				<p><a class="blackRef" href="/visitor/main">Allbooks home</a></p>
			</c:when>
			<c:when test="${information == 'NOT_FOUND'}">
				<h3>Error 404 Not Found.</h3>
				<p><a href="/visitor/main">Allbooks Home</a></p>
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
		
	</div>
	
	</div>
	
	
</body>

</html>