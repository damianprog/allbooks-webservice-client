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
		  href="/css/account/invitationLink.css"/>
	<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC" rel="stylesheet">
	<script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
	<script src="/js/automaticCopy.js"></script>
</head>

<body>
	
	<div id="container">
	<table id="title">
	<tr>
		<jsp:include page='/view/header.jsp' />
	</tr>
	</table>
	
	<div class="whiteContainer">
				<h3>Share this friend link with others</h3>
				<p>Invite Link:</p>
				<p><input type="text" value="${tokenUrl}" id="tokenInput" readonly></p>
				<p>This link will expire after 30 days.</p>
				
				<a href="#" class="copyLink">Copy link to clipboard</a>
				</div>
		
	</div>
	
	
	
</body>

</html>