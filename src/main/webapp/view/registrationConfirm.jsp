<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<title>Register Results</title>
<link type="text/css" rel="stylesheet" href="/css/saved.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script src="/js/jquery-3.3.1.min.js"></script>
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
				<c:when test="${info == 'ALREADY_AUTHENTICATED'}">
					<h3>You are already authenticated!</h3>
					<p>
						<a href="/reader/main">Allbooks Home</a>
					</p>
				</c:when>

				<c:otherwise>
					<c:choose>
						<c:when test="${info == 'VALID_TOKEN'}">
							<h3>You've been successfully Authenticated!</h3>
							<p>
								<a href="/reader/main">Allbooks Home</a>
							</p>
							<p>or</p>
							<p>
								<a href="/login">Login Page</a>
							</p>
						</c:when>
						<c:when test="${info == 'INVALID_TOKEN'}">
							<h3>The token is invalid</h3>
							<p>
								<a href="/reader/main">Go back to Home page</a>
							</p>
						</c:when>
						<c:when test="${info == 'TOKEN_EXPIRED'}">

							<form id="resendForm" action="/readerAccount/resendVerificationToken" method="POST">
					
							<input type="hidden" name="readerId" value="${readerId}">
						
							<h3>The token has expired.Click on the link below to resend
								token to your email.</h3>
								
								<div id="resendSubmit">Resend
									verification token</div>
							
							</form>
							
						</c:when>
						
						<c:when test="${info == 'VERIFICATION_TOKEN_RESEND'}">

							<h3>New verification token has been resent to your email address</h3>
							<p>
								<a class="blackRef" href="/reader/main">Go to main page</a>
							</p>
						</c:when>
						
					</c:choose>
				</c:otherwise>


			</c:choose>

			<div id="sponsored">
				sponsored books
				<table>
					<tr>
						<td><img id="sBook1" src="/css/images/idParadox.jpg"></td>
						<td id="sBook1Desc">
							<h4>The Id Paradox</h4> How do we manage the primal beast within
							us? Is it an inevitable part of us we must accommodate or a
							vicious monster we must suppress?
						</td>
						<td><img id="sBook1" src="/css/images/wizardOfNod.jpg">
						</td>
						<td id="sBook1Desc">
							<h4>The Wizard of Nod</h4> "Those who enjoy Dan Brown or even
							(dare I say) Cassandra Clare should walk away satisfied. I am
							eagerly awaiting the third installment"
						</td>
					</tr>
				</table>
			</div>

		</div>

	</div>

<script type="text/javascript">
	
	$(document).ready(function(){
		
		$('#resendSubmit').click(function(){
			document.getElementById('resendForm').submit();
		});
		
	});
	

	
</script>

</body>

</html>