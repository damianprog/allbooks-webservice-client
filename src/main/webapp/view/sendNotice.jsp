<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Allbooks Review</title>
<link type="text/css" rel="stylesheet" href="/css/sendNotice.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script src="/js/jquery-3.3.1.min.js"></script>
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<c:url var="bookPageRef" value="/reader/showBook">
			<c:param name="bookId" value="${review.book.id}" />
		</c:url>

		<div id="noticeContainer">
			<h2>Send Notice</h2>
			<hr>

			<h3>To Reader: ${reader.username}</h3>
					
			<form action="/admin/sendNotice">
				
				<textarea id="noticeTextArea" name="noticeText"></textarea>
				<br>
				<input type="hidden" name="readerId" value="${reader.id}"/>
				
				<input id="send" type="submit" value="Send"> 
				
			</form>
					
		</div>
		
		
	</div>
</body>



</html>