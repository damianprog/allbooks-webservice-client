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
<link type="text/css" rel="stylesheet" href="/css/book.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>
	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<div>
			<form:form action="/reader/postReview" modelAttribute="rating"
				method="POST">
				<table>
					<tr>
						<td>
							<h3>${book.fullTitle}>Review</h3> <input type="hidden"
							name="bookName" value="${book.miniTitle}">
						</td>
					</tr>
					<tr>

						<td id="img">
							<div id="imgRateReview">
								<img src="data:image/jpeg;base64,${bookPic}">


							</div>
							<div id="reviewDesc">
								<h4 id="h3">${book.fullTitle}</h4>
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
										My Rating <input type="hidden" name="updateRating"
										value="false">
								</c:when>
								<c:otherwise>
								My Rating ${rating.rate}
								<input type="hidden" name="updateRating" value="true">
								</c:otherwise>
							</c:choose> <form:select class="rounded" path="rate">
								<form:option value="1" label="1" />
								<form:option value="2" label="2" />
								<form:option value="3" label="3" />
								<form:option value="4" label="4" />
								<form:option value="5" label="5" selected="selected" />

							</form:select></td>
						<form:hidden path="bookId" />
						<form:hidden path="readerIdentity" />
						<form:hidden path="id" />


					</tr>
					<tr>
						<td><form:form modelAttribute="readerBook">
								<c:choose>
									<c:when test="${readerBook.id == 0}">
										<br>Add this book to your books
									<input type="hidden" name="updateReaderBook" value="false">
									</c:when>
									<c:otherwise>
										<br>Current State:${readerBook.shelves}
									<input type="hidden" name="updateReaderBook" value="true">

									</c:otherwise>
								</c:choose>


								<form:hidden path="dateAdded" />
								<form:select class="rounded" path="shelves">
									<form:option value="Read" label="Read" />
									<form:option value="Currently Reading"
										label="Currently Reading" />
									<form:option value="Want to Read" label="Want to Read" />
								</form:select>
								<input type="hidden" name="postReview" value="false">
							</form:form> <input type="hidden" name="readerBookId"
							value="${readerBook.id}" /></td>

					</tr>
					<tr>
						<td><hr></td>
					</tr>
					<tr>
						<td style="padding-bottom: 15px;">What did you think?</td>
					</tr>
					<tr>
						<td>Title of Review<br> <input id="reviewTitle"
							name="title" required="required" /> <br> Content<br> <textarea
								id="reviewBox" name="text" required="required"></textarea> <br>
						</td>
					</tr>
				</table>

				<input type="submit" value="Submit">
			</form:form>
		</div>
	</div>
</body>

</html>