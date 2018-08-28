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
<link type="text/css" rel="stylesheet" href="/css/admin/deleteComment.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script src="/js/jquery-3.3.1.min.js"></script>
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<h3>Comment</h3>

		<div id="commentAuthorPhoto">
			<img
				src="data:image/jpeg;base64,${comment.postingReader.encodedProfilePhoto}">
		</div>

		<div id="comment">

			<c:url var="readerProfileRef" value="/profile/showProfile">
				<c:param name="readerId" value="${comment.postingReader.id}" />
			</c:url>

			<div id="postingReaderUsername">
				<a class="blackRef" href="${readerProfileRef}">${comment.postingReader.username}</a>
			</div>

			<div id="commentText">${comment.text}</div>

		</div>

		<div style="clear: both"></div>

		<div id="explanation">

			Reason

			<form action="/admin/deleteComment" method="GET">
				<textarea id="reasonTextArea" name="reasonText" required="required"></textarea>
				<br> <input type="hidden" name="commentType"
					value="${commentType}" />
					<input type="hidden" name="commentId"
					value="${comment.id}" /><input id="remove" type="submit"
					value="Remove">
			</form>

		</div>

	</div>


	<div style="clear:both"></div>
</body>



</html>