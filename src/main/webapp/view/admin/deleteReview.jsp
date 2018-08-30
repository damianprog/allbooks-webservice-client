<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.allbooks.org/tags" prefix="ei"%>
<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Allbooks Review</title>
<link type="text/css" rel="stylesheet" href="/css/admin/deleteReview.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script src="/js/jquery-3.3.1.min.js"></script>
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<c:url var="bookPageRef" value="/visitor/showBook">
			<c:param name="bookId" value="${review.book.id}" />
		</c:url>

		<div id="reviewDiv">
			<div id="header">
				<table>
					<tr>
						<td>
							<h3>${review.postingReader.username}'sReviews>
								${review.book.fullTitle}</h3>
						</td>
					</tr>
				</table>
			</div>
			<div id="imgReview">
				<a class="blackRef" href="${bookPageRef}"><img
					src="<ei:image image='${review.book.bookPhoto}' width='150' height='225'/>"/></a> <br>

			</div>
			<div id="reviewDesc">

				<h4 style="margin-top: -5px; margin-bottom: -2px;">
					<a class="blackRef" href="${bookPageRef}">${review.book.fullTitle}</a>
				</h4>
				by ${authorName}<br> ${review.postingReader.username}'s Review<br>
				<div id="reviewText">
					<p>${review.text }</p>
				</div>
				<div id="explanation">

				Reason

					<form action="/admin/deleteReview" method="GET">
						<textarea id="reasonTextArea" name="reasonText"
							required="required"></textarea>
						<br> <input type="hidden" name="reviewId"
							value="${review.id}" />
							<input type="hidden" name="bookId"
							value="${review.book.id}" /> <input id="remove" type="submit"
							value="Remove">
					</form>

				</div>

			</div>

		</div>

		<div style="clear: both">

		</div>
	</div>
</body>



</html>