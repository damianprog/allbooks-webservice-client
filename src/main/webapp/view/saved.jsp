<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>

<html>

<head>
	<title>
		Register Results
	</title>
	<link type="text/css"
		  rel="stylesheet"
		  href="/css/saved.css"/>
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
			<c:when test="${success == true }">
				<h3>You've been successfully Registered</h3>
				<p><a href="/reader/main">Allbooks Home</a></p>
				<p>or</p>
				<p><a href="/login">Login Page</a></p>
			</c:when>
			<c:when test="${success == false }">
				<h3>This login is taken</h3>
				<p><a href="/reader/join">Go back to Register page</a></p>
				
			</c:when>
			<c:when test="${loggingError == true }">
				<h3>Login or password is not correct</h3>
				<p><a href="/login">Go back to Login page</a></p>
			</c:when>
			<c:when test="${passwordChanged == true}">
					<br>
					<span style="color: blue">Your Allbooks account password has been changed successfully!</span>
					<p><a href="/login">Go to Login page</a></p>
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