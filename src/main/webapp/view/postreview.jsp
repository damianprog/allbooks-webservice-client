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
<link type="text/css" rel="stylesheet" href="/css/postreview.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>
	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<div>
			<form:form action="/bookActions/postReview" modelAttribute="review"
				method="POST">
				<table>
					<tr>
						<td>
							<h3>${book.fullTitle}>Review</h3> <input type="hidden"
							name="bookId" value="${book.id}">
						</td>
					</tr>
					<tr>

						<td id="img">
							<div id="imgRateReview">
								<img src="data:image/jpeg;base64,${bookPic}">


							</div>
							<div id="reviewDesc">
								<h4>${book.fullTitle}</h4>
								by ${book.author}<br> (Allbooks author)<br>

							</div>
						</td>
					</tr>
					<tr>
						<td><hr></td>
					</tr>
					<tr>
						<td><c:choose>
								<c:when test="${rating.id == 0}">
										My Rating 
								</c:when>
								<c:otherwise>
								My Rating ${rating.rate}
								</c:otherwise>
							</c:choose> <select class="rounded" name="rate">
								<option value="1" label="1" />
								<option value="2" label="2" />
								<option value="3" label="3" />
								<option value="4" label="4" />
								<option value="5" label="5" selected="selected" />

						</select> <input type="hidden" name="ratingId" value="${rating.id}" /></td>

					</tr>
					<tr>
						<td><c:choose>
								<c:when test="${readerBook.id == 0}">
									<br>Add this book to your books
									<input type="hidden" name="isItUpdateReaderBook" value="false">
								</c:when>
								<c:otherwise>
									<br>Current State:${readerBook.shelves}
									<input type="hidden" name="isItUpdateReaderBook" value="true">

								</c:otherwise>
							</c:choose> <select class="rounded" name="shelves">
								<option value="Read" label="Read" />
								<option value="Currently Reading" label="Currently Reading" />
								<option value="Want to Read" label="Want to Read" />
						</select> <input type="hidden" name="readerBookId" value="${readerBook.id}" /></td>

					</tr>
					<tr>
						<td><hr></td>
					</tr>
					<tr>
						<td style="padding-bottom: 15px;">What did you think?</td>
					</tr>
					<tr>
						<td>Title of Review<br> <form:input path="title"
								id="reviewTitle" required="required" /> <br> Content<br>
							<form:textarea id="reviewBox" path="text" required="required"></form:textarea>
							<br>
						</td>
					</tr>
				</table>

				<input class="submit" type="submit" value="Submit">
			</form:form>
		</div>
	</div>
</body>

</html>